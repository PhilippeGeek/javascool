package org.javascool.gui.editor;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.javascool.widgets.TabbedPane;

/** The JVSFileTabs
 * A powerful JVSTabs to manage a multi-file editing. It only support JVSFile.
 */
public class JVSEditorsPane extends TabbedPane implements PropertyChangeListener, FileKit {

	private static final long serialVersionUID = -9098509565334829901L;

	private static JVSEditorsPane instance;

	public static JVSEditorsPane getInstance(){
		if(instance==null){
			instance=new JVSEditorsPane();
		}
		return instance;
	}

	protected JVSEditorsPane(){
		super();
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.EditorTabs#getEditorForFile(org.javascool.gui.editor.JVSFileReferance)
	 */
	@Override
	public JVSEditor getEditorForFile(FileReference file){
		for(int i=0;i<this.getTabCount();i++)
			if(this.getComponentAt(i) instanceof JVSEditor)
				if(file.equals(((EditorKit) this.getComponentAt(i)).getFile()))
					return ((JVSEditor) this.getComponentAt(i));
		return null;
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.EditorTabs#getOpenedFiles()
	 */
	@Override
	public ArrayList<FileReference> getOpenedFiles(){
		ArrayList<FileReference> al=new ArrayList<FileReference>();
		for(int i=0;i<this.getTabCount();i++){
			Component co=this.getComponentAt(i);
			if(co instanceof JVSEditor)
				al.add(((EditorKit) co).getFile());
		}
		return al;
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.EditorTabs#openFile(org.javascool.gui.editor.JVSFileReferance)
	 */
	@Override
	public boolean openFile(FileReference file){
		if(getOpenedFiles().contains(file)){
			setSelectedComponent(getEditorForFile(file));
			return false;
		}
		JVSEditor editor=new JVSEditor(file);
		((JVSEditor)add(editor)).addPropertyChangeListener("name", this);
		revalidate();
		repaint();
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.EditorTabs#saveAllFiles()
	 */
	@Override
	public boolean saveAllFiles(){
		boolean r=true;
		for(int i=0;i<this.getTabCount()&&r;i++){
			Component co=this.getComponentAt(i);
			if(co instanceof JVSEditor)
				r=r&&((EditorKit)co).saveBeforeClose();
			if(r)
				this.removeTabAt(i);
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.EditorTabs#isAllFilesSaved()
	 */
	@Override
	public boolean isAllFilesSaved(){
		for(int i=0;i<this.getTabCount();i++){
			Component co=this.getComponentAt(i);
			if(co instanceof JVSEditor)
				if(((EditorKit)co).hasToSave())
					return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.EditorTabs#saveCurrentFile()
	 */
	@Override
	public boolean saveCurrentFile() {
		if(getCurrentEditor().save())
			return true;
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.EditorTabs#getCurrentEditor()
	 */
	@Override
	public EditorKit getCurrentEditor(){
		if(getComponentAt(getSelectedIndex()) instanceof JVSEditor)
			return ((EditorKit)getComponentAt(getSelectedIndex()));
		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if(pce.getSource() instanceof JVSEditor&&pce.getPropertyName().equals("name")){
			setTitleAt(this.indexOfComponent((JVSEditor)pce.getSource()), ((EditorKit)pce.getSource()).getName());
		}
	}

	@Override
	public boolean saveFileAtIndex(int index) {
		Component co=this.getComponentAt(index);
		if(co instanceof JVSEditor)
			return ((EditorKit)co).saveBeforeClose();
		return true;
	}
	
	public boolean isTabClosable(int tabIndex) {
		if(getTabCount()<2)
			return false;
		return saveFileAtIndex(tabIndex);
	}

	@Override
	public boolean saveAsCurrentFile() {
		if(getCurrentEditor().saveAs())
			return true;
		return false;
	}

	@Override
	public boolean closeCurrentFile() {
		if(getCurrentEditor()!=null&&getTabCount()>1){
			removeTabAt(indexOfComponent((Component) getCurrentEditor()));
			return true;
		}
		return false;
	}
}
