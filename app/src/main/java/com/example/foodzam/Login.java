package com.example.foodzam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class Login extends AppCompatActivity {
    private EditText Email,Password;
    private Button login, Forgot,Back;
    private ProgressBar pb;
    private FirebaseAuth auth;
    static SharedPreferences sp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);setContentView(R.layout.activity_main5);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
        Email=findViewById(R.id.Email);
        Password=findViewById(R.id.Password);
        login=findViewById(R.id.Lg);
        Forgot=findViewById(R.id.Forgot);
        Back=findViewById(R.id.back);
        pb=findViewById(R.id.pbar);
        pb.setVisibility(View.INVISIBLE);

        auth=FirebaseAuth.getInstance();



        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pb.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                pb.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        Password.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(Login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    sp.edit().putBoolean("logged",true).apply();
                                    finish();
                                }
                            }
                        });

            }
        });


        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String E = Email.getText().toString().trim();

                if (TextUtils.isEmpty(E)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                pb.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(E)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Login.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                pb.setVisibility(View.GONE);
                            }
                        });
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this, Start.class);
                startActivity(i);
            }
        });


    }

}
