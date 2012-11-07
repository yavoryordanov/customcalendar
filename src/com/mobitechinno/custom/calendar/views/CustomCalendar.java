/*
*  Copyright 2012 Mobitech Innovations
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.mobitechinno.custom.calendar.views;

import java.util.Calendar;
import com.mobitechinno.custom.calendar.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
* Custom calendar which shows the days of a particular month.
*
* @version 1.0 07 Nov 2012
* @author Yavor Yordanov
*/
public class CustomCalendar extends LinearLayout {
	// instance variables

	private Context context;
	private Resources resources;
	
	// colors
	private int colorTextToday;
	private int colorBgToday;
	private int colorTextCurrMonthWeekday;
	private int colorBgCurrMonthWeekday;
	private int colorTextCurrMonthWeekend;
	private int colorBgCurrMonthWeekend;
	private int colorTextNotCurrMonthWeekday;
	private int colorBgNotCurrMonthWeekday;
	private int colorTextNotCurrMonthWeekend;
	private int colorBgNotCurrMonthWeekend;

	// calendars
	private Calendar currMontCal;
	private Calendar prevMontCal;
	
	// today
	private int todayYear;
	private int todayMonth;
	private int todayDay;

	public CustomCalendar(Context context) {
		this(context, null);
	}

	public CustomCalendar(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

		init();
	}

	/**
	 * Initiates all instance variables. 
	 * */
	private void init() {
		// inflates a view from an XML resource
		View.inflate(context, R.layout.custom_calendar, this);

		resources = context.getResources();

		currMontCal = Calendar.getInstance();

		prevMontCal = Calendar.getInstance();
		prevMontCal.add(Calendar.MONTH, -1);

		todayYear = currMontCal.get(Calendar.YEAR);
		todayMonth = currMontCal.get(Calendar.MONTH);
		todayDay = currMontCal.get(Calendar.DAY_OF_MONTH);

		initColors();
	}

	/** 
	 * Initiates all colors.
	 * */
	private void initColors() {
		colorTextToday = resources.getColor(R.color.text_today);
		colorBgToday = resources.getColor(R.color.bg_today);
		colorTextCurrMonthWeekday = resources.getColor(R.color.text_curr_month_weekday);
		colorBgCurrMonthWeekday = resources.getColor(R.color.bg_curr_month_weekday);
		colorTextCurrMonthWeekend = resources.getColor(R.color.text_curr_month_weekend);
		colorBgCurrMonthWeekend = resources.getColor(R.color.bg_curr_month_weekend);
		colorTextNotCurrMonthWeekday = resources.getColor(R.color.text_not_curr_month_weekday);
		colorBgNotCurrMonthWeekday = resources.getColor(R.color.bg_not_curr_month_weekday);
		colorTextNotCurrMonthWeekend = resources.getColor(R.color.text_not_curr_month_weekend);
		colorBgNotCurrMonthWeekend = resources.getColor(R.color.bg_not_curr_month_weekend);
	}
	
