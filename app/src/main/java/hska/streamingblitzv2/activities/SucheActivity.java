package hska.streamingblitzv2.activities;


import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.ContactsDBHelper;
import hska.streamingblitzv2.dao.DatabaseSchema;
// import hska.streamingblitzv2.tasks.PopUpMenuEventHandler;

public class SucheActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suche);


        Button suchergebnis_blitzscan = (Button) findViewById(R.id.button_suche_blitzscan);
        suchergebnis_blitzscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SucheActivity.this, ScanActivity.class));
            }
        });
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, ContentListActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText_suche);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_content_details, menu);
        return true;
    }
}
