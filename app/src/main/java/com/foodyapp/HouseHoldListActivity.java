package com.foodyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.foodyapp.model.usersInfo;

import java.util.ArrayList;
import java.util.List;

public class HouseHoldListActivity extends Activity implements AddInputDialogFragment, UpdateInputDialogFragment {
    static List<usersInfo> itemInfos;
    static UsersAdapterOrg adapter;
    static Integer indexVal;
    boolean upFlag = false;
    boolean reFlag=false;
    String item;
    String selectedID;
    String selectedName;
    String selecteAddress;
    Context context;

    DataBase myDB;
    ArrayList<String> household_id, household_name, household_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold_list);
        ListView myList = (ListView) findViewById(R.id.listView);
        this.context = this;
        myDB=new DataBase(HouseHoldListActivity.this);
        household_id=new ArrayList<>();
        household_name=new ArrayList<>();
        household_address=new ArrayList<>();
        itemInfos = new ArrayList<usersInfo>();
        Cursor cursor=myDB.readAllHouseHolds();
        if (cursor.getCount()==0){
            Toast.makeText(context, "There are no households", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                usersInfo u=new usersInfo(cursor.getString(1), cursor.getString(2), cursor.getString(0));
                itemInfos.add(u);
                adapter = new UsersAdapterOrg(this, R.layout.activity_users_adapter_org, itemInfos);
                myList.setAdapter(adapter);
            }
        }

        Button updateBtn=findViewById(R.id.updateBtn);
        Button removeBtn=findViewById(R.id.removeBtn);
        Button addBtn = findViewById(R.id.addBtn);

        //gets us the position of the pressed item in the list
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item=itemInfos.get(position).getName().toString()+ " has been selected";
                selectedID = itemInfos.get(position).getId().toString();
                selecteAddress = itemInfos.get(position).getAddress().toString();
                selectedName = itemInfos.get(position).getName().toString();
                upFlag = true;
                reFlag=true;
                indexVal=position;
                Toast.makeText(HouseHoldListActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        });

        //update house holds
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upFlag){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", selectedID);
                    bundle.putString("name", selectedName);
                    bundle.putString("address", selecteAddress);
                    DialogFragmentInputUpdate frag = new DialogFragmentInputUpdate();
                    bundle.putInt("title", R.string.alert_dialog_two_buttons_title);
                    frag.setArguments(bundle);
                    frag.show(getFragmentManager(), "dialog");

                    upFlag = false;
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("PAY ATTENTION").setMessage("You have to choose one household").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
//                    Toast.makeText(HouseHoldListActivity.this,"You have to select one household to update",Toast.LENGTH_LONG).show();
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
            }
        });


        //remove house holds from the list
        removeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (reFlag) {
                    AlertDialog.Builder removeApprovalDialog = new AlertDialog.Builder(context);
                    removeApprovalDialog.setMessage("Are you sure you want to remove " + itemInfos.get(indexVal).getName());
                    removeApprovalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBase db = new DataBase(HouseHoldListActivity.this);
                            db.reomoveHouseHold(itemInfos.get(indexVal).getId());
                            usersInfo u = itemInfos.get(indexVal);
                            HouseHoldListActivity.itemInfos.remove(u);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    removeApprovalDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    removeApprovalDialog.show();
                    reFlag=false;
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("PAY ATTENTION").setMessage("You have to choose one household").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();
            }

            }
        } );


    }

    @Override
    public void onDialogPositiveClick(DialogFragmentInputAdd dialog) {
    }

    @Override
    public void onDialogNegativeClick(DialogFragmentInputAdd dialog) {
    }

    @Override
    public void onDialogPositiveClick(DialogFragmentInputUpdate dialog) {
    }

    @Override
    public void onDialogNegativeClick(DialogFragmentInputUpdate dialog) {
    }

}