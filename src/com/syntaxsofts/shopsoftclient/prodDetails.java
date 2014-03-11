package com.syntaxsofts.shopsoftclient;

public class prodDetails {

	String prodName;
	int prodQty;
	int prodPrice;
	
	public prodDetails(String pName, int pQty, int pPrice) {
		
		this.prodName = pName;
		this.prodQty = pQty;
		this.prodPrice = pPrice;
		
	}
	
	public prodDetails getDetails()
	{
		return this;
	}
	
	public prodDetails() {
	}
	
}
