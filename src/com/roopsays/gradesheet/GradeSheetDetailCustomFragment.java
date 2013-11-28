package com.roopsays.gradesheet;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * A fragment representing a single GradeSheet detail screen. This fragment is
 * either contained in a {@link GradeSheetHomePage} in two-pane mode (on
 * tablets) or a {@link GradeSheet} on handsets.
 */
public class GradeSheetDetailCustomFragment extends GradeSheetDetailFragment{
	public static final String TAG = "GradeSheetDetailCustomFragment";
	
	private SharedPreferences sharedPref;
	private int textSize;
	private int numberOfRows;
	private Map<String, Integer> dimensions;

	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public GradeSheetDetailCustomFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_gradesheet_detail, container, false);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		textSize = Integer.parseInt(sharedPref.getString("fontSizeId", "18"));

		
		runJustBeforeBeingDrawn(rootView,new Runnable()
		{
		  @Override
		  public void run()
		  {
		    dimensions = getDimensions(getActivity().getApplicationContext(), "1000", textSize, 720, GradeSheetFonts.robotoBold, 0);
		    numberOfRows = (rootView.getHeight()/dimensions.get("height"));
		    
			if (numberOfQuestions != null) {
				createQuestionCards(rootView, numberOfQuestions);
			}
		  }
		});
		return rootView;
	}
	
	private static void runJustBeforeBeingDrawn(final View view, final Runnable runnable)
	{
	    final ViewTreeObserver vto = view.getViewTreeObserver();
	    final OnPreDrawListener preDrawListener = new OnPreDrawListener()
	    {
	        @Override
	        public boolean onPreDraw()
	        {
	            Log.d(TAG, "onpredraw");
	            runnable.run();
	            final ViewTreeObserver vto = view.getViewTreeObserver();
	            vto.removeOnPreDrawListener(this);
	            return true;
	        }
	    };
	    vto.addOnPreDrawListener(preDrawListener);
	}
	
	public static Map<String, Integer> getDimensions(Context context, CharSequence text, int textSize, int deviceWidth, Typeface typeface,int padding) {
        TextView textView = new TextView(context);
        textView.setPadding(padding,0,padding,padding);
        textView.setTypeface(typeface);
        textView.setText(text, TextView.BufferType.SPANNABLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        
        Map<String, Integer> results = new HashMap<String, Integer>();
        results.put("height", textView.getMeasuredHeight());
        results.put("width", textView.getMeasuredWidth());
        return results;
    }

	public void createQuestionCards(View parentView, int numberOfQuestions) {
		Boolean displayNumberWrong = sharedPref.getBoolean("displayNumberWrong", false);

		LinearLayout scoreList = (LinearLayout) parentView.findViewById(R.id.numberOfQuestionList);

		if (displayNumberWrong) { 
			createNumberWrongCards(scoreList);
		} else {
			createNumberRightCards(scoreList);
		}
	}
	
	public void createNumberRightCards(LinearLayout scoreList) {
		LinearLayout layout = new LinearLayout(getActivity());
		
		for (int correctAnswers = 0; correctAnswers <= numberOfQuestions; correctAnswers++) {
			Integer score = Math.round(correctAnswers * 100 / numberOfQuestions);

			if (correctAnswers % numberOfRows == 0) {
				layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				scoreList.addView(layout);
			}
			
			renderScoreLayout(layout, correctAnswers, score);
		}
	}
	
	public void createNumberWrongCards(LinearLayout scoreList) {
		LinearLayout layout = new LinearLayout(getActivity());
		
		for (int wrongAnswers = 0; wrongAnswers <= numberOfQuestions; wrongAnswers++) {
			Integer score = Math.round((numberOfQuestions - wrongAnswers) * 100 / numberOfQuestions);

			if (wrongAnswers % numberOfRows == 0) {
				layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				scoreList.addView(layout);
			}
			
			renderScoreLayout(layout, wrongAnswers, score);
		}
	}

	public LinearLayout renderScoreLayout(LinearLayout layout, Integer label, Integer score) {
		LayoutParams correctAnswerLayoutParams = new LayoutParams(dimensions.get("width"), LayoutParams.WRAP_CONTENT);

		TextView correctAnswerTextView = new TextView(getActivity());
		correctAnswerTextView.setText(String.valueOf(label));
		correctAnswerTextView.setLayoutParams(correctAnswerLayoutParams);
		correctAnswerTextView.setGravity(Gravity.CENTER);
		correctAnswerTextView.setTextAppearance(getActivity().getApplicationContext(), R.style.GradeNumber);
		correctAnswerTextView.setTypeface(GradeSheetFonts.robotoBold);		
		correctAnswerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

		TextView dividerTextView = new TextView(getActivity());
		dividerTextView.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
		dividerTextView.setBackgroundColor(Color.BLACK);

		LayoutParams scoreTextViewLayoutParams = new LayoutParams(dimensions.get("width"), LayoutParams.WRAP_CONTENT);

		TextView scoreTextView = new TextView(getActivity());
		scoreTextView.setText(String.valueOf(score) + "%");
		scoreTextView.setLayoutParams(scoreTextViewLayoutParams);
		scoreTextView.setGravity(Gravity.CENTER);
		scoreTextView.setTextAppearance(getActivity().getApplicationContext(), R.style.GradeScore);
		scoreTextView.setTypeface(GradeSheetFonts.robotoCondRegular);
		scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);



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
