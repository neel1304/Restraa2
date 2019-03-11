package in.othelo.restraa2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    EditText em,pw;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        em=(EditText)findViewById(R.id.email);
        pw=(EditText)findViewById(R.id.pwd);
        login=(Button)findViewById(R.id.log);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String emails,pwds;
                emails = em.getText().toString();
                pwds = pw.getText().toString();

                signIn(emails,pwds);

                Intent intent=new Intent(LoginActivity.this,ProfileActivity.class);
            }
        });

    }

    private void signIn(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
        // [END sign_in_with_email]
    }
    private void updateUI(FirebaseUser user) {

        user = FirebaseAuth.getInstance().getCurrentUser();

        String userEmail = user.getEmail();
        Toast.makeText(LoginActivity.this,userEmail,Toast.LENGTH_SHORT).show();
    }

}
