package hska.streamingblitzv2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import hska.streamingblitzv2.dao.DatabaseSchema.UserEntry;
import hska.streamingblitzv2.dao.DatabaseSchema.ContentEntry;
import hska.streamingblitzv2.dao.DatabaseSchema.EinstellungenEntry;
import hska.streamingblitzv2.model.User;
import hska.streamingblitzv2.model.Content;
import hska.streamingblitzv2.model.Einstellungen;

public class ContactsDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "streamingblitzv2.db";
    private static final String SQL_CREATE_TABLE_USER =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    UserEntry.COLUMN_NAME_PASSWORT + " TEXT," +
                    UserEntry.COLUMN_NAME_MAIL + " TEXT," +
                    UserEntry.COLUMN_EINSTELLUNGEN_FK + " INTEGER," +
                    "FOREIGN KEY(" + UserEntry.COLUMN_EINSTELLUNGEN_FK + ") REFERENCES " + EinstellungenEntry.TABLE_NAME + "(" + EinstellungenEntry._ID + ")" +
                    ")";

    private static final String SQL_CREATE_TABLE_CONTENT =
            "CREATE TABLE " + ContentEntry.TABLE_NAME + " (" +
                    ContentEntry._ID + " INTEGER PRIMARY KEY," +
                    ContentEntry.COLUMN_NAME_NAME + " TEXT," +
                    ContentEntry.COLUMN_NAME_GENRE + " TEXT," +
                    ContentEntry.COLUMN_NAME_LAUFZEIT + " TEXT," +
                    ContentEntry.COLUMN_NAME_SERIE + " INTEGER," +
                    ContentEntry.COLUMN_NAME_FILM + " INTEGER," +
                    ContentEntry.COLUMN_NAME_IMDBSCORE + " TEXT," +
                    ContentEntry.COLUMN_NAME_BILD_PFAD + " TEXT" + ")";

    private static final String SQL_CREATE_TABLE_EINSTELLUNGEN =
            "CREATE TABLE " + EinstellungenEntry.TABLE_NAME + " (" +
                    EinstellungenEntry._ID + " INTEGER PRIMARY KEY," +
                    EinstellungenEntry.COLUMN_NAME_NETFLIX + " INTEGER," +
                    EinstellungenEntry.COLUMN_NAME_AMAZONPRIME + " INTEGER," +
                    EinstellungenEntry.COLUMN_NAME_MAXDOME + " INTEGER," +
                    EinstellungenEntry.COLUMN_NAME_SNAP + " INTEGER," +
                    EinstellungenEntry.COLUMN_USER_FK + " INTEGER," +
                    "FOREIGN KEY(" + EinstellungenEntry.COLUMN_USER_FK + ") REFERENCES " + UserEntry.TABLE_NAME + "(" + UserEntry._ID + ")" +
                    ")";
    private static final String SQL_DROP_TABLE_CONTENT = "DROP TABLE IF EXISTS " + ContentEntry.TABLE_NAME + ";";
    private static final String SQL_DROP_TABLE_USER = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME + ";";
    private static final String SQL_DROP_TABLE_EINSTELLUNGEN = "DROP TABLE IF EXISTS " + EinstellungenEntry.TABLE_NAME + ";";

    private static ContactsDBHelper instance = null;
    private Context ctx;

    private ContactsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    public static ContactsDBHelper getInstance(Context ctx) {
        if (instance == null) {
            return new ContactsDBHelper(ctx.getApplicationContext());
        }
        return instance;
    }

    public Cursor findAllUser() {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String sortOrder = UserEntry.COLUMN_NAME_USERNAME + " ASC";
        qb.setTables(UserEntry.TABLE_NAME + " JOIN " + EinstellungenEntry.TABLE_NAME + " ON " + UserEntry.TABLE_NAME + "." + UserEntry.COLUMN_EINSTELLUNGEN_FK + "=" + EinstellungenEntry.TABLE_NAME + "." + EinstellungenEntry._ID);
        return qb.query(getReadableDatabase(), null, null, null, null, null, sortOrder);
    }

    public Cursor findAllContent() {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String sortOrder = ContentEntry.COLUMN_NAME_NAME + " ASC";
        qb.setTables(ContentEntry.TABLE_NAME);
        return qb.query(getReadableDatabase(), null, null, null, null, null, sortOrder);
    }

    public Cursor findUserByName(String name) {
        String whereClause = UserEntry.COLUMN_NAME_USERNAME + " LIKE ? OR " + UserEntry.COLUMN_NAME_MAIL + " LIKE ?";
        String[] whereArgs = new String[]{name + "%", name + "%"};
        String sortOrder = UserEntry.COLUMN_NAME_USERNAME + " ASC";
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(UserEntry.TABLE_NAME + " JOIN " + EinstellungenEntry.TABLE_NAME + " ON " + UserEntry.TABLE_NAME + "." + UserEntry.COLUMN_EINSTELLUNGEN_FK + "=" + EinstellungenEntry.TABLE_NAME + "." + EinstellungenEntry._ID);
        return qb.query(getReadableDatabase(),null,whereClause, whereArgs, null, null, sortOrder);
    }

    public Cursor findContentByName(String name) {
        String whereClause = ContentEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] whereArgs = new String[]{name + "%"};
        String sortOrder = ContentEntry.COLUMN_NAME_NAME + " ASC";
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(UserEntry.TABLE_NAME);
        return qb.query(getReadableDatabase(),null,whereClause, whereArgs, null, null, sortOrder);
    }

    public User insertUser(User user) {

        Einstellungen einstellungen = insertEinstellungen(user.getEinstellungen());
        if (einstellungen == null) {
            return null;
        }

        user.setEinstellungen(einstellungen);

        ContentValues values = getUserValues(user);
        values.put(UserEntry.COLUMN_EINSTELLUNGEN_FK, user.getEinstellungen().getId());

        long userId = getWritableDatabase().insert(UserEntry.TABLE_NAME, null, values);
        if (userId == -1) {
            return null;
        }

        user.setId(userId);
        return user;
    }

    private Einstellungen insertEinstellungen(Einstellungen einstellungen) {
        ContentValues values = getEinstellungenValues(einstellungen);

        long id = getWritableDatabase().insert(EinstellungenEntry.TABLE_NAME, null, values);
        if (id == -1) {
            return null;
        }
        einstellungen.setId(id);
        return einstellungen;
    }

    public int updateUser(User user) {

        int affected = updateEinstellungen(user.getEinstellungen());
        if (affected != 1) {
            return affected;
        }

        String whereClause = UserEntry._ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(user.getId())};

        ContentValues values = getUserValues(user);
        values.put(UserEntry.COLUMN_EINSTELLUNGEN_FK, user.getEinstellungen().getId());
        Log.d("ContactsDBHelper", "updateUser: " + user.toString());

        return getWritableDatabase().update(UserEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    private int updateEinstellungen(Einstellungen einstellungen) {
        String whereClause = EinstellungenEntry._ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(einstellungen.getId())};

        ContentValues values = getEinstellungenValues(einstellungen);
        Log.d("ContactsDBHelper", "updateEinstellungen: " + einstellungen.toString());

        return getWritableDatabase().update(EinstellungenEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public int deleteUser(User user) {
        String whereClause = UserEntry._ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(user.getId())};

        int affected = deleteEinstellungen(user.getEinstellungen());
        if (affected != 1) {
            return affected;
        }

        return getWritableDatabase().delete(UserEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public int deleteContent(Content content) {
        String whereClause = ContentEntry._ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(content.getId())};


        return getWritableDatabase().delete(ContentEntry.TABLE_NAME, whereClause, whereArgs);
    }

    private int deleteEinstellungen(Einstellungen einstellungen) {
        String whereClause = EinstellungenEntry._ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(einstellungen.getId())};

        return getWritableDatabase().delete(EinstellungenEntry.TABLE_NAME, whereClause, whereArgs);
    }

    private ContentValues getUserValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_USERNAME, user.getUsername());
        values.put(UserEntry.COLUMN_NAME_MAIL, user.getMail());
        values.put(UserEntry.COLUMN_NAME_PASSWORT, user.getPasswort());

        return values;
    }

    private ContentValues getEinstellungenValues(Einstellungen einstellungen) {
        ContentValues values = new ContentValues();
        values.put(EinstellungenEntry.COLUMN_NAME_NETFLIX, einstellungen.getNetflix());
        values.put(EinstellungenEntry.COLUMN_NAME_AMAZONPRIME, einstellungen.getAmazonprime());
        values.put(EinstellungenEntry.COLUMN_NAME_MAXDOME, einstellungen.getMaxdome());
        values.put(EinstellungenEntry.COLUMN_NAME_SNAP, einstellungen.getSnap());
        return values;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_EINSTELLUNGEN);
        db.execSQL(SQL_CREATE_TABLE_CONTENT);
        String ROW2 = "INSERT INTO " + UserEntry.TABLE_NAME + " Values (1 , 'goetz@streamingblitz.com', 'password', 'Mail', 1);";
        String ROW3 = "INSERT INTO " + EinstellungenEntry.TABLE_NAME + " Values (1, 1, 1, 1, 1, 1);";
        String ROW4 = "INSERT INTO " + ContentEntry.TABLE_NAME + " Values (1, 'Batman', 'Action', '120 min', 1, 1, '9.2' , 'drawable/blitz_icon.png');";
        db.execSQL(ROW2);
        db.execSQL(ROW3);
        db.execSQL(ROW4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_USER);
        db.execSQL(SQL_DROP_TABLE_EINSTELLUNGEN);
        db.execSQL(SQL_DROP_TABLE_CONTENT);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
