package com.gruposeven.conversoresapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbProducto extends DbHelper{


    Context context;
    public DbProducto(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertaProducto(String producto, String marca, String precentacion, String precio){

        long id = 0;
        try {


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("producto", producto);
        values.put("marca", marca);
        values.put("precentacion", precentacion);
        values.put("precio", precio);

        id = db.insert(TABLE_PRODUCTOS, null, values);

        }catch (Exception e) {
               e.toString();
        }
            return id;

    }
}
