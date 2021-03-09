package com.system.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BookRecordVO {
	
	private String logId;
	private Long bookId;
	private String borrowerId;
	private Timestamp dtOfIssue;
	private Timestamp dtOfReturn;
	private Timestamp actualDatetOfReturn;
	private String fine;
	private String typ;
	private String brwrType;
	private String issuedBy ;
	private String bookStts;
	private int bookLimit;
	private String message;
	
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public String getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}
	
	
	public String getIssuedBy() {
		return issuedBy;
	}
	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
	public Timestamp getDtOfIssue() {
		return dtOfIssue;
	}
	public void setDtOfIssue(Timestamp dtOfIssue) {
		this.dtOfIssue = dtOfIssue;
	}
	public Timestamp getDtOfReturn() {
		return dtOfReturn;
	}
	public void setDtOfReturn(Timestamp dtOfReturn) {
		this.dtOfReturn = dtOfReturn;
	}
	public Timestamp getActualDatetOfReturn() {
		return actualDatetOfReturn;
	}
	public void setActualDatetOfReturn(Timestamp actualDatetOfReturn) {
		this.actualDatetOfReturn = actualDatetOfReturn;
	}
	public String getFine() {
		return fine;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	public String getBrwrType() {
		return brwrType;
	}
	public void setBrwrType(String brwrType) {
		this.brwrType = brwrType;
	}
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getBookLimit() {
		return bookLimit;
	}
	public void setBookLimit(int bookLimit) {
		this.bookLimit = bookLimit;
	}
	public String getBookStts() {
		return bookStts;
	}
	public void setBookStts(String bookStts) {
		this.bookStts = bookStts;
	}
	@Override
	public String toString() {
		return "BookRecordVO [logId=" + logId + ", bookId=" + bookId + ", borrowerId=" + borrowerId + ", dtOfIssue="
				+ dtOfIssue + ", dtOfReturn=" + dtOfReturn + ", actualDatetOfReturn=" + actualDatetOfReturn + ", fine="
				+ fine + ", typ=" + typ + "]";
	}
	
	
	
	}
