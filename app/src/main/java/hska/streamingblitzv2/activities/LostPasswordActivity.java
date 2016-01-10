package hska.streamingblitzv2.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.DBHelper;

public class LostPasswordActivity extends AppCompatActivity {

    DBHelper dbAdapter;
    EditText txtUserName;
    Button btnSenden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostpassword);

        txtUserName = (EditText) findViewById(R.id.edit_lostpassword_email);
        btnSenden = (Button) findViewById(R.id.button_lostpassword_senden);

        dbAdapter = new DBHelper(this);
        try {
            dbAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnSenden.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
                String username = txtUserName.getText().toString();
                if (username.length() > 0) {
                    try {

                        if (dbAdapter.checkEMail(username)) {
                            Toast.makeText(LostPasswordActivity.this,
                                    "Wir haben dir ein Reset-Passwort gesendet!",
                                    Toast.LENGTH_LONG).show();
                            backLogin();
                        } else {
                            Toast.makeText(LostPasswordActivity.this,
                                    "User nicht bekannt!",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(LostPasswordActivity.this, "Etwas lief schief!",
                                Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }

    public void backLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}