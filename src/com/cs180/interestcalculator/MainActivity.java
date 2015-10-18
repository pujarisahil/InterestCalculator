package com.cs180.interestcalculator;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		final Spinner bankSpinner = (Spinner) findViewById(R.id.bankSpinner);
		List<String> list = new ArrayList<String>();
		list.add("Iron Bank of Braavos");
		list.add("Bank of CS180");
		list.add("Khallesi Fedral Credit Union");
		list.add("Bank of the Forsaken");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bankSpinner.setAdapter(dataAdapter);
		final Context context = this;
		Button submitB = (Button) findViewById(R.id.submitB);
		Button resetB = (Button) findViewById(R.id.resetB);
		Button rateFinder = (Button) findViewById(R.id.findRate);
		final EditText nameBox = (EditText) findViewById(R.id.nameField);
		final EditText principalBox = (EditText) findViewById(R.id.principalField);
		final TextView rateBox = (TextView) findViewById(R.id.rateDisplay);
		final EditText yearsBox = (EditText) findViewById(R.id.timeField);
		final RadioGroup radioGender = (RadioGroup) findViewById(R.id.radioSelection);
		final RadioGroup interestSelection = (RadioGroup) findViewById(R.id.chosenInterest);
		final TextView commentsBox = (TextView) findViewById(R.id.comments);


		resetB.setOnClickListener(new View.OnClickListener() {
			//Executes everything that goes inside this body
			public void onClick(View v) {
				principalBox.setText("");
				rateBox.setText("");
				yearsBox.setText("");
				nameBox.setText("");
			}
		});

		rateFinder.setOnClickListener(new View.OnClickListener() {
			//Executes everything that goes inside this body
			public void onClick(View v) {
				String chosenBank = bankSpinner.getSelectedItem().toString();
				if (chosenBank.equals("Iron Bank of Braavos"))
					rateBox.setText("5%");
				else if(chosenBank.equals("Bank of CS180"))
					rateBox.setText("8%");
				else if(chosenBank.equals("Khallesi Fedral Credit Union"))
					rateBox.setText("3%");
				else if(chosenBank.equals("Bank of the Forsaken"))
					rateBox.setText("12%");
			}
		});

		submitB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String nameEntered = nameBox.getText().toString();

				String principalValue = principalBox.getText().toString();
				String timeValue = yearsBox.getText().toString();
				int selectedId = radioGender.getCheckedRadioButtonId();
				RadioButton radioGenderButton = (RadioButton) findViewById(selectedId);
				String genderSelected = radioGenderButton.getText().toString();
				int selectedId2 = interestSelection.getCheckedRadioButtonId();
				RadioButton interestButton = (RadioButton) findViewById(selectedId2);
				String interestSelected = interestButton.getText().toString();
				String finalAnswer = "";
				double rateValue = 0.00;
				String chosenBank = bankSpinner.getSelectedItem().toString();
				if (chosenBank.equals("Iron Bank of Braavos"))
					rateValue = 5;
				else if(chosenBank.equals("Bank of CS180"))
					rateValue = 8;
				else if(chosenBank.equals("Khallesi Fedral Credit Union"))
					rateValue = 3;
				else if(chosenBank.equals("Bank of the Forsaken"))
					rateValue = 12;



				if(principalValue.matches("[0-9]+") && timeValue.matches("[0-9]+")) {
					/** 
					 * @param pAmount - principle amount in double format
					 * @param rAmount - rate amount in double format
					 * @param yAmount - number of years in double format
					 * @param compoundInterest - the final compound interest i.e the result
					 */
					Double pAmount = 0.00;
					Double rAmount = 0.00;
					Double yAmount = 0.00;

					pAmount = Double.parseDouble(principalValue);
					rAmount = rateValue;
					yAmount = Double.parseDouble(timeValue);

					if(interestSelected.equals("Compound Interest")) {
						Double compoundInterest = 0.00;
						compoundInterest = pAmount * Math.pow((1 + rAmount/100),yAmount); 
						String compoundInterestStr = String.format("%.2f", compoundInterest);
						finalAnswer = compoundInterestStr;

					}
					else if(interestSelected.equals("Simple Interest")) {
						Double simpleInterest = 0.00;
						simpleInterest = pAmount * (rAmount / 100 ) * yAmount;
						String simpleInterestStr = String.format("%.2f", simpleInterest);
						finalAnswer = simpleInterestStr;
					}
					else {
						commentsBox.setText("Please make a selection");
					}
				}


				//Checks if either of the 3 fields is left empty
				else if(principalValue.equals("") || timeValue.equals(""))
					commentsBox.setText("Please enter more data");
				//The ultimate text to be displayed if the conditions above are false



				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.custom_xml);
				dialog.setTitle("Here's the summary");

				TextView text1 = (TextView) dialog.findViewById(R.id.text1);
				String abriviation = "";
				if(genderSelected.equals("Male"))
					abriviation = "Mr. ";
				else
					abriviation = "Mrs. ";
				text1.setText("Customer Name : " + abriviation + nameEntered);
				TextView text2 = (TextView) dialog.findViewById(R.id.text2);
				text2.setText("\n" + "Bank : " + chosenBank);
				TextView text3 = (TextView) dialog.findViewById(R.id.text3);
				text3.setText("\n\n" +"Principal : $ " + principalValue);
				TextView text4 = (TextView) dialog.findViewById(R.id.text4);
				text4.setText("\n\n\n" +"Rate Applied : " + rateValue + "%");
				TextView text5 = (TextView) dialog.findViewById(R.id.text5);
				text5.setText("\n\n\n\n" +"Tennue (Years) : " + timeValue);
				TextView text6 = (TextView) dialog.findViewById(R.id.text6);
				text6.setText("\n\n\n\n\n" +"Interest Type : " + interestSelected);
				TextView text7 = (TextView) dialog.findViewById(R.id.text7);
				text7.setText("\n\n\n\n\n\n" +"Balance : " + finalAnswer);


				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();	
			}
		});




	}
}