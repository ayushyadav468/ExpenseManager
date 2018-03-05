package com.example.ayushyadav.expensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView titleTextView;
    TextView descTextView;
    TextView amountTextView;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = findViewById(R.id.title);
        descTextView = findViewById(R.id.description);
        amountTextView = findViewById(R.id.amount);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        if(bundle != null){
            populateDataFromBundle();
        }
        else {
            bundle = new Bundle();
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
            intent.putExtras(bundle);
            startActivityForResult(intent,Constants.EDIT_EXPENSE_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.EDIT_EXPENSE_REQUEST){
            if(resultCode == Constants.SAVE_SUCCESS_RESULT){

                bundle = data.getExtras();
                if(bundle != null){
                    populateDataFromBundle();
                    setResult(resultCode,data);
                }
            }
        }


    }
    private void populateDataFromBundle(){
        String title = bundle.getString(Constants.TITLE_KEY,"");
        String desc = bundle.getString(Constants.DESCRIPTION_KEY,"");
        int amount = bundle.getInt(Constants.AMOUNT_KEY,0);

        titleTextView.setText(title);
        descTextView.setText(desc);
        amountTextView.setText(amount + " ₹");
    }
}
