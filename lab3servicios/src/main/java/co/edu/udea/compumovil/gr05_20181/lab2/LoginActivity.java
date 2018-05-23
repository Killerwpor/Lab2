package co.edu.udea.compumovil.gr05_20181.lab2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    // UI references.
    private EditText mPasswordView, mEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mLogin = (Button) findViewById(R.id.login_ingresar);
        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(view);
            }
        });

        Button mRegistro = (Button) findViewById(R.id.login_registrar);
        mRegistro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(View view) {
        dbHelper db = new dbHelper(getApplicationContext());

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //correo ss contraseña: d
        Cursor c = db.getLawyerById(email,password);
        Boolean comprobacion=c.moveToNext();
        if (comprobacion) {
            intent.putExtra("Usuario", email);
            startActivity(intent);
        } else {
            Snackbar.make(view, "Correo o contraseña vacíos o incorrectos." + password, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

}

