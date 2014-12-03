package ee.ut.math.tvt.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.SoldHistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldHistoryItemTest {
	StockItem item1, item2;
	SoldItem si1, si2;
	SoldHistoryItem shi;
	List<SoldItem> soldItems = new ArrayList<SoldItem>();

	@Before
	public void setUp() {
		item1 = new StockItem(1L, "Leib", 2.0, 10);
		item2 = new StockItem(1L, "Piim", 5.0, 10);
		si1 = new SoldItem(item1, 1);
		si2 = new SoldItem(item2, 2);
	}

	@Test
	public void testcalcTotalWithZeroSoldItems() {
		SoldHistoryItem shi = new SoldHistoryItem("", "", soldItems);
		assertEquals(shi.calcTotal(), 0, 0.0001);
	}

	@Test
	public void testcalcTotalWithOneSoldItems() {
		soldItems.add(si1);
		SoldHistoryItem shi = new SoldHistoryItem("", "", soldItems);
		assertEquals(shi.calcTotal(), 2.0, 0.0001);
	}

	@Test
	public void testcalcTotalWithMoreThanOneSoldItem() {
		soldItems.add(si1);
		soldItems.add(si2);
		SoldHistoryItem shi = new SoldHistoryItem("", "", soldItems);
		assertEquals(shi.calcTotal(), 12.0, 0.0001);
	}
}