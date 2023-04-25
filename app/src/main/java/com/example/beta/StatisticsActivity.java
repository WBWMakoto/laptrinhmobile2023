package com.example.beta;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.beta.Kho;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    private ArrayList<Kho> danhSachKho;
    private ListView listViewThongKe;
    private TextView textViewThongKe;
    private int tongSoKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        //danhSachKho = getIntent().getParcelableArrayListExtra("danhSachKho");

        danhSachKho = getIntent().<Kho>getParcelableArrayListExtra("danhSachKho");



        tongSoKho = danhSachKho.size();

        listViewThongKe = (ListView) findViewById(R.id.listViewThongKe);
        textViewThongKe = (TextView) findViewById(R.id.textViewThongKe);

        int soKhoTaiTrongLonHon10 = 0;
        ArrayList<String> danhSachKhoTaiTrongLonHon10 = new ArrayList<String>();
        for (Kho kho : danhSachKho) {
            if (kho.getTaiTrong() >= 10) {
                soKhoTaiTrongLonHon10++;
                danhSachKhoTaiTrongLonHon10.add("Mã kho: " + kho.getMaKho() + ", Tên kho: " + kho.getTenKho() + ", Địa chỉ kho: " + kho.getDiaChi() + ", Tải trọng: " + kho.getTaiTrong());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, danhSachKhoTaiTrongLonHon10);
        listViewThongKe.setAdapter(adapter);

        textViewThongKe.setText("Có " + soKhoTaiTrongLonHon10 + " / " + tongSoKho + " kho >=10 về tải trọng và những kho như vậy sẽ được hiển thị ở dưới");

        listViewThongKe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Nothing to do
            }
        });
    }
}
