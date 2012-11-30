package br.com.bhgervendas.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_SYNCS = "syncs";
    public static final String COLUMN_ID = " _id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_VALOR = "valor";
    public static final String COLUMN_TPMOV = "tipoMov";
    public static final String COLUMN_PARC = "parc";
    public static final String COLUMN_SYNC = "sincronizado";
    private static final String DATABASE_NAME = "syncs.db";
    private static final int DATABASE_VERSION = 1;
    // Database creation sql statement
    private static final String DATABASE_CREATE = " create table if not exists "
            + TABLE_SYNCS + "(" + COLUMN_ID
            + " integer primary key autoincrement , " + COLUMN_NOME
            + " text not null, " + COLUMN_DATA
            + " text not null, " + COLUMN_VALOR
            + " decimal not null, " + COLUMN_TPMOV
            + " text not null, " + COLUMN_PARC
            + " text not null, " + COLUMN_SYNC
            + " bit not null);";

    public CustomSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_SYNCS);
        onCreate(db);
    }
}