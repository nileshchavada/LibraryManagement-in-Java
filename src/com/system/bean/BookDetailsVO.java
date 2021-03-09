package com.system.bean;

public class BookDetailsVO {
	
	private Long bookId;
	private String bookName;
	private int bookNameId;
    private int authorId;
    private String authrName;
    private int edition;
    private String publication;
    private String language;
    private String department;
    private String type;
    private String status;
    private int count;
    

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getBookNameId() {
		return bookNameId;
	}

	public void setBookNameId(int bookNameId) {
		this.bookNameId = bookNameId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthrName() {
		return authrName;
	}

	public void setAuthrName(String authrName) {
		this.authrName = authrName;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BookDetailsVO [bookId=" + bookId + ", bookName=" + bookName + ", bookNameId=" + bookNameId
				+ ", authorId=" + authorId + ", authrName=" + authrName + ", edition=" + edition + ", publication="
				+ publication + ", language=" + language + ", department=" + department + ", type=" + type + ", status="
				+ status + ", count=" + count + "]";
	}


	
	



}
