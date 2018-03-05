package com.example.ayushyadav.expensemanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText descEditText;
    EditText amountEditText;

    Expense expense;
    ArrayList<Expense> mExpenses;

    ExpenseOpenHelper openHelper;
    ExpenseAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleEditText = findViewById(R.id.title);
        descEditText = findViewById(R.id.description);
        amountEditText = findViewById(R.id.amount);

        Intent intent = getIntent();
        ArrayList<Expense> expenses = new ArrayList<>();

        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(Contract.Expenses.TABLE_NAME,null,null,null,null,null,null);

        while (cursor.moveToNext()){

            int titleColumnIndex = cursor.getColumnIndex(Contract.Expenses.TITLE);
            String title = cursor.getString(titleColumnIndex);
            String desc = cursor.getString(cursor.getColumnIndex(Contract.Expenses.DESCRIPTION));
            int amount = cursor.getInt(cursor.getColumnIndex(Contract.Expenses.AMOUNT));
            int id = cursor.getInt(cursor.getColumnIndex(Contract.Expenses.ID));

            titleEditText.setText(title);
            descEditText.setText(desc);
            amountEditText.setText(amount+"");

        }
    }

    public void save(View view){

        String title = titleEditText.getText().toString();
        String desc = descEditText.getText().toString();
        String amountString = amountEditText.getText().toString();

        if(isNullOrEmpty(title)){
            Toast.makeText(this,"Title can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isNullOrEmpty(amountString)){
            Toast.makeText(this,"Amount can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isNullOrEmpty(desc)){
            Toast.makeText(this,"Description can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = openHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Expenses.TITLE,expense.getTitle());
        contentValues.put(Contract.Expenses.DESCRIPTION,expense.getDescription());
        contentValues.put(Contract.Expenses.AMOUNT,expense.getAmount());

        long id = database.insert(Contract.Expenses.TABLE_NAME,null,contentValues);
        expense.setId((int) id);
        mExpenses.add(expense);
        mAdapter.notifyDataSetChanged();

        Intent intent = new Intent();
        setResult(Constants.SAVE_SUCCESS_RESULT,intent);

        finish();
    }

    public void cancel(View view){
        Intent intent = new Intent();
        setResult(Constants.Cancel_Clicked, intent);
        finish();
    }

    private boolean isNullOrEmpty(String s){
        return s == null || s.isEmpty();
    }

}
