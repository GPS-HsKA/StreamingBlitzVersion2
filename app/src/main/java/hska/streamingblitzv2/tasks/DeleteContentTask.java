package hska.streamingblitzv2.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import hska.streamingblitzv2.activities.RegisterActivity;
import hska.streamingblitzv2.dao.ContactsDBHelper;
import hska.streamingblitzv2.model.Content;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import hska.streamingblitzv2.activities.RegisterActivity;
import hska.streamingblitzv2.dao.ContactsDBHelper;
import hska.streamingblitzv2.model.Content;

public class DeleteContentTask extends AsyncTask<Content, Void, Integer> {

    private Context ctx;
    private ProgressDialog dialog;
    private Content content;

    public DeleteContentTask(Context context) {
        this.ctx = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(ctx, "", "Bitte warten ...", true);
    }

    @Override
    protected Integer doInBackground(Content... params) {
        content = params[0];
        return ContactsDBHelper.getInstance(ctx).deleteContent(content);
    }

    @Override
    protected void onPostExecute(Integer affectedRows) {
        dialog.dismiss();
        if (affectedRows != 1) {
            Toast.makeText(ctx, "User konnte nicht gelöscht werden!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "User wurde aus Datenbank geslöscht!", Toast.LENGTH_SHORT).show();
            Intent listIntent = new Intent(ctx, RegisterActivity.class);
            ctx.startActivity(listIntent);
        }
    }
}
