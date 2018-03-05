package com.example.ayushyadav.expensemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ayushyadav on 04/03/18.
 */

public class ExpenseOpenHelper extends SQLiteOpenHelper {

    private static  ExpenseOpenHelper openHelper;

    public static ExpenseOpenHelper getInstance(Context context){
        if(openHelper == null){
            openHelper = new ExpenseOpenHelper(context.getApplicationContext());
        }
        return openHelper;
    }

    private ExpenseOpenHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String expensesSql = "CREATE TABLE " + Contract.Expenses.TABLE_NAME  + " ( " +
                Contract.Expenses.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.Expenses.TITLE + " TEXT, " +
                Contract.Expenses.DESCRIPTION + " TEXT, " +
                Contract.Expenses.AMOUNT + " INTEGER)";
        db.execSQL(expensesSql);
        String commentSql = "CREATE TABLE " + Contract.Comment.TABLE_NAME  + " ( " +
                Contract.Comment.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.Comment.Comment + " TEXT, " +
                Contract.Comment.ExpenseID + " INTEGER)";
        db.execSQL(commentSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
