package net.jzapper.android.sqlite_db_words.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * User: jchionh
 * Date: 4/6/13
 * Time: 9:13 PM
 */
public class WordsDB extends SQLiteOpenHelper {

    private static final String TAG = WordsDB.class.getSimpleName();

    public static final String TABLE_WORDS = "table_words";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WORDS = "words";

    private static final String DATABASE_FILENAME = "words.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " +
            TABLE_WORDS + "(" +
            COLUMN_ID + " text primary key, " +
            COLUMN_WORDS + " text not null);";

    /**
     * ctor
     * @param context
     */
    public WordsDB(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    /**
     * called when the class is created
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    /**
     * called when dateabase version is upgraded
     * @param sqLiteDatabase
     * @param i
     * @param i2
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading db from version " + oldVersion + " to " + newVersion + ". Old data will be destroyed.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        onCreate(sqLiteDatabase);
    }
}
