package com.example.fayoUM;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fayoUM.util.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserReg extends AppCompatActivity {

    private FirebaseAuth mAuth;
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db;
    EditText etEmail;
    EditText etPassword;
    EditText etName;
    EditText etPhone;
    RadioGroup genderRadioGroup;
    CheckBox checkbox;
    String TAG="log in status";
    //radioButton

    Button btnCreateAcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //initialize UI
        intializeUI();

    }

    public void intializeUI(){
        etEmail=findViewById(R.id.emailInput);
        etName=findViewById(R.id.fullNameInput);
        etPassword=findViewById(R.id.passwordInput);
        etPhone=findViewById(R.id.mobileInput);
        genderRadioGroup=findViewById(R.id.sexRadioGroup);
        checkbox=findViewById(R.id.checkBox4);
    }

    public void createAccount(View view) {
        final String email=etEmail.getText().toString();
        final String name=etName.getText().toString();
        final String password=etPassword.getText().toString();
        final String phone=etPhone.getText().toString();
            String gender1=" ";
        int selectedRadioButtonId=genderRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId==R.id.fRadioButton)
            gender1="Female";
        if (selectedRadioButtonId==R.id.mRadioButton)
            gender1="Male";
        final String gender=gender1;

        if (!email.isEmpty() && !name.isEmpty() && !password.isEmpty() && !phone.isEmpty() ){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
                                addDataToFirestore(name, email, phone, gender);
                                Toast.makeText(getApplicationContext(), "Authentication Successful.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else{
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
    }

    public void addDataToFirestore(String fullName, String email, String phone, String gender){
        // Create a new user with a first and last name
        User user= new User(fullName, email, phone, gender);

// Add a new document with a generated ID
        db.collection("users")
                .document(email)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {

                   }
               })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public void gotoLoginPage(View view) {
        startActivity(new Intent(getApplicationContext(), UserLogin.class));
        finish();
    }

    public void showPassword(View view) {
        if (checkbox.isChecked())
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        else
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}
