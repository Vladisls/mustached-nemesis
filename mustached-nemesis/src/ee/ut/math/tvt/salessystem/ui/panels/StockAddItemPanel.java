package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class StockAddItemPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private SalesSystemModel model;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel priceLabel;
	private JLabel quantityLabel;
	private JTextField add_id;
	private JTextField add_name;
	private JTextField add_price;
	private JTextField add_quantity;
	private JButton accept;
	private JButton clear;;
	private static final Logger log = Logger.getLogger(StockAddItemPanel.class);
	
	public StockAddItemPanel(SalesSystemModel model) {
		this.model = model;
		initComp();
		setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraints();
		//Adds id
		add(idLabel, gc);
		gc.gridx = 1;
		add(add_id, gc);
		//Adds name
		gc.gridx = 0;
		gc.gridy = 1;
		add(nameLabel, gc);
		gc.gridx = 1;
		add(add_name, gc);
		//Adds price
		gc.gridx = 0;
		gc.gridy = 2;
		add(priceLabel, gc);
		gc.gridx = 1;
		add(add_price, gc);
		//Adds quantity
		gc.gridx = 0;
		gc.gridy = 3;
		add(quantityLabel, gc);
		gc.gridx = 1;
		add(add_quantity, gc);
		//Adds buttons
		gc.ipady = 10;
		gc.gridx = 0;
		gc.gridy = 4;
		add(accept, gc);
		gc.gridx = 1;
		add(clear, gc);
	}
	private void initComp() {
		//Labels
		idLabel = new JLabel("Product ID:");
		nameLabel = new JLabel("Product name:");
		priceLabel = new JLabel("Product price:");
		quantityLabel = new JLabel("Product quantity:");
		//Textfields
		add_id = new JTextField();
		add_name = new JTextField();
		add_price = new JTextField();
		add_quantity = new JTextField();
		accept = new JButton("Accept");
		clear = new JButton("Clear");
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptEventHandler();
			}
		});
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearEventHandler();
			}
		});
	}
	
	private void acceptEventHandler() {
		StockItem si = null;
		try {
			//Gets id, checks if in use
			long id = Long.parseLong(add_id.getText());
			if (id < 0)
				id = -id;
			//Gets name
			String name = add_name.getText();
			//Gets price
			double price = Double.parseDouble(add_price.getText());
			if (price < 0)
				price = -price;
			//Gets quantity
			int quantity = Integer.parseInt(add_quantity.getText());
			if (quantity < 0)
				quantity = -quantity;
			si = new StockItem(id, name, price, quantity);
		} catch (NumberFormatException e) {
			log.info("Invalid input");
		}
		if (si != null) {
			//Finalize
			model.getWarehouseTableModel().addItem(si);
			log.info("Item added to stock");
			Window win = SwingUtilities.getWindowAncestor(accept);
			win.setVisible(false);
		}
	}
	
	private void clearEventHandler() {
		add_id.setText("");
		add_name.setText("");
		add_price.setText("");
		add_quantity.setText("");
		log.info("Fields cleared");
		}
	
	private GridBagConstraints getConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		return gc;
		}
}
