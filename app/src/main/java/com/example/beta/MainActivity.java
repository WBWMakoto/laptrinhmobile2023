package com.example.beta;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context appContext;
    private DatabaseHelper databaseHelper;
    private ListView listView;
    private KhoAdapter khoAdapter;
    private Button addButton;






    private Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        appContext = getApplicationContext();


        // initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // find the ListView in the layout and set up the adapter
        listView = findViewById(R.id.list_view);
        khoAdapter = new KhoAdapter(this, R.layout.activity_kho_adapter, databaseHelper.getAllKho());
        listView.setAdapter(khoAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toggle the selection state of the CheckBox for the clicked item
                Kho kho = khoAdapter.getItem(position);
                kho.setSelected(!kho.isSelected());

                // Update the view for the clicked item
                khoAdapter.notifyDataSetChanged();
            }
        });

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
// Create a list of selected Kho objects
                List<Kho> selectedKhoList = new ArrayList<>();
                for (int i = 0; i < khoAdapter.getCount(); i++) {
                    Kho kho = khoAdapter.getItem(i);
                    if (kho.isSelected()) {
                        selectedKhoList.add(kho);
                    }
                }
                // Remove the selected Kho objects from the database
                DatabaseHelper dbHelper = new DatabaseHelper(appContext);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                for (Kho kho : selectedKhoList) {
                    db.delete("Kho", "MaKho = ?", new String[]{String.valueOf(kho.getMaKho())});
                }
                db.close();

                // Remove the selected Kho objects from the adapter
                khoAdapter.removeAll(selectedKhoList);
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
                khoAdapter.notifyDataSetChanged();

                // clear the EditText fields
                tenKhoEditText.setText("");
                diaChiEditText.setText("");
                maKhoEditText.setText("");
                taiTrongEditText.setText("");
            }
        });
    }




}