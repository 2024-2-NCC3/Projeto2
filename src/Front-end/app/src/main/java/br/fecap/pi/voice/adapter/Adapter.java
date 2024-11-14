package br.fecap.pi.voice.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.fecap.pi.voice.R;
import br.fecap.pi.voice.model.Noticia;

public class Adapter extends RecyclerView.Adapter<Adapter.NewsViewHolder> {

    private Context context;
    private List<Noticia> listaNoticia;

    public Adapter(Context context, List<Noticia> newsList) {
        this.context = context;
        this.listaNoticia = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Noticia noticia = listaNoticia.get(position);

        // Usar Glide para carregar a imagem
        Glide.with(context)
                .load(noticia.getImageUrl())
                .placeholder(R.drawable.placeholder_image) // Imagem temporária
                .error(R.drawable.error_image) // Imagem de erro
                .into(holder.newsImageView);

        holder.newsTitleTextView.setText(noticia.getTitle());
        holder.newsDateTextView.setText(noticia.getDate());

        // Configurar o clique para abrir a página externa
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(noticia.getExternalUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaNoticia.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImageView;
        TextView newsTitleTextView;
        TextView newsDateTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.newsImageView);
            newsTitleTextView = itemView.findViewById(R.id.newsTitleTextView);
            newsDateTextView = itemView.findViewById(R.id.newsDateTextView);
        }
    }
}