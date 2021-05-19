package com.foodyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodyapp.R;
import com.foodyapp.organizationsAlertDialogFragmentListener;


public class DialogFragmentInputAdd extends DialogFragment {


    private EditText idField;
    private EditText NameField;
    private EditText Address;
    private Button saveBtn;
    private Button cancelBtn;
    private Activity activity;

    // Use this instance of the interface to deliver action events
    AddInputDialogFragment mListener;

    // Override the Fragment.onAttach() method to instantiate the MyAlertDialogFragmentListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the MyAlertDialogFragmentListener so we can send events to the host
            mListener = (AddInputDialogFragment) activity;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_dialog_fragment_input_add, null);

        idField = (EditText) v .findViewById(R.id.idfield);
        NameField = (EditText)v .findViewById(R.id.firstnamefieldAdd);
        Address =  (EditText)v.findViewById(R.id.AddressAdd);
        saveBtn = (Button) v.findViewById(R.id.saveAddBtn);
        cancelBtn = (Button) v.findViewById(R.id.cancelAddBtn);

//user save his changes
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  String res= getFirstNameField() + " " + getAddress() + " " + getIdField() ;
                mListener.onDialogPositiveClick(DialogFragmentInputAdd.this);

                 if(TextUtils.isEmpty(NameField.getText())){
                    NameField.setError("Name is empty");
                    NameField.requestFocus();
                }
              else  if(TextUtils.isEmpty(Address.getText())){
                    Address.setError("Address is empty");
                    Address.requestFocus();
                }
                else{
                    DataBase myDB=new DataBase(activity);
                    myDB.addHouseHold( getFirstNameField().trim(),getAddress().trim());
                    String newId=String.valueOf(myDB.newId);
                     HouseHoldListActivity.itemInfos.add(new usersInfo(getFirstNameField() ,getAddress(),newId));
                     HouseHoldListActivity.adapter.notifyDataSetChanged();
                    dismiss();
                }
            }
        });



//user want to close the dialog (cancel)
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogNegativeClick(DialogFragmentInputAdd.this);
                dismiss();
            }
        });
        return v;

    }

    public String getFirstNameField() {
        return NameField.getText().toString();
    }

    public String getAddress() {
        return Address.getText().toString();
    }

    public String getIdField() {
        return idField.getText().toString();
    }
}