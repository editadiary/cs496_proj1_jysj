package com.example.myapplication;

import static com.example.myapplication.Common.goIntent;
import static com.example.myapplication.Common.stack_page;
import static com.example.myapplication.Common.currentTab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.Contact.ContactActivity;
import com.example.myapplication.Gallery.GalleryMainActivity;
import com.example.myapplication.Recommend.RecommendSecondActivity;

public class TabFragment extends Fragment implements View.OnClickListener {
    private ImageView ContactBtn, GalleryBtn, HeartBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, container, false);

        ContactBtn = view.findViewById(R.id.ic_person_square);
        ContactBtn.setOnClickListener(this);
        if(currentTab == 1) ContactBtn.setBackgroundResource(R.color.white);
        else ContactBtn.setBackgroundResource(R.color.transparent);

        GalleryBtn = view.findViewById(R.id.ic_gallery);
        GalleryBtn.setOnClickListener(this);
        if(currentTab == 2) GalleryBtn.setBackgroundResource(R.color.white);
        else GalleryBtn.setBackgroundResource(R.color.transparent);

        HeartBtn = view.findViewById(R.id.ic_heart);
        HeartBtn.setOnClickListener(this);
        if(currentTab == 3) HeartBtn.setBackgroundResource(R.color.white);
        else HeartBtn.setBackgroundResource(R.color.transparent);

        return view;
    }

    public void onClick(View view) {
        int id = view.getId();
        boolean flag = true;
        Intent intent = null;
        FragmentActivity activity = getActivity();
        if(activity == null) return;

        if(id == R.id.ic_person_square && stack_page.peek() != 1) {
            currentTab = 1;
            goIntent(1); flag = false;
            intent = new Intent(activity, ContactActivity.class);
        }

        if (id == R.id.ic_gallery && stack_page.peek() != 2) {
            currentTab = 2;
            goIntent(2); flag = false;
            intent = new Intent(activity, GalleryMainActivity.class);
        }

        if(id == R.id.ic_heart && stack_page.peek() != 3) {
            currentTab = 3;
            goIntent(3); flag = false;
            intent = new Intent(activity, RecommendSecondActivity.class);
        }

        if(!flag){
            startActivity(intent);
        }
    }
}
