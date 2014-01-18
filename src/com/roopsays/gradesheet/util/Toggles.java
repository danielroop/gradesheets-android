package com.roopsays.gradesheet.util;

public enum Toggles {
	ENABLE_ADS(true),
	ENABLE_GRADE_SCALE(false);
	
	private boolean defaultState;
	
	Toggles(boolean defaultState) {
		this.defaultState = defaultState;
	}
	
	public boolean on() {
		return defaultState;
	}
}

