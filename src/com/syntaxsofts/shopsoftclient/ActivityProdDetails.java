package com.syntaxsofts.shopsoftclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityProdDetails extends Activity implements TabListener{

	String prodName;
	String prodMRP;
	int prodRate;
	String prodInfo;
	String prodStock;
	String prodWarranty;
	String barCode;
	
	String[] prodDetails = null;
	String[] tabNames = new String[] {"Description","Images & Videos"};
	
	WebView mWebView;
	TextView txtDescription;
	ImageView mInStock;
	ImageView mSoldOut;
	
	dependencies mDependencies = new dependencies();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		
		if(getIntent().hasExtra("prodName") && getIntent().hasExtra("productDetails"))
		{
			prodName = getIntent().getStringExtra("prodName");
			prodDetails = getIntent().getStringArrayExtra("productDetails");
			
			prodMRP = prodDetails[0];
			prodRate = Integer.parseInt(prodDetails[1]);
			prodInfo = prodDetails[2];
			prodStock = prodDetails[3];
			prodWarranty = prodDetails[4];			
		}

		mWebView = (WebView)findViewById(R.id.prodImgWeb);
		txtDescription = (TextView)findViewById(R.id.txtDescription);
		mInStock = (ImageView)findViewById(R.id.imgInStock);
		mSoldOut = (ImageView)findViewById(R.id.imgSoldOut);
		
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
		
		txtDescription.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mSoldOut.setAlpha((float)0.4);
				mInStock.setAlpha((float)0.4);
				return false;
			}
		});
		
		if(Integer.parseInt(prodStock) >=1)
		{
			mSoldOut.setVisibility(View.INVISIBLE);
		}
		else
		{
			mInStock.setVisibility(View.VISIBLE);
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_view_videos, menu);
		if(Integer.parseInt(prodStock) < 1)
		{
			menu.findItem(R.id.buyProduct).setEnabled(false);
		}
		else
		{
			menu.findItem(R.id.setAlert).setEnabled(false);
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId())
		{
			case R.id.viewVideos:
			{
				try
				{
					AlertDialog.Builder mBuilder = new Builder(ActivityProdDetails.this);
					mBuilder.setTitle(prodName);
					
					WebView mWebView = new WebView(ActivityProdDetails.this);
				
					mWebView.getSettings().setJavaScriptEnabled(true);
					mWebView.getSettings().setPluginState(PluginState.ON);
					
					mWebView.loadUrl("http://www.youtube.com/results?search_query=" + URLEncoder.encode(prodName, "UTF-8"));
					mWebView.setWebViewClient(new WebViewClient() {
						@Override
						public boolean shouldOverrideUrlLoading(WebView view, String url) {
							view.loadUrl(url);
							return true;
						}
					});
					mBuilder.setView(mWebView);
					
					mBuilder.setNegativeButton("Close", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					mBuilder.create().show();
					
					return true;
				}
				catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
			}
			break;
			
			case R.id.buyProduct:
				
				if(!mDependencies.isPresentAlready(prodName))
				{
					prodDetails mProd = new prodDetails(prodName, 2, prodRate);
					mDependencies.addToCart(mProd);
					Toast.makeText(getApplicationContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(getApplicationContext(), "Item already present", Toast.LENGTH_SHORT).show();				
				break;
			case R.id.addWishlist:
				Toast.makeText(getApplicationContext(), "add wishlist code", Toast.LENGTH_SHORT).show();
				break;
			case R.id.talkToShopkeeper:
				Toast.makeText(getApplicationContext(), "add talk code", Toast.LENGTH_SHORT).show();
				break;
			case R.id.setAlert:
				Toast.makeText(getApplicationContext(), "add alert", Toast.LENGTH_SHORT).show();
				break;
		}
		
		return super.onOptionsItemSelected(item);
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
