package com.auto.api;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="transactions")
public class TransactionModel {
	@Id
	private String id;
	private String stransactionCode;
	private String accountCode;
	private Date approvedDate;
	private String type;
	private String action;
	private long amount;
	private long newDeb;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStransactionCode() {
		return stransactionCode;
	}
	public void setStransactionCode(String stransactionCode) {
		this.stransactionCode = stransactionCode;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public long getNewDeb() {
		return newDeb;
	}
	public void setNewDeb(long newDeb) {
		this.newDeb = newDeb;
	}
}
