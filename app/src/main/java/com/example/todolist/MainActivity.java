package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText field_itemName;
    Button button_addItem;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializers();
    }

    public void initializers(){
        field_itemName = findViewById(R.id.itemName);
        button_addItem = findViewById(R.id.button_add);
        listView = findViewById(R.id.listView);
        itemList = FileHelper.readData(this);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Delete Item");
            alert.setMessage("Do you want to delete this item?");
            alert.setCancelable(true);
            alert.setNegativeButton("No", (dialog, which) -> {
                dialog.cancel();
            });
            alert.setPositiveButton("Yes", (dialog, which) -> {
                itemList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                FileHelper.writeData(itemList, getApplicationContext());
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
        listView.setAdapter(arrayAdapter);

        button_addItem.setOnClickListener(v -> {
            if (!field_itemName.getText().toString().equals("")) {

                String itemName = field_itemName.getText().toString();
                itemList.add(itemName);
                field_itemName.setText("");
                FileHelper.writeData(itemList, getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
            }else {
                Snackbar.make(listView, "Field cannot be an empty!", Snackbar.LENGTH_SHORT)
                        .setAction("Insert", v1 -> field_itemName.requestFocus())
                        .show();
            }

        });


    }
}