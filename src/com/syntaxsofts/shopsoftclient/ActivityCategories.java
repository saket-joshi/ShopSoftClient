package com.syntaxsofts.shopsoftclient;

import java.util.concurrent.ExecutionException;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityCategories extends Activity implements TabListener{

	String[] categoryArray = null;
	ActionBar mActionBar;
	String shopName;
	ListView mListView;
	String[] mStrings;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		
		if(getIntent().hasExtra("shopCategoryArray") && getIntent().hasExtra("shopName"))
		{
			categoryArray = getIntent().getStringArrayExtra("shopCategoryArray");
			shopName = getIntent().getStringExtra("shopName");
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
			drawSubcategoryListActivity(new getSubCategories().execute(tab.getText().toString()).get());
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
				String[] prodNames = new getProductNames().execute(subCat).get();
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
		Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
		return super.onContextItemSelected(item);
	}
}
