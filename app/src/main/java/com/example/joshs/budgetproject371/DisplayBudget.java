package com.example.joshs.budgetproject371;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class DisplayBudget extends AppCompatActivity implements View.OnClickListener {

    Button btnDisplayDifference, btnMainMenu;
    ListView lvBudgetItems;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_budget);
        try {
            btnDisplayDifference = (Button) findViewById(R.id.btnGoBackToDisplayingBudget);
            btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
            lvBudgetItems = (ListView) findViewById(R.id.lvBudgetItems);

            SharedPreferences sp = getSharedPreferences(InputBudget.APP_PREFS, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String temp = sp.getString(InputBudget.ITEM_ARRAY_LIST, "");
            arrayList = gson.fromJson(temp, new ArrayList<String>().getClass());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            if(arrayList.size() != 0) {
                lvBudgetItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDisplayDifference.setOnClickListener(this);
        btnMainMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoBackToDisplayingBudget:
                showWithAnim(v);
                displayDifference(v);
                break;
            case R.id.btnMainMenu:
                new FancyShowCaseView.Builder(this)
                        .title("Home")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                goBack(v);
                break;
        }
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void displayDifference(View v){
        Intent intent = new Intent(this, DisplayDifference.class);
        startActivity(intent);
    }
    private void showWithAnim(View v) {
        Animation enterAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation exitAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        final FancyShowCaseView fancy = new FancyShowCaseView.Builder(this)
                .title("Display Difference")
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
}
