package com.syntaxsofts.shopsoftclient;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class setDelivered extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... params) {
		
		HttpClient mClient = new DefaultHttpClient();
		ResponseHandler<String> mResponseHandler = new BasicResponseHandler();
		
		String mResponse;
		
		try
		{
			String URL = "http://shopsoft.syntaxsofts.com/setDelivered.php?shopName=" + URLEncoder.encode(params[0], "UTF-8")
					+ "&billNo=" + URLEncoder.encode(params[1], "UTF-8");
			
			HttpGet mGet = new HttpGet(URL);
			
			mResponse = mClient.execute(mGet,mResponseHandler);
			
			return mResponse;
			
		}
		catch(Exception ex)
		{
			Log.d("setdelivered",ex.toString());
			return "null";
		}
	}

}
