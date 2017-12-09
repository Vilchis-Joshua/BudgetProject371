package com.example.joshs.budgetproject371;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class InputBudget extends AppCompatActivity implements View.OnClickListener {

    public static final String APP_PREFS = "APPLICATION_PREFERENCES";
    public static final String ITEM_ARRAY_LIST = "ITEM_ARRAY_LIST";
    public static final String TOTAL_BUDGET = "TOTAL_BUDGET";
    public static final String ACTUAL_ITEM_ARRAY_LIST = "ACTUAL_ITEM_ARRAY_LIST";
    public static final String ITEM_NAME_ARRAY_LIST = "ITEM_NAME_ARRAY_LIST";

    public Button btnAddItem, btnRemoveItem, btnFinish, btnDelete;

    public EditText itemInput;
    public EditText valueInput;
    public EditText totalBudgetInput;

    //The value for the total
    public String budgetTotal;

    public ListView lv;
    public ArrayList<String> arrayList;
    public ArrayAdapter<String> adapter;

    //For knowing the item to remove
    public String removeItem;

    //For the sharedpreferences
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_budget);
        try {
            //Initialize buttons
            btnAddItem = (Button) findViewById(R.id.btnAddItem);
            btnRemoveItem = (Button) findViewById(R.id.btnRemoveItem);
            btnFinish = (Button) findViewById(R.id.btnFinish);
            btnDelete = (Button) findViewById(R.id.btnDelete);

            disableButtons();
            MyTask myTask = new MyTask();
            myTask.execute();
            //Shared Preferences things
            sp = getSharedPreferences(InputBudget.APP_PREFS, Context.MODE_PRIVATE );
            editor =  sp.edit();

            //Initialize edittexts
            valueInput = (EditText) findViewById(R.id.txtInputValue);
            itemInput = (EditText) findViewById(R.id.txtInputItem);
            totalBudgetInput = (EditText) findViewById(R.id.txtTotalBudget);
            totalBudgetInput.callOnClick();

            //Listview junk
            lv = (ListView) findViewById(R.id.lvBudgetItems);

            Gson gson = new Gson();
            String temp = sp.getString(InputBudget.ITEM_ARRAY_LIST, "");
            if (temp != "") {
                arrayList = gson.fromJson(temp, new ArrayList<String>().getClass());
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
                totalBudgetInput.setText(sp.getString(InputBudget.TOTAL_BUDGET,""));
            } else {
                arrayList = new ArrayList<String>();
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
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


        //On-clicks
        btnAddItem.setOnClickListener(this);
        btnRemoveItem.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyTask myTask = new MyTask();
        switch (v.getId()) {
            case R.id.btnAddItem:
                addItem(v);
                myTask.execute();
                break;
            case R.id.btnRemoveItem:
                removeItem(v);
                myTask.execute();
                break;
            case R.id.btnFinish:
                new FancyShowCaseView.Builder(InputBudget.this)
                        .title("Home")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                myTask.execute();
                BackToMainMenu(v);
                break;
            case R.id.lvBudgetItems:
                removeItem(v);
                myTask.execute();
                break;
            case R.id.btnDelete:
                delete(v);
                myTask.execute();
                break;

        }
    }

    public void disableButtons() {
        btnAddItem.setEnabled(false);
        btnRemoveItem.setEnabled(false);
        btnFinish.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    public void BackToMainMenu(View v) {
        Intent intent = new Intent(this, MainActivity.class);


            budgetTotal = totalBudgetInput.getText().toString();
            try {
                Gson gson = new Gson();
                String json = gson.toJson(arrayList);
                editor.putString(InputBudget.ITEM_ARRAY_LIST, json);
                editor.putString(InputBudget.TOTAL_BUDGET, totalBudgetInput.getText().toString());
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        startActivity(intent);
    }


    public void addItem(View v) {
        try {
            arrayList.add(itemInput.getText().toString() + "    $" + valueInput.getText().toString());
            adapter.notifyDataSetChanged();
            EditText tempText = findViewById(R.id.txtTotalBudget);
            totalBudgetInput.setText(tempText.getText().toString());
            //Checking to change the total budget edit text input
//            if (!arrayList.isEmpty()) {
//                int value = 0;
//                for (int i = 0; i < arrayList.size(); i++) {
//                    String temp = arrayList.get(i);
//                    String temp2 = temp.substring(temp.lastIndexOf("$")+ 1);
//                    value = Integer.parseInt(tempText.getText().toString()) - Integer.parseInt(temp2);
//                }
//                totalBudgetInput.setText(String.valueOf(value));
//            }

            itemInput.setText("");
            valueInput.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItem(View v) {
        if (removeItem == ""){
            Toast.makeText(this, "Select an item and then press remove", Toast.LENGTH_SHORT).show();
        } else {
            if (!arrayList.isEmpty()) {
                arrayList.remove(removeItem);
                adapter.notifyDataSetChanged();
            }  else {
                Toast.makeText(this, "No current budget", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void delete(View v) {
        if (!arrayList.isEmpty()) {
            arrayList.clear();
            editor.putString(InputBudget.ITEM_ARRAY_LIST, "");
            editor.commit();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted current budget", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No current budget", Toast.LENGTH_SHORT).show();
        }
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