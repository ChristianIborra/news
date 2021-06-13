package com.ciborra.news;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterNoticia extends RecyclerView.Adapter<AdapterNoticia.MyViewHolder> {
    ArrayList<noticia> Noticias;
    Context context;

    public AdapterNoticia(ArrayList<noticia> noticias, Context context) {
        Noticias = noticias;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate( R.layout.item_noticia,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    noticia actual = Noticias.get(position);
    holder.mTitulo.setText(actual.getmTitulo());
    holder.mDescripcion.setText(actual.getmDescripcion());
    holder.mFecha.setText(actual.getmFecha());
    holder.mTitulo.setText(actual.getmTitulo());
        Picasso.with(context).load(actual.getmImagen()).into(holder.mImagen);
        holder.mImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Detalles.class);
                intent.putExtra("Enlace",actual.getmEnlace());
                context.startActivity(intent);
            }
        });

    }

   /* private String corregir(String getmDescripcion) {

        String descripcionOriginal = getmDescripcion;
        String separador = "&nbsp";
        String[] partes = descripcionOriginal.charAt(spea);
        String devolver = partes[1];

    }*/


    @Override
    public int getItemCount() {
        return Noticias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTitulo, mDescripcion,mFecha;
        ImageView mImagen;

        public  MyViewHolder(View itemView){
            super(itemView);
            mTitulo=(TextView) itemView.findViewById(R.id.Titulo);
            mDescripcion=(TextView) itemView.findViewById(R.id.Descripcion);
            mFecha=(TextView) itemView.findViewById(R.id.Fecha);
            mImagen=(ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
