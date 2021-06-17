package com.foodyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.foodyapp.model.usersInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class DialogFragmentInputUpdate extends DialogFragment {


    private EditText idField;
    private EditText NameField;
    private EditText Address;
    private Button saveBtn;
    private Button cancelBtn;
    private Activity activity;
    String myID, myName, myAddress;
    Context context;
    protected static final int NEW_ITEM_TAG = -111;
    // Use this instance of the interface to deliver action events
    UpdateInputDialogFragment mListener;

    // Override the Fragment.onAttach() method to instantiate the MyAlertDialogFragmentListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the MyAlertDialogFragmentListener so we can send events to the host
            mListener = (UpdateInputDialogFragment) activity;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_dialog_fragment_input_update, null);
        context = v.getContext();
        MyInfoManager.getInstance().openDataBase(context);
        idField = (EditText) v .findViewById(R.id.idfield);
        NameField = (EditText)v .findViewById(R.id.firstnamefield);
        Address =  (EditText)v.findViewById(R.id.lastnamefield);
        saveBtn = (Button) v.findViewById(R.id.updateAddBtn);
        cancelBtn = (Button) v.findViewById(R.id.cancelAddBtn);

        idField.requestFocus();

        myID = this.getArguments().getString("id");
        myName = this.getArguments().getString("name");
        myAddress = this.getArguments().getString("address");

        if(myID != null)
            setIdField(myID);
        if(myName != null)
            setNameField(myName);
        if(myAddress != null)
            setAddress(myAddress);



//user save his changes
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                mListener.onDialogPositiveClick(DialogFragmentInputUpdate.this);


                 if(TextUtils.isEmpty(NameField.getText())){
                    NameField.setError("Name is empty");
                    NameField.requestFocus();
                }
                else if(TextUtils.isEmpty(Address.getText())){
                    Address.setError("Address is empty");
                    Address.requestFocus();
                }
                //alert dialog opens to save the new details
                else{
                    AlertDialog.Builder updateApprovalDialog=new AlertDialog.Builder(getContext());
                    updateApprovalDialog.setMessage("Are you suer you want to update "+getFirstNameField()+" "+ " ?");
                    updateApprovalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                String id= idField.getText().toString();
                                String name=NameField.getText().toString();
                                String address = Address.getText().toString();
                                int selected=HouseHoldListActivity.indexVal;
                                usersInfo currentuser = HouseHoldListActivity.itemInfos.get(selected);
                                usersInfo household =MyInfoManager.getInstance().getSelectedHouseHold();
//                                if(currentuser==null){
//                                    currentuser = new usersInfo(name, address);
//                                        MyInfoManager.getInstance().updateHousehold(currentuser);
//                                MyInfoManager.getInstance().createHouseHold(currentuser);
//                            }
//                                else{
//                                currentuser.setName(name);
//                                currentuser.setAddress(address);
//                                if(Integer.parseInt(currentuser.getId()) == NEW_ITEM_TAG){
//                                    MyInfoManager.getInstance().createHouseHold(currentuser);
//                                }
//                                else{
                                    boolean notAdd=false;
                                    //check if the address already exists in DB
                                    for (usersInfo u: MyInfoManager.getInstance().getAllHouseHolds()){
                                        if (!u.getId().equals(currentuser.getId()) && u.getAddress().equals(address))
                                            notAdd=true;
                                    }
                                    if (notAdd==false){
                                        currentuser.setName(name);
                                        currentuser.setAddress(address);
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        db.collection("Households")
                                                .document(currentuser.getId()).set(currentuser)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        MyInfoManager.getInstance().updateHousehold(currentuser);
//                                                        HouseHoldListActivity.itemInfos=MyInfoManager.getInstance().getAllHouseHolds();
//                                                        adapter=new UsersAdapterOrg(context, R.layout.activity_users_adapter_org,HouseHoldListActivity.itemInfos);
//                                                        HouseHoldListActivity.myList.setAdapter(adapter);
//                                                        HouseHoldListActivity.adapter.notifyDataSetChanged();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                System.out.println(e);
                                            }
                                        });
                                    }else {
                                        Toast.makeText(activity, "household can't be updated- his address already exists",Toast.LENGTH_LONG).show();

                                    }

//                                }
//                            }

                            } catch (Throwable e) {
                                e.printStackTrace();
                            }


                            dismiss();
                        }
                    });
                    updateApprovalDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    });
                    updateApprovalDialog.show();
                }
            }
        });



//user want to close the dialog (cancel)
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogNegativeClick(DialogFragmentInputUpdate.this);
                dismiss();
            }
        });


        return v;

    }
    public EditText getEditID(){ return idField;}

    public EditText getEditName(){ return NameField;}

    public EditText getEditAddress(){ return Address;}

    public String getFirstNameField() {
        return NameField.getText().toString();
    }

    public String getLastNameField() {
        return Address.getText().toString();
    }

    public String getIdField() {
        return idField.getText().toString();
    }
    public void setIdField(String id){ idField.setText(id); }

    public void setNameField(String name){
        NameField.setText(name);
    }

    public void setAddress(String address){ Address.setText(address);
    }
}