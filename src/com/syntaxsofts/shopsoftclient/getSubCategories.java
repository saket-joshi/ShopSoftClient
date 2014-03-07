package com.syntaxsofts.shopsoftclient;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class getSubCategories extends AsyncTask<String, String, String[]> {

	@Override
	protected String[] doInBackground(String... params) {
		
		HttpClient mClient = new DefaultHttpClient();
		ResponseHandler<String> mResponseHandler = new BasicResponseHandler();
		
		String mResponse;
		String[] arrResponse = null;
		
		try 
		{
			params[0] = URLEncoder.encode(params[0],"UTF-8");
			params[1] = URLEncoder.encode(params[1],"UTF-8");
			
			String URL = "http://syntaxsofts.com/ShopSoft/getSubCategories.php?shopName=" + params[0] + "&categoryName=" + params[1];
			
			HttpGet mGet = new HttpGet(URL);
			mResponse = mClient.execute(mGet,mResponseHandler);
			
			arrResponse = mResponse.split("<!")[0].trim().replace("_", " ").split("%");
			
			return arrResponse;
		}
		catch(Exception ex)
		{
			Log.d("InternetCheck", ex.toString());
			return new String[] {"error"};
		}
	}

}
