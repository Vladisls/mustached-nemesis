package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public StockTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity" });
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with same
	 * id, then existing item's quantity will be increased.
	 * 
	 * @param stockItem
	 */
	public boolean addItem(final StockItem stockItem) {
		try {
			getItemById(stockItem.getId());
			log.debug("Existing item " + stockItem.getName()
					+ ", increased quantity by " + stockItem.getQuantity());
			fireTableDataChanged();
			return true;
		} catch (NoSuchElementException e) {
			rows.add(stockItem);
			log.debug("Added item " + stockItem.getName() + ", quantity of "
					+ stockItem.getQuantity());
			fireTableDataChanged();
			return false;
		}

	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	public boolean hasEnoughInStock(StockItem item, int quantity) {
		for (StockItem i : this.rows) {
			if (i.getId().equals(item.getId())) {
				return (i.getQuantity() >= quantity);
			}
		}
		return false;
	}

	public boolean validateNameUniqueness(String newName) {
		for (StockItem item : rows) {
			log.debug(" === comparing: " + newName + " vs. " + item.getName());
			if (newName.equals(item.getName())) {
				return false;
			}
		}
		return true;
	}

}
