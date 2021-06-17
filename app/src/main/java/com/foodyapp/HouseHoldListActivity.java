package com.foodyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foodyapp.model.PackagesInfo;
import com.foodyapp.model.Volunteers;
import com.foodyapp.model.usersInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HouseHoldListActivity extends Activity implements AddInputDialogFragment, UpdateInputDialogFragment {
    static List<usersInfo> itemInfos;
    static UsersAdapterOrg adapter;
    static Integer indexVal;
    static ListView myList;
    boolean upFlag = false;
    boolean reFlag=false;
    String item;
    String selectedID;
    String selectedName;
    String selecteAddress;
    Context context;
    static int id = 0;
    private FirebaseAuth mAuth;
    List<usersInfo> templist;
    List<usersInfo> deletelist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold_list);
        ImageView toolBarArrow=findViewById(R.id.arrow);
        mAuth = FirebaseAuth.getInstance();
        ImageView rightIcon=findViewById(R.id.menu);

        myList = (ListView) findViewById(R.id.listView);
        this.context = this;

        MyInfoManager.getInstance().openDataBase(this);
        itemInfos=MyInfoManager.getInstance().getAllHouseHolds();
        adapter = new UsersAdapterOrg(this, R.layout.activity_users_adapter_org, itemInfos);
        myList.setAdapter(adapter);

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference collRef = db.collection("Households");
//
//        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
//
//                if (e != null) {
//                    Toast.makeText(context, "Listen failed."+ e,
//                            Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                if (snapshot != null && !snapshot.isEmpty()) {
//
//                    int lastID = 0;
//                    MyInfoManager.getInstance().deleteAllHouseholds();
//                    MyInfoManager.getInstance().deleteAllPackages();
//                    for (DocumentSnapshot document : snapshot.getDocuments() ){
//                        usersInfo house = document.toObject(usersInfo.class);
//                        MyInfoManager.getInstance().createHouseHold(new usersInfo(house.getName(), house.getAddress(), house.getId()));
//                        lastID = Integer.parseInt(house.getId());
//                    }
//                    if(id <= lastID)
//                        id = lastID + 1;
//
//                    itemInfos = MyInfoManager.getInstance().getAllHouseHolds();
//                    adapter = new UsersAdapterOrg(context, R.layout.activity_users_adapter_org, itemInfos);
//                    myList.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//
//
//                } else {
//                    Toast.makeText(context, "Current data: null",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        });


        toolBarArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLatetActivity();
            }
        });

        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        Button updateBtn=findViewById(R.id.updateBtn);
        Button removeBtn=findViewById(R.id.removeBtn);
        Button addBtn = findViewById(R.id.addBtn);

        //gets us the position of the pressed item in the list
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usersInfo user=(usersInfo)parent.getItemAtPosition(position);
                item="household named "+itemInfos.get(position).getName()+ " and id "+itemInfos.get(position).getId()+ " has been selected";
                selectedID = itemInfos.get(position).getId();
                selecteAddress = itemInfos.get(position).getAddress();
                selectedName = itemInfos.get(position).getName();
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
         //           bundle.putString("id", selectedID);
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
                    removeApprovalDialog.setMessage("Are you sure you want to remove " + itemInfos.get(indexVal).getName() + " id: " + itemInfos.get(indexVal).getId());
                    removeApprovalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            usersInfo currentuser = itemInfos.get(indexVal);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Households")
                                    .document(currentuser.getId()).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            MyInfoManager.getInstance().deleteHousehold(currentuser);
                                            FirebaseFirestore dbFire = FirebaseFirestore.getInstance();
                                            dbFire.collection("Packages").document(currentuser.getId()).delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            MyInfoManager.getInstance().deletePackage(new PackagesInfo(currentuser.getId(), currentuser.getId(), currentuser.getName(), currentuser.getAddress()));
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.out.println(e);
                                                }
                                            });
                                            adapter.remove(currentuser);
                                            if(!adapter.isEmpty())
                                                adapter.clear();
                                            itemInfos = MyInfoManager.getInstance().getAllHouseHolds();
                                            adapter = new UsersAdapterOrg(HouseHoldListActivity.this, R.layout.activity_users_adapter_org, itemInfos);
                                            myList.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();

                                            Toast.makeText(HouseHoldListActivity.this, "user: id -> " + currentuser.getId()+ ". name -> " + currentuser.getName() +" was deleted",Toast.LENGTH_LONG);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e);
                                }
                            });
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

    private void openLatetActivity(){
        Intent intent=new Intent(this, LatetMenu.class);
        startActivity(intent);
    }

    private void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openHouseActivity(){
        Intent intent=new Intent(this, HouseHoldListActivity.class);
        startActivity(intent);
    }
    public void openVolsActivity(){
        Intent intent=new Intent(this, VolunteersListActivity.class);
        startActivity(intent);
    }

    private void showMenu(View v){
        PopupMenu popupMenu=new PopupMenu(HouseHoldListActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.latet_toolbar_allscreens, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()== R.id.vols){
                    openVolsActivity();
                }
                else if (item.getItemId()== R.id.house){
                    openHouseActivity();
                }
                else if (item.getItemId()== R.id.logOut){
                    mAuth.signOut();
                    Intent intent=new Intent(HouseHoldListActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        popupMenu.show();
    }
    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
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

    @Override
    public void onDialogPositiveClick(DialogFragmentUpdateVolunteer dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragmentUpdateVolunteer dialog) {

    }


}