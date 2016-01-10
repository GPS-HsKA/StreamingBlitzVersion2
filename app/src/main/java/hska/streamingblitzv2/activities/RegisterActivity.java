package hska.streamingblitzv2.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.DBHelper;
import hska.streamingblitzv2.model.Einstellungen;
import hska.streamingblitzv2.model.User;
import hska.streamingblitzv2.tasks.InsertUserTask;

public class RegisterActivity extends AppCompatActivity {

    DBHelper dbAdapter;
    EditText txtUserName;
    EditText txtPassword;
    Button btnRegister;

    private User user = new User();
    public final static String EXTRA_MESSAGE = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            btnRegister = (Button) findViewById(R.id.button_register_anlegen);
            txtUserName = (EditText) findViewById(R.id.edit_register_username);
            txtPassword = (EditText) findViewById(R.id.edit_register_password);

            dbAdapter = new DBHelper(this);
            try {
                dbAdapter.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_register, menu);
            return super.onCreateOptionsMenu(menu);
        }

        public void sendUser(View view){

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
                try {
                    String username = txtUserName.getText().toString();
                    String password = txtPassword.getText().toString();
                    long i = dbAdapter.registerUser(username, password);
                    if (i != -1){
                        Toast.makeText(RegisterActivity.this, "User angelegt!",
                                Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "Etwas lief schief!",
                            Toast.LENGTH_LONG).show();
                }

            Intent intent = new Intent(this, RegisterEinstellungenActivity.class);
            EditText editText = (EditText) findViewById(R.id.edit_register_username);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }

   /*     private String getStringValue(int id) {
            View field = findViewById(id);
            if (field instanceof EditText) {
                EditText textField = (EditText) field;
                return textField.getText().toString();
            }
            return "";
        }*/

        public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_backLogin:
                showLogin();
                break;

            default:
                break;
        }
        return false;
        }

        protected void showLogin()
        {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }

}
