package com.foodyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.foodyapp.model.Volunteers;
import com.foodyapp.model.usersInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class DialogFragmentUpdateVolunteer extends DialogFragment {


    private EditText idField;
    private EditText NameField;
    private EditText phone;
    private Button saveBtn;
    private Button cancelBtn;
    private Activity activity;
    String myID, myName, myPhone;
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

        View v = inflater.inflate(R.layout.dialog_update_vols, null);
        NameField = (EditText)v .findViewById(R.id.name);
        phone =  (EditText)v.findViewById(R.id.phone);
        saveBtn = (Button) v.findViewById(R.id.updateAddBtn);
        cancelBtn = (Button) v.findViewById(R.id.cancelAddBtn);

        myID = this.getArguments().getString("email");
        myName = this.getArguments().getString("name");
        myPhone = this.getArguments().getString("phone");

        if(myName != null)
            setNameField(myName);
        if(myPhone != null)
            setPhone(myPhone);



//user save his changes
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                mListener.onDialogPositiveClick(DialogFragmentUpdateVolunteer.this);


                if(TextUtils.isEmpty(NameField.getText())){
                    NameField.setError("Name is empty");
                    NameField.requestFocus();
                }
                else if(TextUtils.isEmpty(phone.getText())){
                    phone.setError("phone is empty");
                    phone.requestFocus();
                }
                //alert dialog opens to save the new details
                else{
                    AlertDialog.Builder updateApprovalDialog=new AlertDialog.Builder(getContext());
                    updateApprovalDialog.setMessage("Are you suer you want to update "+myID+" "+ " ?");
                    updateApprovalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                String name=NameField.getText().toString();
                                String phones = phone.getText().toString();
                                int selected=VolunteersListActivity.indexVal;
                                Volunteers currentUser = VolunteersListActivity.itemInfos.get(selected);

                                if(currentUser != null){
                                    Volunteers vol = new Volunteers(currentUser.getEmail(), name, phones);
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("Volunteers")
                                            .document(currentUser.getEmail()).set(vol)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    MyInfoManager.getInstance().updateVolunteer(vol);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            System.out.println(e);
                                        }
                                    });
                                }
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
                mListener.onDialogNegativeClick(DialogFragmentUpdateVolunteer.this);
                dismiss();
            }
        });


        return v;

    }
    public EditText getEditID(){ return idField;}

    public EditText getEditName(){ return NameField;}

    public EditText getEditPhone(){ return phone;}

    public String getFirstNameField() {
        return NameField.getText().toString();
    }

    public String getPhoneField() {
        return phone.getText().toString();
    }

    public String getIdField() {
        return idField.getText().toString();
    }
    public void setIdField(String id){ idField.setText(id); }

    public void setNameField(String name){
        NameField.setText(name);
    }

    public void setPhone(String address){ phone.setText(address);
    }
}