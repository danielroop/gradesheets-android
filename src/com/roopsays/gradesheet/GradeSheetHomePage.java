package com.roopsays.gradesheet;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnDrawListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.roopsays.gradesheet.model.GradesheetHistory;
import com.roopsays.gradesheet.model.GradesheetMeta;

/**
 * An activity representing a list of GradeSheets. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link GradeSheet} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 */
public class GradeSheetHomePage extends FragmentActivity {

	private GradesheetHistory history;
	private SharedPreferences sharedPref;
	private int textSize;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradesheet_homepage);
		
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		textSize = Integer.parseInt(sharedPref.getString("fontSizeId", "18"));
		history = new GradesheetHistory(getApplicationContext());
		
		
		Button button = (Button) findViewById(R.id.create_gradesheet_button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView text = (TextView) findViewById(R.id.gradesheet_numberofquestions);
				int numberOfQuestions = Integer.parseInt(text.getText().toString());
				
				history.updateUsage(numberOfQuestions);

				Intent detailIntent = new Intent(GradeSheetHomePage.this, GradeSheet.class);
				detailIntent.putExtra(GradeSheetDetailFragment.ARG_ITEM_ID, numberOfQuestions);
				startActivity(detailIntent);
			}
		});
		
		List<GradesheetMeta> topResults = history.topGradesheetRequests();
		LinearLayout view = (LinearLayout) findViewById(R.id.gradesheet_history_list);
		
		for(int i = 0; i < topResults.size(); i++) {
			final int numberOfQuestions = topResults.get(i).getNumberOfQuestions();
			
			TextView topResultTextView = new TextView(this);
			topResultTextView.setText(String.valueOf(numberOfQuestions));
			topResultTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

			
			RelativeLayout innerLayout = new RelativeLayout(this);
			innerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 100));
			innerLayout.setClickable(true);
			innerLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					history.updateUsage(numberOfQuestions);
	
					Intent detailIntent = new Intent(GradeSheetHomePage.this, GradeSheet.class);
					detailIntent.putExtra(GradeSheetDetailFragment.ARG_ITEM_ID, numberOfQuestions);
					startActivity(detailIntent);
				}
			});
			
			TextView numberOfUses = new TextView(this);
			numberOfUses.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			numberOfUses.setText("Number of Uses: " + String.valueOf(topResults.get(i).getNumberOfTimesAccessed()));
			numberOfUses.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
			numberOfUses.setTextAlignment(TextView.TEXT_ALIGNMENT_VIEW_END);

			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) numberOfUses.getLayoutParams();
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			
			innerLayout.setBackgroundResource(R.drawable.top_result_box);
			innerLayout.addView(topResultTextView);
			innerLayout.addView(numberOfUses);

			LinearLayout outerLayout = new LinearLayout(this);
			outerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			outerLayout.setPadding(10,  10,  10,  10);
			outerLayout.addView(innerLayout);

			
			view.addView(outerLayout);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global_menu, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		switch(item.getItemId()){
        	case R.id.settings:
    			Intent settingsIntent = new Intent(this, SettingsActivity.class);
    			startActivity(settingsIntent);
            break;

		}
	
		return true;
	}
}
