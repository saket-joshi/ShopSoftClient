package com.syntaxsofts.shopsoftclient;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ActivityLanding extends Activity {

	String strResponse;
	
	private DrawerLayout mDrawerLayout;
	private ListView mListView;
	private ActionBarDrawerToggle mActionBarDrawerToggle;
	
	AsyncTask<String, String, String> mInternetCheck;
	String selectedShop;
	
	dependencies mDependencies;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		
		mInternetCheck = new lstAllShops().execute("helloworld");
		
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		mListView = (ListView)findViewById(R.id.lstMain);
		
		mDependencies = (dependencies)getApplicationContext();
		
		try 
		{
			strResponse = mInternetCheck.get();
			final String[] lstShops = strResponse.split("%");
			
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			{
				getActionBar().setDisplayHomeAsUpEnabled(true);
				getActionBar().setHomeButtonEnabled(true);
				getActionBar().setIcon(R.drawable.ic_launcher);
			}
			
			mActionBarDrawerToggle = new ActionBarDrawerToggle(this, 
					mDrawerLayout, R.drawable.ic_launcher, R.string.app_name, R.string.app_name);
			
			ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, lstShops);
			mListView.setAdapter(adapter);			
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {	
					try 
					{
						selectedShop = lstShops[position];
						mDependencies.setSelectedShop(selectedShop);
						openShopDetailsFromDrawer(new getShopCategories().execute(selectedShop).get());
					}
					catch(Exception ex)
					{
						Log.d("openshopdetails", ex.toString());
					}
					mDrawerLayout.closeDrawer(Gravity.START);
				}
			});			
		} 
		catch (Exception ex)
		{
			Log.d("LandingActivity", ex.toString());
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }

	    switch (item.getItemId()) {
	    case android.R.id.home:
	        return true;
	    }
	   return super.onOptionsItemSelected(item);
	}
	
	void openShopDetailsFromDrawer(String[] shopCategories)
	{
		Intent mIntent = new Intent(ActivityLanding.this, ActivityCategories.class);
		mIntent.putExtra("shopCategoryArray", shopCategories);
		startActivity(mIntent);
	}
	
}
