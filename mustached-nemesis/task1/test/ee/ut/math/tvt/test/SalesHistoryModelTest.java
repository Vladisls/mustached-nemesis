package ee.ut.math.tvt.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.SoldHistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesHistoryModel;

public class SalesHistoryModelTest {
	SalesHistoryModel shm;
	List<SoldItem> soldItems = new ArrayList<SoldItem>();
	SoldHistoryItem shi;

	@Test
	public void testGetValueAt() {
		SalesHistoryModel shm = new SalesHistoryModel();
		shi = new SoldHistoryItem("20/11/2014", "Time1", soldItems);
		shm.addItem(shi);
		assertEquals(shm.getValueAt(0, 0), "20/11/2014");
	}

	@Test
	public void testGetValueAtWithNoHistoryItems() {
		SalesHistoryModel shm = new SalesHistoryModel();
		try {
			assertEquals(shm.getValueAt(0, 0), "20/11/2014");
		} catch (Exception e) {
			return;
		}
		fail();
	}
}
