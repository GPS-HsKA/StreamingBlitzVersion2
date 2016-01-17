package hska.streamingblitzv2.dao;

import android.provider.BaseColumns;

public final class DatabaseSchema {

    private DatabaseSchema() {

    }

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "user_username";
        public static final String COLUMN_NAME_PASSWORT = "user_passwort";
        public static final String COLUMN_EINSTELLUNGEN_FK = "user_einstellungen_fk";
    }

    public static abstract class ContentEntry implements BaseColumns {
        public static final String TABLE_NAME = "content";
        public static final String COLUMN_NAME_NAME = "content_name";
        public static final String COLUMN_NAME_GENRE = "content_genre";
        public static final String COLUMN_NAME_LAUFZEIT = "content_laufzeit";
        public static final String COLUMN_NAME_NETFLIX = "content_netflix";
        public static final String COLUMN_NAME_AMAZON = "content_amazon";
        public static final String COLUMN_NAME_MAXDOME = "content_maxdome";
        public static final String COLUMN_NAME_SNAP = "content_snap";
        public static final String COLUMN_NAME_IMDBSCORE = "content_imdbscore";
        public static final String COLUMN_NAME_JAHR = "content_jahr";
        public static final String COLUMN_NAME_BILD_PFAD = "content_bild";
    }

    public static abstract class UserContentEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_content";
        public static final String COLUMN_USER_ID_FK = "user_id_fk";
        public static final String COLUMN_CONTENT_ID_FK = "content_id_fk";
    }

    public static abstract class EinstellungenEntry implements BaseColumns {
        public static final String TABLE_NAME = "einstellungen";
        public static final String COLUMN_NAME_NETFLIX = "einstellungen_netflix";
        public static final String COLUMN_NAME_AMAZONPRIME = "einstellungen_amazonprime";
        public static final String COLUMN_NAME_MAXDOME = "einstellungen_maxdome";
        public static final String COLUMN_NAME_SNAP = "einstellungen_snap";
        public static final String COLUMN_USER_FK = "user_fk";
    }
}
