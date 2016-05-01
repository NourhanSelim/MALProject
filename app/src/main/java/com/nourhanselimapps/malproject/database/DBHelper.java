package com.nourhanselimapps.malproject.database;

/**
 * Created by MH-Acer on 4/30/2016.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.nourhanselimapps.malproject.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String favouriteMovies_TABLE_NAME = "favouriteMovies";
    public static final String favouriteMovies_COLUMN_ID = "id";
    public static final String favouriteMovies_COLUMN_TITLE = "title";
    public static final String favouriteMovies_COLUMN_POSTER_PATH = "poster_path";
    public static final String favouriteMovies_COLUMN_BACKDROP_PATH = "backdrop_path";
    public static final String favouriteMovies_COLUMN_OVERVIEW = "overview";
    public static final String favouriteMovies_COLUMN_RELEASED_DATE = "release_date";
    public static final String favouriteMovies_COLUMN_ORIGINAL_TITLE = "original_title";
    public static final String favouriteMovies_COLUMN_ORIGINAL_LANGUAGE = "original_language";
    public static final String favouriteMovies_COLUMN_POPULARITY = "popularity";
    public static final String favouriteMovies_COLUMN_VOTE_COUNT = "vote_count";
    public static final String favouriteMovies_COLUMN_VOTE_AVERAGE = "vote_average";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table favouriteMovies " +
                        "(id text primary key, poster_path text, backdrop_path text,overview text,release_date text," +
                        " original_title text , original_language text, popularity text, vote_count text, vote_average text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS favouriteMovies");
        onCreate(db);
    }

    public boolean insertFavouriteMovie (String id, String poster_path, String backdrop_path, String overview, String release_date,
                                         String original_title, String original_language, String popularity, String vote_count,
                                         String vote_average )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TAG_MOVIE_ID, id);
        contentValues.put(Constants.TAG_POSTER_PATH, poster_path);
        contentValues.put(Constants.TAG_BACKDROP_PATH, backdrop_path);
        contentValues.put(Constants.TAG_OVERVIEW, overview);
        contentValues.put(Constants.TAG_RELEASE_DATE, release_date);
        contentValues.put(Constants.TAG_ORIGINAL_TITLE, original_title);
        contentValues.put(Constants.TAG_ORIGINAL_LANGUAGE, original_language);
        contentValues.put(Constants.TAG_POPULARITY, popularity);
        contentValues.put(Constants.TAG_VOTE_COUNT, vote_count);
        contentValues.put(Constants.TAG_VOTE_AVERAGE, vote_average);

        db.insert("favouriteMovies", null, contentValues);
        return true;
    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from favouriteMovies where id="+id+"", null );
        return cursor;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, favouriteMovies_TABLE_NAME);
        return numRows;
    }

//    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("phone", phone);
//        contentValues.put("email", email);
//        contentValues.put("street", street);
//        contentValues.put("place", place);
//        db.update("favouriteMovies", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }

    public Integer deleteMovie (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favouriteMovies",
                "id = ? ",
                new String[] {id});
    }

    public ArrayList<String> getAllMovies() throws JSONException {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from favouriteMovies", null );
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(Constants.TAG_MOVIE_ID, cursor.getString(cursor.getColumnIndex(Constants.TAG_MOVIE_ID)));
            jsonObject.put(Constants.TAG_POSTER_PATH, cursor.getString(cursor.getColumnIndex(Constants.TAG_POSTER_PATH)));
            jsonObject.put(Constants.TAG_BACKDROP_PATH, cursor.getString(cursor.getColumnIndex(Constants.TAG_BACKDROP_PATH)));
            jsonObject.put(Constants.TAG_OVERVIEW, cursor.getString(cursor.getColumnIndex(Constants.TAG_OVERVIEW)));
            jsonObject.put(Constants.TAG_RELEASE_DATE, cursor.getString(cursor.getColumnIndex(Constants.TAG_RELEASE_DATE)));
            jsonObject.put(Constants.TAG_ORIGINAL_TITLE, cursor.getString(cursor.getColumnIndex(Constants.TAG_ORIGINAL_TITLE)));
            jsonObject.put(Constants.TAG_ORIGINAL_LANGUAGE, cursor.getString(cursor.getColumnIndex(Constants.TAG_ORIGINAL_LANGUAGE)));
            jsonObject.put(Constants.TAG_POPULARITY, cursor.getString(cursor.getColumnIndex(Constants.TAG_POPULARITY)));
            jsonObject.put(Constants.TAG_VOTE_COUNT, cursor.getString(cursor.getColumnIndex(Constants.TAG_VOTE_COUNT)));
            jsonObject.put(Constants.TAG_VOTE_AVERAGE, cursor.getString(cursor.getColumnIndex(Constants.TAG_VOTE_AVERAGE)));
            array_list.add(jsonObject.toString());

            cursor.moveToNext();
        }
        return array_list;
    }

    public boolean isFavourite(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from favouriteMovies where id="+id+"", null );
        cursor.moveToFirst();
        return cursor.isAfterLast()==false;
    }
}
