package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldHistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		//throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}
	

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		
		@SuppressWarnings("unchecked")
		List<StockItem> dataset = HibernateUtil.currentSession()
				.createQuery("from StockItem").list();
		return dataset;
		
		
		
	}
	
	public List<SoldHistoryItem> loadSaleHistoryState() {
		@SuppressWarnings("unchecked")
		List<SoldHistoryItem> dataset = HibernateUtil.currentSession()
				.createQuery("from SoldHistoryItem").list();
		return dataset;
	}
	
	public void endSession() {
		 HibernateUtil.closeSession();
		
	}
}
