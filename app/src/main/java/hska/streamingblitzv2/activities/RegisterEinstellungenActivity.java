package hska.streamingblitzv2.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.DBHelper;
import hska.streamingblitzv2.model.Einstellungen;

public class RegisterEinstellungenActivity extends AppCompatActivity {

    String userString;
    DBHelper dbAdapter;
    CheckBox checkBoxNetflix;
    CheckBox checkBoxAmazon;
    CheckBox checkBoxMaxdome;
    CheckBox checkBoxSnap;

    public final static String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_einstellungen);

        Intent intent = getIntent();
        userString = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);

        dbAdapter = new DBHelper(this);
        try {
            dbAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.einstellungen_fueruser)).setText(userString);

        checkBoxNetflix = (CheckBox) findViewById(R.id.checkBox_netflix);
        checkBoxAmazon = (CheckBox) findViewById(R.id.checkBox_amazon);
        checkBoxMaxdome = (CheckBox) findViewById(R.id.checkBox_maxdome);
        checkBoxSnap = (CheckBox) findViewById(R.id.checkBox_snap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void saveSettings(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(checkBoxNetflix.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(checkBoxAmazon.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(checkBoxMaxdome.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(checkBoxSnap.getWindowToken(), 0);
        Einstellungen einstellungen = new Einstellungen(checkBoxNetflix.isChecked(), checkBoxAmazon.isChecked(), checkBoxMaxdome.isChecked(), checkBoxSnap.isChecked(), userString);
        try {
            Einstellungen i = dbAdapter.insertEinstellungen(einstellungen);
            if (i == einstellungen)
                Toast.makeText(RegisterEinstellungenActivity.this, "Einstellung wurde angelegt", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(RegisterEinstellungenActivity.this, "Etwas lief schief!",
                    Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this, SucheActivity.class);
        String message = userString;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

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
