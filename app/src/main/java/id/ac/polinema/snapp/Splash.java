package id.ac.polinema.snapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import android.os.Handler;
//import android.view.View;
//import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {

    FirebaseAuth fAuth;

//    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        fAuth = FirebaseAuth.getInstance();
//        pbar = findViewById(R.id.progressBar2);


        // check if user has logged in
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            //   finish();
        } else {
            //create new anonymous account / dont need real data
            fAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Splash.this, "Logged in with temporary account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    pbar.setVisibility(View.GONE);
                    killActivity();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Splash.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                // check if user has logged in
//                if (fAuth.getCurrentUser() != null) {
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    //   finish();
//                } else {
//                    //create new anonymous account / dont need real data
//                    fAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//                            Toast.makeText(Splash.this, "Logged in with temporary account", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            pbar.setVisibility(View.GONE);
//                            killActivity();
//                            finish();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Splash.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    });
//                }
//            }
//        }, 1000);
    }

    private void killActivity() {
        finish();
    }
}
