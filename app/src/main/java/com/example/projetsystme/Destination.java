package com.example.projetsystme;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * create an instance of this fragment.
 */
public class Destination extends Fragment {

    private Button les100,les200,les300,les400;
    int choice1,choice2;
    boolean b;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_destination, container, false);
        b=false;
        les100=view.findViewById(R.id.Les100);

        les200=view.findViewById(R.id.Les200);
        les300=view.findViewById(R.id.Les300);
        les400=view.findViewById(R.id.Les400);
        les100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b==false){
                les300.setVisibility(View.GONE);
                les400.setVisibility(View.GONE);
                les100.setText("Pair");
                les200.setText("Impair");
                choice1=1;
                b=true;
                }else{
                    choice2=2;
                    Intent intent = new Intent(getActivity(), Show.class);
                    intent.putExtra("key", String.valueOf(choice1));
                    intent.putExtra("key2", String.valueOf(choice2));
                    startActivity(intent);
                }
            }
        });
        les200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b==false){
                    les300.setVisibility(View.GONE);
                    les400.setVisibility(View.GONE);
                    les100.setText("Pair");
                    les200.setText("Impair");
                    choice1=2;
                    b=true;
                }else{
                    choice2=1;
                    Intent intent = new Intent(getActivity(), Show.class);
                    intent.putExtra("key", String.valueOf(choice1));
                    intent.putExtra("key2", String.valueOf(choice2));
                    startActivity(intent);
                }
            }
        });
        les300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                les300.setVisibility(View.GONE);
                les400.setVisibility(View.GONE);
                les100.setText("Pair");
                les200.setText("Impair");
                choice1=3;
            }
        });
        les400.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                les300.setVisibility(View.GONE);
                les400.setVisibility(View.GONE);
                les100.setText("Pair");
                les200.setText("Impair");
                choice1=4;
            }
        });
        return view;

    }
}