package com.laioffer.tinnews.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.SearchNewsItemBinding;
import com.laioffer.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder>{
    //1.supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList){
        articles.clear();
        articles.addAll(newsList);
        //refresh
        notifyDataSetChanged();
    }

    //2.adapter overrides:
    //call onCreateViewHolder number of view times
    //e.g. 7 item(view) on page, call 7 times
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate a view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item,parent,false);
        return new SearchNewsViewHolder(view);
    }
    //why holder? if we don't have holder, we have to use binder bind or use find view by id
    //binding is slow, so we don't do binding here. we bind in constructor
    //view holder to view
    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_24dp);
        holder.itemTitleTextView.setText(article.title);
        Picasso.get().load(article.urlToImage).into(holder.itemImageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    //3.SearchNewsViewHolder:
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView favoriteImageView;
        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            //view binding (get binding reference)
            //binding happens in constructor
            //reduce total number of calls of binding
            //bind xml and view class
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            favoriteImageView = binding.searchItemFavorite;
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }

}
