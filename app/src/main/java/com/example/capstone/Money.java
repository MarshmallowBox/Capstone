package com.example.capstone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Money extends Fragment // Fragment 클래스를 상속받아야한다
{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_money, container, false);

        final EditText editText = view.findViewById(R.id.editText);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int m = Integer.parseInt(String.valueOf(editText.getText()));
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.money_recyclerview);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext()); //리스트뷰를 띄워준다
                RecyclerViewAdapter myRecyclerViewAdapter = new RecyclerViewAdapter(10,1,"상호명","주소","카테고리","전화번호");
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myRecyclerViewAdapter);

            }
        });

        return view;
    }
}
