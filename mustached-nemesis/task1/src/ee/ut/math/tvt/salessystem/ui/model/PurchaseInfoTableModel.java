package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);

	private SalesSystemModel model;

	public PurchaseInfoTableModel(SalesSystemModel model) {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
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

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	/**
	 * Add new StockItem to table.
	 */
	public void addItem(final SoldItem item) throws SalesSystemException {
		/**
		 * XXX In case such stockItem already exists increase the quantity of
		 * the existing stock.
		 */
		SoldItem onListItem = getItemByStockId(item.getStockItem().getId());
		if (onListItem != null) {
			int totalQuantity = onListItem.getQuantity() + item.getQuantity();
			validateQuantityInStock(item.getStockItem(), totalQuantity);
			onListItem.setQuantity(totalQuantity);
			log.debug("Existing item " + onListItem.getName()
					+ ", increased quantity by " + item.getQuantity());
		}

		else {
			validateQuantityInStock(item.getStockItem(), item.getQuantity());
			rows.add(item);
			log.debug("Added item " + item.getName() + ", quantity of "
					+ item.getQuantity());
		}

		fireTableDataChanged();
	}

	public double getSum() {
		double sum = 0;
		for (SoldItem el : rows)
			sum += el.getSum();
		return sum;
	}

	public SoldItem getItemByStockId(final long id) {
		for (final SoldItem item : rows) {
			if (item.getStockItem().getId() == id)
				return item;
		}
		return null;
	}

	private void validateQuantityInStock(StockItem item, int quantity)
			throws SalesSystemException {
		if (!model.getWarehouseTableModel().hasEnoughInStock(item, quantity)) {
			log.info(" -- not enough in stock!");
			throw new SalesSystemException();
		}
	}
}
