package ee.ut.math.tvt.test;

import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	StockItem item;

	@Before
	public void setUp() {
		item = new StockItem(1L, "Leib", 2.0, 10);
	}

	@Test
	public void testValidateNameUniqueness() {
		// Add item with name "Leib" to model, check if we
		// add another item with name "Leib" we get false
		// for uniqueness
		StockTableModel stm = new StockTableModel();
		stm.addItem(item);
		assertFalse(stm.validateNameUniqueness("Leib"));
	};

	@Test
	public void testHasEnoughInStock() {
		// Add item with quantity 10 to stock
		// Check if we have 5 units of that
		// item in stock
		StockTableModel stm = new StockTableModel();
		stm.addItem(item);
		assertTrue(stm.hasEnoughInStock(item, 5));
	};

	@Test
	public void testGetItemByIdWhenItemExists() {
		// Add item to model. Then search for that
		// item by given method. Check if method
		// returned right item.
		StockTableModel stm = new StockTableModel();
		stm.addItem(item);
		assertEquals(item, stm.getItemById(item.getId()));
	};

	@Test
	public void testGetItemByIdWhenThrowsException() {
		// Empty model, get item with ID 1L, check
		// if that returns exception
		StockTableModel stm = new StockTableModel();
		try {
			assertEquals(0, stm.getItemById(1L));
		} catch (NoSuchElementException e) {
			return;
		}
		fail();
	};
}