package com.example.moviettn.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;

import com.example.moviettn.activities.TestCreateBillActivity;
import com.example.moviettn.model.response.ModeOfPayment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListModeOfPaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<ModeOfPayment> mModeOfPaymentList;

    public ListModeOfPaymentAdapter(Context mContext, List<ModeOfPayment> mModeOfPaymentList) {
        this.mContext = mContext;
        this.mModeOfPaymentList = mModeOfPaymentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mode_of_payment, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModeOfPayment modeOfPayment = mModeOfPaymentList.get(position);

        ((ItemViewHolder) holder).itemModeOfPayment.setText(modeOfPayment.getName());
        Picasso.with(mContext)
                .load(modeOfPayment.getImage().getUrl()).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemImgModeOfPayment);
        ((ItemViewHolder)holder).ctModeOfPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TestCreateBillActivity.class);
                intent.putExtra("id_mode",modeOfPayment.getId());
                intent.putExtra("name_mode", modeOfPayment.getName());
                intent.putExtra("url_mode",modeOfPayment.getImage().getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mModeOfPaymentList != null) {
            return mModeOfPaymentList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

         TextView itemModeOfPayment;
         ImageView itemImgModeOfPayment;
         ConstraintLayout ctModeOfPayment;


        public ItemViewHolder(View itemView) {
            super(itemView);
            itemModeOfPayment = itemView.findViewById(R.id.tv_title_mode_of_payment);
            itemImgModeOfPayment = itemView.findViewById(R.id.img_mode_of_payment);
            ctModeOfPayment = itemView.findViewById(R.id.ct_mode_of_payment);
        }
    }

}
