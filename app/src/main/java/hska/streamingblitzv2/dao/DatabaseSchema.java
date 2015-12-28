package hska.streamingblitzv2.dao;

import android.provider.BaseColumns;

public final class DatabaseSchema {

    private DatabaseSchema() {

    }

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "user_username";
        public static final String COLUMN_NAME_PASSWORT = "user_passwort";
        public static final String COLUMN_NAME_MAIL = "user_mail";
        public static final String COLUMN_EINSTELLUNGEN_FK = "user_einstellungen_fk";
    }

    public static abstract class ContentEntry implements BaseColumns {
        public static final String TABLE_NAME = "content";
        public static final String COLUMN_NAME_NAME = "content_name";
        public static final String COLUMN_NAME_GENRE = "content_genre";
        public static final String COLUMN_NAME_LAUFZEIT = "content_laufzeit";
        public static final String COLUMN_NAME_SERIE = "content_serie";
        public static final String COLUMN_NAME_FILM = "content_film";
        public static final String COLUMN_NAME_IMDBSCORE = "content_imdbscore";
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

    public static abstract class StreaminganbieterContentEntry implements BaseColumns {
        public static final String TABLE_NAME = "streaminganbieter_content";
        public static final String COLUMN_CONTENT_FK = "content_fk";
        public static final String COLUMN_STREAMINGANBIETER_FK = "streaminganbieter_fk";
    }

    public static abstract class WerbeagenturEntry implements BaseColumns {
        public static final String TABLE_NAME = "werbeagentur";
        public static final String COLUMN_NAME_NAME = "werbeagentur_name";
        public static final String COLUMN_NAME_MAILADRESSE = "werbeagentur_mailadresse";
        public static final String COLUMN_NAME_BACKENDUSER = "werbeagentur_backenduser";
        public static final String COLUMN_NAME_BACKENDPASSWORT = "werbeagentur_backendpasswort";
    }

    public static abstract class WerbeagenturWerbungEntry implements BaseColumns {
        public static final String TABLE_NAME = "werbeagentur_werbung";
        public static final String COLUMN_WERBEAGENTUR_FK = "werbeagentur_id";
        public static final String COLUMN_WERBUNG_FK = "werbung_id";
    }

    public static abstract class WerbungEntry implements BaseColumns {
        public static final String TABLE_NAME = "werbung";
        public static final String COLUMN_NAME_LINK = "werbung_link";
        public static final String COLUMN_NAME_AKTIV = "werbung_aktiv";
        public static final String COLUMN_WERBEAGENTUR_FK = "werbeagentur_fk";
    }

    public static abstract class StreaminganbieterEntry implements BaseColumns {
        public static final String TABLE_NAME = "streaminganbieter";
        public static final String COLUMN_NAME_LINK = "streaminganbieter_link";
        public static final String COLUMN_NAME_MAILADRESSE = "streaminganbieter_mailadresse";
        public static final String COLUMN_NAME_BACKENDUSER = "streaminganbieter_backenduser";
        public static final String COLUMN_NAME_BACKENDPASSWORT = "streaminganbieter_backendpasswort";
    }
}
