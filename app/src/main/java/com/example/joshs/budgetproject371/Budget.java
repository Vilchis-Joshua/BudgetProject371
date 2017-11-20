package com.example.joshs.budgetproject371;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by joshs on 11/18/2017.
 */

class Budget {
    public String budgetItem;
    public String itemValue;

    public Budget(String budgetItem, String itemValue) {
        this.budgetItem = budgetItem;
        this.itemValue = itemValue;
    }


    private static int lastBudgetItem = 0;
    public  ArrayList<Budget> createBudgetList(int numItems) {
        ArrayList<Budget> budgetItems = new ArrayList<Budget>();
        for (int i = 0; i < numItems; i++) {
            budgetItems.add(new Budget("Item: " + ++lastBudgetItem, getItemValue()));
        }
        return budgetItems;
    }

    public String getBudgetItem() {
        return budgetItem;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void updateList(String v1, String v2) {
        budgetItem = v1;
        itemValue = v2;
    }
}
