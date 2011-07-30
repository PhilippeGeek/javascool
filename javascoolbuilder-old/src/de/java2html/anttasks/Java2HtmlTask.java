package de.java2html.anttasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSourceConverterProvider;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.options.ConversionOptionsUtilities;
import de.java2html.options.HorizontalAlignment;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.options.JavaSourceStyleTable;
import de.java2html.util.IoUtilities;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.util.FileNameMapper;
import org.apache.tools.ant.util.GlobPatternMapper;
import org.apache.tools.ant.util.SourceFileScanner;

/**
 * Runs the java2html converter as a task inside the well known build tool
 * "ant" (see ant.apache.org).
 * 
 * Thanks to <a href="mailto:markus@jave.de">Markus Gebhard</a>, the author
 * of java2html itself. I contribute this code to the project under the same
 * license as java2html.
 * 
 * For an example for a <code>build.xml</code> containing this task have a
 * look at the docs/anttask/ folder.
 * 
 * @author <a href="mailto:mbohlen@mbohlen.de">Matthias Bohlen</a>
 * @author <a href="mailto:markus@jave.de">Markus Gebhard</a>
 */
public class Java2HtmlTask extends MatchingTask {
  private String style = JavaSourceConversionOptions.getDefault().getStyleTable().getName();
  private File srcDir;
  private File destDir;
  private boolean overwrite = false;
  private String outputFormat = JavaSourceConverterProvider.getDefaultConverterName();
  private int tabs = JavaSourceConversionOptions.getDefault().getTabSize();
  private boolean showLineNumbers = JavaSourceConversionOptions.getDefault().isShowLineNumbers();
  private boolean showDefaultTitle = false;
  private boolean addLineAnchors = false;
  private String lineAnchorPrefix = "";
  private boolean showTableBorder = false;
  private boolean showFileName = false;
  private boolean includeDocumentHeader = true;
  private boolean includeDocumentFooter = true;
  private boolean useShortFileName = false;
  private String horizontalAlignment = JavaSourceConversionOptions
      .getDefault().getHorizontalAlignment().getName();


  /**
   * Sets the directory where the Java sources are stored.
   * 
   * @param srcDir
   *          directory name
   */
  public void setSrcDir(File srcDir) {
    this.srcDir = srcDir;
  }

  /**
   * Sets the directory where the output is written.
   * 
   * @param destDir
   *          directory name
   */
  public void setDestDir(File destDir) {
    this.destDir = destDir;
  }

  /**
   * Sets the output format.
   * 
   * @param outputFormat
   *          the output format identifier ("html", "xhtml", "latex")
   */
  public void setOutputFormat(String outputFormat) {
    this.outputFormat = outputFormat;
  }

  /**
   * @see org.apache.tools.ant.Task#execute()
   */
  public void execute() throws BuildException {
    if (srcDir == null) {
      // We directly change the user variable, because it
      // shouldn't lead to problems
      srcDir = project.resolveFile(".");
    }

    // find the files/directories
    DirectoryScanner dirScanner = getDirectoryScanner(srcDir);

    // get a list of files to work on
    String[] allSourceFiles = dirScanner.getIncludedFiles();

    IJavaSourceConverter converter = getConverter();
    JavaSourceConversionOptions options = getConversionOptions();
    SourceFileScanner sourceScanner = new SourceFileScanner(this);

    String[] sourceFilesToProcess;
    if (isOverwrite()) {
      sourceFilesToProcess = allSourceFiles;
    }
    else {
      FileNameMapper sourceToOutMapper = new GlobPatternMapper();
      sourceToOutMapper.setFrom("*");
      sourceToOutMapper.setTo("*." + converter.getMetaData().getDefaultFileExtension());
      sourceFilesToProcess = sourceScanner.restrict(allSourceFiles, srcDir, destDir, sourceToOutMapper);
    }

    if (sourceFilesToProcess.length > 0) {
      String files = (sourceFilesToProcess.length == 1 ? " file" : " files");
      log("Converting " + sourceFilesToProcess.length + files, Project.MSG_INFO);
    }

    for (int i = 0; i < sourceFilesToProcess.length; ++i) {
      process(sourceFilesToProcess[i], options, converter);
    }
  }

  /**
   * Returns a new conversions options object filled in from the Ant task.
   * 
   * @return a new conversions options object
   */
  private JavaSourceConversionOptions getConversionOptions() {
    JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
    options.setTabSize(tabs);
    options.setShowFileName(isShowFileName());
    options.setShowTableBorder(isShowTableBorder());
    options.setShowLineNumbers(isShowLineNumbers());
    options.setAddLineAnchors(isAddLineAnchors());
    options.setLineAnchorPrefix(lineAnchorPrefix);

    JavaSourceStyleTable table = JavaSourceStyleTable.getPredefinedTable(style);
    if (table == null) {
      throw new BuildException("Specified style table '"
          + style
          + "' does not exist "
          + " - valid values are: "
          + ConversionOptionsUtilities.getPredefinedStyleTableNameString());
    }
    options.setStyleTable(table);

    HorizontalAlignment alignment = HorizontalAlignment.getByName(horizontalAlignment);
    if (alignment == null) {
      throw new BuildException("Specified alignment '" //$NON-NLS-1$
          + horizontalAlignment
          + "'does not exist - valid values are: " //$NON-NLS-1$
          + ConversionOptionsUtilities.getAvailableHorizontalAlignmentNames());
    }
    options.setHorizontalAlignment(alignment);

    return options;
  }

