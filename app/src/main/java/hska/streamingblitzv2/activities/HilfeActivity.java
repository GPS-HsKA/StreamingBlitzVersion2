package hska.streamingblitzv2.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import hska.streamingblitzv2.R;

public class HilfeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilfe);

        // Anzeige einer zufälligen Werbung auf der Seite
        int[] werbungimages = new int[] {R.drawable.avengers_banner, R.drawable.expendables_banner, R.drawable.antman_banner};
        ImageView mImageView = (ImageView)findViewById(R.id.hilfe_werbung);
        int imageId = (int)(Math.random() * werbungimages.length);
        mImageView.setBackgroundResource(werbungimages[imageId]);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_hilfe, menu);

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
        Intent i = new Intent(this, EinstellungenActivity.class);
        startActivity(i);

    }
}