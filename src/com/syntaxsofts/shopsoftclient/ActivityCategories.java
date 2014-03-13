package com.syntaxsofts.shopsoftclient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityCategories extends Activity implements TabListener{

	String[] categoryArray = null;
	ActionBar mActionBar;
	String shopName;
	ListView mListView;
	String[] mStrings;
	String selectedProduct;
		
	dependencies mDependencies;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
	
		mDependencies =(dependencies)getApplicationContext();
		
		if(getIntent().hasExtra("shopCategoryArray"))
		{
			categoryArray = getIntent().getStringArrayExtra("shopCategoryArray");
			shopName = mDependencies.getSelectedShop();
		}
		
		mListView = (ListView)findViewById(R.id.lstSubCat);
		
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle(shopName);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			for(int i=0; i < categoryArray.length;i++)
			{
				Tab newTab = mActionBar.newTab();
				newTab.setText(categoryArray[i])
					.setTabListener(this);
				
				mActionBar.addTab(newTab);
			}
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		try 
		{
			drawSubcategoryListActivity(new getSubCategories().execute(new String[] {shopName, tab.getText().toString()}).get());
		}
		catch(Exception ex)
		{
			Log.d("getsubcat", ex.toString());
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	void drawSubcategoryListActivity(final String[] subCatArray)
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityCategories.this, R.layout.list_subcategories_layout, subCatArray);
		mListView.setAdapter(adapter);
		registerForContextMenu(mListView);		
	}	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		Vibrator mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		mVibrator.vibrate(100);
		
		if(v.getId() == R.id.lstSubCat)
		{
			ListView lv = (ListView)v;
			AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo)menuInfo;
			String subCat = (String) lv.getItemAtPosition(acmi.position);
			
			try
			{
				String[] prodNames = new getProductNames().execute(new String[] {shopName, subCat}).get();
				menu.setHeaderTitle(subCat);
				for(int i=0;i < prodNames.length; i++)
				{
					menu.add(prodNames[i]);
				}
			}
			catch(Exception ex)
			{
				
			}
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {		
		try 
		{
			selectedProduct = item.getTitle().toString();
			getItemDetailsAndStartNewActivity(selectedProduct, 
					new getProductDetails().execute(new String[] {shopName, selectedProduct}).get());
		}
		catch (Exception ex) 
		{
			Log.d("getproductdetails", ex.toString());
		}
		
		return super.onContextItemSelected(item);
	}
	
	void getItemDetailsAndStartNewActivity(String prodName, String[] prodDetails)
	{
		Intent mIntent = new Intent(ActivityCategories.this,ActivityProdDetails.class);
		mIntent.putExtra("prodName", prodName);
		mIntent.putExtra("productDetails", prodDetails);
		startActivity(mIntent);
	}
}
