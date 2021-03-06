package com.example.myapplication.Recommend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

public class RecommendFirstActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> foodTags;
    private Spinner foodSpinner;
    private FoodSpinnerAdapter foodAdapter;
    private int selectedTagPosition;
    Button searchBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_firstpage);

        initVariables();
        initTags();
        initViews();
        initListener();
    }

    private void initVariables() {
        foodTags = new ArrayList<>();
    }

    private void initTags() {
        final String[] foods = {"전체", "한식", "중식", "일식", "양식", "후식", "기타"};
        int sz = foods.length;

        foodTags.addAll(Arrays.asList(foods).subList(0, sz));
    }

    private void initViews() {
        foodAdapter = new FoodSpinnerAdapter(this, foodTags);

        foodSpinner = findViewById(R.id.foodSpinner);
        foodSpinner.setAdapter(foodAdapter);

        searchBtn = findViewById(R.id.searchBtn);
    }

    private void initListener() {
        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTagPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int min = 0, max = 100;
        Intent intent = new Intent(this, RecommendSecondActivity.class);
        intent.putExtra("min", min)
                .putExtra("max", max)
                .putExtra("selectedTag", selectedTagPosition-1);

        startActivity(intent);
    }
}
