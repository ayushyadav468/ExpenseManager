package com.example.ayushyadav.expensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText descEditText;
    EditText amountEditText;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleEditText = findViewById(R.id.title);
        descEditText = findViewById(R.id.description);
        amountEditText = findViewById(R.id.amount);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        if(bundle != null){

            String title = bundle.getString(Constants.TITLE_KEY,"");
            String desc = bundle.getString(Constants.DESCRIPTION_KEY,"");
            int amount = bundle.getInt(Constants.AMOUNT_KEY,-1);

            titleEditText.setText(title);
            descEditText.setText(desc);
            if(amount != -1){
                amountEditText.setText(amount+"");
            }
        }
        else {
            bundle = new Bundle();
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

        int amount = Integer.parseInt(amountString);

        bundle.putString(Constants.TITLE_KEY,title);
        bundle.putString(Constants.DESCRIPTION_KEY,desc);
        bundle.putInt(Constants.AMOUNT_KEY,amount);

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Constants.SAVE_SUCCESS_RESULT,intent);

        finish();
    }

    private boolean isNullOrEmpty(String s){
        return s == null || s.isEmpty();
    }


}
