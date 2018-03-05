package com.example.ayushyadav.expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ayushyadav on 04/03/18.
 */

public class ExpenseAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Expense> mExpenses;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenses) {
        mContext = context;
        mExpenses = expenses;
    }

    @Override
    public int getCount() {
        return mExpenses.size();
    }

    @Override
    public Expense getItem(int position) {
        return mExpenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output = convertView;
        if(output == null){
            output = inflater.inflate(R.layout.expense_row,parent,false);
            ExpenseViewHolder holder = new ExpenseViewHolder(output);
            output.setTag(holder);
        }
        ExpenseViewHolder holder = (ExpenseViewHolder) output.getTag();
        Expense expense = getItem(position);

        holder.titleTextView.setText(expense.getTitle());
        holder.amountTextView.setText(expense.getAmountString());
        holder.descTextView.setText(expense.getDescription());
        return output;
    }


    class ExpenseViewHolder {

        TextView titleTextView;
        TextView descTextView;
        TextView amountTextView;

        ExpenseViewHolder(View rowLayout) {
            titleTextView = rowLayout.findViewById(R.id.title);
            descTextView = rowLayout.findViewById(R.id.description);
            amountTextView = rowLayout.findViewById(R.id.amount);
        }
    }
}

