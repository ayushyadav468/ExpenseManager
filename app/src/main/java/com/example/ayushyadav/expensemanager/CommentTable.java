package com.example.ayushyadav.expensemanager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ayushyadav on 04/03/18.
 */

public class CommentTable {

    private int id;
    private String comment;
    private int expense_id;

    public CommentTable(int id, String comment, int expense_id) {
        this.id = id;
        this.comment = comment;
        this.expense_id = expense_id;
    }

    public CommentTable(String comment, int expense_id) {
        this.comment = comment;
        this.expense_id = expense_id;
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public static ArrayList<String> getDummyComment(int id){
        ArrayList<String> comment = new ArrayList<>();
        Random random = new Random();
        comment.add("Comment" + random);
        return comment;
    }
}
