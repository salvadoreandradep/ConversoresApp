package com.gruposeven.conversoresapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class BD extends SQLiteOpenHelper{
    public static final String dbname="db_catalogo";
    public static final int v=1;
    static final String sqlDB = "CREATE TABLE catalogo(idcatalogo interger primary key autoincrement, nombre text, descripcion text, codigo text)";
    public  BD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, dbname, factory, v);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlDB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String administrar_catalogo(String id, String nom, String dir, String cod, String accion){


        try {
            SQLiteDatabase db = getWritableDatabase();
                if(accion.equals("nuevo")){
                    db.execSQL("INSERT INTO catalogo(nombre, descripcion, codigo)VALUES('"+nom+"', '"+dir+"', '"+cod+"')");

            } else if (accion.equals("modificar")){
                    db.execSQL("UPDATE catalogo SET nombre= '"+nom+"', descripcion = '"+dir+"', codigo = '"+cod+"'");
                } else if (accion.equals("eliminar")) {
                    db.execSQL("DELETE FROM catalogo WHERE idcatalogo= '"+id+"'");
                }


            return "ok";


        }catch (Exception e){
            return "ERROR "+e.getMessage();


        }
    }
    public Cursor consulta(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM catalogo ORDER BY nombre";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

}
