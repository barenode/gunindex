package gunindex.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import gunindex.model.Guns;
import gunindex.view.event.FilterChangedEvent;
import gunindex.view.event.FilterChangedEventListener;
import gunindex.view.event.ModelChangeEvent;
import gunindex.view.event.ModelChangeEventListener;
import gunindex.view.event.ModelUpdatedEvent;
import gunindex.view.event.ModelUpdatedEventListener;

public class GunTable extends JPanel implements ModelChangeEventListener, ModelUpdatedEventListener, FilterChangedEventListener {
	private static final long serialVersionUID = 1L;
	
	private JTable gunTable;
	private Guns guns;
	private GunTableModel tableModel;
	private TableRowSorter<GunTableModel> tableSorter;
	
	public GunTable(Guns guns) {
		super();
		this.guns = guns;
		init();
	}
	
	private void init() {		
		setLayout(new BorderLayout());		
		setBorder(BorderFactory.createLineBorder(Color.black));
		gunTable = new JTable();
		JScrollPane pane = new JScrollPane(gunTable);
		pane.setPreferredSize(new Dimension(1240, 400));
		add(pane, BorderLayout.CENTER);
		onModelChanged(new ModelChangeEvent(this, guns));
	}

	@Override
	public void onModelChanged(ModelChangeEvent e) {
		guns = e.getGuns();
		tableModel = new GunTableModel(guns);
		tableSorter = new TableRowSorter<GunTableModel>(tableModel);
		tableSorter.setSortsOnUpdates( true );
		gunTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		gunTable.setRowSorter(tableSorter);
		gunTable.setModel(tableModel);		
	}
	
	@Override
	public void onModelUpdated(ModelUpdatedEvent e) {
		tableModel.fireTableDataChanged();		
	}

	@Override
	public void onFilterChanged(FilterChangedEvent e) {
		try {
			tableSorter.setRowFilter(RowFilter.regexFilter(e.getFilter(), 1));
		} catch (RuntimeException exception) {
			tableSorter.setRowFilter(null);
			throw exception;
		}
	}
}
