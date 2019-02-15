package com.sven.wms.business.transaction;

public interface TransactionBusiness {
	public int transaction(boolean exception);

	public int transactionXA(boolean exception);
}
