package id.ac.polinema.snapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import id.ac.polinema.snapp.MainActivity;
import id.ac.polinema.snapp.R;

public class Register extends AppCompatActivity {

    EditText regUname, regEmail, regPass, regPassConf;
    Button syncAcc;
    TextView txtLogin;
    ProgressBar prob;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register to SNApp");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        regUname = findViewById(R.id.userName);
        regEmail = findViewById(R.id.userEmail);
        regPass = findViewById(R.id.password);
        regPassConf = findViewById(R.id.passwordConfirm);

        syncAcc = findViewById(R.id.createAccount);
        txtLogin = findViewById(R.id.login);
        prob = findViewById(R.id.progressBar4);

        fAuth = FirebaseAuth.getInstance();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        syncAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = regUname.getText().toString();
                String email = regEmail.getText().toString();
                String pass = regPass.getText().toString();
                String passConf = regPassConf.getText().toString();

//                String emailPattern = "[a-zA-Z0-9._-]+@gmail+\\.+[a-z]+";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (username.isEmpty() || pass.isEmpty() || passConf.isEmpty()) {
                    Toast.makeText(Register.this, "All Field is Required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.isEmpty()) {
                    regEmail.setError("Your Email still empty");
                } else {
                    if (!email.trim().matches(emailPattern)) {
                        regEmail.setError("Invalid email address");
                    }
                }

                if (pass.length() < 6) {
                    regPass.setError("Shout be at least 6 characters");
                }

                if (!pass.equals(passConf)) {
                    regPassConf.setError("Password does not match");
                    return;
                }

                prob.setVisibility(View.VISIBLE);

                AuthCredential credential = EmailAuthProvider.getCredential(email, pass);
                fAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        prob.setVisibility(View.GONE);
                        Toast.makeText(Register.this, "Note are synced", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        FirebaseUser usr = fAuth.getCurrentUser();
                        UserProfileChangeRequest req = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username).build();

                        usr.updateProfile(req);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        prob.setVisibility(View.GONE);
                        Toast.makeText(Register.this, "FAILED to connect, please Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}
