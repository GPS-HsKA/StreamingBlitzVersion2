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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.ContactsDBHelper;
import hska.streamingblitzv2.dao.DatabaseSchema.ContentEntry;
import hska.streamingblitzv2.util.ContentMapper;

public class ContentListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SQLITE_LOADER = 0;
    private static final String PARCEL_CONTENT = "hska.streamingblitzv2.model.Content";

    private SimpleCursorAdapter adapter;
    private ListView contentList;
    private SearchView searchField;
    private int backButtonCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suchergebnis);

        initAdapter();
        getLoaderManager().initLoader(SQLITE_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_suchergebnis, menu);

        searchField = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Cursor result = ContactsDBHelper.getInstance(ContentListActivity.this).findContentByName(query);
                adapter.changeCursor(result);
                if (result.getCount() == 0) {
                    Toast.makeText(ContentListActivity.this, "Es wurde kein Film oder Serie gefunden!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchField.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getLoaderManager().restartLoader(SQLITE_LOADER, null, ContentListActivity.this);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void initAdapter() {
        String[] fromColumns = {ContentEntry.COLUMN_NAME_NAME};
        int[] toViews = {android.R.id.text1};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null,fromColumns,toViews, 0);
        contentList = (ListView) findViewById(R.id.listView_contentList);
        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteCursor cursor = (SQLiteCursor) parent.getItemAtPosition(position);
                Intent detailIntent = new Intent(ContentListActivity.this, ContentDetailActivity.class);
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
                        return ContactsDBHelper.getInstance(ContentListActivity.this).findAllContent();
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

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            backButtonCount = 0;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press again to exit the application", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
