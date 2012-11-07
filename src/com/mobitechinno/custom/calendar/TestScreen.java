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
package com.mobitechinno.custom.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import com.mobitechinno.custom.calendar.views.CustomCalendar;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
* This activity shows how to use the CustomCalendar view.
*
* @version 1.0 07 Nov 2012
* @author Yavor Yordanov
*/
public class TestScreen extends Activity {
	// instance variables

	private ArrayList<Item> yearItems;
	private ArrayList<Item> monthItems;
	private int currYearIndex;
	private int currMonthIndex;
	private Calendar todayCal;
	
	// UI components
	private CustomCalendar customCalendar;
	private TextView tvYears;
	private TextView tvMonths;
	private Button btnYearPrev;
	private Button btnYearNext;
	private Button btnMonthPrev;
	private Button btnMonthNext;

	// inner classes
	private class Item {
		private int id;
		private String title;
		
		private Item(int id, String title) {
			this.id = id;
			this.title = title;
		}
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.test_screen);
        
        initVars();
        loadYears();
        loadMonths();
        initUIComponents();
        setListeners();
        main();
    }

    private void initVars() {
    	todayCal = Calendar.getInstance();
    }

    private void initUIComponents() {
    	customCalendar = (CustomCalendar) findViewById(R.id.custom_calendar);
    	tvYears = (TextView) findViewById(R.id.tv_years);
    	tvMonths = (TextView) findViewById(R.id.tv_months);
    	btnYearPrev = (Button) findViewById(R.id.btn_prev_year);
    	btnYearNext = (Button) findViewById(R.id.btn_next_year);
    	btnMonthPrev = (Button) findViewById(R.id.btn_prev_month);
    	btnMonthNext = (Button) findViewById(R.id.btn_next_month);
    }
    
    private void setListeners() {
    	btnYearPrev.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (currYearIndex > 0) {
					currYearIndex--;
				}
				
				updateCalendar();
			}
		});
    	
    	btnYearNext.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (currYearIndex < yearItems.size() - 1) {
					currYearIndex++;
				}
				
				updateCalendar();
			}
		});
    	
    	btnMonthPrev.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (currMonthIndex > 0) {
					currMonthIndex--;
				}
				
				updateCalendar();
			}
		});
    	
    	btnMonthNext.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (currMonthIndex < monthItems.size() - 1) {
					currMonthIndex++;
				}
				
				updateCalendar();
			}
		});
    }

    private void loadYears() {
    	int currYear;
    	
    	currYear = todayCal.get(Calendar.YEAR);
    	
    	yearItems = new ArrayList<Item>();

    	for (int i = currYear - 10; i <= currYear + 10; i++) {
    		yearItems.add(new Item(i, i + ""));
    	}
    	
    	currYearIndex = 10;
    }
    
    private void loadMonths() {
    	currMonthIndex = todayCal.get(Calendar.MONTH);
    	
    	monthItems = new ArrayList<Item>();
    	
    	monthItems.add(new Item(0, "January"));
    	monthItems.add(new Item(1, "February"));
    	monthItems.add(new Item(2, "March"));
    	monthItems.add(new Item(3, "April"));
    	monthItems.add(new Item(4, "May"));
    	monthItems.add(new Item(5, "June"));
    	monthItems.add(new Item(6, "July"));
    	monthItems.add(new Item(7, "August"));
    	monthItems.add(new Item(8, "September"));
    	monthItems.add(new Item(9, "October"));
    	monthItems.add(new Item(10, "November"));
    	monthItems.add(new Item(11, "December"));
    }
    
    private void main() {
    	updateCalendar();
    }

    private void updateCalendar() {
    	tvYears.setText(yearItems.get(currYearIndex).title);
    	tvMonths.setText(monthItems.get(currMonthIndex).title);
    	
    	customCalendar.generateDays(yearItems.get(currYearIndex).id, 
    			monthItems.get(currMonthIndex).id);
    }
}