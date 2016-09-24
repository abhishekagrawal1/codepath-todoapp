package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//xml layout for the activity is applied
        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapter =  new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setUpListViewListener();
        setUpEditViewListener();
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }


    /*
        Function: Listening delete item event(long-click)
     */
    private void setUpListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }


    /*
        Function: Listening edit item event(single-click)
     */
    private void setUpEditViewListener(){
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        launchEditView(position);
                    }
                }
        );
    }


    /*
        Function makes a request to start a new activity
        for modifying a todo list item
     */
    private void launchEditView(int position){
        //This is an explicit intent used to call a new call a new screen
        Intent intent = new Intent(MainActivity.this,EditItemAcivity.class);
        intent.putExtra("task", items.get(position));
        intent.putExtra("position", position);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK && requestCode==REQUEST_CODE){
            String modifiedItem = data.getStringExtra("task");
            int position = data.getIntExtra("position", 0);
            items.set(position,modifiedItem);
            writeItems();
            itemsAdapter.notifyDataSetChanged();
        }
    }

    /*
        Function to read todo list from a local file
     */
    private void readItems(){
        File fileDir = getFilesDir();
        File toDoFile = new File(fileDir,"todo.txt");

        try{
            items  = new ArrayList<>(FileUtils.readLines(toDoFile));
        }catch (IOException ex){
            ex.printStackTrace();
            items = new ArrayList<>();
        }
    }

    /*
        Function to write todo list to a local file
    */
    private void writeItems(){
        File fileDir = getFilesDir();
        File toDoFile = new File(fileDir,"todo.txt");

        try{
            FileUtils.writeLines(toDoFile, items);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

}

