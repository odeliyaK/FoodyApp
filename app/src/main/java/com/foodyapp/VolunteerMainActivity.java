package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class VolunteerMainActivity extends AppCompatActivity {

    String items[] = new String[]{"Choose organization", "Manage Orders", "order food from supplier", "inventory management",
            "Parcels history"};
    ListView volList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_main);

        volList =(ListView)findViewById(R.id.volunteerlistview);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        volList.setAdapter(adapter);
        volList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if "choose organization" was clicked
                if (position==0){
                    openOrganizationActivity();
                }
                //if "Manage Orders" was clicked
                else if(position==1){
                    openOrdersActivity();
                }
            }
        });

    }


    public void openOrganizationActivity(){
        Intent intent=new Intent(this, OrganizationActivity.class);
        startActivity(intent);
    }

    public void openOrdersActivity(){
        Intent intent=new Intent(this, ManageOrdersActivity.class);
        startActivity(intent);
    }
}