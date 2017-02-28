package com.invizorys.evotest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invizorys.evotest.R;
import com.invizorys.evotest.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roma on 28.02.2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<Product> products;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle, tvStatus, tvPrice;

        MyViewHolder(View view) {
            super(view);
            ivThumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
        }
    }

    public CartAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.cart_item_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.tvTitle.setText(product.getName());
        holder.tvStatus.setText(R.string.in_stock);
        holder.tvPrice.setText(product.getPriceAndCurrency());

        Picasso.with(context).load(product.getImageUrl()).into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
