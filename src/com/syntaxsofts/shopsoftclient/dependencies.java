package com.syntaxsofts.shopsoftclient;

import java.io.File;
import java.util.LinkedList;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class dependencies extends Application{
	
	private File billFile = null;
	private String selectedShop = "";
	private String PREFS = "ShopSoftCli_Prefs";
	private LinkedList<prodDetails>lstCart = new LinkedList<prodDetails>();
	
	public void setBillFile(File nFile)
	{
		this.billFile = nFile;
	}
	
	public File getBillFile()
	{
		return this.billFile;
	}
	
	public void setSelectedShop(String shopName)
	{
		this.selectedShop = shopName;
	}
	
	public String getSelectedShop()
	{
		return this.selectedShop;
	}
	
	public boolean addToCart(prodDetails prod)
	{
		return lstCart.add(prod);
	}
	
	public boolean removeFromCart(prodDetails prod)
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
	
	public void registerNewCustomer(Context mContext, String id, String name, String addr, String phone, String pass)
	{
		SharedPreferences mPreferences = mContext.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
		SharedPreferences.Editor mEditor = mPreferences.edit();
		
		mEditor.putInt("customerID", Integer.parseInt(id));
		mEditor.putString("customerName", name);
		mEditor.putString("customerAddress", addr);
		mEditor.putString("customerPhone", phone);
		mEditor.putString("customerPwd", pass);
		
		mEditor.apply();
	}
	
	public String getPassword(Context mContext)
	{
		SharedPreferences mPreferences = mContext.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
		return mPreferences.getString("customerPwd","null");
	}
	
	public void emptyCart()
	{
		lstCart.clear();
	}
	
}
