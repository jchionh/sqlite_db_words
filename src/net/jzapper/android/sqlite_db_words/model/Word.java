package net.jzapper.android.sqlite_db_words.model;

/**
 * User: jchionh
 * Date: 4/6/13
 * Time: 9:39 PM
 */
public class Word {
    private String id;
    private String data;

    public Word(String word) {
        id = word;
        data = word;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
