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

import java.io.IOException;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.model.Content;

import static hska.streamingblitzv2.util.Constants.PARCEL_CONTENT;

public class ContentDetailActivity extends AppCompatActivity {

    private Content content;
    ImageView moviePosterContainer;
    Bitmap moviePoster;
    String userString;
    Integer netflix;
    Integer amazon;
    Integer maxdome;
    Integer snap;
    ImageView netflixLogo;
    ImageView amazonLogo;
    ImageView maxdomeLogo;
    ImageView snapLogo;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        content = getIntent().getParcelableExtra(PARCEL_CONTENT);
        userString = getIntent().getStringExtra("EXTRA_USER");

        try {
            initHeaderContent();
            getMovieposter();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Anzeige einer zufälligen Werbung auf der Seite
        int[] werbungimages = new int[] {R.drawable.avengers_banner, R.drawable.expendables_banner, R.drawable.antman_banner};
        ImageView mImageView = (ImageView)findViewById(R.id.contentdetail_werbung);
        int imageId = (int)(Math.random() * werbungimages.length);
        mImageView.setBackgroundResource(werbungimages[imageId]);
    }

    public void getMovieposter(){
        moviePoster = BitmapFactory.decodeFile(content.getImage());
        moviePosterContainer = (ImageView) findViewById(R.id.contentdetail_movieimage);
        moviePosterContainer.setImageBitmap(moviePoster);
    }

    private void initHeaderContent() throws IOException {
        ((TextView) findViewById(R.id.contentdetail_title)).setText(content.getName());
        ((TextView) findViewById(R.id.contentdetail_genre)).setText(content.getGenre());
        ((TextView) findViewById(R.id.contentdetail_laufzeit)).setText(content.getLaufzeit());
        ((TextView) findViewById(R.id.contentdetail_imdbscore)).setText(content.getImdbScore());
        ((TextView) findViewById(R.id.contentdetail_jahr)).setText(content.getJahr());

        netflixLogo = (ImageView) findViewById(R.id.imageView_contentdetail_netflix);
        amazonLogo = (ImageView) findViewById(R.id.imageView_contentdetail_amazon);
        maxdomeLogo = (ImageView) findViewById(R.id.imageView_contentdetail_maxdome);
        snapLogo = (ImageView) findViewById(R.id.imageView_contentdetail_snap);

        netflix = content.getNetflix();
        if (netflix == 1){
            int netflixId = getResources().getIdentifier("hska.streamingblitzv2:drawable/netflix_icon", null, null);
            netflixLogo.setImageResource(netflixId);
        }
        amazon = content.getAmazon();
        if (amazon == 1){
            int amazonId = getResources().getIdentifier("hska.streamingblitzv2:drawable/amazon_logo_2", null, null);
            amazonLogo.setImageResource(amazonId);
        }
        maxdome = content.getMaxdome();
        if (maxdome == 1){
            int maxdomeId = getResources().getIdentifier("hska.streamingblitzv2:drawable/maxdome_icon", null, null);
            maxdomeLogo.setImageResource(maxdomeId);
        }
        snap = content.getSnap();
        if (snap == 1){
            int snapId = getResources().getIdentifier("hska.streamingblitzv2:drawable/snap_icon", null, null);
            snapLogo.setImageResource(snapId);
        }
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
        searchView.setIconifiedByDefault(false);

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
        Intent intentEinstellungen = new Intent(this, EinstellungenActivity.class);
        extras = new Bundle();
        extras.putString("EXTRA_USER", userString);
        intentEinstellungen.putExtras(extras);
        startActivity(intentEinstellungen);
    }
}
