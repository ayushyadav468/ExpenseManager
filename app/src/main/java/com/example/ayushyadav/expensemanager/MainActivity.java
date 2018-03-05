package com.example.ayushyadav.expensemanager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Expense> mExpenses;
    ExpenseAdapter mAdapter;
    ExpenseOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        openHelper = ExpenseOpenHelper.getInstance(this);
        mExpenses = fetchExpensesFromDB();
        mAdapter = new ExpenseAdapter(this,mExpenses);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
                detailIntent.putExtra("Click_ID", id);
                startActivityForResult(detailIntent, Constants.DETAIL_ACTIVITY_REQUEST);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Expense?");
                builder.setMessage("Are you sure you want to delete this expense? You can't undo this action.");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase database = openHelper.getWritableDatabase();
                        String[] ids = {id + ""};
                        database.delete(Contract.Expenses.TABLE_NAME, Contract.Expenses.ID + " = ?", ids);
                        mExpenses.remove(position);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            Intent intent = new Intent(this,EditActivity.class);
            startActivityForResult(intent,Constants.ADD_EXPENSE_REQUEST);
            return true;
        } else if(item.getItemId() == R.id.callUS){
            callUs();
        }
        return super.onOptionsItemSelected(item);
    }

    private void callUs(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_CALL);
        shareIntent.setData(Uri.parse(Constants.callDeveloper));
        startActivity(shareIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null){
//            Bundle bundle = data.getExtras();
            switch (requestCode){
                case Constants.DETAIL_ACTIVITY_REQUEST:
                    if(resultCode == Constants.SAVE_SUCCESS_RESULT){
                        ArrayList<Expense> expenseArrayList = fetchExpensesFromDB();
                        Expense expense = expenseArrayList.get(0);
                        mExpenses.set(1,expense);
                        mAdapter.notifyDataSetChanged();
                    } else if (resultCode == Constants.Cancel_Clicked){
                        //Do Nothing
                    }
                    break;
                case Constants.ADD_EXPENSE_REQUEST:
                    if(resultCode == Constants.SAVE_SUCCESS_RESULT){
                        ArrayList<Expense> expenseArrayList = fetchExpensesFromDB();
                        Expense expense = expenseArrayList.get(0);

                        SQLiteDatabase database = openHelper.getWritableDatabase();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Contract.Expenses.TITLE,expense.getTitle());
                        contentValues.put(Contract.Expenses.DESCRIPTION,expense.getDescription());
                        contentValues.put(Contract.Expenses.AMOUNT,expense.getAmount());

                        long id = database.insert(Contract.Expenses.TABLE_NAME,null,contentValues);
                        expense.setId((int) id);
                        mExpenses.add(expense);
                        mAdapter.notifyDataSetChanged();
                    } else if (resultCode == Constants.Cancel_Clicked){
                        //Do Nothing
                    }
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

