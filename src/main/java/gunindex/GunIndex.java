package gunindex;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import gunindex.model.Guns;
import gunindex.persistence.GunDao;
import gunindex.view.GunMenuBar;
import gunindex.view.GunTable;
import gunindex.view.GunToolbar;
import gunindex.view.event.ErrorEvent;
import gunindex.view.event.FilterChangedEvent;
import gunindex.view.event.FilterChangedEventListener;
import gunindex.view.event.ModelChangeEvent;
import gunindex.view.event.ModelChangeEventListener;
import gunindex.view.event.ModelUpdatedEvent;
import gunindex.view.event.ModelUpdatedEventListener;

/**
 * This class servers as application controller. Receives events from view, updates model and finally notifies view accordingly. 
 * 
 * @author hylmar
 */
public class GunIndex extends JFrame implements FilterChangedEventListener {	
	private static final long serialVersionUID = 1L;

	private final GunDao gunDao = new GunDao();
	
	//model
	private Guns guns;
	
	//listeners
	private final List<ModelChangeEventListener> modelChangeEventListeners = new ArrayList<>();
	private final List<ModelUpdatedEventListener> modelUpdatedEventListeners = new ArrayList<>();
	private final List<FilterChangedEventListener> filterChangedEventListeners = new ArrayList<>();
	
	public GunIndex() {
		super();
		this.guns = initialModel();
		init();
	}	
	
	private void init() {
		setTitle("GunIndex");
		setDefaultCloseOperation(EXIT_ON_CLOSE);				
		setLayout(new BorderLayout());
		//table UI component
		GunTable gunTable = new GunTable(guns);			
		add(gunTable, BorderLayout.CENTER);
		modelChangeEventListeners.add(gunTable);
		modelUpdatedEventListeners.add(gunTable);
		filterChangedEventListeners.add(gunTable);
		
		//header
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());		
		add(header, BorderLayout.NORTH);
		//tool bar UI component
		GunToolbar toolbar = new GunToolbar();
		header.add(toolbar, BorderLayout.SOUTH);		
		toolbar.addNewGunListener(e -> {
			guns.newGun();
			onModelUpdated();
		});
		toolbar.addRemoveGunsListener(e -> {
			guns.removeSelected();
			onModelUpdated();
		});
		toolbar.addFilterChangedEventListener(this);
		
		//menu bar 
		GunMenuBar menuBar = new GunMenuBar();
		menuBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		header.add(menuBar, BorderLayout.NORTH);		
		//menu bar event handlers
		menuBar.addNewFileListener(e -> {
			guns = new Guns();
			onModelChanged();
		});
		menuBar.addOpenFileListener(e -> {
			try {
				guns = (gunDao.load(new FileInputStream(e.getFile())));
				onModelChanged();
			} catch (Exception exception) {
				onError(new ErrorEvent(this, exception));
			}
		});
		menuBar.addSaveFileListener(e -> {
			try {
				gunDao.save(guns, new FileOutputStream(e.getFile()));
			} catch (Exception exception) {
				onError(new ErrorEvent(this, exception));
			}
		});		
		
		pack();
	}	

	private void onError(ErrorEvent e) {
		 JOptionPane.showConfirmDialog(null, e.getException().getMessage(), "Error", JOptionPane.DEFAULT_OPTION);		
	}	
	
	private void onModelUpdated() {
		modelUpdatedEventListeners.stream().forEach(l -> l.onModelUpdated(new ModelUpdatedEvent(this)));
	}

	private void onModelChanged() {
		modelChangeEventListeners.stream().forEach(l -> l.onModelChanged(new ModelChangeEvent(this, guns)));
	}
	
	@Override
	public void onFilterChanged(FilterChangedEvent e) {
		try {
			//catch regexp parsing exceptions and notify user
			filterChangedEventListeners.stream().forEach(l -> l.onFilterChanged(e));
		} catch (Exception exception) {
			onError(new ErrorEvent(this, exception));
		}
	}
	
	private Guns initialModel() {
		try {
			return gunDao.load(GunIndex.class.getResourceAsStream("guns.txt"));
		} catch (Exception e) {
			onError(new ErrorEvent(this, e));
			return new Guns();
		}
	}

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GunIndex().setVisible(true);
			}			
		});
	}
}
