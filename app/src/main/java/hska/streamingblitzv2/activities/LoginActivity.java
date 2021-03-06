package hska.streamingblitzv2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.DBHelper;


public class LoginActivity extends AppCompatActivity {

    DBHelper dbAdapter;
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;
    Button btnPwd;
    String TAG = "!LOGGING!";
    String EXTRA_MESSAGE = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUserName = (EditText) findViewById(R.id.text_login_email);
        txtPassword = (EditText) findViewById(R.id.text_login_password);
        btnLogin = (Button) findViewById(R.id.button_login_signin);
        btnRegister = (Button) findViewById(R.id.button_login_registrieren);
        btnPwd = (Button) findViewById(R.id.button_login_pwvrg);

        dbAdapter = new DBHelper(this);
        try {
            dbAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        Login mit Überprüfung von Username und Passwort, sowie vorhandenem @-Zeichen
         */

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
                        if (!isEmailValid(username)){
                        Toast.makeText(LoginActivity.this,
                                "Bitte eine E-Mail Adresse angeben!",
                                Toast.LENGTH_LONG).show();
                        }
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
                        Log.d(TAG, "Login FEHLER!" + e);

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

        btnPwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                pwdVergessen();
            }
        });

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public void register() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    /*
    Übergabe des Usernames auf die nächste Activity
     */

    public void login() {
        Intent intent = new Intent(this, SucheActivity.class);
        EditText editText = (EditText) findViewById(R.id.text_login_email);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        }

    public void pwdVergessen() {
        Intent i = new Intent(this, LostPasswordActivity.class);
        startActivity(i);
    }
}

