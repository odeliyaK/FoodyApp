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

import com.foodyapp.model.Volunteers;
import com.foodyapp.model.usersInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VolunteersListActivity extends Activity implements UpdateInputDialogFragment {
    static List<Volunteers> itemInfos;
    static VolunteerAdapterOrg adapter;
    static Integer indexVal;
    static ListView myList;
    boolean upFlag = false;
    boolean reFlag=false;
    String item;
    String selectedID;
    String selectedName;
    String selectePhone;
    Context context;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteers_list);
        ImageView toolBarArrow=findViewById(R.id.arrow);
        ImageView rightIcon=findViewById(R.id.menu);
        mAuth = FirebaseAuth.getInstance();

        myList = (ListView) findViewById(R.id.listView);
        this.context = this;

        MyInfoManager.getInstance().openDataBase(this);
        itemInfos=MyInfoManager.getInstance().allVolunteers();
        adapter = new VolunteerAdapterOrg(this, R.layout.activity_volunteer_adapter_org, itemInfos);
        myList.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("Volunteers");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                        Toast.makeText(context, "Listen failed."+ e,
                                Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    Toast.makeText(context, "Current data: " + snapshot.getDocuments(),
                            Toast.LENGTH_LONG).show();

                    MyInfoManager.getInstance().deleteAllVols();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Volunteers vol = document.toObject(Volunteers.class);
                        MyInfoManager.getInstance().newVolunteer(vol.getEmail(), vol.getName(), vol.getPhone());

                    }

                    itemInfos = MyInfoManager.getInstance().allVolunteers();
                    adapter = new VolunteerAdapterOrg(context,R.layout.activity_volunteer_adapter_org,itemInfos);
                    myList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(context, "Current data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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
                Volunteers user=(Volunteers)parent.getItemAtPosition(position);
                item=itemInfos.get(position).getEmail()+ " has been selected";
                selectedID = itemInfos.get(position).getEmail();
                selectePhone = itemInfos.get(position).getPhone();
                selectedName = itemInfos.get(position).getName();
                upFlag = true;
                reFlag=true;
                indexVal=position;
                Toast.makeText(VolunteersListActivity.this, user.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //update volunteer
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upFlag){
                    Bundle bundle = new Bundle();
                    bundle.putString("email", selectedID);
                    bundle.putString("name", selectedName);
                    bundle.putString("phone", selectePhone);
                    DialogFragmentUpdateVolunteer frag = new DialogFragmentUpdateVolunteer();
                    bundle.putInt("title", R.string.alert_dialog_two_buttons_title);
                    frag.setArguments(bundle);
                    frag.show(getFragmentManager(), "dialog");

                    upFlag = false;
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("PAY ATTENTION").setMessage("You have to choose one volunteer").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
            }
        });

        //remove volunteer from the list
        removeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (reFlag) {

                    AlertDialog.Builder removeApprovalDialog = new AlertDialog.Builder(context);
                    removeApprovalDialog.setMessage("Are you sure you want to remove " + itemInfos.get(indexVal).getEmail());
                    removeApprovalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Volunteers currentuser = itemInfos.get(indexVal);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Volunteers")
                                    .document(currentuser.getEmail()).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            MyInfoManager.getInstance().removeVolunteer(currentuser);
                                            adapter.remove(currentuser);
                                            if(!adapter.isEmpty())
                                                adapter.clear();
                                            VolunteersListActivity.itemInfos = MyInfoManager.getInstance().allVolunteers();
                                            adapter = new VolunteerAdapterOrg(VolunteersListActivity.this, R.layout.activity_volunteer_adapter_org,VolunteersListActivity.itemInfos);
                                            myList.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();

                                            Toast.makeText(VolunteersListActivity.this, currentuser.getEmail()+" was deleted",Toast.LENGTH_LONG);
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
                    builder.setTitle("PAY ATTENTION").setMessage("You have to choose one volunteer").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }

            }
        } );


    }

    public void newVolunteerInList(Volunteers vol){
//        if(adapter != null)
//            adapter.clear();
        itemInfos = MyInfoManager.getInstance().allVolunteers();
        adapter = new VolunteerAdapterOrg(VolunteersListActivity.this, R.layout.activity_volunteer_adapter_org, itemInfos);
        myList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
        PopupMenu popupMenu=new PopupMenu(VolunteersListActivity.this, v);
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
                    Intent intent=new Intent(VolunteersListActivity.this, MainActivity.class);
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