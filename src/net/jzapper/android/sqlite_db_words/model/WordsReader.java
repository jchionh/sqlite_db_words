package net.jzapper.android.sqlite_db_words.model;

import android.util.Log;
import net.jzapper.android.sqlite_db_words.db.WordsDB;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Scanner;

/**
 * User: jchionh
 * Date: 4/6/13
 * Time: 10:22 PM
 */
public class WordsReader {

    private static final String TAG = WordsReader.class.getSimpleName();
    private final InputStream wordsStream;

    public WordsReader(InputStream inputStream) {
        wordsStream = inputStream;
    }

    public void scanAndPopulateDb(WordsDB wordsDB) {
        // we record our seen words
        HashSet<String> seenWords = new HashSet<String>();
        Scanner scanner = null;
        int wordCount = 0;

        try {
            scanner = new Scanner(wordsStream);
            // from the scanner stream, we want to pick out words
            // these are delimited with non alpha letters
            scanner.useDelimiter("[^a-zA-Z]");

            while (scanner.hasNext()) {
                String word = scanner.next().toUpperCase();
                if (word.length() > 0) {
                    // now we record our string into the hashset
                    // and only count the letter freqs if it's unique
                    boolean unique = seenWords.add(word);

                    // a reusable field we will use for reflection in case 4
                    Field field = String.class.getDeclaredField("value");
                    field.setAccessible(true);

                    if (unique) {
                        // fields to extract characters in the word
                        //final char[] chars = (char[]) field.get(word);
                        //final int wordLength = chars.length;
                        wordsDB.addWord(new Word(word));
                        Log.d(TAG, "read: [" + word + "]");
                        wordCount++;
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            //System.out.println(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        Log.d(TAG, "Populated db with [" + wordCount + "] words." );
    }
}
