package com.example.foodzam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodzam.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Start extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register,about;
    private Button login;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.Register);
        login = findViewById(R.id.Login);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        progressBar = (ProgressBar) findViewById(R.id.pbar);


       /* btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.sp.edit().putBoolean("logged",false).apply();
                Intent l=new Intent(Start.this,Login.class);
                startActivity(l);
                finish();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String E = email.getText().toString().trim();
                String P = password.getText().toString().trim();

                if (TextUtils.isEmpty(E)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(P)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (P.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(E, P)
                        .addOnCompleteListener(Start.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(MainActivity.this, "Registered successfully " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                        if (!task.isSuccessful()) {

                                            Toast.makeText(Start.this,  task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();


                                        } else {
                                            Intent i = new Intent(Start.this, MainActivity.class);
                                            startActivity(i);
                                            //Toast.makeText(MainActivity.this, "Authentication failed.",
                                            //Toast.LENGTH_SHORT).show();
                                           //FirebaseUser user = auth.getCurrentUser();
                                            finish();
                                        }

                            }
                        });

            }
        });

    }

        @Override
        protected void onResume () {
            super.onResume();
            progressBar.setVisibility(View.GONE);
        }


}




    /*public void Next(View view)
    {
        /*writeNewUser(mTitleField.getText().toString(),mBodyField.getText().toString());

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }


    public void Us(View view)
    {
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
    }*/





