package ee.ut.math.tvt.salessystem.domain.controller.impl;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

	private static final Logger log = Logger
			.getLogger(SalesDomainControllerImpl.class);

	private Session session = HibernateUtil.currentSession();

	@SuppressWarnings("unchecked")
	public List<StockItem> getAllStockItems() {
		session.clear();
		List<StockItem> result = session.createQuery("from StockItem").list();

		log.info(result.size() + " items loaded from disk");

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Sale> getAllSales() {
		session.clear();
		List<Sale> result = session.createQuery("from Sale").list();
		log.info(result.size() + " Sales loaded from disk");

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Client> getAllClients() {
		session.clear();
		List<Client> clients = session.createQuery("from Client").list();

		log.info(clients.size() + " clients loaded from disk");

		return clients;
	}


	private StockItem getStockItem(long id) {
		return (StockItem) session.get(StockItem.class, id);
	}


	public void registerSale(Sale sale) {
		// Begin transaction
		Transaction tx = session.beginTransaction();
		sale.setSellingTime(new Date());
		// Reduce quantities of stockItems in warehouse
		for (SoldItem item : sale.getSoldItems()) {
			StockItem stockItem = getStockItem(item.getStockItem().getId());
			stockItem.setQuantity(stockItem.getQuantity() - item.getQuantity());
			session.save(stockItem);
		}
		session.save(sale);
		session.flush();
		// end transaction
		tx.commit();
	}

	public void createStockItem(StockItem stockItem) {
		// Begin transaction
		Transaction tx = session.beginTransaction();
		session.save(stockItem);
		tx.commit();
		log.info("Added new stockItem : " + stockItem);
	}

	@Override
	public void endSession() {
		HibernateUtil.closeSession();
	}

}
