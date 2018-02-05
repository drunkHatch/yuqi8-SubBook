
/*
 * Copyright ©
 *
 * Free to use the code without breaking any rules of University of Alberta.
 *
 * Oops, remember to cite XD
 */

package com.nby.yuqi8_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 This activity is invoked when user tap existing listview items(subcruptions)
 user could edit/delete/cancel
 */
public class editItemActivity extends AppCompatActivity {
    public subList currentItem;
    public static ArrayList<subList> currentList = new ArrayList<subList>();
    public int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // get item position to get item info
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        currentList = MainActivity.getData(); //check invoked item
        currentItem = currentList.get(position);
        setEditText();
    }
    //init all values of UI
    public void setEditText(){
        String name;
        Date date;
        double charge;
        String comment;
        EditText editText;
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");

        name = currentItem.getName();
        date = currentItem.getDate();
        charge = currentItem.getcharge();
        comment = currentItem.getComment();

        editText = (EditText)findViewById(R.id.name);
        editText.setText(name, TextView.BufferType.EDITABLE);
        editText = (EditText)findViewById(R.id.date);
        editText.setText(dateFormate.format(date), TextView.BufferType.EDITABLE);
        editText = (EditText)findViewById(R.id.charge);
        editText.setText(Double.toString(charge), TextView.BufferType.EDITABLE);
        editText = (EditText)findViewById(R.id.comment);
        editText.setText(comment, TextView.BufferType.EDITABLE);

    }

    //check all values in edittext, if valid then return to main else stay and report error
    public void saveEdit(View view) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        EditText name;
        EditText date;
        EditText charge;
        EditText comment;
        TextView notification;
        int valid = 1;

        String temp;
        double charge_value;
        String dateString;
        Date formattedDate;
        Date todayDate = new Date();

        name = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.date);
        charge = (EditText) findViewById(R.id.charge);
        comment = (EditText) findViewById(R.id.comment);
        notification = (TextView) findViewById(R.id.notification);

        try {
            temp = charge.getText().toString();
            charge_value = Double.parseDouble(temp);
        } catch (NumberFormatException  e) {
            notification.setText("Enter correct charge");
            return;
        }

        if (charge_value >= 0){
            currentItem.setCharge(charge_value);
        }
        else{
            notification.setText("Please enter non-negative charge");
            return;
        }

        if (comment.getText().toString().length() <= 30){
            currentItem.setComment(comment.getText().toString());
        }
        else{
            notification.setText("Comment too long");
            return;
        }

        if (name.getText().toString().length() <= 20 &&
                name.getText().toString().replaceAll("\\s+","").length() > 0){
            currentItem.setName(name.getText().toString());
        }
        else{
            notification.setText("Invalid name");
            return;
        }


        dateString = date.getText().toString();
        try {
            formattedDate = dateFormat.parse(dateString);
            currentItem.setDate(formattedDate);
            valid = 1;
        } catch (ParseException e) {
            date = (EditText)findViewById(R.id.date);
            notification.setText("Enter correct date");
            return;
        }

        super.onBackPressed();

    }

    public void cancelEdit(View view) {
        super.onBackPressed();
    }

    // delete invoked item
    public void delete(View view) {
        currentList.remove(position);
        super.onBackPressed();
    }

}