  private IJavaSourceConverter getConverter() throws BuildException {
    IJavaSourceConverter converter = JavaSourceConverterProvider.getJavaSourceConverterByName(outputFormat);
    if (converter == null) {
      throw new BuildException("unknown output file format: " + outputFormat); //$NON-NLS-1$
    }
    return converter;
  }

  /**
   * Convert a Java source to HTML, XHTML or LaTex.
   * 
   * @param sourcefileName
   *          the name of the file to convert
   * @param options
   *          conversion options
   * @param converter
   *          the converter to use
   */
  private void process(String sourcefileName, JavaSourceConversionOptions options, IJavaSourceConverter converter)
      throws BuildException {
    log("Converting '" + sourcefileName + "'", Project.MSG_VERBOSE); //$NON-NLS-1$ //$NON-NLS-2$
    JavaSourceParser parser = new JavaSourceParser(options);
    JavaSource source;
    File inFile = new File(srcDir, sourcefileName);
    try {
      source = parser.parse(inFile);
    }
    catch (IOException e1) {
      throw new BuildException("Unable to parse file " + inFile.getName(), e1); //$NON-NLS-1$
    }

    File outFile = createOutputFile(sourcefileName, converter);
    ensureDirectoryFor(outFile);
    Writer writer = null;
    try {
      writer = new FileWriter(outFile);
    }
    catch (Exception e) {
      throw new BuildException("Error opening output file " + outFile.getName(), e); //$NON-NLS-1$
    }

    String title = ""; //$NON-NLS-1$
    if (isShowDefaultTitle()) {
      title = sourcefileName.replace('\\', '/');
    }
    try {
      if (isIncludeDocumentHeader()) {
        converter.writeDocumentHeader(writer, options, title);
      }
      converter.convert(source, options, writer);
      if (isIncludeDocumentFooter()) {
        converter.writeDocumentFooter(writer, options);
      }
    }
    catch (Exception e) {
      throw new BuildException("Error writing output to " + outFile.getName(), e); //$NON-NLS-1$
    }
    finally {
      IoUtilities.close(writer);
    }

    log("Output: " + outFile, Project.MSG_VERBOSE); //$NON-NLS-1$
  }

  private File createOutputFile(String sourcefileName, IJavaSourceConverter converter) {
    String fileNamePrefix = sourcefileName;
    if (isUseShortFileName()) {
      int index = sourcefileName.lastIndexOf('.');
      if (index != -1) {
        fileNamePrefix = sourcefileName.substring(0, index);
      }
    }
    return new File(destDir, fileNamePrefix + "." + converter.getMetaData().getDefaultFileExtension());
  }

  /**
   * Sets the number of spaces per tab.
   * 
   * @param tabs
   */
  public void setTabs(int tabs) {
    this.tabs = tabs;
  }

  /**
   * Sets the table name for the output style, e.g. "kawa" or "eclipse".
   * 
   * @see JavaSourceStyleTable
   */
  public void setStyle(String style) {
    this.style = style;
  }

  /**
   * Creates directories as needed.
   * 
   * @param targetFile
   *          a <code>File</code> whose parent directories need to exist
   * @exception BuildException
   *              if the parent directories couldn't be created
   */
  private void ensureDirectoryFor(File targetFile) throws BuildException {
    File directory = new File(targetFile.getParent());
    if (!directory.exists()) {
      if (!directory.mkdirs()) {
        throw new BuildException("Unable to create directory: " + directory.getAbsolutePath());
      }
    }
  }

  private boolean isShowFileName() {
    return showFileName;
  }

  private boolean isShowLineNumbers() {
    return showLineNumbers;
  }

  private boolean isShowDefaultTitle() {
    return showDefaultTitle;
  }

  private boolean isShowTableBorder() {
    return showTableBorder;
  }

  public void setShowFileName(boolean showFileName) {
    this.showFileName = showFileName;
  }

  public void setShowLineNumbers(boolean showLineNumbers) {
    this.showLineNumbers = showLineNumbers;
  }

  public void setShowDefaultTitle(boolean showDefaultTitle) {
    this.showDefaultTitle = showDefaultTitle;
  }

  public void setShowTableBorder(boolean showTableBorder) {
    this.showTableBorder = showTableBorder;
  }

  private boolean isIncludeDocumentFooter() {
    return includeDocumentFooter;
  }

  private boolean isIncludeDocumentHeader() {
    return includeDocumentHeader;
  }

  public void setIncludeDocumentFooter(boolean includeDocumentFooter) {
    this.includeDocumentFooter = includeDocumentFooter;
  }

  public void setIncludeDocumentHeader(boolean includeDocumentHeader) {
    this.includeDocumentHeader = includeDocumentHeader;
  }

  private boolean isAddLineAnchors() {
    return addLineAnchors;
  }

  public void setAddLineAnchors(boolean addLineAnchors) {
    this.addLineAnchors = addLineAnchors;
  }

  public void setLineAnchorPrefix(String string) {
    lineAnchorPrefix = string;
  }

  public void setHorizontalAlignment(String horizontalAlignment) {
    this.horizontalAlignment = horizontalAlignment;
  }

  private boolean isUseShortFileName() {
    return useShortFileName;
  }

  public void setUseShortFileName(boolean useShortFileName) {
    this.useShortFileName = useShortFileName;
  }

  private boolean isOverwrite() {
    return overwrite;
  }

  public void setOverwrite(boolean overwrite) {
    this.overwrite = overwrite;
  }
}