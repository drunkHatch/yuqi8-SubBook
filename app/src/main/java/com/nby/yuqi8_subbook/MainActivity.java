
/*
 * Copyright ©
 *
 * Free to use the code without breaking any rules of University of Alberta.
 *
 * Oops, remember to cite me XD
 */

package com.nby.yuqi8_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.DynamicLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity {
    public ListView subArrayList;

    public static ArrayList<subList> currentList = new ArrayList<subList>();
    public ArrayAdapter<subList> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView sum;
        double total = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();
        sum = (TextView) findViewById(R.id.sum);
        for (int i = 0; i < currentList.size(); i++){
            subList tempItem = currentList.get(i);
            total = total + tempItem.getcharge();
        }
        sum.setText(Double.toString(total));
        subArrayList = (ListView) findViewById(R.id.subArrayList);

        //ArrayAdapter<subList> subArrayListAdapter = new ArrayAdapter<subList>(this,
                //android.R.layout.simple_list_item_1, android.R.id.text1, currentList);
        subArrayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, editItemActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                subList item = (subList) parent.getItemAtPosition(position);
            }
        });
    }

    //https://github.com/slark1995/taijie-CountBook.git
    //modifying activities finish, then save changes on disk and update list view
    protected void onStart() {
        // TODO Auto-generated method stub
        TextView sum;
        double total = 0;

        super.onStart();

        sum = (TextView) findViewById(R.id.sum);
        for (int i = 0; i < currentList.size(); i++){
            subList tempItem = currentList.get(i);
            total = total + tempItem.getcharge();
        }
        sum.setText(Double.toString(total));

        saveInFile();
        adapter = new ArrayAdapter<subList>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, currentList);
        subArrayList.setAdapter(adapter);

    }

    public void addItem(View view) {
        Intent intent = new Intent(this, addItemActivity.class);
        startActivity(intent);
    }

    // This is the function for other activities to add item to currentList
    public static void addData(subList item) {
        currentList.add(item);
    }

    // This is the function for other activities to access currentList
    public static ArrayList<subList> getData() {
        return currentList;
    }

    //https://github.com/slark1995/taijie-CountBook.git
    //https://github.com/drunkHatch/lonelyTwitter/blob/w18Tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    //load from file to currentList
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<subList>>() {}.getType();
            currentList = gson.fromJson(in,listType);
            //https://github.com/google/gson/blob/master/UserGuide.md#TOC-Collections-Examples

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            currentList = new ArrayList<subList>();
        }

    }

    //https://github.com/slark1995/taijie-CountBook.git
    //https://github.com/drunkHatch/lonelyTwitter/blob/w18Tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    //save currentList to local file
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(currentList,writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}

