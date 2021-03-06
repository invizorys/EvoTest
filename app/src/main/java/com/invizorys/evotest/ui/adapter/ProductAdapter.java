package com.invizorys.evotest.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.invizorys.evotest.R;
import com.invizorys.evotest.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roma on 25.02.2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context context;
    private List<Product> products;
    private ProductListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle, tvStatus, tvPrice;
        ImageButton ibtnFavorite;
        Button btnBuy;

        MyViewHolder(View view) {
            super(view);
            ivThumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            btnBuy = (Button) view.findViewById(R.id.btn_buy);
            ibtnFavorite = (ImageButton) view.findViewById(R.id.ibtn_favorite);
        }
    }

    public ProductAdapter(List<Product> products, ProductListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.catalog_item_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.tvTitle.setText(product.getName());
        holder.tvStatus.setText(R.string.in_stock);
        holder.tvPrice.setText(product.getPriceAndCurrency());

        Picasso.with(context).load(product.getImageUrl()).into(holder.ivThumbnail);

        holder.btnBuy.setTextColor(product.isAddedInCart() ?
                ContextCompat.getColor(context, R.color.lime_500) :
                ContextCompat.getColor(context, R.color.colorPrimary));
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setAddedInCart(!product.isAddedInCart());
                holder.btnBuy.setTextColor(product.isAddedInCart() ?
                        ContextCompat.getColor(context, R.color.lime_500) :
                        ContextCompat.getColor(context, R.color.colorPrimary));
                listener.onBuyClicked(product);
            }
        });

        holder.ibtnFavorite.setImageResource(product.isFavorite() ?
                R.drawable.ic_favorite :
                R.drawable.ic_favorite_border);
        holder.ibtnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setFavorite(!product.isFavorite());
                holder.ibtnFavorite.setImageResource(product.isFavorite() ?
                        R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                listener.onFavoriteClicked(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    public interface ProductListener {
        void onFavoriteClicked(Product product);

        void onBuyClicked(Product product);
    }
}
