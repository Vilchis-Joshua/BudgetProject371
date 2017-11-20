package com.example.joshs.budgetproject371;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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


        try{
            //Shared Preferences things
            sp = getSharedPreferences(InputBudget.APP_PREFS, Context.MODE_PRIVATE );
            editor =  sp.edit();

            //Initialize buttons
            btnAddItem = (Button) findViewById(R.id.btnAddItem);
            btnRemoveItem = (Button) findViewById(R.id.btnRemoveItem);
            btnFinish = (Button) findViewById(R.id.btnFinish);
            btnDelete = (Button) findViewById(R.id.btnDelete);

            //Initialize text
            itemInput = (EditText) findViewById(R.id.txtInputItem);
            valueInput = (EditText) findViewById(R.id.txtInputValue);

            //Set up for the list view
            lv = (ListView) findViewById(R.id.lvBudgetItems);
            arrayListActual = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListActual);
            lv.setAdapter(adapter);
//            SharedPreferences sp = getSharedPreferences(InputBudget.APP_PREFS, Context.MODE_PRIVATE);
//            Gson gson = new Gson();
//            String temp = sp.getString(InputBudget.ITEM_ARRAY_LIST, "");
//            arrayList = gson.fromJson(temp,new ArrayList<String>().getClass());

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    removeItem = (String)lv.getItemAtPosition(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (arrayListActual.size() != 0) {
            btnDelete.setOnClickListener(this);
        }
        btnAddItem.setOnClickListener(this);
        btnRemoveItem.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddItem:
                addItem(v);
                break;
            case R.id.btnInputActual:
                removeItem(v);
                break;
            case R.id.btnFinish:
                new FancyShowCaseView.Builder(this)
                        .title("Home")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                BackToMainMenu(v);
                break;
            case R.id.btnDelete:
                delete(v);
                break;
        }
    }


    public void BackToMainMenu(View v) {
        Intent intent = new Intent(this, MainActivity.class);

        if (arrayListActual.size() != 0) {
            try {
                Gson gson = new Gson();
                String json = gson.toJson(arrayListActual);
                editor.putString(InputActual.ACTUAL_ITEM_ARRAY_LIST, json);
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        startActivity(intent);
    }

    public void removeItem(View v) {
        if (removeItem == ""){
            Toast.makeText(this, "Select an item and then press remove", Toast.LENGTH_SHORT).show();
        } else {
            if (arrayListActual.size() != 0) {
                arrayListActual.remove(removeItem);
                adapter.notifyDataSetChanged();
            }  else {
                Toast.makeText(this, "No current budget", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addItem(View v) {
        try {
            arrayListActual.add("Budget item: " + itemInput.getText().toString() + "    Amount: " + valueInput.getText().toString());
            adapter.notifyDataSetChanged();

            itemInput.setText("");
            valueInput.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(View v) {
        if (arrayListActual.size() != 0) {
            editor.clear();
            editor.commit();
            arrayListActual.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted current budget", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No current budget", Toast.LENGTH_SHORT).show();
        }
    }
}
