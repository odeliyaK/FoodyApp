package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HouseHoldListActivity extends Activity implements AddInputDialogFragment, UpdateInputDialogFragment {
    static List<usersInfo> itemInfos;
    static UsersAdapterOrg adapter;
    static Integer indexVal;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold_list);
        ListView myList = (ListView) findViewById(R.id.listView);

        itemInfos = new ArrayList<usersInfo>();
        itemInfos.add(new usersInfo("Linda Ron","Haifa, Hgalil st 23", "111"));
        itemInfos.add(new usersInfo("John Cohen","Kiryat Ata, Tal st 1", "222"));
        itemInfos.add(new usersInfo("Chris Levis","Nesher, Yael st 31", "333"));
        itemInfos.add(new usersInfo("Loren Li","Nesher, oren st 51", "444"));

         adapter = new UsersAdapterOrg(this, R.layout.activity_users_adapter_org, itemInfos);

        myList.setAdapter(adapter);
        Button updateBtn=findViewById(R.id.updateBtn);
        Button removeBtn=findViewById(R.id.removeBtn);
        Button addBtn = findViewById(R.id.addBtn);

        //gets us the position of the pressed item in the list
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item=itemInfos.get(position).getName().toString()+ " has been selected";

                indexVal=position;
                Toast.makeText(HouseHoldListActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        });

        //update house holds
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentInputUpdate frag = new DialogFragmentInputUpdate();
                Bundle args = new Bundle();
                args.putInt("title", R.string.alert_dialog_two_buttons_title);
                frag.setArguments(args);
                frag.show(getFragmentManager(), "dialog");
            }
        });

        //add new house holds to the list
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentInputAdd frag = new DialogFragmentInputAdd();
                Bundle args = new Bundle();
                args.putInt("title", R.string.alert_dialog_two_buttons_title);
                frag.setArguments(args);
                frag.show(getFragmentManager(), "dialog");
//                itemInfos.add(new usersInfo("Moshe Ron","Haifa, Hgalil st 25", "555"));
//                adapter.notifyDataSetChanged();
            }
        });


        //remove house holds from the list
        removeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                usersInfo u=itemInfos.get(indexVal);
                HouseHoldListActivity.itemInfos.remove(u);
               adapter.notifyDataSetChanged();
            }
        } );


    }

    @Override
    public void onDialogPositiveClick(DialogFragmentInputAdd dialog) {
        String res= dialog.getFirstNameField() + " " + dialog.getLastNameField() + " " + dialog.getIdField() ;
        Toast.makeText(this, "onDialogPositiveClick " + res,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragmentInputAdd dialog) {
        Toast.makeText(this, "onDialogNegativeClick " ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragmentInputUpdate dialog) {
        String res= dialog.getFirstNameField() + " " + dialog.getLastNameField() + " " + dialog.getIdField() ;
        Toast.makeText(this, "onDialogPositiveClick " + res,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragmentInputUpdate dialog) {
        Toast.makeText(this, "onDialogNegativeClick " ,Toast.LENGTH_SHORT).show();
    }
}