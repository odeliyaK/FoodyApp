package com.foodyapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.foodyapp.model.usersInfo;

import java.util.List;


public class DialogFragmentInputAdd extends DialogFragment {


    private EditText idField;
    private EditText NameField;
    private EditText Address;
    private Button saveBtn;
    private Button cancelBtn;
    private Activity activity;
    UsersAdapterOrg adapter;
    private Context context = null;

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
         context=v.getContext();
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

                    String name=NameField.getText().toString();
                    String address=Address.getText().toString();
                        usersInfo user=new usersInfo(name,address);
                     MyInfoManager.getInstance().createHouseHold(user);

                     HouseHoldListActivity.itemInfos=MyInfoManager.getInstance().getAllHouseHolds();
                     List<usersInfo> list = MyInfoManager.getInstance().getAllHouseHolds();
                     adapter=new UsersAdapterOrg(context, R.layout.activity_users_adapter_org,HouseHoldListActivity.itemInfos);
//
                    HouseHoldListActivity.myList.setAdapter(adapter);
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