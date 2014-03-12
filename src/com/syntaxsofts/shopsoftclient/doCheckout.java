package com.syntaxsofts.shopsoftclient;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class doCheckout extends AsyncTask<prodDetails, String, Boolean> {

	dependencies mDependencies;
	
	private Context mContext;
	
	public doCheckout(Context nContext) {
		this.mContext = nContext;
	}
	
	@Override
	protected Boolean doInBackground(prodDetails... params) {
		
		HttpClient mClient = new DefaultHttpClient();
		ResponseHandler<String> mResponseHandler = new BasicResponseHandler();
		
		mDependencies = (dependencies)mContext.getApplicationContext();
		
		String mResponse;
		String shopName = mDependencies.getSelectedShop();
		int customerID = mDependencies.getCustomerID(mContext);
		int billTotal = 0;
		String prodArray = "";
		String prodQty = "";
		String cusID = "-1";
		String total = "-1";
		
		for (int i = 0; i < params.length; i++) {
			prodDetails nProd = params[i];
			
			if(i==0)
			{
				prodArray = nProd.prodName + "%";
				prodQty = String.valueOf(nProd.prodQty) + "%";
			}
			else
			{
				prodArray = prodArray + nProd.prodName + "%";
				prodQty = prodQty + String.valueOf(nProd.prodQty) + "%"; 
			}
			
			billTotal = billTotal + (nProd.prodPrice * nProd.prodQty);			
		}
		
		try
		{
			shopName = URLEncoder.encode(shopName, "UTF-8");
			cusID = URLEncoder.encode(String.valueOf(customerID), "UTF-8");
			total = URLEncoder.encode(String.valueOf(billTotal), "UTF-8");
			prodArray = URLEncoder.encode(prodArray, "UTF-8");
			prodQty = URLEncoder.encode(prodQty, "UTF-8");
		}
		catch(Exception ex)
		{
			
		}

		try
		{
			String URL = "http://shopsoft.syntaxsofts.com/checkout.php?ShopName=" + shopName + "&customerID=" + cusID 
					+ "&billTotal=" + total + "&prodArr=" + prodArray + "&prodQty=" + prodQty;
			
			Log.i("checkout",URL);
			
			/*HttpGet mGet = new HttpGet(URL);
			
			mResponse = mClient.execute(mGet, mResponseHandler);
			
			if(mResponse.toString().split("<!")[0].replace("_", " ").trim().toString() == "1")
				return true;*/
			return false;
		}
		catch(Exception ex)
		{
			Log.d("docheckout.pph", ex.toString());
			return false;
		}
	}

}
