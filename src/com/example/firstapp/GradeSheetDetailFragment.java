package com.example.firstapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

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

	
	private Integer numberOfQuestions;
	private Typeface robotoCondRegular;
	private Typeface robotoBold;

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
			numberOfQuestions = getArguments().getInt(ARG_ITEM_ID);
		}
		
		robotoCondRegular = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "RobotoCondensed-Regular.ttf");
		robotoBold = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "Roboto-Bold.ttf");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_gradesheet_detail,
				container, false);

		if (numberOfQuestions != null) {
			createQuestionCards(rootView, numberOfQuestions);
		}

		return rootView;
	}

	public void createQuestionCards(View parentView, int numberOfQuestions) {
		LinearLayout scoreList = (LinearLayout) parentView.findViewById(R.id.numberOfQuestionList);

		LinearLayout layout = new LinearLayout(getActivity());
		for (int correctAnswers = 0; correctAnswers <= numberOfQuestions; correctAnswers++) {
			Integer score = Math.round(correctAnswers * 100 / numberOfQuestions);

			if (correctAnswers % 21 == 0) {
				layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				scoreList.addView(layout);
			}
			
			renderScoreLayout(layout, correctAnswers, score);
			
		}

	}

	public LinearLayout renderScoreLayout(LinearLayout layout, Integer correctAnswers, Integer score) {
		int textWidth = 80;
		
		
		LayoutParams correctAnswerLayoutParams = new LayoutParams(textWidth, LayoutParams.WRAP_CONTENT);

		TextView correctAnswerTextView = new TextView(getActivity());
		correctAnswerTextView.setText(String.valueOf(correctAnswers));
		correctAnswerTextView.setLayoutParams(correctAnswerLayoutParams);
		correctAnswerTextView.setGravity(Gravity.CENTER);
		correctAnswerTextView.setTextAppearance(getActivity().getApplicationContext(), R.style.GradeNumber);
		correctAnswerTextView.setTypeface(robotoBold);
		
		

		TextView dividerTextView = new TextView(getActivity());
		dividerTextView.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
		dividerTextView.setBackgroundColor(Color.BLACK);

		LayoutParams scoreTextViewLayoutParams = new LayoutParams(textWidth, LayoutParams.WRAP_CONTENT);

		TextView scoreTextView = new TextView(getActivity());
		scoreTextView.setText(String.valueOf(score) + "%");
		scoreTextView.setLayoutParams(scoreTextViewLayoutParams);
		scoreTextView.setGravity(Gravity.CENTER);
		scoreTextView.setTextAppearance(getActivity().getApplicationContext(), R.style.GradeScore);
		correctAnswerTextView.setTypeface(robotoCondRegular);



		LinearLayout innerLayout = new LinearLayout(getActivity());
		innerLayout.setOrientation(LinearLayout.HORIZONTAL);
		innerLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		innerLayout.addView(correctAnswerTextView);
		//innerLayout.addView(dividerTextView);
		innerLayout.addView(scoreTextView);
		layout.addView(innerLayout);

		return layout;
	}
}
