package com.google.android.glass.WatchMeTalk;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.glass.app.Card;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardScrollView;

public class MemoScrollActivity extends Activity 
{
private GestureDetector openMenuGesture;
private List<Card> mCards;
private CardScrollView mCardScrollView;
private ArrayList<String> memoList;
private MemoScrollAdapter adapter;

@Override
protected void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
//Create the cards to be used in the CardScrollView
createCards();
//Set up the gesture recognizer 
openMenuGesture = createGestureDetector(this);
//Create the the CardScrollView
mCardScrollView = new CardScrollView(this);
//Create the adapter for the cardscoll
adapter = new MemoScrollAdapter(mCards);
mCardScrollView.setAdapter(adapter);
mCardScrollView.activate();
setContentView(mCardScrollView);
}

@Override
public void onResume() 
{
super.onResume();
//Get the saved list of memos from shared preferences 

//Check if a memo has been deleted 
if (adapter.getCount() > memoList.size())
{
//remove the card of the memo that has been deleted  
adapter.removeMemo(mCardScrollView.getSelectedItemPosition());
//refresh the service. This allows the list in the live card to update 
stopService(new Intent(this , DisplayService.class));
Intent serviceIntent = new Intent(this , DisplayService.class);
serviceIntent.putExtra("update" , true);
startService(serviceIntent);
}
//close the card scroll view if there are no more memos 
if (memoList.size() == 0)
{
finish();
}

adapter.notifyDataSetChanged();
}
private void createCards() 
{
//initalize the array list that will hold all the cards 
mCards = new ArrayList<Card>();


//Create a card for each memo
Card tempMemoCard;
for (int i = 0 ; i < memoList.size() ; i++)
{
tempMemoCard = new Card(this);
tempMemoCard.setText(memoList.get(i));
tempMemoCard.setFootnote("Memo " + (i+1) + " of " +  memoList.size());
mCards.add(tempMemoCard);
}
}
//Gesture recognizer. this will handle opening the menu when a card is clicked
private GestureDetector createGestureDetector(final Context context)
{
GestureDetector gestureDetector = new GestureDetector(context);
//Create a base listener for generic gestures
gestureDetector.setBaseListener( new GestureDetector.BaseListener() 
{
@Override
public boolean onGesture(Gesture gesture) 
{
if (gesture == Gesture.LONG_PRESS) 
{
//create intent to open the menu
Intent memoScrollMenuIntent = new Intent(context , MemoScrollActivity.class); 
//pass card index and card text to the intent
memoScrollMenuIntent.putExtra("scrollposition", mCardScrollView.getSelectedItemPosition())
.putExtra("memotext" , memoList.get(mCardScrollView.getSelectedItemPosition()));
//open the menu
startActivity(memoScrollMenuIntent);
return true;
}
return false;
}
});
return gestureDetector;
}

/*
* Send generic motion events to the gesture detector
*/
@Override
public boolean onGenericMotionEvent(MotionEvent event) 
{
if (openMenuGesture != null) 
{
return openMenuGesture.onMotionEvent(event);
}
return false;
}

}
