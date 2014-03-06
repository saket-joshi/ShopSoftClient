package com.syntaxsofts.shopsoftclient;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ActivityCategories extends Activity implements TabListener{

	String[] categoryArray = null;
	ActionBar mActionBar;
	String shopName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		
		if(getIntent().hasExtra("shopCategoryArray") && getIntent().hasExtra("shopName"))
		{
			categoryArray = getIntent().getStringArrayExtra("shopCategoryArray");
			shopName = getIntent().getStringExtra("shopName");
		}
		
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
		
		drawSubcategoryListActivity
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}
	
}
