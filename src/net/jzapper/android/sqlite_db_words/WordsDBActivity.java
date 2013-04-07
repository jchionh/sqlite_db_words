package net.jzapper.android.sqlite_db_words;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import net.jzapper.android.sqlite_db_words.db.WordsDB;

public class WordsDBActivity extends Activity {

    private static final String TAG = WordsDBActivity.class.getSimpleName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WordsDB wordsDb = new WordsDB(this);
        //wordsDb.init();
        Log.d(TAG, "DB open, records count: " + wordsDb.countRecords());
    }
}
