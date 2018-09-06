package com.example.korealong.qrcodescanner.Databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.korealong.qrcodescanner.Sqlite.UserCard;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseGetData extends SQLiteAssetHelper {
    private static final String DB_NAME = "MCardDB.sqlite";
    private static final int DB_VER=1;
    public DatabaseGetData(Context context) {
        super(context, DB_NAME,null,DB_VER);
    }
    //function get all cards
    public List<UserCard> getCard()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id","nameCard","nameStore","image"};
        String tableName = "Card";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<UserCard> result = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do {
                UserCard userCard = new UserCard();
                userCard.setId(cursor.getInt(cursor.getColumnIndex("id")));
                userCard.setNameCard(cursor.getString(cursor.getColumnIndex("nameCard")));
                userCard.setNameStore(cursor.getString(cursor.getColumnIndex("nameStore")));
                userCard.setImage(cursor.getBlob(cursor.getColumnIndex("image")));

                result.add(userCard);
            }while (cursor.moveToNext());
        }
        return result;
    }

    //function get all name card
    public List<String> getNameCard()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"nameCard"};
        String tableName = "Card";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do {
                result.add(cursor.getString(cursor.getColumnIndex("nameCard")));
            }while (cursor.moveToNext());
        }
        return result;
    }

    //function get card by name
    public List<UserCard> getCardByName(String name)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id","nameCard","nameStore","image"};
        String tableName = "Card";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,"nameCard LIKE ?",new String[]{"%"+name+"%"},null,null,null);
        List<UserCard> result = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do {
                UserCard userCard = new UserCard();
                userCard.setId(cursor.getInt(cursor.getColumnIndex("id")));
                userCard.setNameCard(cursor.getString(cursor.getColumnIndex("nameCard")));
                userCard.setNameStore(cursor.getString(cursor.getColumnIndex("nameStore")));
                userCard.setImage(cursor.getBlob(cursor.getColumnIndex("image")));

                result.add(userCard);
            }while (cursor.moveToNext());
        }
        return result;
    }
}
