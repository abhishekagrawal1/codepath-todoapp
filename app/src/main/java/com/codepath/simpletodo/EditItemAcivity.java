package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemAcivity extends AppCompatActivity {
    private EditText etModilfiedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String task = getIntent().getStringExtra("task");

        etModilfiedItem = (EditText) findViewById(R.id.etModifiedItem);
        etModilfiedItem.setText(task);

    }


    /*
        Function: saving the modified item in the intent
        to be returned back to the parent activity
     */
    public void onItemSave(View v){
        Intent data = new Intent();
        data.putExtra("task", etModilfiedItem.getText().toString());
        data.putExtra("position", getIntent().getIntExtra("position", 0));
        setResult(RESULT_OK, data);
        this.finish();
    }
}
