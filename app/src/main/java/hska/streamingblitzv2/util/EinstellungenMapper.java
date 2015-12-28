package hska.streamingblitzv2.util;


import android.database.Cursor;

import hska.streamingblitzv2.dao.DatabaseSchema.EinstellungenEntry;
import hska.streamingblitzv2.model.Einstellungen;

public class EinstellungenMapper {

    public static Einstellungen map(Cursor cursor) {
        Einstellungen einstellungen = new Einstellungen();
        einstellungen.setId(cursor.getLong(cursor.getColumnIndex(EinstellungenEntry._ID)));
        einstellungen.setNetflix(cursor.getInt(cursor.getColumnIndex(EinstellungenEntry.COLUMN_NAME_NETFLIX)));
        einstellungen.setAmazonprime(cursor.getInt(cursor.getColumnIndex(EinstellungenEntry.COLUMN_NAME_AMAZONPRIME)));
        einstellungen.setMaxdome(cursor.getInt(cursor.getColumnIndex(EinstellungenEntry.COLUMN_NAME_MAXDOME)));
        einstellungen.setSnap(cursor.getInt(cursor.getColumnIndex(EinstellungenEntry.COLUMN_NAME_SNAP)));
        einstellungen.setUser(UserMapper.map(cursor));

        return einstellungen;
    }
}
