package com.example.firstapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.firstapp.dummy.DummyContent;

/**
 * A fragment representing a single GradeSheet detail screen. This fragment is
 * either contained in a {@link GradeSheetHomePage} in two-pane mode (on
 * tablets) or a {@link GradeSheet} on handsets.
 */
public class GradeSheetDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public GradeSheetDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_gradesheet_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			createQuestionCareds(rootView, Integer.parseInt(mItem.content));
		}

		return rootView;
	}

	public void createQuestionCareds(View parentView, int numberOfQuestions) {
		LinearLayout scoreList = (LinearLayout) parentView.findViewById(R.id.numberOfQuestionList);

		LinearLayout layout = new LinearLayout(getActivity());
		for (int correctAnswers = 0; correctAnswers <= numberOfQuestions; correctAnswers++) {
			Integer score = Math.round(correctAnswers * 100 / numberOfQuestions);

			if (correctAnswers % 3 == 0) {
				layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				scoreList.addView(layout);
			}
			
			renderScoreLayout(layout, correctAnswers, score);
			
		}

	}

	public LinearLayout renderScoreLayout(LinearLayout layout, Integer correctAnswers, Integer score) {
		LayoutParams correctAnswerLayoutParams = new LayoutParams(125, LayoutParams.WRAP_CONTENT);
		correctAnswerLayoutParams.setMargins(5, 0, 5, 0);

		TextView correctAnswerTextView = new TextView(getActivity());
		correctAnswerTextView.setText(String.valueOf(correctAnswers) + "  ");
		correctAnswerTextView.setLayoutParams(correctAnswerLayoutParams);

		correctAnswerTextView.setGravity(Gravity.CENTER);
		correctAnswerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);
		correctAnswerTextView.setBackgroundColor(Color.LTGRAY);

		TextView dividerTextView = new TextView(getActivity());
		dividerTextView.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
		dividerTextView.setBackgroundColor(Color.BLACK);

		LayoutParams scoreTextViewLayoutParams = new LayoutParams(125, LayoutParams.WRAP_CONTENT);
		scoreTextViewLayoutParams.setMargins(5, 5, 5, 5);

		TextView scoreTextView = new TextView(getActivity());
		scoreTextView.setText("  " + String.valueOf(score) + "%");
		scoreTextView.setLayoutParams(scoreTextViewLayoutParams);
		scoreTextView.setGravity(Gravity.CENTER);
		scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);

		layout.addView(correctAnswerTextView);
		layout.addView(dividerTextView);
		layout.addView(scoreTextView);

		return layout;
	}
}
