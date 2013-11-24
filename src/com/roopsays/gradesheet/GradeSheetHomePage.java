package com.roopsays.gradesheet;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnDrawListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 * The activity makes heavy use of fragments. The list of items is a
 * {@link GradeSheetListFragment} and the item details (if present) is a
 * {@link GradeSheetDetailCustomFragment}.
 * <p>
 * This activity also implements the required
 * {@link GradeSheetListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class GradeSheetHomePage extends FragmentActivity implements
		GradeSheetListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private GradesheetHistory history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradesheet_homepage);

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
			TextView topResultTextView = new TextView(this);
			topResultTextView.setText(String.valueOf(topResults.get(i).getNumberOfQuestions()) + "::" + String.valueOf(topResults.get(i).getNumberOfTimesAccessed()));
			topResultTextView.setTextSize(20);
			view.addView(topResultTextView);
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
	
	
	
	/**
	 * Callback method from {@link GradeSheetListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {		
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(GradeSheetDetailFragment.ARG_ITEM_ID, id);
			GradeSheetDetailCustomFragment fragment = new GradeSheetDetailCustomFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.gradesheet_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					GradeSheet.class);
			detailIntent.putExtra(GradeSheetDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
