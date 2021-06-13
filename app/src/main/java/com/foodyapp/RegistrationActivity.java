package com.foodyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity  implements  organizationsAlertDialogFragmentListener {
      Button organization_btn;
      EditText email;
      EditText pass;
    EditText confirm_pass;
    EditText name;
    Button reg_btn;
    EditText phone;
    private Context context;
    private FirebaseAuth mAuth;
    VolunteerAdapterOrg volAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyInfoManager.getInstance().openDataBase(this);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_registration);


        email=(EditText)findViewById(R.id.emailAddress);
        pass=(EditText)findViewById(R.id.password);
        confirm_pass=(EditText)findViewById(R.id.confirm_password);
        phone=(EditText)findViewById(R.id.phone);
        name = (EditText)findViewById(R.id.myName);
        reg_btn=(Button)findViewById(R.id.registration_btn);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(email.getText().toString(), pass.getText().toString(), phone.getText().toString(), name.getText().toString());

            }
        });
    }

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

    public boolean valid(){
        boolean valid = true;
        if (TextUtils.isEmpty(email.getText())){
            email.setError("Enter an email");
            email.requestFocus();
            valid = false;
        }else if(!isValidEmail(email.getText())){
            email.setError("Email address not valid");
            email.requestFocus();
            valid = false;
        }
        else if(MyInfoManager.getInstance().isVolunteerExist(email.getText().toString())){
            email.setError("Email address already exists for another volunteer");
            email.requestFocus();
            valid = false;
        }
        else if (TextUtils.isEmpty(pass.getText())){
            pass.setError("Enter a password");
            pass.requestFocus();
            valid = false;
        }else if (!TextUtils.equals(pass.getText(), confirm_pass.getText())){
            pass.setError("password not equal");
            pass.requestFocus();
            confirm_pass.setError("password not equal");
            confirm_pass.requestFocus();
            valid = false;
        }else if (TextUtils.isEmpty(name.getText())){
            name.setError("Enter your name");
            name.requestFocus();
            valid = false;
        }
        else if (TextUtils.isEmpty(phone.getText())){
            phone.setError("Enter your phone number");
            phone.requestFocus();
            valid = false;
        }
        return valid;

    }

    public void openVolunteerActivity(){
        Intent intent=new Intent(this, VolunteerMainActivity.class);
        startActivity(intent);
    }

    public static boolean isValidEmail(CharSequence mail) {
        return (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches());
    }

    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }


    public void onStop() {
        super.onStop();
    }

    protected void onStart() {
        super.onStart();
        //FirebaseApp.initializeApp(this);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void register(String email, String password, String phone, String name){
        if(!valid()){
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(!email.equals("latet@gmail.com")){
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Volunteers vol = new Volunteers(email,name,phone);
                                db.collection("Volunteers")
                                        .document(email)
                                        .set(vol)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                if(MyInfoManager.getInstance().newVolunteer(email, name, phone) > 0)
                                                    //openVolunteerActivity();
                                                    System.out.println("hello");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println(e);
                                    }
                                });
            }
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrationActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            MyInfoManager.getInstance().newVolunteer(email.getText().toString(), name.getText().toString(), phone.getText().toString());
            VolunteersListActivity v = new VolunteersListActivity();
            v.newVolunteerInList(new Volunteers(email.getText().toString(), name.getText().toString(), phone.getText().toString()));
            //Intent intent = new Intent(this, CitiesActivity.class);
            Intent intent = new Intent(this, VolunteerMainActivity.class);
            startActivity(intent);

        } else {

        }
    }

}