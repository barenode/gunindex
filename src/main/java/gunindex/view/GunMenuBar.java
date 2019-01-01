package gunindex.view;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import gunindex.view.event.GenericEventListener;
import gunindex.view.event.GunFileEvent;
import gunindex.view.event.GunFileEventListener;

public class GunMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private final List<GunFileEventListener> openFileListeners = new ArrayList<>();
	private final List<GunFileEventListener> saveFileListeners = new ArrayList<>();
	private final List<GenericEventListener> newFileListeners = new ArrayList<>();	
	
	public GunMenuBar() {
		super();
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		add(menu);
		
		//file
		JFileChooser fileChooser = new JFileChooser();
		JMenuItem newFile = new JMenuItem("New", createImageIcon("/images/New16.gif"));     
		newFile.setMnemonic(KeyEvent.VK_N);
		newFile.addActionListener(e -> {
			newFileListeners.stream().forEach(l -> l.onEvent(new EventObject(this)));
		});
		menu.add(newFile);
		JMenuItem openFile = new JMenuItem("Open", createImageIcon("/images/Open16.gif"));
		openFile.setMnemonic(KeyEvent.VK_O);
		openFile.addActionListener(e -> {
			int returnVal = fileChooser.showOpenDialog(GunMenuBar.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {	
				openFileListeners.stream().forEach(l -> l.onEvent(new GunFileEvent(GunMenuBar.this, fileChooser.getSelectedFile())));
			}
		});
		menu.add(openFile);
		JMenuItem saveFile = new JMenuItem("Save", createImageIcon("/images/Save16.gif"));        
		saveFile.setMnemonic(KeyEvent.VK_S);
		saveFile.addActionListener(e -> {
			int returnVal = fileChooser.showSaveDialog(GunMenuBar.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {	
				saveFileListeners.stream().forEach(l -> l.onEvent(new GunFileEvent(GunMenuBar.this, fileChooser.getSelectedFile())));
			}
		});
		menu.add(saveFile);
	}
	
	public void addOpenFileListener(GunFileEventListener listener) {
		openFileListeners.add(listener);
	}
	
	public void addSaveFileListener(GunFileEventListener listener) {
		saveFileListeners.add(listener);
	}
	
	public void addNewFileListener(GenericEventListener listener) {
		newFileListeners.add(listener);
	}
	
	private static ImageIcon createImageIcon(String path) {
		return new ImageIcon(GunMenuBar.class.getResource(path));
    }
}
