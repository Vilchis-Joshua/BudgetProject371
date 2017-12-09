package com.example.joshs.budgetproject371;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class InputActual extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTUAL_ITEM_ARRAY_LIST = "ACTUAL_ITEM_ARRAY_LIST";

    Button btnAddItem, btnRemoveItem, btnFinish, btnDelete;
    EditText itemInput;
    EditText valueInput;

    ListView lv;
    ArrayList<String> arrayListActual;
    ArrayAdapter<String>  adapter;

    //For knowing the item to remove
    public String removeItem;

    //For the sharedpreferences
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_actual);

        try {
            //Shared Preferences things
            sp = getSharedPreferences(InputBudget.APP_PREFS, Context.MODE_PRIVATE );
            editor =  sp.edit();

            //Initialize buttons
            btnAddItem = (Button) findViewById(R.id.btnAddItem);
            btnRemoveItem = (Button) findViewById(R.id.btnRemoveItem);
            btnFinish = (Button) findViewById(R.id.btnFinish);
            btnDelete = (Button) findViewById(R.id.btnDelete);

            MyTask myTask = new MyTask();
            myTask.execute();

            //Initialize text
            itemInput = (EditText) findViewById(R.id.txtInputItem);
            valueInput = (EditText) findViewById(R.id.txtInputValue);

            //Set up for the list view
            Gson gson = new Gson();
            String temp = sp.getString(InputBudget.ACTUAL_ITEM_ARRAY_LIST, "");
            if (temp != "") {
                arrayListActual = gson.fromJson(temp, new ArrayList<String>().getClass());
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListActual);
            } else {
                arrayListActual = new ArrayList<String>();
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListActual);
            }
            lv = (ListView) findViewById(R.id.lvBudgetItems);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    removeItem = (String)lv.getItemAtPosition(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Enable the buttons again
//        btnAddItem.setEnabled(true);
//        btnRemoveItem.setEnabled(true);
//        btnFinish.setEnabled(true);
//        btnDelete.setEnabled(true);

        // On click listeners
        btnDelete.setOnClickListener(this);
        btnAddItem.setOnClickListener(this);
        btnRemoveItem.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyTask myTask = new MyTask();
        switch (v.getId()) {
            case R.id.btnAddItem:
                myTask.execute();
                addItem(v);
                break;
            case R.id.btnRemoveItem:
                myTask.execute();
                removeItem(v);
                break;
            case R.id.btnFinish:
                new FancyShowCaseView.Builder(this)
                        .title("Home")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                myTask.execute();
                BackToMainMenu(v);
                break;
            case R.id.btnDelete:
                myTask.execute();
                delete(v);
                break;
        }
    }


    public void BackToMainMenu(View v) {
        Intent intent = new Intent(this, MainActivity.class);
            try {
                Gson gson = new Gson();
                String json = gson.toJson(arrayListActual);
                editor.putString(InputActual.ACTUAL_ITEM_ARRAY_LIST, json);
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        startActivity(intent);
    }

    public void removeItem(View v) {
        if (removeItem == ""){
            Toast.makeText(this, "Select an item and then press remove", Toast.LENGTH_SHORT).show();
        } else {
            if (!arrayListActual.isEmpty()) {
                arrayListActual.remove(removeItem);
                adapter.notifyDataSetChanged();
            }  else {
                Toast.makeText(this, "No current budget", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addItem(View v) {
        try {
            arrayListActual.add(itemInput.getText().toString() + "    $" + valueInput.getText().toString());
            adapter.notifyDataSetChanged();
            itemInput.setText("");
            valueInput.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(View v) {
        if (!arrayListActual.isEmpty()) {
            arrayListActual.clear();
            editor.putString(InputBudget.ACTUAL_ITEM_ARRAY_LIST, "");
            editor.commit();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted current budget", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No current budget", Toast.LENGTH_SHORT).show();
        }
    }

    public void disableButtons() {
        btnAddItem.setEnabled(false);
        btnRemoveItem.setEnabled(false);
        btnFinish.setEnabled(false);
        btnDelete.setEnabled(false);
    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Thread.sleep(800);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            disableButtons();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            btnAddItem.setEnabled(true);
            btnRemoveItem.setEnabled(true);
            btnFinish.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }
}
