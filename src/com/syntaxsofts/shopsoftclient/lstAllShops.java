package com.syntaxsofts.shopsoftclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class lstAllShops extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... params) {

		HttpClient mClient = new DefaultHttpClient();
		ResponseHandler<String> mResponseHandler = new BasicResponseHandler();
		String mResponse;
		
		try 
		{
			String URL = "http://syntaxsofts.com/ShopSoft/lstAllShops.php";
			
			HttpGet mGet = new HttpGet(URL);
			mResponse = mClient.execute(mGet,mResponseHandler);
			
			mResponse = mResponse.split("<!")[0].trim();
		
			return mResponse;
		}
		catch(Exception ex)
		{
			Log.d("InternetCheck", ex.toString());
			return "error";
		}
	}

}
