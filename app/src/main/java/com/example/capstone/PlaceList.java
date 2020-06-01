package com.example.capstone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceList extends Fragment // Fragment 클래스를 상속받아야한다
{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_placelist, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.placelist_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext()); //리스트뷰를 띄워준다
        RecyclerViewAdapter myRecyclerViewAdapter = new RecyclerViewAdapter(10,1,"상호명","주소","카테고리","전화번호");
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myRecyclerViewAdapter);

        //이것도 하단바마냥 수정하기
        Button button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext()); //리스트뷰를 띄워준다
                RecyclerViewAdapter myRecyclerViewAdapter = new RecyclerViewAdapter(10,1,"상호명","주소","카테고리","전화번호");
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myRecyclerViewAdapter);
            }
        });
        Button button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext()); //리스트뷰를 띄워준다
                RecyclerViewAdapter myRecyclerViewAdapter = new RecyclerViewAdapter(100,1,"상호명","주소","카테고리","전화번호");
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myRecyclerViewAdapter);
            }
        });


        return view;
    }
}
