package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UsersActivity extends Activity {

    private ListView list;
    static  UsersLIstAdapter adapter;
    static Integer indexVal;
    static List<usersInfo> itemInfos;
    String item;
    DataBase myDB;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        this.context = this;
        list = (ListView) findViewById(R.id.list);
        myDB=new DataBase(UsersActivity.this);
        itemInfos = new ArrayList<usersInfo>();
        Cursor cursor=myDB.readAllPackages();
        if (cursor.getCount()==0){
            Toast.makeText(context, "There are no households", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                usersInfo u=new usersInfo(cursor.getString(1), cursor.getString(2), cursor.getString(0));
                itemInfos.add(u);
                adapter = new UsersLIstAdapter(this, itemInfos);
                list.setAdapter(adapter);
            }
        }


//        adapter = new UsersLIstAdapter(this, itemInfos);


    //    list.setAdapter(adapter);


    }


}