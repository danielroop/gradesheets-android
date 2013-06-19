package com.example.firstapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ScrollingNumbersStaticCards extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling_numbers_static_cards);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scrolling_numbers_static_cards, menu);
		return true;
	}

}
