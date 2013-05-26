package com.example.firstapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.firstapp.MESSAGE";
	
	private EditText numberOfQuestionField;
	private EditText correctAnswersField;
	private TextView scoreField;
	private LinearLayout scoreList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //numberOfQuestionField = (EditText) findViewById(R.id.numberOfQuestions);
        //correctAnswersField = (EditText) findViewById(R.id.numberCorrect);
        //scoreField = (TextView) findViewById(R.id.score);
        //scoreList = (LinearLayout) findViewById(R.id.scoreList);
        
        createQuestionCareds();
        //numberOfQuestionField.addTextChangedListener(new ScoreTextWatcher());
        //correctAnswersField.addTextChangedListener(new ScoreTextWatcher());
    }
    
    public void calculateScore() {
        if ("".equals(numberOfQuestionField.getText().toString()) || "".equals(correctAnswersField.getText().toString())) {
        	return;
        }
        
        Integer numberOfQuestions = Integer.valueOf(numberOfQuestionField.getText().toString());
        Integer correctAnswers = Integer.valueOf(correctAnswersField.getText().toString());
        
        Integer score = Math.round(correctAnswers * 100 / numberOfQuestions);
        
        scoreField.setText(String.valueOf(score) + "%");
    }
    
    public void createQuestionCareds() {
    	LinearLayout numberOfQuestionsList = (LinearLayout) findViewById(R.id.numberOfQuestionList);

    	
    	for (int numberOfQuestions = 46; numberOfQuestions <= 50; numberOfQuestions++) {
    		TextView numberOfQuestionLabel = new TextView(this);
    		numberOfQuestionLabel.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    		numberOfQuestionLabel.setText(String.valueOf(numberOfQuestions));
    		numberOfQuestionLabel.setTextSize(TypedValue.COMPLEX_UNIT_PT, 24);
    	

    		LinearLayout scoreList = new LinearLayout(this);
    		scoreList.setOrientation(LinearLayout.VERTICAL);
    		scoreList.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
   		
    		
    		ScrollView scoreListView = new ScrollView(this);
    		scoreListView.setLayoutParams(new LayoutParams(350, LayoutParams.MATCH_PARENT));
    		scoreListView.addView(scoreList);
    		
    		LinearLayout questionCard = new LinearLayout(this);
    		questionCard.setOrientation(LinearLayout.HORIZONTAL);
    		questionCard.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    	
    		questionCard.addView(numberOfQuestionLabel);
    		questionCard.addView(scoreListView);
    		
    		numberOfQuestionsList.addView(questionCard);
    		
    		for (int correctAnswers = 0; correctAnswers <= (numberOfQuestions / 2); correctAnswers++) {
        		Integer score = Math.round(correctAnswers * 100 / numberOfQuestions);
        		
           	
            	LinearLayout layout = new LinearLayout(this);
            	layout.setOrientation(LinearLayout.HORIZONTAL);  
            	layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
            	layout.addView(renderScoreLayout(correctAnswers, score));
            	
            	if ( (correctAnswers * 2) < numberOfQuestions ) layout.addView(renderScoreLayout(correctAnswers*2, score));

        		
            	scoreList.addView(layout);
        	}
    	}
    }
    
    public LinearLayout renderScoreLayout(Integer correctAnswers, Integer score) {
    	LayoutParams correctAnswerLayoutParams = new LayoutParams(75, LayoutParams.WRAP_CONTENT);
		correctAnswerLayoutParams.setMargins(5,5,5,5);
		
		TextView correctAnswerTextView = new TextView(this);
		correctAnswerTextView.setText(String.valueOf(correctAnswers) + "  ");
		correctAnswerTextView.setLayoutParams(correctAnswerLayoutParams);
	
		correctAnswerTextView.setGravity(Gravity.CENTER);
		correctAnswerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);

		TextView dividerTextView = new TextView(this);
		dividerTextView.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
		dividerTextView.setBackgroundColor(Color.BLACK);
		
		LayoutParams scoreTextViewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		scoreTextViewLayoutParams.setMargins(5, 5, 5, 5);
		
		TextView scoreTextView = new TextView(this);
		scoreTextView.setText("  " + String.valueOf(score) + "%");
		scoreTextView.setLayoutParams(scoreTextViewLayoutParams);
		scoreTextView.setGravity(Gravity.CENTER);
		scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);

    	LinearLayout layout = new LinearLayout(this);
    	layout.setOrientation(LinearLayout.HORIZONTAL);  
    	layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
    	layout.addView(correctAnswerTextView);
    	layout.addView(dividerTextView);
    	layout.addView(scoreTextView);
    	
    	return layout;
    }
    
    
    public void populateOptions() {
    	scoreList.removeAllViews();
    	
        Integer numberOfQuestions = Integer.valueOf(numberOfQuestionField.getText().toString());
    	
    	for (int correctAnswers = 0; correctAnswers <= numberOfQuestions; correctAnswers++) {
    		Integer score = Math.round(correctAnswers * 100 / numberOfQuestions);
    		
    		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    		layoutParams.setMargins(0, 0, 5, 0);
    		
    		TextView correctAnswerTextView = new TextView(this);
    		correctAnswerTextView.setText(String.valueOf(correctAnswers));
    		correctAnswerTextView.setLayoutParams(layoutParams);
    		correctAnswerTextView.setGravity(Gravity.CENTER);
    		correctAnswerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 15);

    		TextView dividerTextView = new TextView(this);
    		dividerTextView.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
    		dividerTextView.setBackgroundColor(Color.BLACK);
    		
    		TextView scoreTextView = new TextView(this);
    		scoreTextView.setText(String.valueOf(score));
    		scoreTextView.setLayoutParams(layoutParams);
    		scoreTextView.setGravity(Gravity.CENTER);
    		scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 12);

    		
        	LinearLayout layout = new LinearLayout(this);
        	layout.setOrientation(LinearLayout.HORIZONTAL);  
        	layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
        	layout.addView(correctAnswerTextView);
        	layout.addView(dividerTextView);
        	layout.addView(scoreTextView);
        	
        	scoreList.addView(layout);
    	}
    	  
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    class ScoreTextWatcher implements TextWatcher {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			//calculateScore();
			populateOptions();

		}
	}
    
}
