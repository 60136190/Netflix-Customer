package com.example.moviettn.model.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmFollowOptionActivity;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Test> testList;

    public DetailAdapter(Context mContext, List<Test> testList) {
        this.mContext = mContext;
        this.testList = testList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail_test,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Test test = testList.get(position);
        ((ItemViewHolder)holder).tvName.setText(test.getName());
        ((ItemViewHolder)holder).tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailFilmFollowOptionActivity.class);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (testList != null){
            return testList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ItemViewHolder( View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_detail);
        }
    }

}
