package net.jzapper.android.sqlite_db_words.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import net.jzapper.android.sqlite_db_words.model.Word;
import net.jzapper.android.sqlite_db_words.model.WordsReader;

import java.io.InputStream;

/**
 * User: jchionh
 * Date: 4/6/13
 * Time: 9:13 PM
 */
public class WordsDB extends SQLiteOpenHelper {

    private static final String TAG = WordsDB.class.getSimpleName();

    public static final String TABLE_WORDS = "table_words";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATA = "words";

    private static final String DATABASE_FILENAME = "words.db";
    private static final int DATABASE_VERSION = 1;

    private static final int RECORD_COUNT_REFERENCE = 19223;

    private static final String DATABASE_CREATE =
            "CREATE TABLE " +
            TABLE_WORDS + "(" +
            COLUMN_ID + " TEXT PRIMARY KEY, " +
            COLUMN_DATA + " TEXT NOT NULL, " +
            "UNIQUE (" + COLUMN_ID + ") ON CONFLICT REPLACE );";

    private SQLiteDatabase writedb;
    private SQLiteDatabase readdb;

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
        // Log.d(TAG, "TABLE_WORDS created [" + countRecords() + "]");
        Log.d(TAG, "TABLE_WORDS created.");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d(TAG, "Database opened.");
    }

    /**
     * called when dateabase version is upgraded
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading db from version " + oldVersion + " to " + newVersion + ". Old data will be destroyed.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        onCreate(sqLiteDatabase);
    }

    public void checkAndPopulate(InputStream wordsStream) {
        int count = countRecords();
        if (count != RECORD_COUNT_REFERENCE) {
            Log.d(TAG, "Db not populated, populating now.");
            deleteAllRows();
            WordsReader wordsReader = new WordsReader(wordsStream);
            wordsReader.scanAndPopulateDb(this);
        } else {
            Log.d(TAG, "Db populated with words, no need to populate.");
        }
    }

    /**
     * add a word to the database
     * @param word
     */
    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, word.getId());
        values.put(COLUMN_DATA, word.getData());
        db.insert(TABLE_WORDS, null, values);
        db.close();
    }

    public int countRecords() {
        String countQuery = "SELECT * FROM " + TABLE_WORDS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    private void deleteAllRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, null, null);
        db.close();
    }
}
