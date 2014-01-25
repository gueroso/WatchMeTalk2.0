package com.google.android.glass.WatchMeTalk;


import java.util.List;

import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;

class MemoScrollAdapter extends CardScrollAdapter
{
private List<Card> mCards;
public MemoScrollAdapter(List<Card> cards) 
{
super();
this.mCards = cards;
}
@Override
public int findIdPosition(Object id) 
{
return -1;
}

@Override
public int findItemPosition(Object item) 
{
return mCards.indexOf(item);
}

@Override
public int getCount() 
{
return mCards.size();
}

@Override
public Object getItem(int position) 
{
return mCards.get(position);
}

@Override
public View getView(int position, View convertView, ViewGroup parent) 
{
return mCards.get(position).toView();
} 
//Remove a memo from the card scroll 
public void removeMemo(int position)
{
mCards.remove(position) ;
updateMemoNumbers() ;
notifyDataSetChanged() ;
}
//updates the numbering on the memos
public void updateMemoNumbers()
{
for (int i  = 0 ; i < mCards.size() ; i++)
{
mCards.get(i).setFootnote("Memo " + (i+1) + " of " +  mCards.size());
}
}
}