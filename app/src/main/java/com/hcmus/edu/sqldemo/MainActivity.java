package com.hcmus.edu.sqldemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private String dbName = "phonebook.db";

    ListView lvContacts;
    ArrayList<Contact> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContacts = findViewById(R.id.lvContacts);
        getContacts();
        Contact_Adapter adapter = new Contact_Adapter(this, R.layout.contact_row, data);
        lvContacts.setAdapter(adapter);
    }

    public static ArrayList<Contact> getMockData() {
        String[] names = {"Gary Hawkins", "Mike Lason", "Trung Tâm Tin Học", "Jose Alexander", "Stephanie Gardner", "Nancy Greene", "Howard Wheeler", "Patricia Stephens", "Carl Garza", "Kelly Evans"};
        int[] id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] phones = {"2701876007", "1671898312", "8308954038", "3508320983", "3317970165", "5076902404", "6322118141", "3263941740", "5544270757", "5540715128"};
        ArrayList<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < names.length; i++)
            contacts.add(new Contact(id[i], names[i], phones[i]));
        return contacts;
    }



    //SQLite Code below

    private SQLiteDatabase openDB() {
        return openOrCreateDatabase(dbName, MODE_PRIVATE, null);
    }

    private void closeDB(SQLiteDatabase db) {
        db.close();
    }

    private void createContactTable() {
        SQLiteDatabase db = openDB();
        String sql = "create table if not exists tblPhonebook(id integer PRIMARY KEY autoincrement, name text, phone text);";
        db.execSQL(sql);
        closeDB(db);
    }

    private void insert(Contact c) {
        SQLiteDatabase db = openDB();
        ContentValues cv = new ContentValues();
        cv.put("name", c.getName());
        cv.put("phone", c.getPhone());
        db.insert("tblPhonebook", null, cv);
        closeDB(db);
    }

    private ArrayList<Contact> getContacts() {
        SQLiteDatabase db = openDB();
        ArrayList<Contact> data = getMockData();
        String sql = "select*from tblPhonebook";
        Cursor csr = db.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int id = csr.getInt(0);
                    String name= csr.getString(1);
                    String phone = csr.getString(2);
                    data.add(new Contact(id, name, phone));
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return data;
    }

    private void updateContact(Contact c) {
        SQLiteDatabase db = openDB();
        ContentValues cv = new ContentValues();
        cv.put("name", c.getName());
        cv.put("phone", c.getPhone());
        String[] id = {String.valueOf(c.getId())};
        int row = db.update("tblPhonebook", cv, "id=?", id);
        closeDB(db);
    }

    private void deleteConact(Contact c) {
        String[] id = {String.valueOf(c.getId())};
        SQLiteDatabase db = openDB();
        db.delete("tblPhonebook", "id=?", id);
        closeDB(db);
    }

    private Contact getContact(Contact c) {
        String[] fields = {"id", "name", "phone"};
        String[] ids = {String.valueOf(c.getId())};
        SQLiteDatabase db = openDB();
        Cursor cursor = db.query("tblPhonebook", fields, "id=?", ids, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String phone = cursor.getString(2);
        closeDB(db);
        return new Contact(id, name, phone);

    }

    //SQLite Code ended
}
