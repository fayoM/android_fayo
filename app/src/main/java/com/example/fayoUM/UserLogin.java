package com.example.fayoUM;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity {
    FirebaseAuth auth;
    EditText etEmail;
    EditText etPassword;
    String TAG;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         auth=FirebaseAuth.getInstance();
        intializeUI();



    }

    public void intializeUI(){
        etEmail=findViewById(R.id.emai_id);
        etPassword=findViewById(R.id.pass_id);
        checkBox=findViewById(R.id.checkBox);
    }

    public void login(View view) {
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()) {

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(getApplicationContext(), "signInWithEmail:success.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();
//                            updateUI(user);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                 finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
//        updateUI(currentUser);
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void gotoRegistrationActivityPage(View view) {
        startActivity(new Intent(getApplicationContext(), UserReg.class));
        finish();
    }

    public void showPassword(View view) {
        if (checkBox.isChecked())
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        else
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}
