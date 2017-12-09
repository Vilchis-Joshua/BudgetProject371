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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class DisplayDifference extends AppCompatActivity implements View.OnClickListener {

    Button btnGoBackToDisplayingBudget, btnMainMenu;
    ListView lvBudgetItems;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_difference);

        try {
            btnGoBackToDisplayingBudget = (Button) findViewById(R.id.btnGoBackToDisplayingBudget);
            btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
            MyTask myTask = new MyTask();
            myTask.execute();

            lvBudgetItems = (ListView) findViewById(R.id.lvBudgetItems);

            SharedPreferences sp = getSharedPreferences(InputBudget.APP_PREFS, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String temp = sp.getString(InputActual.ACTUAL_ITEM_ARRAY_LIST, "");
            arrayList = gson.fromJson(temp,new ArrayList<String>().getClass());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            if (!arrayList.isEmpty()) {
                lvBudgetItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnGoBackToDisplayingBudget.setOnClickListener(this);
        btnMainMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyTask myTask = new MyTask();
        switch (v.getId()) {
            case R.id.btnGoBackToDisplayingBudget:
                showWithAnim(v);
                myTask.execute();
                displayBudget(v);
                break;
            case R.id.btnMainMenu:
                new FancyShowCaseView.Builder(this)
                        .title("Home")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                myTask.execute();
                goHome(v);
                break;
        }
    }

    public void displayBudget(View v) {
        Intent intent = new Intent(this, DisplayBudget.class);
        startActivity(intent);
    }

    public void goHome(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showWithAnim(View v) {
        Animation enterAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation exitAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        final FancyShowCaseView fancy = new FancyShowCaseView.Builder(this)
                .title("Display Budget")
                .enterAnimation(enterAnimation)
                .exitAnimation(exitAnimation)
                .build();
        fancy.show();
        exitAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fancy.removeView();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
            enableButtons();
        }
    }

    public void disableButtons() {
        btnGoBackToDisplayingBudget.setEnabled(false);
        btnMainMenu.setEnabled(false);
    }

    public void enableButtons() {
        btnGoBackToDisplayingBudget.setEnabled(true);
        btnMainMenu.setEnabled(true);
    }
}
