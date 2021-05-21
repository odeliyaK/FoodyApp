package com.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.foodyapp.model.usersInfo;

import java.util.ArrayList;
import java.util.List;

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
      


//        adapter = new UsersLIstAdapter(this, itemInfos);


    //    list.setAdapter(adapter);


    }


}