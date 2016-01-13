package hska.streamingblitzv2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hska.streamingblitzv2.R;


public class SucheActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "";
    String userString;
    String TAG = "User: ";
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suche);

        Intent intent = getIntent();
        userString = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);

        Log.d(TAG, "-" + userString);

        Button suchergebnis_blitzscan = (Button) findViewById(R.id.button_suche_blitzscan);
        suchergebnis_blitzscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SucheActivity.this, ScanActivity.class));
            }
        });
    }

    public void sendMessage(View view){
        Intent intent1 = new Intent(this, ContentListActivity.class);
        extras = new Bundle();
        EditText editText = (EditText) findViewById(R.id.editText_suche);
        String searchString = editText.getText().toString();
        extras.putString("EXTRA_SEARCH", searchString);
        extras.putString("EXTRA_USER", userString);
        intent1.putExtras(extras);
        startActivity(intent1);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_suche, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_history:
                showHistory();
                break;
            case R.id.menu_datenbank:
                showDatenbank();
                break;
            case R.id.menu_scanner:
                showScanner();
                break;
            case R.id.menu_hilfe:
                showHilfe();
                break;
            case R.id.menu_einstellungen:
                showEinstellungen();
                break;

            default:
                break;
        }
        return false;
    }

    protected void showDatenbank()
    {
        Intent i = new Intent(this, DatenbankActivity.class);
        startActivity(i);
    }

    protected void showHistory()
    {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    protected void showScanner()
    {
        Intent i = new Intent(this, ScanActivity.class);
        startActivity(i);
    }

    protected void showHilfe()
    {
        Intent i = new Intent(this, HilfeActivity.class);
        startActivity(i);
    }

    protected void showEinstellungen()
    {
        Intent intentEinstellungen = new Intent(this, EinstellungenActivity.class);
        extras = new Bundle();
        extras.putString("EXTRA_USER", userString);
        intentEinstellungen.putExtras(extras);
        startActivity(intentEinstellungen);
    }
}
