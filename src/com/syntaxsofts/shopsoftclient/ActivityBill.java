package com.syntaxsofts.shopsoftclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityBill extends Activity {

	File nFile;
	Button btnSetDelivered;
	
	String shopName = "";
	String billNo = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill);

		nFile = ((dependencies)getApplicationContext()).getBillFile();
		billNo = nFile.getName().split("ShopSoftCli-")[1].split(".txt")[0].toString().trim();
		LinearLayout mLayout = (LinearLayout)findViewById(R.id.layoutBill);
		btnSetDelivered = (Button)findViewById(R.id.btnSetDelivered);
		
		if(nFile.getName().contains("Delivered"))
			btnSetDelivered.setEnabled(false);
		
		String billContents = getFileContents(nFile);
		String[] seperatorArr = billContents.split("\n\n\n---------------------------------------\n\n\t\t\t\t\t\t");
		shopName = seperatorArr[0].split("=====================================\n\n")[0].split("ShopSoft - ")[1].split("\n")[0].trim();
		String prodArray = seperatorArr[0].split("=====================================\n\n")[1];
		String totalArr = seperatorArr[1];
		
		TextView nHeaderText = new TextView(this);
		nHeaderText.setText("Product\t\tQuantity\t\tPrice\n");
		
		mLayout.addView(nHeaderText);
		
		String[] products = prodArray.split("\n");
		
		for (int i = 0; i < products.length; i++) {
			String prodDetails = products[i];
			
			TextView nTextView = new TextView(this);
			nTextView.setText(prodDetails);
			nHeaderText.setId(i);
			
			mLayout.addView(nTextView);
		}
		
		TextView nTotalText = new TextView(this);
		nTotalText.setText("\n\n\t\t\t\t\t\t" + totalArr);
		
		mLayout.addView(nTotalText);
		
		btnSetDelivered.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					if(new setDelivered().execute(new String[] {shopName,billNo}).get().contains("set"))
					{
						File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Bills");
						File mFile = new File(root, "ShopSoftCli-Delivered" + billNo + ".txt");
						nFile.renameTo(mFile);
						
						Toast.makeText(getApplicationContext(), "Your delivery has been registered", Toast.LENGTH_SHORT).show();
						ActivityBill.this.finish();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Your delivery could not be registered", Toast.LENGTH_SHORT).show();
					}
				}
				catch(Exception ex)
				{
					Log.d("setdel",ex.toString());
					Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private String getFileContents(File nFile)
	{
		StringBuilder strBuilder = new StringBuilder();
		try 
		{
			BufferedReader mReader = new BufferedReader(new FileReader(nFile));
			String line;
			
			while((line = mReader.readLine()) != null)
			{
				strBuilder.append(line);
				strBuilder.append("\n");
			}
			
			mReader.close();
			return strBuilder.toString();
		}
		catch (FileNotFoundException e) 
		{
			Toast.makeText(getApplicationContext(), "Bill file not found", Toast.LENGTH_SHORT).show();
			return null;
		}
		catch (IOException e) 
		{
			Toast.makeText(getApplicationContext(), "Cannot read the bill", Toast.LENGTH_SHORT).show();
			return null;
		}
		
	}
}
