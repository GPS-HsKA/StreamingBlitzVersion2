package hska.streamingblitzv2.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.dao.DatabaseSchema;
import hska.streamingblitzv2.model.Content;

import static hska.streamingblitzv2.util.Constants.PARCEL_CONTENT;

public class ContentDetailActivity extends AppCompatActivity {

    private Content content;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        content = getIntent().getParcelableExtra(PARCEL_CONTENT);
        initHeaderContent();

        // Anzeige einer zufälligen Werbung auf der Seite
        int[] werbungimages = new int[] {R.drawable.avengers_banner, R.drawable.expendables_banner, R.drawable.antman_banner};
        ImageView mImageView = (ImageView)findViewById(R.id.contentdetail_werbung);
        int imageId = (int)(Math.random() * werbungimages.length);
        mImageView.setBackgroundResource(werbungimages[imageId]);
    }

    private void initHeaderContent() {
        int[] images = new int[] {R.drawable.amazon_logo, R.drawable.itunes_logo, R.drawable.netflix_icon};
        ImageView mImageView = (ImageView)findViewById(R.id.contentdetail_movieimage);
        int imageId = (int)(Math.random() * images.length);
        mImageView.setBackgroundResource(images[imageId]);
        // BitmapFactory.decodeByteArray(content.getImage(), 0, content.getImage().length)
        ((TextView) findViewById(R.id.contentdetail_title)).setText(content.getName());
        ((TextView) findViewById(R.id.contentdetail_genre)).setText(content.getGenre());
        ((TextView) findViewById(R.id.contentdetail_laufzeit)).setText(content.getLaufzeit());
        ((TextView) findViewById(R.id.contentdetail_imdbscore)).setText(content.getImdbScore());
        ((TextView) findViewById(R.id.contentdetail_jahr)).setText(content.getJahr());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_content_details, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
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
        Intent i = new Intent(this, EinstellungenActivity.class);
        startActivity(i);

    }
}
