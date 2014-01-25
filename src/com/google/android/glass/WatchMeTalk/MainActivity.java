package com.google.android.glass.WatchMeTalk;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;


public class MainActivity extends Activity {

	public static final int SPEECH_REQUEST = 0;
	private String memoResult;
	private ArrayList<String> memoList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		displaySpeechRecognizer();
	}
	
	private void displaySpeechRecognizer()
	{
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		startActivityForResult(intent, SPEECH_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == SPEECH_REQUEST && resultCode == RESULT_OK)
		{
			//Get results of speech to text
			memoResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
			//get list of memos saved in shared prefs
			
			//add the new memo to the list
			memoList.add(memoResult);
			//Save the new list
			ArrayList<String> memoArrayList = new ArrayList<String>(memoList);
			
			//update the list in the live card
			stopService(new Intent(this, DisplayService.class));
			Intent serviceIntent = new Intent(this, DisplayService.class);
			serviceIntent.putExtra("update", true);
			startService(serviceIntent);
		}
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

}
