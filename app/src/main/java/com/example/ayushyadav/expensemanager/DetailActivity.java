package com.example.ayushyadav.expensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TextView titleTextView;
    TextView descTextView;
    TextView amountTextView;
    ExpenseOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = findViewById(R.id.title);
        descTextView = findViewById(R.id.description);
        amountTextView = findViewById(R.id.amount);

        Intent intent = getIntent();
        long id = intent.getLongExtra("Click_ID", -1);

        if(id != -1){
            ArrayList<Expense> expenseArrayList = fetchExpensesFromDB();
            Expense expense = expenseArrayList.get(0);
            titleTextView.setText(expense.getTitle());
            descTextView.setText(expense.getDescription());
            amountTextView.setText(expense.getAmount());
        }else {
            //Do nothing
            titleTextView.setText("Nothing To Show");
            descTextView.setText("Nothing To Show");
            amountTextView.setText("0.00");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit){
            Intent intent = new Intent(this,EditActivity.class);
            startActivityForResult(intent,Constants.EDIT_EXPENSE_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.EDIT_EXPENSE_REQUEST){
            if(resultCode == Constants.SAVE_SUCCESS_RESULT){
                fetchExpensesFromDB();
            } else if (resultCode == Constants.Cancel_Clicked){
               //Do Nothing
            }
        }

    }

    private ArrayList<Expense> fetchExpensesFromDB() {
        ArrayList<Expense> expenses = new ArrayList<>();

        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(Contract.Expenses.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            int titleColumnIndex = cursor.getColumnIndex(Contract.Expenses.TITLE);
            String title = cursor.getString(titleColumnIndex);
            String desc = cursor.getString(cursor.getColumnIndex(Contract.Expenses.DESCRIPTION));
            int amount = cursor.getInt(cursor.getColumnIndex(Contract.Expenses.AMOUNT));
            int id = cursor.getInt(cursor.getColumnIndex(Contract.Expenses.ID));
            Expense expense = new Expense(title,desc,amount,id);
            expenses.add(expense);
        }
        return  expenses;
    }
}
