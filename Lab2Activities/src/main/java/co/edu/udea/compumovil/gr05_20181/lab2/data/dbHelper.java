package co.edu.udea.compumovil.gr05_20181.lab2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Usuario.db";

    public dbHelper(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + usuarioContract.usuarioEntry.TABLE_NAME + " ("
                + usuarioContract.usuarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + usuarioContract.usuarioEntry.NOMBRE + " TEXT NOT NULL,"
                + usuarioContract.usuarioEntry.APELLIDO + " TEXT NOT NULL,"
                + usuarioContract.usuarioEntry.CONTRASEÑA + " TEXT NOT NULL,"
                + usuarioContract.usuarioEntry.CORREO + " TEXT NOT NULL,"
                + usuarioContract.usuarioEntry.FOTO + " TEXT NOT NULL,"
                + "UNIQUE (" + usuarioContract.usuarioEntry._ID+ "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long guardarUsuario(usuario user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                usuarioContract.usuarioEntry.TABLE_NAME,
                null,
                user.toContentValues());

    }
}
