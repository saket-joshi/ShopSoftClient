package com.syntaxsofts.shopsoftclient;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class dependencies {
	
	private String PREFS = "ShopSoftCli_Prefs";
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
	
	public int getCustomerID(Context mContext)
	{
		SharedPreferences mPreferences = mContext.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
		return mPreferences.getInt("customerID", -1);
	}
	
}
