package hska.streamingblitzv2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.ContactsDBHelper;


public class LoginActivity extends AppCompatActivity {

    ContactsDBHelper dbAdapter;
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;
    Button btnPwVrg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUserName = (EditText) findViewById(R.id.text_login_email);
        txtPassword = (EditText) findViewById(R.id.text_login_password);
        btnLogin = (Button) findViewById(R.id.button_login_signin);
        btnRegister = (Button) findViewById(R.id.button_login_registrieren);
        btnPwVrg = (Button) findViewById(R.id.button_login_pwvrg);

        dbAdapter = new ContactsDBHelper(this);
        try {
            dbAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                if (username.length() > 0 && password.length() > 0) {
                    try {

                        if (dbAdapter.loginUser(username, password)) {
                            login();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Falscher User oder Passwort!",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Etwas lief schief!",
                                Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Bitte User oder Passwort angeben!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                register();
            }
        });


    }

    public void register() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void login() {
        Intent i = new Intent(this, SucheActivity.class);
        startActivity(i);
        }
}

