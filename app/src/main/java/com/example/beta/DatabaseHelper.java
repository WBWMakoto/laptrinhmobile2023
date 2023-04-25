package com.example.beta;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "KhoHang.db";
    private static final int DATABASE_VERSION = 1;




    // Constructor




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // Check if Kho table exists before creating it
        String checkKhoTableQuery = "SELECT name FROM sqlite_master WHERE type='table' AND name='Kho'";
        Cursor cursor = db.rawQuery(checkKhoTableQuery, null);
        boolean tableExists = false;
        if (cursor != null) {
            tableExists = cursor.getCount() > 0;
            cursor.close();
        }

        // If Kho table doesn't exist, create it
        if (!tableExists) {
            String CREATE_KHO_TABLE = "CREATE TABLE Kho (MaKho TEXT PRIMARY KEY, TenKho TEXT, DiaChi TEXT, TaiTrong REAL)";
            db.execSQL(CREATE_KHO_TABLE);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS Kho");

        // Create tables again
        onCreate(db);
    }
    public ArrayList<Kho> getAllKho() {
        ArrayList<Kho> listKho = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Kho", null);

        if (cursor.moveToFirst()) {
            do {
                Kho kho = new Kho();
                kho.setMaKho(cursor.getString(0));
                kho.setTenKho(cursor.getString(1));
                kho.setDiaChi(cursor.getString(2));
                kho.setTaiTrong(cursor.getFloat(3));
                listKho.add(kho);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listKho;
    }
    public void deleteKho(Kho kho) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Kho", "MaKho = ?", new String[] {kho.getMaKho()});
        db.close();
    }

    public void deleteKhoList(List<Kho> khoList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Kho kho : khoList) {
            db.delete("Kho", "MaKho = ?", new String[]{String.valueOf(kho.getMaKho())});
        }
        db.close();
    }
    public void addKho(Kho kho) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaKho", kho.getMaKho());
        values.put("TenKho", kho.getTenKho());
        values.put("DiaChi", kho.getDiaChi());
        values.put("TaiTrong", kho.getTaiTrong());
        db.insert("Kho", null, values);
        db.close();
    }


}

