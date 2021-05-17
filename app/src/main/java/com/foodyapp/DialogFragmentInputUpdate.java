package com.foodyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.foodyapp.R;
import com.foodyapp.organizationsAlertDialogFragmentListener;


public class DialogFragmentInputUpdate extends DialogFragment {


    private EditText idField;
    private EditText NameField;
    private EditText Address;
    private Button saveBtn;
    private Button cancelBtn;
    private Activity activity;
    String myID, myName, myAddress;

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

//                if(TextUtils.isEmpty(idField.getText()) )
//                {
//                   idField.setError("ID is empty");
//                   idField.requestFocus();
//                }
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
//
                            Toast.makeText(activity, "id= "+ idField.getText().toString(), Toast.LENGTH_SHORT).show();
                            DataBase myDB= new DataBase(activity);
                            myDB.updateHouseHold(idField.getText().toString().trim(), NameField.getText().toString().trim(), Address.getText().toString().trim());
                            HouseHoldListActivity.itemInfos.set(HouseHoldListActivity.indexVal,new usersInfo(getFirstNameField() ,getLastNameField(),getIdField()));
                            HouseHoldListActivity.adapter.notifyDataSetChanged();
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