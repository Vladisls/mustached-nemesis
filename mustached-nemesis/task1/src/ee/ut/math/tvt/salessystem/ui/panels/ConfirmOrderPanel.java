package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.SoldHistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

public class ConfirmOrderPanel extends JPanel {
	private static final long serialVersionUID = -8013931014111078167L;
	private JLabel orderSumLabelText, orderSumLabelValue, payAmountLabel,
			changeLabelText, changeLabelValue;
	private JTextField payAmountField;
	private JButton cancelButton, acceptButton;
	private static final Logger log = Logger.getLogger(ConfirmOrderPanel.class);

	// Sales system model
	SalesSystemModel model;

	public ConfirmOrderPanel(SalesSystemModel model) {
		// Init model
		this.model = model;
		// vv
		initComp();
		setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraints();
		// Add labels
		add(orderSumLabelText, gc);
		// Add the sum
		gc.gridx = 1;
		add(orderSumLabelValue, gc);
		// Add amount needed to be paid
		gc.gridx = 0;
		gc.gridy = 1;
		add(payAmountLabel, gc);
		// Pay field
		gc.gridx = 1;
		add(payAmountField, gc);
		// Change label...
		gc.gridy = 2;
		gc.gridx = 0;
		add(changeLabelText, gc);
		// Change value
		gc.gridx = 1;
		add(changeLabelValue, gc);
		// Accept button
		gc.gridx = 0;
		gc.gridy = 3;
		add(acceptButton, gc);
		// Cancel button
		gc.gridx = 1;
		add(cancelButton, gc);
	}

	private void initComp() {
		// Labeeeels
		orderSumLabelText = new JLabel("Total sum: ");
		payAmountLabel = new JLabel("Payment:");
		changeLabelText = new JLabel("Change: ");
		// orderSumLabelValue = new JLabel(String.valueOf(sum));
		orderSumLabelValue = new JLabel(String.valueOf(model
				.getCurrentPurchaseTableModel().getOrderTotal()));
		changeLabelValue = new JLabel();
		// More initializing
		payAmountField = new JTextField();
		cancelButton = new JButton("Cancel");
		acceptButton = new JButton("Accept");
		// Listeners
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
		payAmountField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
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
			if (Double.parseDouble(changeLabelValue.getText()) >= 0) {
				// Finalize sale - update history + db
				finalizeSale();
				Window win = SwingUtilities.getWindowAncestor(acceptButton);
				win.setVisible(false);
			} else
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			changeLabelValue.setText("Invalid input");
		}
	}

	private void finalizeSale() {
		List<SoldItem> soldItems = model.getCurrentPurchaseTableModel()
				.getTableRows();
		SoldHistoryItem historyItem = new SoldHistoryItem(getCurrentDate(),
				getCurrentTime(), soldItems);
		StockItem dbItem;
		//Update database
		Session session = HibernateUtil.currentSession();
		session.getTransaction().begin();
		//Add history item
		session.persist(historyItem);
		for (SoldItem el : soldItems) {
			//Add solditems to db
			session.persist(el);
			//Change stock quantities
			dbItem = (StockItem) session.get(StockItem.class, el.getStockItem()
					.getId());
			dbItem.setQuantity(dbItem.getQuantity() - el.getQuantity());
		}
		session.getTransaction().commit();
		//Update local info
		//If sale is successful add it to history
		model.getSalesHistoryModel().addItem(historyItem);
		log.info("Sale complete!");
	}

	private void calcChange() throws NumberFormatException {
		double change = -1;
		try {
			change = Double.parseDouble(payAmountField.getText())
					- Double.parseDouble(orderSumLabelValue.getText());
			if (change < 0)
				throw new NumberFormatException();
			else
				changeLabelValue.setText(String.format("%.2f", change).replace(
						",", "."));
		} catch (NumberFormatException e) {
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