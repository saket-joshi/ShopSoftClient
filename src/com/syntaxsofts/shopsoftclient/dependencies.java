package com.syntaxsofts.shopsoftclient;

import java.util.LinkedList;

public class dependencies {

	private LinkedList<prodDetails>lstCart = new LinkedList<prodDetails>();
	
	public boolean addToCart(prodDetails prod)
	{
		return lstCart.add(prod);
	}
	
	public boolean removeFromCart(String prod)
	{
		return lstCart.remove(prod);
	}
	
	public boolean isPresentAlready(String prod)
	{
		prodDetails[] mList = getCart();
		for (int i = 0; i < mList.length; i++) 
		{
			{
				prodDetails mProd = mList[i];
				if(mProd.prodName == prod)
					return true;
			}
		}
		return false;
	}
	
	public prodDetails[] getCart()
	{
		prodDetails[] arr = new prodDetails[lstCart.size()];
		return lstCart.toArray(arr);
	}
	
}
