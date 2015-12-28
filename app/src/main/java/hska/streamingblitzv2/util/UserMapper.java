package hska.streamingblitzv2.util;

import android.database.Cursor;

import hska.streamingblitzv2.dao.DatabaseSchema.UserEntry;
import hska.streamingblitzv2.model.User;

public class UserMapper {

    public static User map(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex(UserEntry._ID)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_NAME_USERNAME)));
        user.setPasswort(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_NAME_PASSWORT)));
        user.setMail(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_NAME_MAIL)));
        user.setEinstellungen(EinstellungenMapper.map(cursor));

        return user;
    }

}
