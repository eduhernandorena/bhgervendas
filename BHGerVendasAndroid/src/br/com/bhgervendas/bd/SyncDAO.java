package br.com.bhgervendas.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.bhgervendas.bean.Sync;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo Hernandorena
 */
public class SyncDAO {

    private SQLiteDatabase database;
    private String[] columns = {CustomSQLiteOpenHelper.COLUMN_ID,
        CustomSQLiteOpenHelper.COLUMN_NOME,
        CustomSQLiteOpenHelper.COLUMN_DATA,
        CustomSQLiteOpenHelper.COLUMN_VALOR,
        CustomSQLiteOpenHelper.COLUMN_TPMOV,
        CustomSQLiteOpenHelper.COLUMN_SYNC};
    private CustomSQLiteOpenHelper sqliteOpenHelper;

    public SyncDAO(Context context) {
        sqliteOpenHelper = new CustomSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        database = sqliteOpenHelper.getWritableDatabase();
    }

    public void close() {
        sqliteOpenHelper.close();
    }

    public Sync create(Sync sync) {
        ContentValues values = new ContentValues();
        values.put(CustomSQLiteOpenHelper.COLUMN_NOME, sync.getNome());
        values.put(CustomSQLiteOpenHelper.COLUMN_DATA, sync.getDataPag());
        values.put(CustomSQLiteOpenHelper.COLUMN_VALOR, sync.getValor());
        values.put(CustomSQLiteOpenHelper.COLUMN_TPMOV, sync.getTpMov());
        values.put(CustomSQLiteOpenHelper.COLUMN_SYNC, sync.isSincronizado());
        long insertId = database.insert(CustomSQLiteOpenHelper.TABLE_SYNCS, null,
                values);
        Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_SYNCS,
                columns, CustomSQLiteOpenHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Sync newSync = new Sync();
        newSync.setId(cursor.getLong(0));
        newSync.setNome(cursor.getString(1));
        newSync.setDataPag(cursor.getString(2));
        newSync.setValor(cursor.getDouble(3));
        newSync.setTpMov(cursor.getString(4));
        newSync.setSincronizado(cursor.getInt(5) == 1);
        cursor.close();
        return newSync;
    }

    public void delete(Sync Sync) {
        long id = Sync.getId();
        database.delete(CustomSQLiteOpenHelper.TABLE_SYNCS, CustomSQLiteOpenHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteAll() {
        database.delete(CustomSQLiteOpenHelper.TABLE_SYNCS, "", null);
    }

    public List<Sync> getAll() {
        List<Sync> Syncs = new ArrayList<Sync>();

        Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_SYNCS,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sync Sync = new Sync();
            Sync.setId(cursor.getLong(0));
            Sync.setNome(cursor.getString(1));
            Sync.setDataPag(cursor.getString(2));
            Sync.setValor(cursor.getDouble(3));
            Sync.setTpMov(cursor.getString(4));
            Sync.setSincronizado(cursor.getInt(5) == 1);
            Syncs.add(Sync);
            cursor.moveToNext();
        }
        cursor.close();
        return Syncs;
    }
}