	/**
	 * Generates the days of a particular month from a particular year.
	 * */
	public void generateDays(int year, int month) {
		int firstDayOfWeek;
		int lastDayPrevMonth;
		int lastDayCurrMonth;

		// sets a Calendar object for the current month
		currMontCal.set(Calendar.YEAR, year);
		currMontCal.set(Calendar.MONTH, month);
		currMontCal.set(Calendar.DAY_OF_MONTH, 1);

		// sets a Calendar object for the previous month
		prevMontCal.set(Calendar.YEAR, year);
		prevMontCal.set(Calendar.MONTH, month);
		prevMontCal.add(Calendar.MONTH, -1);

		// gets last day of the current and previous month 
		lastDayPrevMonth = prevMontCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		lastDayCurrMonth = currMontCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// gets the first day of the current month as day of a week i.e. Monday, Tuesday etc.
		firstDayOfWeek = currMontCal.get(Calendar.DAY_OF_WEEK);

		// converts the first day of the current month to Integer between 0 and 6
		switch (firstDayOfWeek) {
			case Calendar.MONDAY : {
				firstDayOfWeek = 0;

				break;
			}
			case Calendar.TUESDAY : {
				firstDayOfWeek = 1;
	
				break;
			}
			case Calendar.WEDNESDAY : {
				firstDayOfWeek = 2;
				
				break;
			}
			case Calendar.THURSDAY: {
				firstDayOfWeek = 3;
				
				break;
			}
			case Calendar.FRIDAY: {
				firstDayOfWeek = 4;
				
				break;
			}
			case Calendar.SATURDAY : {
				firstDayOfWeek = 5;
				
				break;
			}
			case Calendar.SUNDAY : {
				firstDayOfWeek = 6;
				
				break;
			}
		}

		// loops through all 42 days
		for (int i = 0; i < 42; i++) {
			TextView tvCurrDay;
			boolean isToday;
			boolean isPrevMonth; 
			boolean isNextMonth;
			boolean isWeekend;
			int row;
			int index;
			int dayNumber;
			String strDayNumber;

			row = i / 7;
			index = i % 7;
			isToday = false;
			isPrevMonth = false;
			isNextMonth = false;
			isWeekend = false;

			// calculates the current day number and the status of the current day 
			if (i < firstDayOfWeek) {
				// previous month
				isPrevMonth = true;
				dayNumber = lastDayPrevMonth - (firstDayOfWeek - i) + 1;
			} else if (i >= firstDayOfWeek && i < (lastDayCurrMonth + firstDayOfWeek)) {
				// current month
				dayNumber = (i - firstDayOfWeek) + 1;
				
				if (todayDay == dayNumber && todayMonth == currMontCal.get(Calendar.MONTH) &&
						todayYear == currMontCal.get(Calendar.YEAR)) {
					isToday = true;
				}
			} else {
				// next month
				isNextMonth = true;

				dayNumber = (i % (lastDayCurrMonth + firstDayOfWeek)) + 1;
			}

			if (i % 7 >= 5) {
				isWeekend = true;
			}

			// gets a TextView for the current day
			tvCurrDay = getTextView(row, index);
			strDayNumber = dayNumber + "";

			// modifies the TextView properties
			if (isToday) {
				modifyToday(tvCurrDay, strDayNumber);
			} else if (isPrevMonth || isNextMonth) {
				if (isWeekend) {
					modifyNotCurrMonthWeekend(tvCurrDay, strDayNumber);
				} else {
					modifyNotCurrMonthWeekday(tvCurrDay, strDayNumber);
				}
			} else {
				if (isWeekend) {
					modifyCurrMonthWeekend(tvCurrDay, strDayNumber);
				} else {
					modifyCurrMonthWeekday(tvCurrDay, strDayNumber);
				}
			}
		}
	}

	private void modifyToday(TextView tv, String text) {
		modifyTextView(tv, colorTextToday, colorBgToday, text);
	}

	private void modifyCurrMonthWeekday(TextView tv, String text) {
		modifyTextView(tv, colorTextCurrMonthWeekday, colorBgCurrMonthWeekday, text);
	}

	private void modifyCurrMonthWeekend(TextView tv, String text) {
		modifyTextView(tv, colorTextCurrMonthWeekend, colorBgCurrMonthWeekend, text);
	}

	private void modifyNotCurrMonthWeekday(TextView tv, String text) {
		modifyTextView(tv, colorTextNotCurrMonthWeekday, colorBgNotCurrMonthWeekday, text);
	}

	private void modifyNotCurrMonthWeekend(TextView tv, String text) {
		modifyTextView(tv, colorTextNotCurrMonthWeekend, colorBgNotCurrMonthWeekend, text);
	}

	/**
	 * This method modifies the text color, background color and text of a given
	 * TextView object.
	 * */
	private void modifyTextView(TextView tv, int textColor, int bgColor, String text) {
		tv.setTextColor(textColor);
		tv.setBackgroundColor(bgColor);
		tv.setText(text);
	}
	
	/**
	 * Returns a TextView object by row and index.
	 * */
	private TextView getTextView(int row, int index) {
		int rowIndex;
		int childIndex;
		
		rowIndex = (row + 1) * 2;
		childIndex = index * 2;

		return (TextView) ((LinearLayout) ((LinearLayout) getChildAt(0)).getChildAt(rowIndex)).getChildAt(childIndex);
	}
}