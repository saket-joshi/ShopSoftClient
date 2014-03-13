package com.syntaxsofts.shopsoftclient;

import java.io.File;
import java.io.FileWriter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ActivityCart extends Activity implements View.OnClickListener{

	RelativeLayout mLayout;
	dependencies mDependencies;
	prodDetails[] cartProds;

	Button btnCheckout;
	
	Context mContext;
	
	TableLayout mLinear;
	String billNo = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);

		btnCheckout = (Button)findViewById(R.id.btnCheckout);
		mLayout = (RelativeLayout)findViewById(R.id.layoutCart);
		mDependencies = ((dependencies)this.getApplicationContext());
		mContext = this;
	
		ScrollView scroll = new ScrollView(this);
		scroll.setBackgroundColor(Color.TRANSPARENT);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		                                             LayoutParams.MATCH_PARENT));
		
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		getWindow().setTitle("Shopping Cart");
		
		cartProds = mDependencies.getCart();
		
		mLinear = new TableLayout(this);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
		TableRow tHeader = new TableRow(this);

		tHeader.setLayoutParams(lp);
		TextView mHeaderName = new TextView(this);
		mHeaderName.setText("Name");
		mHeaderName.setTextColor(Color.WHITE);
		
		TextView mHeaderQty = new TextView(this);
		mHeaderQty.setText(" Quantity ");
		mHeaderQty.setTextColor(Color.WHITE);
		
		TextView mHeaderPrice = new TextView(this);
		mHeaderPrice.setText(" Price ");
		mHeaderPrice.setTextColor(Color.WHITE);
		
		tHeader.addView(mHeaderName);
		tHeader.addView(mHeaderQty);
		tHeader.addView(mHeaderPrice);
		
		mLinear.addView(tHeader,0);
		
		mLinear.setColumnShrinkable(0, true);
		mLinear.setColumnStretchable(0, false);
		
		for (int i = 0, j=1000, k=2000, m=3000; i < cartProds.length; i++,j++,k++,m++) {
			prodDetails nProd = cartProds[i];
			
			TableRow tr = new TableRow(this);

			tr.setLayoutParams(lp);
			
			TextView mTextView = new TextView(this);
			mTextView.setId(i);
			mTextView.setText(nProd.prodName);
			mTextView.setTextColor(Color.WHITE);
			
			TextView mPrice = new TextView(this);
			mPrice.setId(j);
			mPrice.setText(" " + String.valueOf(nProd.prodPrice) + " ");
			mPrice.setTextColor(Color.WHITE);
			
			TextView mQty = new TextView(this);
			mQty.setId(k);
			mQty.setText(" " + String.valueOf(nProd.prodQty) + " ");
			mTextView.setTextColor(Color.WHITE);
			
			Button mButton = new Button(this);
			mButton.setText("Remove");
			mButton.setId(m);
			mButton.setOnClickListener(this);
			
			tr.addView(mTextView);
			tr.addView(mQty);
			tr.addView(mPrice);
			tr.addView(mButton);
			
			mLinear.addView(tr,i+1);
		}
		
		mLayout.addView(scroll);
		scroll.addView(mLinear);
		
		btnCheckout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(cartProds.length >= 1)
				{
					try
					{
						billNo = new doCheckout(ActivityCart.this).execute(cartProds).get();
						if(billNo!="null")
						{
							createBill(cartProds);
							clearTheCart(cartProds.length);
							finish();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Cannot checkout at this time, please try again later",Toast.LENGTH_SHORT).show();
						}
					}
					catch (Exception ex)
					{
						Log.d("dochekout", ex.toString());
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Your cart is empty",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void clearTheCart(int length)
	{
		mLinear.removeViews(1, length);
		mDependencies.emptyCart();
	}
	
	@Override
	public void onClick(final View v) {
		
		final String prodName = cartProds[v.getId()-3000].prodName;
		
		AlertDialog.Builder mBuilder = new Builder(ActivityCart.this);
		mBuilder.setTitle("Remove from cart")
			.setMessage("Are you sure you want to remove " + prodName)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mDependencies.removeFromCart(cartProds[v.getId()-3000]);
					Toast.makeText(getApplicationContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
					mLinear.removeViewAt((v.getId()-3000) + 1);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			})
			.setCancelable(false);
		mBuilder.create().show();
	}
	
	public void createBill(prodDetails[] cart)
	{
		String billDetails = getBillString(cart);
		
		String billHeader = "\t\t\t\tShopSoft - " + mDependencies.getSelectedShop() + "\n\n" + "Product \t\t Quantity \t\t Rate \n" +
				"=====================================\n\n";
				
		if(isExternalStorageWritable())
		{
			try
			{
				File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Bills");
				if(!root.exists())
				{
					root.mkdirs();
				}
				
				File opFile = new File(root, "ShopSoftCli-" + billNo + ".txt");
				FileWriter mWriter = new FileWriter(opFile);
				mWriter.write(billHeader);
				mWriter.append(billDetails);
				mWriter.flush();
				mWriter.close();
				
				Toast.makeText(getApplicationContext(), "Bill saved as " + opFile.getName() + " in Downloads", Toast.LENGTH_LONG).show();
			}
			catch(Exception ex)
			{
				Log.d("createbill", ex.toString());
				Toast.makeText(getApplicationContext(), "Cannot create the bill", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public String getBillString(prodDetails[] cart)
	{
		String retStr = "";
		int total = 0;
		String prodNameBill;
		
		for (int i = 0; i < cart.length; i++) {
			prodDetails nProd = cart[i];
			prodNameBill = nProd.prodName;
			
			if(prodNameBill.length() > 30)
				prodNameBill = prodNameBill.substring(0, 30);
			
			retStr = retStr + prodNameBill + "\t\t" + String.valueOf(nProd.prodQty) + "\t\t" + String.valueOf(nProd.prodPrice) + "\n";
			total = total + (nProd.prodPrice * nProd.prodQty);
		}
		
		retStr = retStr + "\n\n\n---------------------------------------\n\n\t\t\t\t\t\tTotal = " + String.valueOf(total);
		return retStr;
	}
	
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
}
