package com.syntaxsofts.shopsoftclient;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ActivityCategories extends Activity implements TabListener{

	String[] categoryArray = null;
	ActionBar mActionBar;
	String shopName;
	ListView mListView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		
		if(getIntent().hasExtra("shopCategoryArray") && getIntent().hasExtra("shopName"))
		{
			categoryArray = getIntent().getStringArrayExtra("shopCategoryArray");
			shopName = getIntent().getStringExtra("shopName");
		}
		
		mListView = (ListView)findViewById(R.id.listView1);
		
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
	
	void drawSubcategoryListActivity(String[] subCatArray)
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_subcategories_layout, subCatArray);
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), adapter.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
			}
		
		});
	}
	
}
