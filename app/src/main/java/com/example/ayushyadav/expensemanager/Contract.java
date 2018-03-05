package com.example.ayushyadav.expensemanager;

/**
 * Created by ayushyadav on 04/03/18.
 */

public class Contract {

    public static final String DATABASE_NAME = "expenses_db";
    public static final int VERSION = 1;


    static class Expenses {

        public static final String TABLE_NAME = "expenses";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "desc";
        public static final String AMOUNT = "amount";

    }

    static class Comment {

        public static final String TABLE_NAME = "comment";
        public static final String ID = "id";
        public static final String Comment = "comment";
        public static final String ExpenseID = "expense_id";

    }

}
