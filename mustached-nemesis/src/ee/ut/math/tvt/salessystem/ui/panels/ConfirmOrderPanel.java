package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldHistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class ConfirmOrderPanel extends JPanel {
	private static final long serialVersionUID = -8013931014111078167L;
	private JLabel orderSumLabelText, orderSumLabelValue, payAmountLabel, changeLabelText, changeLabelValue;
	private JTextField payAmountField;
	private JButton cancelButton, acceptButton;
	private static final Logger log = Logger.getLogger(ConfirmOrderPanel.class);
	
	//Sales system model
	SalesSystemModel model;
	public ConfirmOrderPanel(SalesSystemModel model) {
		//Init model
		this.model = model;
		//vv
		initComp();
		setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraints();
		//Add labels
		add(orderSumLabelText, gc);
		//Add the sum
		gc.gridx = 1;
		add(orderSumLabelValue, gc);
		//Add amount needed to be paid
		gc.gridx = 0;
		gc.gridy = 1;
		add(payAmountLabel, gc);
		//Pay field
		gc.gridx = 1;
		add(payAmountField, gc);
		//Change label...
		gc.gridy = 2;
		gc.gridx = 0;
		add(changeLabelText, gc);
		//Change value
		gc.gridx = 1;
		add(changeLabelValue, gc);
		//Accept button
		gc.gridx = 0;
		gc.gridy = 3;
		add(acceptButton, gc);
		//Cancel button
		gc.gridx = 1;
		add(cancelButton, gc);
	}
	
	private void initComp() {
		//Total sum
		int sum = 0;
		for (SoldItem el : model.getCurrentPurchaseTableModel().getTableRows())
			sum += el.getSum();
		//Labeeeels
		orderSumLabelText = new JLabel("Total sum: ");
		payAmountLabel = new JLabel("Paid:");
		changeLabelText = new JLabel("Change: ");
		orderSumLabelValue = new JLabel(String.valueOf(sum));
		changeLabelValue = new JLabel();
		//More initializing
		payAmountField = new JTextField();
		cancelButton = new JButton("Cancel");
		acceptButton = new JButton("Accept");
		//Listeners
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSaleEventHandler();
			}
		});
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptSaleEventHandler();
			}
		});
		payAmountField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcChange();
			}
		});
	}
	
	protected void cancelSaleEventHandler() {
		log.info("Sale cancelled");
		Window win = SwingUtilities.getWindowAncestor(cancelButton);
		win.setVisible(false);
		}
	protected void acceptSaleEventHandler() {
		try {
			String tmp = changeLabelValue.getText();
			if (tmp.length() == 0) {
				calcChange();
				tmp = changeLabelValue.getText();
			}
			if (Double.parseDouble(tmp) >= 0) {
				log.info("Sale complete");
				//Adds to the history
				model.getSalesHistoryModel().addItem(
						new SoldHistoryItem(getCurrentDate(), getCurrentTime(),
								model.getCurrentPurchaseTableModel()
								.getTableRows()));
				//Substracts from the warehouse
				model.getWarehouseTableModel().substractStock(
						model.getCurrentPurchaseTableModel().getTableRows());
				//Closes
				Window win = SwingUtilities.getWindowAncestor(acceptButton);
				win.setVisible(false);
			} else
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			changeLabelValue.setText("Invalid input");
		}
	}
	private void calcChange() throws NumberFormatException{
		double change = -1;
		try{
			change = Double.parseDouble(payAmountField.getText())
					- Double.parseDouble(orderSumLabelValue.getText());
			if (change < 0)
				throw new NumberFormatException();
			else
				changeLabelValue.setText(String.format("%.2f", change).replace(
						",", "."));
		}catch (NumberFormatException e) {
			changeLabelValue.setText("Invalid input");
		}
	}
	public String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(date).toString();
	}
	public String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date).toString();
	}
	
	private GridBagConstraints getConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		return gc;
	}

	
}