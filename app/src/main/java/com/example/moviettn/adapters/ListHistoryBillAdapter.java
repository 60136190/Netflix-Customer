package com.example.moviettn.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailBillActivity;
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.model.Favorite;
import com.example.moviettn.model.response.HistoryBill;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListHistoryBillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<HistoryBill> historyBills;

    public ListHistoryBillAdapter(Context mContext, List<HistoryBill> historyBills) {
        this.mContext = mContext;
        this.historyBills = historyBills;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_bill, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryBill history = historyBills.get(position);


        if (history.getFilm().getImageFilm() != null ){
            Picasso.with(mContext)
                    .load(history.getFilm().getImageFilm().getUrl()).fit().centerInside().into(((ItemViewHolder) holder).itemImage);
        }
        String strDate = history.getDatePurchase();
        ((ItemViewHolder)holder).tvPrice.setText(String.valueOf(history.getPrice() + " $"));
        ((ItemViewHolder) holder).tvDatePurchase.setText(strDate.substring(0,10));
        ((ItemViewHolder)holder).tvTitleFilm.setText(history.getFilm().getTitle());

        ((ItemViewHolder)holder).cardListBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailBillActivity.class);
                intent.putExtra("idFilm",history.getFilm().getId());
                intent.putExtra("price",String.valueOf(history.getPrice()));
                intent.putExtra("datePurchase",history.getDatePurchase());
                intent.putExtra("modeName",history.getModeOfPayment().getName());
                intent.putExtra("urlMode",history.getModeOfPayment().getImage().getUrl());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (historyBills != null) {
            return historyBills.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView tvTitleFilm, tvPrice, tvDatePurchase;
        CardView cardListBill;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.img_film);
            tvTitleFilm = itemView.findViewById(R.id.tv_title_film);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDatePurchase = itemView.findViewById(R.id.tv_date_purchase);
            cardListBill = itemView.findViewById(R.id.card_list_bill);
        }
    }

}
