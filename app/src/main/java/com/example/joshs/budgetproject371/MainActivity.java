package com.example.joshs.budgetproject371;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnInputBudget, btnInputActual, btnDisplay;
    public FancyShowCaseView mFancyShowCaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            btnInputBudget = (Button) findViewById(R.id.btnInputBudget);
            btnInputActual = (Button) findViewById(R.id.btnInputActual);
            btnDisplay = (Button) findViewById(R.id.btnDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnInputBudget.setOnClickListener(this);
        btnInputActual.setOnClickListener(this);
        btnDisplay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInputBudget:
                new FancyShowCaseView.Builder(this)
                        .title("Input Budget")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                inputBudget(v);
                break;
            case R.id.btnInputActual:
                new FancyShowCaseView.Builder(this)
                        .title("Input Actual")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                inputActual(v);
                break;
            case R.id.btnDisplay:
                new FancyShowCaseView.Builder(this)
                        .title("Your Budget")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                displayBudget(v);
                break;
        }
    }

    public void inputBudget(View v) {
        Intent intent = new Intent(this, InputBudget.class);
        startActivity(intent);
    }

    public void inputActual(View v) {
        Intent intent = new Intent(this, InputActual.class);
//        v.startAnimation(buttonClick);
        startActivity(intent);
    }


    public void displayBudget(View v) {
        Intent intent = new Intent(this, DisplayBudget.class);
        startActivity(intent);
    }
}
