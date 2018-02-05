package com.nby.yuqi8_subbook;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Boolean.TRUE;

public class addItemActivity extends AppCompatActivity {
    private ListView newItemInfo;
    private subList newItem;
    public subList currentItem;
    public ArrayList<subList> currentList = new ArrayList<subList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        currentItem = new subList();
        currentList = MainActivity.getData();
    }

    //check all values in edittext, if valid then return to main else stay and report error
    public void saveAdd(View view) {
        //Intent intent = new Intent(this, MainActivity.class);
        EditText name;
        EditText date;
        EditText charge;
        EditText comment;
        TextView notification;
        int valid = 1;

        String temp;
        double charge_value;
        String dateString;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedDate = new Date();

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
            notification.setText("Please enter non-negative charge", TextView.BufferType.EDITABLE);
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
        } catch (ParseException e) {
            date = (EditText)findViewById(R.id.date);
            notification.setText("Enter correct date");
            return;
        }
        currentList.add(currentItem);
        super.onBackPressed();
    }

    public void cancleAdd(View view) {
        super.onBackPressed();
    }

}
