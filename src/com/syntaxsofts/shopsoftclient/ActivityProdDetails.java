package com.syntaxsofts.shopsoftclient;

import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityProdDetails extends Activity implements TabListener{

	String prodName;
	String prodMRP;
	String prodRate;
	String prodInfo;
	String prodStock;
	String prodWarranty;
	String barCode;
	
	String[] prodDetails = null;
	String[] tabNames = new String[] {"Description","Images & Videos"};
	
	WebView mWebView;
	TextView txtDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		
		if(getIntent().hasExtra("prodName") && getIntent().hasExtra("productDetails"))
		{
			prodName = getIntent().getStringExtra("prodName");
			prodDetails = getIntent().getStringArrayExtra("productDetails");
			
			prodMRP = prodDetails[0];
			prodRate = prodDetails[1];
			prodInfo = prodDetails[2];
			prodStock = prodDetails[3];
			prodWarranty = prodDetails[4];			
		}

		mWebView = (WebView)findViewById(R.id.prodImgWeb);
		txtDescription = (TextView)findViewById(R.id.txtDescription);
		
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle(prodName);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		
		for(int i=0; i < tabNames.length; i++)
		{
			Tab mTab = mActionBar.newTab();
			mTab.setText(tabNames[i])
				.setTabListener(this);
			
			mActionBar.addTab(mTab);
		}
		
		mWebView.loadUrl("http://syntaxsofts.com/ShopSoft/images/" + prodDetails[5]);
		txtDescription.setText("Info: " + prodInfo + "\n\nMRP: " + prodMRP + " ₹"+ "\n\nRate: " + prodRate + "  ₹" + "\n\nWarranty: " + prodWarranty + " years");
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		if(tab.getText() == tabNames[0])
		{
			mWebView.setVisibility(View.INVISIBLE);
			txtDescription.setVisibility(View.VISIBLE);
		}
		else if(tab.getText() == tabNames[1])
		{
			txtDescription.setVisibility(View.INVISIBLE);
			mWebView.setVisibility(View.VISIBLE);
		}
		
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	
	}
	
}
