package hska.streamingblitzv2.activities;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

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
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suchergebnis);

        Intent intent = getIntent();

        query = intent.getStringExtra(SucheActivity.EXTRA_MESSAGE);

        initAdapter();

        getLoaderManager().initLoader(SQLITE_LOADER, null, this);

            // Anzeige einer zufälligen Werbung auf der Seite
            int[] werbungimages = new int[]{R.drawable.avengers_banner, R.drawable.expendables_banner, R.drawable.antman_banner};
            ImageView mImageView = (ImageView) findViewById(R.id.werbungimageview);
            int imageId = (int) (Math.random() * werbungimages.length);
            mImageView.setBackgroundResource(werbungimages[imageId]);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content_details, menu);
        return true;
    }

    private void initAdapter() {
        String[] fromColumns = {ContentEntry.COLUMN_NAME_NAME, ContentEntry.COLUMN_NAME_GENRE};
        int[] toViews = {android.R.id.text1, android.R.id.text2};
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
                        return ContactsDBHelper.getInstance(ContentListActivity.this).findContentByName(query);
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
}
