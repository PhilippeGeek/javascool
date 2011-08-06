//@deprecated voir org.javascool.tools.Jvs2Html
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.builder.doc;

import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.javasource.JavaSourceType;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.util.RGB;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 *
 * @author Philippe Vienne
 */
public class Formater {

    public static String format(String code) {
        String html = "";

        StringReader stringReader = new StringReader(code);
        JavaSource source = null;
        try {
            source = new JavaSourceParser().parse(stringReader);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
        JavaSource2HTMLConverter converter = new JavaSource2HTMLConverter();

        JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
        //options.getStyleTable().put(JavaSourceType.KEYWORD, new JavaSourceStyleEntry(RGB.ORANGE, true, false));

        StringWriter writer = new StringWriter();
        try {
            converter.convert(source, options, writer);
        } catch (IOException e) {
            //can not happen
        }
        html = writer.toString() + "";
        return html;
    }
}
