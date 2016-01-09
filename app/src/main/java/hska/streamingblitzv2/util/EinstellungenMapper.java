package hska.streamingblitzv2.util;


import android.database.Cursor;

import hska.streamingblitzv2.dao.DatabaseSchema;
import hska.streamingblitzv2.dao.DatabaseSchema.EinstellungenEntry;
import hska.streamingblitzv2.model.Einstellungen;

public class EinstellungenMapper {

    public static Einstellungen map(Cursor cursor) {
        Einstellungen einstellungen = new Einstellungen();
        einstellungen.setId(cursor.getLong(cursor.getColumnIndex(EinstellungenEntry._ID)));
        einstellungen.setNetflix(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(EinstellungenEntry.COLUMN_NAME_NETFLIX))));
        einstellungen.setAmazonprime(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(EinstellungenEntry.COLUMN_NAME_AMAZONPRIME))));
        einstellungen.setMaxdome(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(EinstellungenEntry.COLUMN_NAME_MAXDOME))));
        einstellungen.setSnap(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(EinstellungenEntry.COLUMN_NAME_SNAP))));
        einstellungen.setUser(cursor.getString(cursor.getColumnIndex(DatabaseSchema.UserEntry._ID)));

        return einstellungen;
    }
}
