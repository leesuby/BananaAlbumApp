package com.example.bananaalbum.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Test;

import java.util.List;

//Adapter for RecyclerView
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder>{
    private List<Test> mlistTest;

    public TestAdapter(List<Test> mlistTest) {
        this.mlistTest = mlistTest;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser,parent,false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Test test = mlistTest.get(position);
        if(test== null){
            return;
        }

        holder.imgAvatar.setImageResource(test.getImageAvatar());
        holder.tvName.setText(test.getName());
        holder.tvDes.setText(test.getDescription());
    }

    @Override
    public int getItemCount() {
        if(mlistTest!= null){
            return mlistTest.size();
        }
        return 0;
    }

    public class TestViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvDes;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar= itemView.findViewById(R.id.img_avatar);
            tvName= itemView.findViewById(R.id.tv_name);
            tvDes= itemView.findViewById(R.id.tv_des);
        }

    }
}
