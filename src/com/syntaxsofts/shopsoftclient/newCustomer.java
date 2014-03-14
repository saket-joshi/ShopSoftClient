package com.syntaxsofts.shopsoftclient;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class newCustomer extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... params) {

		HttpClient mClient = new DefaultHttpClient();
		ResponseHandler<String>mHandler = new BasicResponseHandler();
		
		try
		{
			String URL = "http://shopsoft.syntaxsofts.com/newCustomer.php?name=" + URLEncoder.encode(params[0], "UTF-8")
					+ "&pwd=" + URLEncoder.encode(params[1], "UTF-8") + "&addr=" + 
					URLEncoder.encode(params[2], "UTF-8") + "&phone=" + URLEncoder.encode(params[3],"UTF-8");
			HttpGet mGet = new HttpGet(URL);
			
			return mClient.execute(mGet,mHandler).split("<!")[0].trim().replace("_", " ");
		}
		catch(Exception ex)
		{
			Log.d("newcus", ex.toString());
			return "error";
		}		
	}
}
