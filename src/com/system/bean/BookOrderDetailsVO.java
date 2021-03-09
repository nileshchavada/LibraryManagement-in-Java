package com.system.bean;

import java.sql.Timestamp;
import java.sql.Date;

public class BookOrderDetailsVO {
	
	private String orderId;
	private String store;
	private String acquiredTyp;
	private String status;
	private Timestamp dateOfOrder;
	private int copies;
	private Date toBeReturned;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getAcquiredTyp() {
		return acquiredTyp;
	}
	public void setAcquiredTyp(String acquiredTyp) {
		this.acquiredTyp = acquiredTyp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getDateOfOrder() {
		return dateOfOrder;
	}
	public void setDateOfOrder(Timestamp dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}
	public int getCopies() {
		return copies;
	}
	public void setCopies(int copies) {
		this.copies = copies;
	}
	public Date getToBeReturned() {
		return toBeReturned;
	}
	public void setToBeReturned(Date toBeReturned) {
		this.toBeReturned = toBeReturned;
	}
	@Override
	public String toString() {
		return "BookOrderDetailsVO [orderId=" + orderId + ", store=" + store + ", acquiredTyp=" + acquiredTyp
				+ ", status=" + status + ", dateOfOrder=" + dateOfOrder + ", copies=" + copies + ", toBeReturned="
				+ toBeReturned + "]";
	}
	
	
	
	
	

}
