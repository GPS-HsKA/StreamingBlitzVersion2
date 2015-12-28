package hska.streamingblitzv2.util;

import android.database.Cursor;
import android.net.Uri;

import hska.streamingblitzv2.dao.DatabaseSchema.ContentEntry;
import hska.streamingblitzv2.model.Content;

public class ContentMapper {

    public static Content map(Cursor cursor) {

        Content contact = new Content();
        contact.setId(cursor.getLong(cursor.getColumnIndex(ContentEntry._ID)));
        contact.setName(cursor.getString(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_NAME)));
        contact.setGenre(cursor.getString(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_GENRE)));
        contact.setImage(Uri.parse(cursor.getString(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_BILD_PFAD))));
        contact.setLaufzeit(cursor.getString(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_LAUFZEIT)));
        contact.setFilm(cursor.getInt(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_FILM)));
        contact.setSerie(cursor.getInt(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_SERIE)));
        contact.setImdbScore(cursor.getDouble(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_IMDBSCORE)));

        return contact;
    }
}
