package hska.streamingblitzv2.util;

import android.database.Cursor;
import android.net.Uri;

import hska.streamingblitzv2.dao.DatabaseSchema.ContentEntry;
import hska.streamingblitzv2.model.Content;

public class ContentMapper {

    public static Content map(Cursor cursor) {

        Content content = new Content();
        content.setId(cursor.getLong(cursor.getColumnIndex(ContentEntry._ID)));
        content.setName(cursor.getString(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_NAME)));
        content.setGenre(cursor.getString(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_GENRE)));
        content.setImage(cursor.getBlob(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_BILD_PFAD)));
        content.setLaufzeit(cursor.getString(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_LAUFZEIT)));
        content.setFilm(cursor.getInt(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_FILM)));
        content.setSerie(cursor.getInt(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_SERIE)));
        content.setImdbScore(cursor.getString(cursor.getColumnIndex(ContentEntry.COLUMN_NAME_IMDBSCORE)));

        return content;
    }
}
