package com.example.joshs.budgetproject371;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnInputBudget, btnInputActual, btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        try {
            btnInputBudget = (Button) findViewById(R.id.btnInputBudget);
            btnInputActual = (Button) findViewById(R.id.btnInputActual);
            btnDisplay = (Button) findViewById(R.id.btnDisplay);
            MyTask myTask = new MyTask();
            myTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnInputBudget.setOnClickListener(this);
        btnInputActual.setOnClickListener(this);
        btnDisplay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyTask myTask = new MyTask();
        switch (v.getId()) {
            case R.id.btnInputBudget:
                new FancyShowCaseView.Builder(this)
                        .title("Input Budget")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                myTask.execute();
                inputBudget(v);
                break;
            case R.id.btnInputActual:
                new FancyShowCaseView.Builder(this)
                        .title("Input Actual")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                myTask.execute();
                inputActual(v);
                break;
            case R.id.btnDisplay:
                new FancyShowCaseView.Builder(this)
                        .title("Your Budget")
                        .titleStyle(R.style.FancyShowCaseDefaultTitleStyle, Gravity.CENTER | Gravity.CENTER)
                        .backgroundColor(Color.parseColor("#333639"))
                        .build()
                        .show();
                myTask.execute();
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

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Thread.sleep(1000);
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
        btnInputBudget.setEnabled(false);
        btnInputActual.setEnabled(false);
        btnDisplay.setEnabled(false);
    }

    public void enableButtons() {
        btnInputBudget.setEnabled(true);
        btnInputActual.setEnabled(true);
        btnDisplay.setEnabled(true);
    }
}
