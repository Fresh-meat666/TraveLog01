package com.example.travelog01.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelog01.Model.DiaryBean;

import java.util.ArrayList;

public class DiaryHelper {
    private static SQLiteDatabase getWritableAndOpen(DatabaseHelper databaseHelper){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        return sqLiteDatabase;
    }

    private static SQLiteDatabase getReadableAndOpen(DatabaseHelper databaseHelper){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        return sqLiteDatabase;
    }

    private static void closeTransaction(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public static ArrayList<DiaryBean> getDiaries(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = getReadableAndOpen(databaseHelper);
        Cursor cursor = sqLiteDatabase.query(DiaryBean.TABLE_NAME, null, null, null, null, null, DiaryBean.DATE + " DESC");
        closeTransaction(sqLiteDatabase);
        ArrayList<DiaryBean> list = convertCursorToList(cursor);
        cursor.close();
        return list;
    }

    public static DiaryBean getDiary(Context context, int id){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = getReadableAndOpen(databaseHelper);
        Cursor cursor = sqLiteDatabase.query(DiaryBean.TABLE_NAME, null, DiaryBean.ID + "=" + id, null, null, null, null);
        closeTransaction(sqLiteDatabase);
        cursor.moveToFirst();
        DiaryBean diary = convertFromCursor(cursor);
        cursor.close();
        return diary;
    }
    private static ArrayList<DiaryBean> convertCursorToList(Cursor cursor) {
        ArrayList<DiaryBean> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(convertFromCursor(cursor));
            } while (cursor.moveToNext());
            return list;
        } else {
            return list;
        }
    }
    private static DiaryBean convertFromCursor(Cursor cursor){
        DiaryBean diary = new DiaryBean();

        final int id = cursor.getColumnIndex(DiaryBean.ID);
        final int title = cursor.getColumnIndex(DiaryBean.TITLE);
        final int content = cursor.getColumnIndex(DiaryBean.CONTENT);
        final int date = cursor.getColumnIndex(DiaryBean.DATE);
        diary.setId(cursor.getInt(id));
        diary.setTitle(cursor.getString(title));
        diary.setContent(cursor.getString(content));
        diary.setDate(cursor.getString(date));
        return diary;
    }
}
