package com.syntaxsofts.shopsoftclient;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityChat extends Activity implements OnClickListener{

	Button btnSend;
	Button btnCheck;
	
	ListView lstChat;
	
	dependencies mDependencies;
	
	ArrayList<String> mList;
	ArrayAdapter<String> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		btnSend = (Button)findViewById(R.id.btnSend);
		btnCheck = (Button)findViewById(R.id.btnCheck);
		lstChat = (ListView)findViewById(R.id.lstChat);
		mDependencies = (dependencies)getApplicationContext();
		
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle("Chat");
		mActionBar.setSubtitle(mDependencies.getSelectedShop());
		
		btnSend.setOnClickListener(this);
		btnCheck.setOnClickListener(this);
		
		mList = new ArrayList<String>();
		
		mList.add("Chat with " + mDependencies.getSelectedShop());
		mAdapter = new ArrayAdapter<String>(ActivityChat.this, R.layout.list_subcategories_layout, mList);
		
		lstChat.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.btnSend:
			
			final Dialog mDialog = new Dialog(ActivityChat.this);
			mDialog.setContentView(R.layout.inputbox);
			mDialog.setTitle("Message to send");
			
			final EditText txtInput = (EditText)mDialog.findViewById(R.id.txtInputBox);
			Button btnInputOk = (Button)mDialog.findViewById(R.id.btnInputBox);
			
			txtInput.setInputType(InputType.TYPE_CLASS_TEXT);
			
			btnInputOk.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String msgText = txtInput.getText().toString().trim();
					if(msgText.length() > 0 )
					{
						try
						{
							String sentID = new sendMessage().execute(new String[] {String.valueOf(
									mDependencies.getCustomerID(ActivityChat.this)),mDependencies.getSelectedShop(),msgText}).get();
							if(sentID.contains("sentmessageID"))
							{
								Toast.makeText(ActivityChat.this, "Your message has been sent", Toast.LENGTH_SHORT).show();
								mDependencies.newChatMsg(Integer.parseInt(sentID.split("%")[1].trim()), ActivityChat.this);
								mList.add("You: " + msgText);
								mAdapter.notifyDataSetChanged();
								mDialog.dismiss();
							}
							else
							{
								Toast.makeText(ActivityChat.this, "Cannot send the message", Toast.LENGTH_SHORT).show();
							}
						}
						catch(Exception ex)
						{
							Log.d("sendMsg",ex.toString());
							Toast.makeText(ActivityChat.this, "Cannot send the message, internal error", Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
			
			mDialog.show();
			break;
			
		case R.id.btnCheck:
			
			btnCheck.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					try
					{
						String newMsg = new checkMessage().execute(new String[] {String.valueOf(
								mDependencies.getCustomerID(ActivityChat.this)),
								mDependencies.getSelectedShop(),
								String.valueOf(mDependencies.getLastChatMsgID(ActivityChat.this))}).get();
								
						if(newMsg.contains("nonewmsgs"))
						{
							Toast.makeText(ActivityChat.this, "No new messages", Toast.LENGTH_SHORT).show();
						}
						else
						{
							mList.add(mDependencies.getSelectedShop() + ": " + newMsg);
							mAdapter.notifyDataSetChanged();
						}
					}
					catch(Exception ex)
					{
						Toast.makeText(ActivityChat.this, "Internal error", Toast.LENGTH_SHORT).show();
						Log.d("checknew", ex.toString());
					}
					
				}
			});
			break;
		}
		
	}
	
}