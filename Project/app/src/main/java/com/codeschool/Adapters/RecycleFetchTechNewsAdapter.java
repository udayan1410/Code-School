package com.codeschool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Models.Article;
import com.codeschool.Models.TechNewsModel;
import com.codeschool.project.NewsView;
import com.codeschool.project.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleFetchTechNewsAdapter extends RecyclerView.Adapter<RecycleFetchTechNewsAdapter.MyViewHolder> {

    TechNewsModel techNewsModel;
    Context context;

    public RecycleFetchTechNewsAdapter(Context context, TechNewsModel techNewsModel) {
        this.context = context;
        this.techNewsModel = techNewsModel;
    }


    @NonNull
    @Override
    public RecycleFetchTechNewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.listitem_news, parent, false);
        RecycleFetchTechNewsAdapter.MyViewHolder myViewHolder = new RecycleFetchTechNewsAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleFetchTechNewsAdapter.MyViewHolder holder, int position) {
        List<Article> articleList = techNewsModel.getArticles();

        holder.title.setText(articleList.get(position).getTitle());
        holder.description.setText(articleList.get(position).getDescription());
        Glide.with(context).load(articleList.get(position).getUrlToImage()).into(holder.newsImage);


        if (articleList.get(position).getAuthor() == null || articleList.get(position).getAuthor().equalsIgnoreCase("") || articleList.get(position).getAuthor().equalsIgnoreCase(" "))
            holder.author.setText("Anonymous");
        else
            holder.author.setText(articleList.get(position).getAuthor());


        holder.newsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, NewsView.class);
                intent.putExtra("url",articleList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return techNewsModel.getArticles().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description, author;
        private ImageView newsImage;
        private CardView newsItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.newsImage);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            author = itemView.findViewById(R.id.author);
            newsItem = itemView.findViewById(R.id.newsItem);
        }
    }

}
