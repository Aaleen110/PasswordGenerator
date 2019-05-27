package com.developer.aaleen.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private EditText mEdtPassword;
    private Button mBtnGeneratePassword;
    private ImageButton mImageBtnSave, mImageBtnCopy, mImageBtnShare;

    private boolean my_checkbox_uppercase, my_checkbox_lowercase, my_checkbox_numbers, my_checkbox_spaces, my_checkbox_special;
    private String len, prefix, password, passwordCondition;

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_SYMBOLS = "!@#$%^&*_=+-/";
    private static final String SPACES = " ";

    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadPref();

        mBtnGeneratePassword.setOnClickListener(new ButtonClickListener());
        mImageBtnCopy.setOnClickListener(new ButtonClickListener());
        mImageBtnSave.setOnClickListener(new ButtonClickListener());
        mImageBtnShare.setOnClickListener(new ButtonClickListener());




    }


    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch(v.getId()){

                case R.id.mBtnGeneratePassword:

         //LOGIC TO GENERATE PASSWORD ON BASIS OF SELECTION
                    if(len.equals("")){

                        len = "10";
                    }
                    passwordCondition = "";

                Log.d("NUMBER", prefix + len);
                    if( my_checkbox_uppercase  && my_checkbox_lowercase && my_checkbox_numbers  && my_checkbox_spaces && my_checkbox_special) {

                        password = generatePassword(Integer.parseInt(len), UPPERCASE + LOWERCASE + NUMBERS + SPECIAL_SYMBOLS + SPACES);
                    }
                    else {
                            if (my_checkbox_uppercase) {
                                passwordCondition = passwordCondition + UPPERCASE;
                            }
                            if (my_checkbox_lowercase) {
                                passwordCondition = passwordCondition + LOWERCASE;
                            }
                            if (my_checkbox_numbers) {
                                passwordCondition = passwordCondition + NUMBERS;
                            }
                            if (my_checkbox_spaces) {
                                passwordCondition = passwordCondition + SPACES;
                            }
                            if (my_checkbox_special) {
                                passwordCondition = passwordCondition + SPECIAL_SYMBOLS;
                            }
                            password = generatePassword(Integer.parseInt(len), passwordCondition);

                    }

                    if(!prefix.equals("")){
                        password = prefix+password;

                    }

                    Log.d("STRING", prefix.toString()+ " "+ password);

                    mEdtPassword.setText(password);
                    break;

                case R.id.mImageBtnSave:

                    if(!mEdtPassword.getText().toString().equals("")){


                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.custom_dialog, null);

                    builder.setView(dialogView);
                    builder.setTitle("Enter name for this Password");
                  //  builder.setMessage("Enter a name for this password");
                    builder.setCancelable(true);

                    final EditText input = (EditText) dialogView.findViewById(R.id.edtSaveAlert);

                    builder.setPositiveButton(
                            "Save",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                   if (!input.getText().toString().equals("")) {
                                       dbHelper = new DatabaseHelper(MainActivity.this, "Password", null, 1);
                                       PasswordModel passwordModel = new PasswordModel(input.getText().toString(), mEdtPassword.getText().toString());
                                       dbHelper.addNewPassword(passwordModel);
                                       toast("Password saved");

                                   } else {
                                       toast("Name cannot be blank");
                                   }



                                }
                            });

                    builder.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                    break;

            }else
            {
                toast("Generate password first");
            }
                    break;
                case R.id.mImageBtnCopy:

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Text", mEdtPassword.getText().toString());
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(MainActivity.this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.mImageBtnShare:
                    Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        loadPref();
    }

    private void loadPref() {


        //getting the settings from shared Preference
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

             my_checkbox_uppercase = mySharedPreferences.getBoolean("checkbox_uppercase", false);
             my_checkbox_lowercase = mySharedPreferences.getBoolean("checkbox_lowercase", false);
             my_checkbox_numbers = mySharedPreferences.getBoolean("checkbox_numbers", false);
             my_checkbox_spaces = mySharedPreferences.getBoolean("checkbox_spaces", false);
             my_checkbox_special = mySharedPreferences.getBoolean("checkbox_special", false);

         len = mySharedPreferences.getString("edittext_passwordlength", "10");
         prefix = mySharedPreferences.getString("edittext_prefix", "abc");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 1, 0, "Saved Password");;
        menu.add(0, 2, 0, "Settings");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case 1:
                Intent intent1 = new Intent(MainActivity.this, SavedPassword.class);
                startActivityForResult(intent1, 0);
                break;

            case 2:
                Intent intent2 = new Intent(MainActivity.this, SettingsPref.class);
                startActivityForResult(intent2, 1);
                break;


        }

        return true;
    }




    private void init() {

        mEdtPassword = (EditText) findViewById(R.id.mEdtPassword);
        mBtnGeneratePassword = (Button) findViewById(R.id.mBtnGeneratePassword);
        mImageBtnSave = (ImageButton) findViewById(R.id.mImageBtnSave);
        mImageBtnCopy = (ImageButton) findViewById(R.id.mImageBtnCopy);
        mImageBtnShare = (ImageButton) findViewById(R.id.mImageBtnShare);

        // To make edit text non editable
        // mEdtPassword.setKeyListener(null);

    }

    //Method to generate password
    public static String generatePassword(int len, String dic){
        String result = "";
        Random random = new Random();

       for(int i = 0; i< len; i++){
       int index = random.nextInt(dic.length());
           result += dic.charAt(index);
       }
        return result;
    }

    public void toast(String str){
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

}