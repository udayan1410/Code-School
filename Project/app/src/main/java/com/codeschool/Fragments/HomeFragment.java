package com.codeschool.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeschool.Adapters.RecycleEnrollCourseAdapter;
import com.codeschool.Models.EnrollCourseModel;
import com.codeschool.project.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<EnrollCourseModel> enrollCourseModelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_home,container,false);

        initialize(v);

        return v;
    }

    public void initialize(View v){
        enrollCourseModelList.add(new EnrollCourseModel("Java","Info related to Java",R.mipmap.java_image));
        enrollCourseModelList.add(new EnrollCourseModel("Android","Info related to Android",R.drawable.android_image));
        enrollCourseModelList.add(new EnrollCourseModel("Python","Info related to Python",R.drawable.python_image));

        RecyclerView.Adapter adapter=new RecycleEnrollCourseAdapter(getContext(),enrollCourseModelList);

        recyclerView = v.findViewById(R.id.enrolledCoursesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


}
