package com.syntaxsofts.shopsoftclient;

import java.util.LinkedList;

public class dependencies {

	private LinkedList<String>lstCart = new LinkedList<String>();
	
	public boolean addToCart(String prodName)
	{
		return lstCart.add(prodName);
	}
	
	public boolean removeFromCart(String prodName)
	{
		return lstCart.remove(prodName);
	}
	
	public String[] getCart()
	{
		return lstCart.toArray(new String[lstCart.size()]);
	}
	
}
