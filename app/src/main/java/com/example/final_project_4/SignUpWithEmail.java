package com.example.final_project_4;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpWithEmail extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "userRegister";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText edEmail;
    private EditText edPass;
    private EditText phoneNum;
    private Button btnDaftar;
    private String email, password, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        edEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        edPass = (EditText) findViewById(R.id.editTextTextPassword2);
        phoneNum = (EditText) findViewById(R.id.editTextPhone);
        btnDaftar = (Button) findViewById(R.id.signup_manual_btn);

        btnDaftar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup_manual_btn) {
            signUp();
        }
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        email = edEmail.getText().toString();
        password = edPass.getText().toString();
        phone = phoneNum.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpWithEmail.this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                   // addUser(email, password, phone);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Akun gagal dibuat", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(edEmail.getText().toString())) {
            edEmail.setError("Required");
            result = false;
        } else {
            edEmail.setError(null);

        }
        if (TextUtils.isEmpty(edPass.getText().toString())) {
            edPass.setError("Required");
            result = false;
        } else {
            edPass.setError(null);
        }
        if (edPass.getText().length() < 6) {
            edPass.setError("try again!, use 6 character or number");
            result = false;
        } else {
            edPass.setError(null);
        }
        if (TextUtils.isEmpty(phoneNum.getText().toString())) {
            phoneNum.setError("Required");
            result = false;
        } else {
            phoneNum.setError(null);
        }

        return result;
    }
}
