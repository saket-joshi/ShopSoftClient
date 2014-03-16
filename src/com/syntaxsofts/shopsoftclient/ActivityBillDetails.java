package com.syntaxsofts.shopsoftclient;

import java.io.File;
import java.io.FilenameFilter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityBillDetails extends Activity {

	ListView lstFiles;
	File root;
	
	Button btnSetDelivered;
	Button btnDelete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_details);
		
		lstFiles = (ListView)findViewById(R.id.lstBills);
	
		final String[] lstBills = getBillNames(); 
		
		ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, lstBills);
		lstFiles.setAdapter(adapter);
		
		lstFiles.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
				openFileAndShowBill(new File(root, lstBills[position]));
			}
			
		});
		
	}
	
	private String[] getBillNames()
	{
		root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Bills");
		
		if(!root.exists())
		{
			Toast.makeText(getApplicationContext(), "No bill records found on sdcard", Toast.LENGTH_LONG).show();
		}
		
		return root.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				if(filename.contains("ShopSoftCli-") && filename.endsWith(".txt"))
					return true;
				return false;
			}
		});
	}
	
	private void openFileAndShowBill(File nFile)
	{
		((dependencies)getApplicationContext()).setBillFile(nFile);
		Intent mIntent = new Intent(ActivityBillDetails.this, ActivityBill.class);
		startActivity(mIntent);
	}
}
