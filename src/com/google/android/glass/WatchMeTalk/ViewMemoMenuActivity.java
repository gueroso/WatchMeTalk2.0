/**
 * OpenQuartz Memo for Glass
 * Github - https://github.com/jaredsburrows/OpenQuartz
 * @author Andre Compagno
 * 
 * Copyright (C) 2013 OpenQuartz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.glass.WatchMeTalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



public class ViewMemoMenuActivity extends Activity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState); 
    }

    @Override
    public void onResume() 
    {
        super.onResume();
        openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        int itemId = item.getItemId();
		if (itemId == R.id.close) {
			//close the card 
			stopService(new Intent(this, DisplayService.class));
			return true;
		} else if (itemId == R.id.view_memo_scroll) {
			//open the card scroll activity
            startActivity(new Intent(this, MemoScrollActivity.class));
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) 
    {
        finish();
    }
}
