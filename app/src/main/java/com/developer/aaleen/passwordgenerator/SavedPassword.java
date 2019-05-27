package com.developer.aaleen.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SavedPassword extends AppCompatActivity  {


//    private EditText mEdtSearch;
    private ListView mListView;
    private ArrayList<PasswordModel> mArrayList;
    private CustomAdapter mAdapter;
    DatabaseHelper dbHelper;
    String Name, Password;
    PasswordModel passwordModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_password);

  //      mEdtSearch = (EditText) findViewById(R.id.edtSearch);
        mListView = (ListView) findViewById(R.id.listView);
        mArrayList = new ArrayList<PasswordModel>();
        mAdapter = new CustomAdapter(this, mArrayList);
        mListView.setAdapter(mAdapter);

        dbHelper = new DatabaseHelper(this, "Password", null, 1);

        populate();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PasswordModel passwordModel = mArrayList.get(position);

                String Name = passwordModel.getName();
                String Password = passwordModel.getPassword();


            }
        });


        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                registerForContextMenu(mListView);
                dbHelper = new DatabaseHelper(SavedPassword.this, "Password", null, 1);
                passwordModel = mArrayList.get(position);

                 Name = passwordModel.getName();
                 Password = passwordModel.getPassword();

                return false;
            }
        });

    }

    private void populate() {

        mArrayList.clear();
        mArrayList.addAll(dbHelper.getAllPasswords());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "Copy");
        menu.add(0, 1, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 0:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", Password);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(SavedPassword.this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
                break;

            case 1:
                dbHelper.deletePassword(passwordModel);
                populate();
                Toast.makeText(SavedPassword.this, "Password deleted sucessfully", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }
}


