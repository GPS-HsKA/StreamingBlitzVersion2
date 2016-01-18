package hska.streamingblitzv2.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.DBHelper;
import hska.streamingblitzv2.dao.DatabaseSchema.ContentEntry;
import hska.streamingblitzv2.util.ContentMapper;

public class DatenbankActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SQLITE_LOADER = 0;
    private static final String PARCEL_CONTENT = "hska.streamingblitzv2.model.Content";

    private SimpleCursorAdapter adapter;
    private ListView contentList;
    Bundle extras;
    String userString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datenbank);

        Intent intent = getIntent();
        userString = intent.getStringExtra(SucheActivity.EXTRA_MESSAGE);

        initAdapter();

        getLoaderManager().initLoader(SQLITE_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }

    /*
    Alle Daten welche im Moment in der Datenbank liegen werden geladen und angezeigt
     */

    private void initAdapter() {
        String[] fromColumns = {ContentEntry.COLUMN_NAME_NAME, ContentEntry.COLUMN_NAME_JAHR};
        int[] toViews = {android.R.id.text1, android.R.id.text2};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_2, null,fromColumns,toViews, 0);
        contentList = (ListView) findViewById(R.id.listView_datenbank);

        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteCursor cursor = (SQLiteCursor) parent.getItemAtPosition(position);
                Intent detailIntent = new Intent(DatenbankActivity.this, ContentDetailActivity.class);
                detailIntent.putExtra(PARCEL_CONTENT, ContentMapper.map(cursor));
                startActivity(detailIntent);
            }
        });

        contentList.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
        switch (loaderID) {
            case SQLITE_LOADER:
                final String sortOrder = ContentEntry.COLUMN_NAME_NAME + " ASC";
                return new CursorLoader(this, null, null, null, null, sortOrder) {

                    @Override
                    public Cursor loadInBackground() {
                        return DBHelper.getInstance(DatenbankActivity.this).findAllContent();
                    }
                };
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }

    public void switchFromDatabaseToSuche(View view){
        startActivity(new Intent(DatenbankActivity.this, SucheActivity.class));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_history:
                showHistory();
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

