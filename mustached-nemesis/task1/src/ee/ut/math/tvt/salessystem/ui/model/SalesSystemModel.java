package ee.ut.math.tvt.salessystem.ui.model;

import javax.swing.DefaultComboBoxModel;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

	private static final Logger log = Logger.getLogger(SalesSystemModel.class);

	// Warehouse model
	private StockTableModel warehouseTableModel;

	// Current shopping cart model
	private PurchaseInfoTableModel currentPurchaseTableModel;

	// History model
	private SalesHistoryModel salesHistoryModel;

	// Model for viewing specific order info on history tab
	private PurchaseInfoTableModel historyPurchaseTableModel;

	// Sales controller
	private final SalesDomainController domainController;

	// ComboBox model
	private DefaultComboBoxModel<String> salesComboModel;

	/**
	 * Construct application model.
	 * 
	 * @param domainController
	 *            Sales domain controller.
	 */
	public SalesSystemModel(SalesDomainController domainController) {
		this.domainController = domainController;

		warehouseTableModel = new StockTableModel();
		currentPurchaseTableModel = new PurchaseInfoTableModel(this);
		historyPurchaseTableModel = new PurchaseInfoTableModel(this);
		salesHistoryModel = new SalesHistoryModel();

		// populate stock model with data from the warehouse
		warehouseTableModel.populateWithData(domainController
				.loadWarehouseState());

		// populate stock model with data from the warehouse
		salesHistoryModel.populateWithData(domainController
				.loadSaleHistoryState());

		// create and populate combobox model with warehouse
		salesComboModel = new DefaultComboBoxModel<String>(
				warehouseTableModel.getAllNames());

	}
	
	//Testing
	public SalesSystemModel() {
		this.domainController = new SalesDomainControllerImpl();
		warehouseTableModel = new StockTableModel();
	}

	public StockTableModel getWarehouseTableModel() {
		return warehouseTableModel;
	}

	public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
		return currentPurchaseTableModel;
	}

	public PurchaseInfoTableModel getHistoryPurchaseTableModel() {
		return historyPurchaseTableModel;
	}

	public SalesHistoryModel getSalesHistoryModel() {
		return salesHistoryModel;
	}

	public DefaultComboBoxModel<String> getSalesComboBoxModel() {
		return salesComboModel;
	}

}
