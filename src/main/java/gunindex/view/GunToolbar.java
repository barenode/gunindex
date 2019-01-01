package gunindex.view;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gunindex.view.event.FilterChangedEvent;
import gunindex.view.event.FilterChangedEventListener;
import gunindex.view.event.GenericEventListener;
import gunindex.view.event.GunFileEvent;
import gunindex.view.event.GunFileEventListener;

public class GunToolbar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
//	private final List<GunFileEventListener> openFileListeners = new ArrayList<>();
//	private final List<GunFileEventListener> saveFileListeners = new ArrayList<>();
//	private final List<GenericEventListener> newFileListeners = new ArrayList<>();	
	private final List<GenericEventListener> newGunListeners = new ArrayList<>();
	private final List<GenericEventListener> removeGunsListeners = new ArrayList<>();
	private final List<FilterChangedEventListener> filterChangedEventListeners = new ArrayList<>();
	
	public GunToolbar() {
		super();
		setLayout(new FlowLayout(FlowLayout.LEADING));		
		
		//filter
        JLabel filterLabel = new JLabel("Name: ", SwingConstants.TRAILING);
        add(filterLabel);
        JTextField filterText = new JTextField();
        filterText.setColumns(20);
        filterText.getDocument().addDocumentListener(
        	new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {
					filterChanged(filterText.getText());
				}
				
				public void insertUpdate(DocumentEvent e) {
					filterChanged(filterText.getText());
				}
				
				public void removeUpdate(DocumentEvent e) {
					filterChanged(filterText.getText());
				}
        });
        filterLabel.setLabelFor(filterText);
        add(filterText);
		add(new JSeparator(SwingConstants.VERTICAL));
		
        //add remove
		JButton newGunButton = new JButton("New Gun", createImageIcon("/images/New16.gif"));        
		newGunButton.addActionListener(e -> {
			newGunListeners.stream().forEach(l -> l.onEvent(new EventObject(this)));
		});
		add(newGunButton);		
		JButton removeGunButton = new JButton("Remove Guns", createImageIcon("/images/Remove16.gif"));        
		removeGunButton.addActionListener(e -> {
			removeGunsListeners.stream().forEach(l -> l.onEvent(new EventObject(this)));
		});
		add(removeGunButton);
		add(new JSeparator(SwingConstants.VERTICAL));
		
//		//file
//		JFileChooser fileChooser = new JFileChooser();
//		JButton newButton = new JButton("New File...", createImageIcon("/images/New16.gif"));        
//		newButton.addActionListener(e -> {
//			newFileListeners.stream().forEach(l -> l.onEvent(new EventObject(this)));
//		});
//		add(newButton);
//		JButton openButton = new JButton("Open a File...", createImageIcon("/images/Open16.gif"));        
//		openButton.addActionListener(e -> {
//			int returnVal = fileChooser.showOpenDialog(GunToolbar.this);
//			if (returnVal == JFileChooser.APPROVE_OPTION) {	
//				openFileListeners.stream().forEach(l -> l.onEvent(new GunFileEvent(GunToolbar.this, fileChooser.getSelectedFile())));
//			}
//		});
//		add(openButton);
//		JButton saveButton = new JButton("Save a File...", createImageIcon("/images/Save16.gif"));        
//		saveButton.addActionListener(e -> {
//			int returnVal = fileChooser.showSaveDialog(GunToolbar.this);
//			if (returnVal == JFileChooser.APPROVE_OPTION) {	
//				saveFileListeners.stream().forEach(l -> l.onEvent(new GunFileEvent(GunToolbar.this, fileChooser.getSelectedFile())));
//			}
//		});
//		add(saveButton);
	}
	
	private void filterChanged(String filter) {
		filterChangedEventListeners.stream().forEach(l -> l.onFilterChanged(new FilterChangedEvent(GunToolbar.this, filter)));
	}
	
//	public void addOpenFileListener(GunFileEventListener listener) {
//		openFileListeners.add(listener);
//	}
//	
//	public void addSaveFileListener(GunFileEventListener listener) {
//		saveFileListeners.add(listener);
//	}
//	
//	public void addNewFileListener(GenericEventListener listener) {
//		newFileListeners.add(listener);
//	}
	
	public void addNewGunListener(GenericEventListener listener) {
		newGunListeners.add(listener);
	}
	
	public void addRemoveGunsListener(GenericEventListener listener) {
		removeGunsListeners.add(listener);
	}
	
	public void addFilterChangedEventListener(FilterChangedEventListener listener) {
		filterChangedEventListeners.add(listener);
	}
	
	private static ImageIcon createImageIcon(String path) {
		return new ImageIcon(GunToolbar.class.getResource(path));
    }
}
