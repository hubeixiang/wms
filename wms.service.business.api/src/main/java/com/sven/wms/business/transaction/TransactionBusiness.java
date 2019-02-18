package com.sven.wms.business.transaction;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public interface TransactionBusiness {
	public int transaction(boolean exception);

	public int transactionXA(boolean exception);
}
