package com.system.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class ReservedBookDetailsVO {
	private int reqId;
	private int bookNmId;
	private String bookNm;
	private String reserverId;
	private Timestamp dateOfReservtn;
	private Date reservedFor;
	private String bookTyp;
	private Date reservedTill;
	
	
	public Date getReservedTill() {
		return reservedTill;
	}
	public void setReservedTill(Date reservedTill) {
		this.reservedTill = reservedTill;
	}
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public String getBookTyp() {
		return bookTyp;
	}
	public void setBookTyp(String bookTyp) {
		this.bookTyp = bookTyp;
	}
	
	public int getBookNmId() {
		return bookNmId;
	}
	public void setBookNmId(int bookNmId) {
		this.bookNmId = bookNmId;
	}
	public String getBookNm() {
		return bookNm;
	}
	public void setBookNm(String bookNm) {
		this.bookNm = bookNm;
	}
	public String getReserverId() {
		return reserverId;
	}
	public void setReserverId(String reserverId) {
		this.reserverId = reserverId;
	}
	public Timestamp getDateOfReservtn() {
		return dateOfReservtn;
	}
	public void setDateOfReservtn(Timestamp dateOfReservtn) {
		this.dateOfReservtn = dateOfReservtn;
	}
	public Date getReservedFor() {
		return reservedFor;
	}
	public void setReservedFor(Date reservedFor) {
		this.reservedFor = reservedFor;
	}
	@Override
	public String toString() {
		return "ReservedBookDetailsVO [reqId=" + reqId + ", bookNmId=" + bookNmId + ", bookNm=" + bookNm
				+ ", reserverId=" + reserverId + ", dateOfReservtn=" + dateOfReservtn + ", reservedFor=" + reservedFor
				+ ", bookTyp=" + bookTyp + "]";
	}
	
	
	
	
	}
