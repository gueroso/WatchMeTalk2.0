package com.google.android.glass.WatchMeTalk;

import java.util.ArrayList;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class DisplayService extends Service {
	
	private TimelineManager mTimeLineManager;
	private boolean update;
	private LiveCard mLiveCard;
	private String cardID = "WatchMeTalk";
	private String cardText="";
	
	
	
	ArrayList<String> list = Util.arrayList;
	
	
	
	
	@Override
	public void onCreate(){
		super.onCreate();
		mTimeLineManager = TimelineManager.from(this);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		update = intent.getBooleanExtra("update", false);
		
		if(mLiveCard == null){
			mLiveCard = mTimeLineManager.createLiveCard(cardID);
			RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.card_layout);
			if (list.size() > 0){
				for (int i = 0; i< list.size() && i < 5; i ++){
					cardText += "" + list.get(i) + "\n";
				}
				remoteViews.setTextViewText(R.id.memo_list_text_view, cardText);
				remoteViews.setTextViewText(R.id.no_memos_text_view, "");
			}
			else{
				remoteViews.setTextViewText(R.id.memo_list_text_view, "");
				remoteViews.setTextViewText(R.id.no_memos_text_view, "No One Spoke");
			}
			mLiveCard.setViews(remoteViews);
			Intent menu;
			if (list.size() == 0){
				menu = new Intent(this, ViewMemoMenuActivityNoMemos.class);
			}
			else{
				menu = new Intent(this, ViewMemoMenuActivity.class);
			}
			mLiveCard.setAction(PendingIntent.getActivity(this, 0, menu, 0));
			
			if (update){
				mLiveCard.publish(LiveCard.PublishMode.SILENT);
			}
			else{
				mLiveCard.publish(LiveCard.PublishMode.SILENT);
			}
		}
		else{
			mLiveCard.unpublish();
			mLiveCard.publish(LiveCard.PublishMode.REVEAL);
		
		}
		return START_STICKY;
		
	}
	
	@Override
	public void onDestroy(){
		if (mLiveCard!=null && mLiveCard.isPublished())
		{
			mLiveCard.unpublish();
			mLiveCard = null;
		}
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
