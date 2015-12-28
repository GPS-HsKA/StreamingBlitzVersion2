package hska.streamingblitzv2.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import hska.streamingblitzv2.activities.UpdateUserActivity;
import hska.streamingblitzv2.dao.ContactsDBHelper;
import hska.streamingblitzv2.model.User;

import static hska.streamingblitzv2.util.Constants.PARCEL_USER;


public class UpdateUserTask extends AsyncTask<User, Void, Integer> {

    private ProgressDialog dialog;
    private Context ctx;
    private User user;

    public UpdateUserTask(Context context) {
        ctx = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(ctx, "", "Please wait...", true);
    }

    @Override
    protected Integer doInBackground(User... params) {
        user = params[0];

        return ContactsDBHelper.getInstance(ctx).updateUser(user);
    }

    @Override
    protected void onPostExecute(Integer affectedRows) {
        dialog.dismiss();
        if (affectedRows != 1) {
            Toast.makeText(ctx, "User konnte in der Datenbank nicht aktualisiert werden!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "Userdaten wurden aktualisiert!", Toast.LENGTH_SHORT).show();
            Intent detailIntent = new Intent(ctx, UpdateUserActivity.class);
            detailIntent.putExtra(PARCEL_USER, user);
            ctx.startActivity(detailIntent);
        }
    }
}
