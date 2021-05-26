package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodyapp.model.Volunteers;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity  implements  organizationsAlertDialogFragmentListener {
      Button organization_btn;
      EditText email;
      EditText pass;
    EditText confirm_pass;
    EditText name;
    Button reg_btn;
    EditText phone;
    int whoIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyInfoManager.getInstance().openDataBase(this);

        setContentView(R.layout.activity_registration);

//        organization_btn=(Button)findViewById(R.id.present_organizations);
//        organization_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openOrganizationsActivity();
//            }
//        });

        email=(EditText)findViewById(R.id.emailAddress);
        pass=(EditText)findViewById(R.id.password);
        confirm_pass=(EditText)findViewById(R.id.confirm_password);
        phone=(EditText)findViewById(R.id.phone);
        name = (EditText)findViewById(R.id.myName);
        reg_btn=(Button)findViewById(R.id.registration_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valid();
            }
        });
    }

//    public void openOrganizationsActivity(){
//        if (TextUtils.isEmpty(email.getText())){
//            email.setError("Enter an email");
//            email.requestFocus();
//        } else if (TextUtils.isEmpty(pass.getText())){
//            pass.setError("Enter an password");
//            pass.requestFocus();
//        }else if (!TextUtils.equals(pass.getText(), confirm_pass.getText())){
//            pass.setError("password not equal");
//            pass.requestFocus();
//            confirm_pass.setError("password not equal");
//            confirm_pass.requestFocus();
//        }
//        else {
//            showCustomAlertDialog();
//        }
//
//    }

    private void showCustomAlertDialog() {
        OganizationDialogFragment frag = new OganizationDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", 1);
        frag.setArguments(args);
        frag.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onDialogPositiveClick(OganizationDialogFragment dialog) {
//        String res= dialog.getFirstNameField() + " " + dialog.getLastNameField() + " " + dialog.getIdField() ;
//        Toast.makeText(this, "onDialogPositiveClick " + res,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(OganizationDialogFragment dialog) {
        Toast.makeText(this, "onDialogNegativeClick " ,Toast.LENGTH_SHORT).show();
    }

    public void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void valid(){
        if (TextUtils.isEmpty(email.getText())){
            email.setError("Enter an email");
            email.requestFocus();
        }else if(!isValidEmail(email.getText())){
            email.setError("Email address not valid");
            email.requestFocus();
        }
        else if (TextUtils.isEmpty(pass.getText())){
            pass.setError("Enter a password");
            pass.requestFocus();
        }else if (!TextUtils.equals(pass.getText(), confirm_pass.getText())){
            pass.setError("password not equal");
            pass.requestFocus();
            confirm_pass.setError("password not equal");
            confirm_pass.requestFocus();
        }else if (TextUtils.isEmpty(name.getText())){
            name.setError("Enter your name");
            name.requestFocus();
        }
        else {
            if(MyInfoManager.getInstance().newVolunteer(name.getText().toString(), phone.getText().toString()) > 0)
                openVolunteerActivity();
        }


    }

//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.orgBtn:
//                if (checked){
//                    organization_btn.setVisibility(View.GONE);
//                    whoIsChecked = 1;
//                    reg_btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            valid();
//                        }
//                    });
//                }
//                    break;
//            case R.id.volBtn:
//                if (checked){
//                    organization_btn.setVisibility(View.VISIBLE);
//                    whoIsChecked = 2;
//                    reg_btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            valid();
//                        }
//                    });
//                }
//                    break;
//        }
//    }

    public void openVolunteerActivity(){
        Intent intent=new Intent(this, VolunteerMainActivity.class);
        startActivity(intent);
    }

    public static boolean isValidEmail(CharSequence mail) {
        return (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches());
    }
//    public void openOrgActivity(){
//        Intent intent=new Intent(this, HouseHoldListActivity.class);
//        startActivity(intent);
//    }

    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }

}