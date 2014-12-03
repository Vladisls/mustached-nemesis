package ee.ut.math.tvt.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class PurchaseTest {
	SalesSystemModel ssm;
	private StockItem item1, item2;

	@Before
	public void setUp() {
		item1 = new StockItem(1L, "Leib", "Must", 5.0, 5);
		item2 = new StockItem(1L, "Piim", "Kodune", 7.0, 5);
		ssm = new SalesSystemModel();
		ssm.getWarehouseTableModel().addItem(item1);
		ssm.getWarehouseTableModel().addItem(item2);
	}

	@Test
	public void testAddSoldItem() {
		PurchaseInfoTableModel sm = new PurchaseInfoTableModel(ssm);
		try {
			sm.addItem(new SoldItem(item1, 5));
		} catch (SalesSystemException e) {
			fail();
		}
	};

	@Test
	public void testGetSumWithNoItems() {
		PurchaseInfoTableModel sm = new PurchaseInfoTableModel(ssm);
		assertEquals(0, sm.getSum(), 0.0001);
	};

	@Test
	public void testGetSumWithOneItem() {
		PurchaseInfoTableModel sm = new PurchaseInfoTableModel(ssm);
		try {
			sm.addItem(new SoldItem(item1, 5));
			assertEquals(25.0, sm.getSum(), 0.0001);
		} catch (SalesSystemException e) {
			fail();
		}
	};

	@Test
	public void testGetSumWithMultipleItems() {
		PurchaseInfoTableModel sm = new PurchaseInfoTableModel(ssm);
		try {
			sm.addItem(new SoldItem(item1, 5));
			sm.addItem(new SoldItem(item2, 5));
			assertEquals(60.0, sm.getSum(), 0.0001);
		} catch (SalesSystemException e) {
			fail();
		}
	};
}