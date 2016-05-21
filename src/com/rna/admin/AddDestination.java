package com.rna.admin;

public class AddDestination {
	private String destinationName;
	private String companyID;
	private String trucktype;
	
	public String getDestinationName() {
		return destinationName;
	}

	public void getDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getTruckType() {
		return trucktype;
	}
	public void setTruckType(String trucktype) {
		this.trucktype = trucktype;
	}
}
