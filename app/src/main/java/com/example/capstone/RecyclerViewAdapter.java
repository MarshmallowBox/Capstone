package com.example.capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FranchiseDTO> memberDTOs = new ArrayList<>();



    public RecyclerViewAdapter(int count,int id, String name, String address, String category, String tel) {
        for (int i = 0; i < count; i++) {
        memberDTOs.add(new FranchiseDTO(id, name, address, category, tel));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //XML을 가져오는 부분
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //데이터를 넣어주는 부분

        ((RowCell) holder).imageView.setImageResource(R.drawable.appicon64);
        ((RowCell) holder).name.setText(memberDTOs.get(position).name);
        ((RowCell) holder).address.setText(memberDTOs.get(position).address);
        ((RowCell) holder).category.setText(memberDTOs.get(position).category);
        ((RowCell) holder).tel.setText(memberDTOs.get(position).tel);

    }

    @Override
    public int getItemCount() {

        //카운터
        return memberDTOs.size();
    }

    //소스코드 절약해주는 부분
    private static class RowCell extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView address;
        TextView category;
        TextView tel;

        public RowCell(View view) {
            super(view);
            imageView = view.findViewById(R.id.recyclerview_image);
            name = (TextView) view.findViewById(R.id.recyclerview_item_name);
            address = (TextView) view.findViewById(R.id.recyclerview_item_address);
            category = (TextView) view.findViewById(R.id.recyclerview_item_category);
            tel = (TextView) view.findViewById(R.id.recyclerview_item_tel);
        }
    }
}

class FranchiseDTO {

    public int id;
    public String name;
    public String address;
    public String category;
    public String tel;

    public FranchiseDTO(int id, String name, String address, String category, String tel) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
        this.tel = tel;
    }
}
