package com.developer.aaleen.passwordgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Aaleen on 12/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table Credential(Username text, Password text)");
        db.execSQL("Create table Password(Name text, Password text)");
        Log.d("Table: ","Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(CredentialModel credentialModel){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Username", credentialModel.getUsername());
        values.put("Password", credentialModel.getPassword());

       long cred =  db.insert("Credential", null, values);
        if (cred>0){

            Log.d("User Added:", cred+"");

        }
        db.close();
        return true;
    }

    public boolean addNewPassword(PasswordModel passwordModel){
         db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", passwordModel.getName());
        values.put("Password", passwordModel.getPassword());

        long rowname = db.insert("Password", null, values);

        if (rowname>0){

            Log.d("Password Inserted:", rowname+"");

        }
        db.close();
        return true;
    }




    public ArrayList<PasswordModel> getAllPasswords(){
        ArrayList mArrayList = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        String columns[] = {"Name", "Password"};
        Cursor cursor = db.query("Password", columns, null, null, null, null, null);
        while(cursor.moveToNext()) {

            String name = cursor.getString(cursor.getColumnIndex("Name"));
            String password = cursor.getString(cursor.getColumnIndex("Password"));

            PasswordModel passwordModel = new PasswordModel(name, password);

            mArrayList.add(passwordModel);

        }
        return mArrayList;
      }

      public int deletePassword(PasswordModel passwordModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
          values.put("Name", passwordModel.getName());
          values.put("Password", passwordModel.getPassword());

          int deleteCnt = db.delete("Password", "Name = ? and Password = ?", new String[]{passwordModel.getName(), passwordModel.getPassword()});

          if (deleteCnt>0){
              Log.d("Data Deleted :", deleteCnt+"");
          }
          return deleteCnt;

      }
    }

