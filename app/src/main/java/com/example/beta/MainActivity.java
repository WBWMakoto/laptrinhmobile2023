package com.example.beta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private KhoAdapter khoAdapter;
    private Button addButton;

    private Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // find the ListView in the layout and set up the adapter
        listView = findViewById(R.id.list_view);
        khoAdapter = new KhoAdapter(this, R.layout.activity_kho_adapter, databaseHelper.getAllKho());
        listView.setAdapter(khoAdapter);

        // find the buttons in the layout and set up click listeners


        Button thongKeButton = findViewById(R.id.button_statistics);
        thongKeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass the count and list of Kho objects to the StatisticsActivity
                int count = khoAdapter.getCount();
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                intent.putExtra("count", count);
                intent.putParcelableArrayListExtra("danhSachKho", new ArrayList<>(khoAdapter.getList()));
                startActivity(intent);
            }
        });

        buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
                if (checkedPositions != null) {
                    // Create a list to store the Kho objects to delete
                    List<Kho> khoToDelete = new ArrayList<>();

                    // Loop through the checked items in reverse order to avoid index issues
                    for (int i = checkedPositions.size() - 1; i >= 0; i--) {
                        int position = checkedPositions.keyAt(i);

                        // Check if the item at the current position is checked
                        if (checkedPositions.valueAt(i)) {
                            // Get the Kho object at the current position
                            Kho kho = (Kho) listView.getItemAtPosition(position);

                            // Add the Kho object to the list of items to delete
                            khoToDelete.add(kho);
                        }
                    }

                    // Delete the Kho objects from the database using the databaseHelper object
                    databaseHelper.deleteKhoList(khoToDelete);

                    // Remove the Kho objects from the adapter
                    khoAdapter.removeAll(khoToDelete);

                    // Clear the checked items in the ListView
                    listView.clearChoices();

                    // Notify the adapter that the data set has changed
                    khoAdapter.notifyDataSetChanged();
                }
            }
        });




        // find the "Add" button in the layout and set up a click listener
        addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the user input from the EditText fields
                EditText tenKhoEditText = findViewById(R.id.edit_text_ten_kho);
                EditText diaChiEditText = findViewById(R.id.edit_text_dia_chi);
                EditText maKhoEditText = findViewById(R.id.edit_text_ma_kho);
                EditText taiTrongEditText = findViewById(R.id.edit_text_tai_trong);

                String tenKho = tenKhoEditText.getText().toString().trim();
                String diaChi = diaChiEditText.getText().toString().trim();
                String maKho = maKhoEditText.getText().toString().trim();
                String taiTrongString = taiTrongEditText.getText().toString().trim();

                // check that the required fields are not empty
                if (tenKho.isEmpty() || diaChi.isEmpty() || maKho.isEmpty() || taiTrongString.isEmpty()) {
                    // show an error message to the user
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // parse the Tai Trong value to a float
                float taiTrong = Float.parseFloat(taiTrongString);

                // create a new Kho object with the user input and add it to the database and adapter
                Kho kho = new Kho(maKho, tenKho, diaChi, taiTrong, false);
                databaseHelper.addKho(kho);
                khoAdapter.add(kho);

                // clear the EditText fields
                tenKhoEditText.setText("");
                diaChiEditText.setText("");
                maKhoEditText.setText("");
                taiTrongEditText.setText("");
            }
        });
    }




}