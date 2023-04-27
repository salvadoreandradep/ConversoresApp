package com.example.semana13_sqlite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<Productos> datosRepuestosArrayList;
    LayoutInflater layoutInflater;
    Productos misRepuestos;

    /*    <uses-feature android:name="android.hardware.camera" android:required="true"></uses-feature>   */

    public adaptadorImagenes(Context context, ArrayList<Productos> datosRepuestosArrayList) {
        this.context = context;
        this.datosRepuestosArrayList = datosRepuestosArrayList;
    }

    @Override
    public int getCount() {
        return datosRepuestosArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return datosRepuestosArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return Long.parseLong(datosRepuestosArrayList.get(position).getIdProducto());
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listview_imagenes, parent, false);
        TextView tempVal = itemView.findViewById(R.id.lblTitulo);
        ImageView imgViewView = itemView.findViewById(R.id.imgPhoto);
        try{
            misRepuestos = datosRepuestosArrayList.get(position);
            tempVal.setText("Nommbre: "+misRepuestos.getNombre());

            tempVal = itemView.findViewById(R.id.lblCategoria);
            tempVal.setText("Categooria: "+misRepuestos.getCategoria());

            tempVal = itemView.findViewById(R.id.lblPrecio);
            tempVal.setText("Precioo: "+misRepuestos.getPrecio());

            tempVal = itemView.findViewById(R.id.lblMarca);
            tempVal.setText("Marca: "+misRepuestos.getMarca());


            Bitmap imagenBitmap = BitmapFactory.decodeFile(misRepuestos.getUrlImg());
            imgViewView.setImageBitmap(imagenBitmap);

        }catch (Exception e){
        }
        return itemView;
    }
}
