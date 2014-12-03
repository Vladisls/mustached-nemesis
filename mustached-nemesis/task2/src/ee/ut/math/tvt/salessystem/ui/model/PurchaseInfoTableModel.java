package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);

	private SalesSystemModel model;

	Sale currentSale;

	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
		currentSale = new Sale(new ArrayList<SoldItem>());
	}

	public PurchaseInfoTableModel(SalesSystemModel model) {
		this();
		this.model = model;
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : currentSale.getSoldItems()) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	public SoldItem getForStockItem(long stockItemId) {
		for (SoldItem item : currentSale.getSoldItems()) {
			if (item.getStockItem().getId().equals(stockItemId)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Add new StockItem to table.
	 */
	public void addItem(final StockItem stockItem, int quantity)
			throws SalesSystemException {

		SoldItem existingItem = getForStockItem(stockItem.getId());

		if (existingItem != null) {
			int totalQuantity = existingItem.getQuantity() + quantity;
			validateQuantityInStock(stockItem, totalQuantity);
			existingItem.setQuantity(totalQuantity);

			log.debug("Found existing item " + existingItem.getName()
					+ " increased quantity by " + quantity);

		} else {
			validateQuantityInStock(stockItem, quantity);
			currentSale.addSoldItem(new SoldItem(stockItem, quantity));
			log.debug("Added " + stockItem.getName() + " quantity of "
					+ quantity);
		}

		fireTableDataChanged();
	}

	/**
	 * Returns the total sum that needs to be paid for all the items in the
	 * basket.
	 */
	public double getTotalPrice() {
		double price = 0.0;
		for (SoldItem item : currentSale.getSoldItems()) {
			price += item.getSum();
		}
		return price;
	}

	private void validateQuantityInStock(StockItem item, int quantity)
			throws SalesSystemException {

		if (!model.getWarehouseTableModel().hasEnoughInStock(item, quantity)) {
			log.info(" -- not enough in stock!");
			throw new SalesSystemException();
		}

	}

	public List<SoldItem> getTableRows() {
		return currentSale.getSoldItems();
	}

	public void setSale(Sale sale) {
		currentSale = sale;
		fireTableDataChanged();
	}

	public Sale getSale() {
		return currentSale;
	}

}
