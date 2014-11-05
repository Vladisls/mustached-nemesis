package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.data.SoldHistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {
    
	private SalesSystemModel model;
	JTable historyTable;
	JTable orderTable;
	JScrollPane historyScrollPane;
	JScrollPane orderScrollPane;
	
	public HistoryTab(SalesSystemModel model) {
		this.model = model;
	}
	
    //public HistoryTab() {} 
    
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		// Creates new table
		createHistoryTable();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 0.7;
		panel.add(historyScrollPane, gc);
		// New table for specific orders
		createSpecificOrderTable();
		gc.gridy = 1;
		panel.add(orderScrollPane, gc);
		return panel;
	}
    
    private void createHistoryTable() {
    	//System.out.println(model.getSalesHistoryModel());
		historyTable = new JTable(model.getSalesHistoryModel());
		JTableHeader header = historyTable.getTableHeader();
		header.setReorderingAllowed(false);
		//Listener, shows order details
		historyTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int selectedRow = historyTable.getSelectedRow();
						if (selectedRow != -1)
							showOrder(model.getSalesHistoryModel().getRow(
									selectedRow));
					}
				});
		historyScrollPane = new JScrollPane(historyTable);
	}
    
	private void createSpecificOrderTable() {
		orderTable = new JTable(model.getHistoryPurchaseTableModel());
		JTableHeader header = orderTable.getTableHeader();
		header.setReorderingAllowed(false);
		orderScrollPane = new JScrollPane(orderTable);
		orderScrollPane.setVisible(false);
		orderScrollPane.setBorder(BorderFactory
				.createTitledBorder("Order details"));
	}
	
	private void showOrder(SoldHistoryItem item) {
		model.getHistoryPurchaseTableModel().clear();
		if (!orderScrollPane.isVisible())
			orderScrollPane.setVisible(true);
		for (SoldItem el : item.getSoldItems())
			model.getHistoryPurchaseTableModel().addItem(el);
	}
}