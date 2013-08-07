package com.viepak.halalfood.shared;

import java.util.Date;
import java.util.List;

/*
 * Ingredient Name	E-Number	Alternative Names	Status	Date Evaluated	Evaluated By	Remarks
 */

public class Ingredient {
	private long Id;
	
	private String name;
	private String eNumber;
	private List<String> alternativeNames;
	private String status;
	private Date evaluatedDate;
	private long evaluatedBy;
	private String remarks;
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String geteNumber() {
		return eNumber;
	}
	public void seteNumber(String eNumber) {
		this.eNumber = eNumber;
	}
	public List<String> getAlternativeNames() {
		return alternativeNames;
	}
	public void setAlternativeNames(List<String> alternativeNames) {
		this.alternativeNames = alternativeNames;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getEvaluatedDate() {
		return evaluatedDate;
	}
	public void setEvaluatedDate(Date evaluatedDate) {
		this.evaluatedDate = evaluatedDate;
	}
	public long getEvaluatedBy() {
		return evaluatedBy;
	}
	public void setEvaluatedBy(long evaluatedBy) {
		this.evaluatedBy = evaluatedBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
