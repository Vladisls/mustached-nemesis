package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ee.ut.math.tvt.salessystem.domain.data.SoldHistoryItem;

public class SalesHistoryModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	protected List<SoldHistoryItem> rows;
	protected final String[] headers;

	public SalesHistoryModel() {
		headers = new String[] { "Date", "Time", "Total price" };
		rows = new ArrayList<SoldHistoryItem>();
	}

	public int getColumnCount() {
		return headers.length;
	}

	public int getRowCount() {
		return rows.size();
	}

	public SoldHistoryItem getRow(int rowNumber) {
		return rows.get(rowNumber);
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return getColumnValue(rows.get(rowIndex), columnIndex);
	}

	protected Object getColumnValue(SoldHistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return item.getTotal();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	public void addItem(final SoldHistoryItem historyItem) {
		rows.add(historyItem);
		fireTableDataChanged();
	}

	public String getColumnName(final int columnIndex) {
		return headers[columnIndex];
	}
}