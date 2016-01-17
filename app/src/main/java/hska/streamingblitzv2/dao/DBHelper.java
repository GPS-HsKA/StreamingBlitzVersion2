package hska.streamingblitzv2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.sql.SQLException;

import hska.streamingblitzv2.dao.DatabaseSchema.ContentEntry;
import hska.streamingblitzv2.dao.DatabaseSchema.EinstellungenEntry;
import hska.streamingblitzv2.dao.DatabaseSchema.UserEntry;
import hska.streamingblitzv2.model.Einstellungen;
import hska.streamingblitzv2.model.User;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "streamingblitzv2.db";
    private static final String SQL_CREATE_TABLE_USER =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    UserEntry.COLUMN_NAME_PASSWORT + " TEXT," +
                    UserEntry.COLUMN_EINSTELLUNGEN_FK + " INTEGER," +
                    "FOREIGN KEY(" + UserEntry.COLUMN_EINSTELLUNGEN_FK + ") REFERENCES " + EinstellungenEntry.TABLE_NAME + "(" + EinstellungenEntry._ID + ")" +
                    ")";

    private static final String SQL_CREATE_TABLE_CONTENT =
            "CREATE TABLE " + ContentEntry.TABLE_NAME + " (" +
                    ContentEntry._ID + " INTEGER PRIMARY KEY," +
                    ContentEntry.COLUMN_NAME_NAME + " TEXT," +
                    ContentEntry.COLUMN_NAME_GENRE + " TEXT," +
                    ContentEntry.COLUMN_NAME_LAUFZEIT + " TEXT," +
                    ContentEntry.COLUMN_NAME_NETFLIX + " INTEGER," +
                    ContentEntry.COLUMN_NAME_AMAZON + " INTEGER," +
                    ContentEntry.COLUMN_NAME_MAXDOME + " INTEGER," +
                    ContentEntry.COLUMN_NAME_SNAP + " INTEGER," +
                    ContentEntry.COLUMN_NAME_IMDBSCORE + " TEXT," +
                    ContentEntry.COLUMN_NAME_JAHR + " TEXT," +
                    ContentEntry.COLUMN_NAME_BILD_PFAD + " TEXT" + ")";

    private static final String SQL_CREATE_TABLE_EINSTELLUNGEN =
            "CREATE TABLE " + EinstellungenEntry.TABLE_NAME + " (" +
                    EinstellungenEntry._ID + " INTEGER PRIMARY KEY," +
                    EinstellungenEntry.COLUMN_NAME_NETFLIX + " BOOLEAN," +
                    EinstellungenEntry.COLUMN_NAME_MAXDOME + " BOOLEAN," +
                    EinstellungenEntry.COLUMN_NAME_AMAZONPRIME + " BOOLEAN," +
                    EinstellungenEntry.COLUMN_NAME_SNAP + " BOOLEAN," +
                    EinstellungenEntry.COLUMN_USER_FK + " TEXT," +
                    "FOREIGN KEY(" + EinstellungenEntry.COLUMN_USER_FK + ") REFERENCES " + UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_NAME_USERNAME + ")" +
                    ")";
    private static final String SQL_DROP_TABLE_CONTENT = "DROP TABLE IF EXISTS " + ContentEntry.TABLE_NAME + ";";
    private static final String SQL_DROP_TABLE_USER = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME + ";";
    private static final String SQL_DROP_TABLE_EINSTELLUNGEN = "DROP TABLE IF EXISTS " + EinstellungenEntry.TABLE_NAME + ";";

    private static DBHelper instance = null;
    private static final String TAG = "!## LOG-MESSAGE ##!";
    private Context ctx;
    SQLiteDatabase mDb;
    DBHelper mDbHelper;
    String imgZoolanderPath;
    String imgBatmanPath;
    String imgBatmanBeginsPath;
    String imgInterstellarPath;
    String imgDarkKnightPath;
    String imgDarkKnightRisesPath;
    String imgStarWarsPath;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    public static DBHelper getInstance(Context ctx) {
        if (instance == null) {
            return new DBHelper(ctx.getApplicationContext());
        }
        return instance;
    }

    public Cursor findAllContent() {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String sortOrder = ContentEntry.COLUMN_NAME_NAME + " ASC";
        qb.setTables(ContentEntry.TABLE_NAME);
        return qb.query(getReadableDatabase(), null, null, null, null, null, sortOrder);
    }

    public Cursor findContentByName(String name) {
        String whereClause = ContentEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] whereArgs = new String[]{name + "%"};
        String sortOrder = ContentEntry.COLUMN_NAME_NAME + " ASC";
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(ContentEntry.TABLE_NAME);
        return qb.query(getReadableDatabase(),null,whereClause, whereArgs, null, null, sortOrder);
    }

    public Einstellungen insertEinstellungen(Einstellungen einstellungen) {
        ContentValues values = getEinstellungenValues(einstellungen);

        long id = getWritableDatabase().insert(EinstellungenEntry.TABLE_NAME, null, values);
        if (id == -1) {
            return null;
        }
        einstellungen.setId(id);
        return einstellungen;
    }

    public boolean updateUser(String userName, Long einstellungenId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserEntry.COLUMN_EINSTELLUNGEN_FK, einstellungenId);
        db.update(UserEntry.TABLE_NAME, contentValues, UserEntry.COLUMN_NAME_USERNAME + " = ? ", null);
        return true;
    }

    public int updateEinstellungen(Einstellungen einstellungen) {
        ContentValues values = getEinstellungenValues(einstellungen);
        Log.d("DBHelper", "updateEinstellungen: " + einstellungen.toString());
        values.put(EinstellungenEntry.COLUMN_NAME_NETFLIX, einstellungen.getNetflix());
        values.put(EinstellungenEntry.COLUMN_NAME_AMAZONPRIME, einstellungen.getAmazonprime());
        values.put(EinstellungenEntry.COLUMN_NAME_MAXDOME, einstellungen.getMaxdome());
        values.put(EinstellungenEntry.COLUMN_NAME_SNAP, einstellungen.getSnap());

        return getWritableDatabase().update(EinstellungenEntry.TABLE_NAME, values, EinstellungenEntry.COLUMN_USER_FK + " = ?",
                new String[]{String.valueOf(einstellungen.getUser())});
    }

    private ContentValues getUserValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserEntry._ID, user.getId());
        values.put(UserEntry.COLUMN_NAME_USERNAME, user.getUsername());
        values.put(UserEntry.COLUMN_NAME_PASSWORT, user.getPasswort());

        return values;
    }

    /*
    Speichern der Checkboxen zu den Einstellungen innerhalb der Userregistrierung
     */

    private ContentValues getEinstellungenValues(Einstellungen einstellungen) {
        ContentValues values = new ContentValues();
        values.put(EinstellungenEntry.COLUMN_NAME_NETFLIX, einstellungen.getNetflix());
        values.put(EinstellungenEntry.COLUMN_NAME_AMAZONPRIME, einstellungen.getAmazonprime());
        values.put(EinstellungenEntry.COLUMN_NAME_MAXDOME, einstellungen.getMaxdome());
        values.put(EinstellungenEntry.COLUMN_NAME_SNAP, einstellungen.getSnap());
        values.put(EinstellungenEntry.COLUMN_USER_FK, einstellungen.getUser());
        return values;
    }

    public long registerUser(String user, String pw)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(UserEntry.COLUMN_NAME_USERNAME, user);
        initialValues.put(UserEntry.COLUMN_NAME_PASSWORT, pw);
        initialValues.put(UserEntry.COLUMN_EINSTELLUNGEN_FK, 1);

        return mDb.insert(UserEntry.TABLE_NAME, null, initialValues);
    }

    public boolean checkEMail(String email) throws SQLException
    {
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + UserEntry.TABLE_NAME + " WHERE user_username=?", new String[]{email});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                mDb.close();
                return true;
            }
        }
        mDb.close();
        return false;
    }

    public boolean loginUser(String username, String password) throws SQLException
    {
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + UserEntry.TABLE_NAME + " WHERE user_username=? AND user_passwort=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                mDb.close();
                return true;
            }
        }
        return false;
    }

    public Cursor loadEinstellungen(String user) throws SQLException{
        String whereClause = EinstellungenEntry.COLUMN_USER_FK + " LIKE ?";
        String[] whereArgs = new String[]{user};
        String sortOrder = EinstellungenEntry.COLUMN_USER_FK + " ASC";
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(EinstellungenEntry.TABLE_NAME);
        return qb.query(getReadableDatabase(),null,whereClause, whereArgs, null, null, sortOrder);
    }

    public void bilderLaden() {

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d(TAG, "SD-Karte gefunden!" + "");
        }
        else Log.d(TAG, "SD-Karte NICHT gefunden!" + "");

        File   imgZoolander = new  File(Environment.getExternalStorageDirectory()+"/zoolander.jpg");
        imgZoolanderPath = imgZoolander.getAbsolutePath();
        File   imgBatman = new  File(Environment.getExternalStorageDirectory()+"/batman.jpg");
        imgBatmanPath = imgBatman.getAbsolutePath();
        File   imgBatmanBegins = new  File(Environment.getExternalStorageDirectory()+"/batmanbegins.jpg");
        imgBatmanBeginsPath = imgBatmanBegins.getAbsolutePath();
        File   imgStarWars = new  File(Environment.getExternalStorageDirectory()+"/starwars.jpg");
        imgStarWarsPath = imgStarWars.getAbsolutePath();
        File   imgDarkKnight = new  File(Environment.getExternalStorageDirectory()+"/darkknight.jpg");
        imgDarkKnightPath = imgDarkKnight.getAbsolutePath();
        File   imgDarkKnightRises = new  File(Environment.getExternalStorageDirectory()+"/darkknightrises.jpg");
        imgDarkKnightRisesPath = imgDarkKnightRises.getAbsolutePath();
        File   imgInsterstellar = new  File(Environment.getExternalStorageDirectory()+"/interstellar.jpg");
        imgInterstellarPath = imgInsterstellar.getAbsolutePath();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_EINSTELLUNGEN);
        db.execSQL(SQL_CREATE_TABLE_CONTENT);
        String ROW2 = "INSERT INTO " + UserEntry.TABLE_NAME + " Values (1 , 'goetz@streamingblitz.com', 'password', 1);";
        String ROW3 = "INSERT INTO " + EinstellungenEntry.TABLE_NAME + " Values (1, 1, 1, 1, 1, '1');";
        db.execSQL(ROW2);
        db.execSQL(ROW3);

        bilderLaden();

        /*
        ** Batman ContentValues
        */

        Integer id = 1;
        String name = "Batman";
        String genre = "Action";
        String laufzeit = "126 min";
        Integer netflix = 1;
        Integer amazon = 0;
        Integer maxdome = 0;
        Integer snap = 1;
        String imdb = "7,6";
        String jahr = "1989";
        ContentValues batman = new ContentValues();
        batman.put(ContentEntry._ID, id);
        batman.put(ContentEntry.COLUMN_NAME_NAME, name);
        batman.put(ContentEntry.COLUMN_NAME_GENRE, genre);
        batman.put(ContentEntry.COLUMN_NAME_LAUFZEIT, laufzeit);
        batman.put(ContentEntry.COLUMN_NAME_NETFLIX, netflix);
        batman.put(ContentEntry.COLUMN_NAME_AMAZON, amazon);
        batman.put(ContentEntry.COLUMN_NAME_MAXDOME, maxdome);
        batman.put(ContentEntry.COLUMN_NAME_SNAP, snap);
        batman.put(ContentEntry.COLUMN_NAME_IMDBSCORE, imdb);
        batman.put(ContentEntry.COLUMN_NAME_JAHR, jahr);
        batman.put(ContentEntry.COLUMN_NAME_BILD_PFAD, imgBatmanPath);
        db.insertWithOnConflict(ContentEntry.TABLE_NAME, null, batman, SQLiteDatabase.CONFLICT_REPLACE);

        /*
        ** Batman Begins ContentValues
        */

        Integer id1 = 2;
        String name1 = "Batman Begins";
        String genre1 = "Action";
        String laufzeit1 = "140 min";
        Integer netflix1 = 1;
        Integer amazon1 = 1;
        Integer maxdome1 = 0;
        Integer snap1 = 1;
        String imdb1 = "8,3";
        String jahr1 = "2005";
        ContentValues batmanBegins = new ContentValues();
        batmanBegins.put(ContentEntry._ID, id1);
        batmanBegins.put(ContentEntry.COLUMN_NAME_NAME, name1);
        batmanBegins.put(ContentEntry.COLUMN_NAME_GENRE, genre1);
        batmanBegins.put(ContentEntry.COLUMN_NAME_LAUFZEIT, laufzeit1);
        batman.put(ContentEntry.COLUMN_NAME_NETFLIX, netflix1);
        batman.put(ContentEntry.COLUMN_NAME_AMAZON, amazon1);
        batman.put(ContentEntry.COLUMN_NAME_MAXDOME, maxdome1);
        batman.put(ContentEntry.COLUMN_NAME_SNAP, snap1);
        batmanBegins.put(ContentEntry.COLUMN_NAME_IMDBSCORE, imdb1);
        batmanBegins.put(ContentEntry.COLUMN_NAME_JAHR, jahr1);
        batmanBegins.put(ContentEntry.COLUMN_NAME_BILD_PFAD, imgBatmanBeginsPath);
        db.insertWithOnConflict(ContentEntry.TABLE_NAME, null, batmanBegins, SQLiteDatabase.CONFLICT_REPLACE);

        /*
        ** The Dark Knight ContentValues
        */

        Integer id2 = 3;
        String name2 = "The Dark Knight";
        String genre2 = "Action";
        String laufzeit2 = "152 min";
        Integer netflix2 = 1;
        Integer amazon2 = 1;
        Integer maxdome2 = 1;
        Integer snap2 = 1;
        String imdb2 = "9,0";
        String jahr2 = "2008";
        ContentValues darkKnight = new ContentValues();
        darkKnight.put(ContentEntry._ID, id2);
        darkKnight.put(ContentEntry.COLUMN_NAME_NAME, name2);
        darkKnight.put(ContentEntry.COLUMN_NAME_GENRE, genre2);
        darkKnight.put(ContentEntry.COLUMN_NAME_LAUFZEIT, laufzeit2);
        batman.put(ContentEntry.COLUMN_NAME_NETFLIX, netflix2);
        batman.put(ContentEntry.COLUMN_NAME_AMAZON, amazon2);
        batman.put(ContentEntry.COLUMN_NAME_MAXDOME, maxdome2);
        batman.put(ContentEntry.COLUMN_NAME_SNAP, snap2);
        darkKnight.put(ContentEntry.COLUMN_NAME_IMDBSCORE, imdb2);
        darkKnight.put(ContentEntry.COLUMN_NAME_JAHR, jahr2);
        darkKnight.put(ContentEntry.COLUMN_NAME_BILD_PFAD, imgDarkKnightPath);
        db.insertWithOnConflict(ContentEntry.TABLE_NAME, null, darkKnight, SQLiteDatabase.CONFLICT_REPLACE);

        /*
        ** The Dark Knight Rises ContentValues
        */

        Integer id3 = 4;
        String name3 = "The Dark Knight Rises";
        String genre3 = "Action";
        String laufzeit3 = "164 min";
        Integer netflix3 = 0;
        Integer amazon3 = 1;
        Integer maxdome3 = 0;
        Integer snap3 = 1;
        String imdb3 = "8,5";
        String jahr3 = "2012";
        ContentValues darkKnightRises = new ContentValues();
        darkKnightRises.put(ContentEntry._ID, id3);
        darkKnightRises.put(ContentEntry.COLUMN_NAME_NAME, name3);
        darkKnightRises.put(ContentEntry.COLUMN_NAME_GENRE, genre3);
        darkKnightRises.put(ContentEntry.COLUMN_NAME_LAUFZEIT, laufzeit3);
        batman.put(ContentEntry.COLUMN_NAME_NETFLIX, netflix3);
        batman.put(ContentEntry.COLUMN_NAME_AMAZON, amazon3);
        batman.put(ContentEntry.COLUMN_NAME_MAXDOME, maxdome3);
        batman.put(ContentEntry.COLUMN_NAME_SNAP, snap3);
        darkKnightRises.put(ContentEntry.COLUMN_NAME_IMDBSCORE, imdb3);
        darkKnightRises.put(ContentEntry.COLUMN_NAME_JAHR, jahr3);
        darkKnightRises.put(ContentEntry.COLUMN_NAME_BILD_PFAD, imgDarkKnightRisesPath);
        db.insert(ContentEntry.TABLE_NAME, null, darkKnightRises);

        /*
        ** Zoolander ContentValues
        */

        Integer id4 = 5;
        String name4 = "Zoolander";
        String genre4 = "Comedy";
        String laufzeit4 = "89 min";
        Integer netflix4 = 1;
        Integer amazon4 = 0;
        Integer maxdome4 = 0;
        Integer snap4 = 0;
        String imdb4 = "6,6";
        String jahr4 = "2014";
        ContentValues zoolander = new ContentValues();
        zoolander.put(ContentEntry._ID, id4);
        zoolander.put(ContentEntry.COLUMN_NAME_NAME, name4);
        zoolander.put(ContentEntry.COLUMN_NAME_GENRE, genre4);
        zoolander.put(ContentEntry.COLUMN_NAME_LAUFZEIT, laufzeit4);
        batman.put(ContentEntry.COLUMN_NAME_NETFLIX, netflix4);
        batman.put(ContentEntry.COLUMN_NAME_AMAZON, amazon4);
        batman.put(ContentEntry.COLUMN_NAME_MAXDOME, maxdome4);
        batman.put(ContentEntry.COLUMN_NAME_SNAP, snap4);
        zoolander.put(ContentEntry.COLUMN_NAME_IMDBSCORE, imdb4);
        zoolander.put(ContentEntry.COLUMN_NAME_JAHR, jahr4);
        zoolander.put(ContentEntry.COLUMN_NAME_BILD_PFAD, imgZoolanderPath);
        db.insert(ContentEntry.TABLE_NAME, null, zoolander);


        /*
        ** Star Wars ContentValues
        */

        Integer id5 = 6;
        String name5 = "Star Wars Das Erwachen der Macht";
        String genre5 = "Action";
        String laufzeit5 = "135 min";
        Integer netflix5 = 0;
        Integer amazon5 = 0;
        Integer maxdome5 = 0;
        Integer snap5 = 0;
        String imdb5 = "8,5";
        String jahr5 = "2015";
        ContentValues starWars = new ContentValues();
        starWars.put(ContentEntry._ID, id5);
        starWars.put(ContentEntry.COLUMN_NAME_NAME, name5);
        starWars.put(ContentEntry.COLUMN_NAME_GENRE, genre5);
        starWars.put(ContentEntry.COLUMN_NAME_LAUFZEIT, laufzeit5);
        batman.put(ContentEntry.COLUMN_NAME_NETFLIX, netflix5);
        batman.put(ContentEntry.COLUMN_NAME_AMAZON, amazon5);
        batman.put(ContentEntry.COLUMN_NAME_MAXDOME, maxdome5);
        batman.put(ContentEntry.COLUMN_NAME_SNAP, snap5);
        starWars.put(ContentEntry.COLUMN_NAME_IMDBSCORE, imdb5);
        starWars.put(ContentEntry.COLUMN_NAME_JAHR, jahr5);
        starWars.put(ContentEntry.COLUMN_NAME_BILD_PFAD, imgStarWarsPath);
        db.insertWithOnConflict(ContentEntry.TABLE_NAME, null, starWars, SQLiteDatabase.CONFLICT_REPLACE);

        /*
        ** Interstellar ContentValues
        */

        Integer id6 = 7;
        String name6 = "Interstellar";
        String genre6 = "Adventure";
        String laufzeit6 = "169 min";
        Integer netflix6 = 1;
        Integer amazon6 = 1;
        Integer maxdome6 = 0;
        Integer snap6 = 1;
        String imdb6 = "8,6";
        String jahr6 = "2014";
        ContentValues interstellar = new ContentValues();
        interstellar.put(ContentEntry._ID, id6);
        interstellar.put(ContentEntry.COLUMN_NAME_NAME, name6);
        interstellar.put(ContentEntry.COLUMN_NAME_GENRE, genre6);
        interstellar.put(ContentEntry.COLUMN_NAME_LAUFZEIT, laufzeit6);
        batman.put(ContentEntry.COLUMN_NAME_NETFLIX, netflix6);
        batman.put(ContentEntry.COLUMN_NAME_AMAZON, amazon6);
        batman.put(ContentEntry.COLUMN_NAME_MAXDOME, maxdome6);
        batman.put(ContentEntry.COLUMN_NAME_SNAP, snap6);
        interstellar.put(ContentEntry.COLUMN_NAME_IMDBSCORE, imdb6);
        interstellar.put(ContentEntry.COLUMN_NAME_JAHR, jahr6);
        interstellar.put(ContentEntry.COLUMN_NAME_BILD_PFAD, imgInterstellarPath);
        db.insertWithOnConflict(ContentEntry.TABLE_NAME, null, interstellar, SQLiteDatabase.CONFLICT_REPLACE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_USER);
        db.execSQL(SQL_DROP_TABLE_EINSTELLUNGEN);
        db.execSQL(SQL_DROP_TABLE_CONTENT);
        onCreate(db);
    }

    public DBHelper open() throws SQLException
    {
        mDbHelper = new DBHelper(ctx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
