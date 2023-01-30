package com.example.projetsystme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Button buttonH,buttonD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_home,container,false);
        buttonD=rootView.findViewById(R.id.Choose);

        buttonH=rootView.findViewById(R.id.Help);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr=getChildFragmentManager().beginTransaction();
                fr.replace(R.id.fragemntcontainer,new Destination());
                fr.commit();
                buttonH.setVisibility(View.GONE);
                buttonD.setVisibility(View.GONE);
            }
        });
        return rootView;
    }
}