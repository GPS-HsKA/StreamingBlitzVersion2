package hska.streamingblitzv2.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import hska.streamingblitzv2.R;
import hska.streamingblitzv2.model.Content;
import hska.streamingblitzv2.tasks.DeleteContentTask;

import static hska.streamingblitzv2.util.Constants.DIALOG_DELETE_MESSAGE;
import static hska.streamingblitzv2.util.Constants.DIALOG_DELETE_NEGATIVE;
import static hska.streamingblitzv2.util.Constants.DIALOG_DELETE_POSITIVE;
import static hska.streamingblitzv2.util.Constants.DIALOG_DELETE_TITLE;
import static hska.streamingblitzv2.util.Constants.PARCEL_CONTENT;

public class ContentDetailActivity extends AppCompatActivity {

    private Content content;
    private ImageView imageView;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        content = getIntent().getParcelableExtra(PARCEL_CONTENT);
        initHeaderContent();
    }

    private void initHeaderContent() {
        ((TextView) findViewById(R.id.contactFirstname)).setText(content.getName());
        ((TextView) findViewById(R.id.contactLastname)).setText(content.getGenre());
    }

    private void deleteContent() {
        DeleteContentTask deleteTask = new DeleteContentTask(this);
        deleteTask.execute(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                Dialog confirmDialog = createConfirmDialog();
                confirmDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent listIntent = new Intent(this, ContentListActivity.class);
        /*
        if(getCallingActivity().getClassName().equals("ContactListActivity")) {
            listIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        */
        startActivity(listIntent);
    }


    public Fragment getCurrentFragment() {
        Fragment result = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + pager.getCurrentItem());
        return result;
    }

    private Dialog createConfirmDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(DIALOG_DELETE_TITLE)
                .setMessage(DIALOG_DELETE_MESSAGE)
                .setPositiveButton(DIALOG_DELETE_POSITIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteContent();
                    }
                })
                .setNegativeButton(DIALOG_DELETE_NEGATIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
