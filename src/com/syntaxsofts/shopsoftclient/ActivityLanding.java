package com.syntaxsofts.shopsoftclient;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
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
		
		if(mDependencies.getCustomerID(ActivityLanding.this) == -1)
		{
			registerNewCustomer();
		}
		
		try 
		{
			strResponse = mInternetCheck.get();
			strResponse = strResponse + "View Bills%";
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
						if(position == lstShops.length - 1)
						{
							openBillDetails();
						}
						else
						{
							selectedShop = lstShops[position];
							mDependencies.setSelectedShop(selectedShop);
							openShopDetailsFromDrawer(new getShopCategories().execute(selectedShop).get());
						}
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
	
	void openBillDetails()
	{
		Intent mIntent = new Intent(ActivityLanding.this, ActivityBillDetails.class);
		startActivity(mIntent);
	}
	
	
	void registerNewCustomer()
	{
		
		final EditText txtName, txtPass, txtAddr, txtPhone;
		Button btnOK;
		
		Dialog mDialog = new Dialog(ActivityLanding.this);
		mDialog.setContentView(R.layout.dialog_new_customer);
		
		txtName = (EditText)mDialog.findViewById(R.id.txtName);
		txtPass = (EditText)mDialog.findViewById(R.id.txtPassword);
		txtAddr = (EditText)mDialog.findViewById(R.id.txtAddress);
		txtPhone = (EditText)mDialog.findViewById(R.id.txtPhone);
		btnOK = (Button)mDialog.findViewById(R.id.btnSaveNewCustomer);
		
		mDialog.setTitle("New Customer");
		
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String strName = txtName.getText().toString().trim();
				String strAddr = txtAddr.getText().toString().trim();
				String strPwd = txtPass.getText().toString().trim();
				String strPhone = txtPhone.getText().toString().trim();
				
				if(strName != "" && strPwd != "" && strAddr != "" && strPhone != "")
				{
					try
					{
						String isReg = new isCustomerRegistered().execute(new String[] {strName,strPhone}).get();
						
						if(isReg.contains("nosuchuser"))
						{
							String newID = new newCustomer().execute(new String[] {strName, strPwd, strAddr, strPhone}).get();
							mDependencies.registerNewCustomer(ActivityLanding.this,newID.replace("addedID=", ""),strName,strAddr,strPhone,strPwd);
						}
						else if(isReg != "exception")
						{
							String[] cDetails = isReg.split("%");
							mDependencies.registerNewCustomer(ActivityLanding.this,cDetails[0],cDetails[1],cDetails[2],cDetails[3],strPwd);
						}
						
						Toast.makeText(getApplicationContext(), "Registered as a new customer", Toast.LENGTH_SHORT).show();
					}
					catch(Exception ex)
					{
						Log.d("reg", ex.toString());
						Toast.makeText(getApplicationContext(), "Cannot register", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		mDialog.setCancelable(false);
		
		mDialog.show();
	}
}
