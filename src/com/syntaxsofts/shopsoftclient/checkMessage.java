package com.syntaxsofts.shopsoftclient;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class checkMessage extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... params) {
		HttpClient mClient = new DefaultHttpClient();
		ResponseHandler<String> mHandler = new BasicResponseHandler();
		
		try
		{
			String URL = "http://shopsoft.syntaxsofts.com/getNewChatMsgCSide.php?sName=" + URLEncoder.encode(params[1], "UTF-8") + "&cID=" 
					+ URLEncoder.encode(params[0], "UTF-8") + "&prevID=" + URLEncoder.encode(params[2], "UTF-8");
			
			HttpGet mGet = new HttpGet(URL);
			
			return mClient.execute(mGet, mHandler).split("<!")[0].replace("_", " ").trim();
		}
		catch(Exception ex)
		{
			Log.d("checkasync",ex.toString());
			return "error";
		}
	}
}
