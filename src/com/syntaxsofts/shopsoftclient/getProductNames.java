package com.syntaxsofts.shopsoftclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class getProductNames extends AsyncTask<String, String, String[]> {

	@Override
	protected String[] doInBackground(String... params) {
		
		HttpClient mClient = new DefaultHttpClient();
		ResponseHandler<String> mResponseHandler = new BasicResponseHandler();
		
		String mResponse;
		String[] arrResponse = null;
		
		try
		{
			String URL = "http://syntaxsofts.com/ShopSoft/getProducts.php?subCategory=" + params[0];
			HttpGet mGet = new HttpGet(URL);
			
			mResponse = mClient.execute(mGet,mResponseHandler);
			arrResponse = mResponse.split("<!")[0].trim().replace("_", " ").split("%");
			
			return arrResponse;
		}
		catch(Exception ex)
		{
			Log.d("getProducts", ex.toString());
			return new String[]{"error"};
		}
	}

}