package hska.streamingblitzv2.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import hska.streamingblitzv2.activities.RegisterActivity;
import hska.streamingblitzv2.activities.UpdateUserActivity;
import hska.streamingblitzv2.dao.DBHelper;
import hska.streamingblitzv2.model.User;

import static hska.streamingblitzv2.util.Constants.*;

public class InsertUserTask extends AsyncTask<User, Void, User> {

    private ProgressDialog dialog;
    private Context ctx;
    private User user;

    public InsertUserTask(Context context) {
        this.ctx = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(ctx, "", "Bitte warten ...", true);
    }

    @Override
    protected User doInBackground(User... params) {
        user = params[0];

        return DBHelper.getInstance(ctx).insertUser(user);
    }

    @Override
    protected void onPostExecute(User insertedUser) {
        dialog.dismiss();
        if (insertedUser == null) {
            Toast.makeText(ctx, "User konnte nicht angelget werden!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "Neuer User wurde zur Datenbank hinzugefügt!", Toast.LENGTH_SHORT).show();
            Intent detailIntent = new Intent(ctx, UpdateUserActivity.class);
            detailIntent.putExtra(PARCEL_USER, insertedUser);
            ctx.startActivity(detailIntent);
        }
    }
}
