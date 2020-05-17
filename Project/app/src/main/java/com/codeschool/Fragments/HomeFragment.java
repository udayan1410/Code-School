package com.codeschool.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeschool.Adapters.RecycleEnrollCourseAdapter;
import com.codeschool.Adapters.RecycleFetchTechNewsAdapter;
import com.codeschool.Models.CoursesEnrolledModel;
import com.codeschool.Models.TechNewsModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.codeschool.project.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView,recyclerViewNews;
    CoursesEnrolledModel coursesEnrolledModel;
    NetworkCient.ServerCommunicator communicator;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_home,container,false);

        initialize(v);
        fetchNews();

        return v;
    }

    public void initialize(View v){
        //Preparing dialog
        dialog = Misc.createDialog(getContext(),R.layout.dialog_progress,"Fetching Latest News");
        dialog.show();

        //Get Enrolled courses from shared prefs
        String coursesEnrolledModelString =Misc.getStringFromSharedPref(getContext(),Constants.COURSEENROLLED,Constants.COURSEENROLLED);
        if(coursesEnrolledModelString.equalsIgnoreCase("Default Value"))
            coursesEnrolledModel = new CoursesEnrolledModel();
        else
            coursesEnrolledModel = new Gson().fromJson(coursesEnrolledModelString,CoursesEnrolledModel.class);

        RecyclerView.Adapter adapter=new RecycleEnrollCourseAdapter(getContext(),coursesEnrolledModel);

        recyclerView = v.findViewById(R.id.enrolledCoursesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);


        //Displaying news data
        recyclerViewNews = v.findViewById(R.id.techNewsRecyclerView);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));


        //Get Courses enrolled
    }



    public void fetchNews(){
        communicator = NetworkCient.getClient(Constants.TECH_NEWS_BASE_URL);
        Call<TechNewsModel> call =  communicator.getTechNews();

        call.enqueue(new Callback<TechNewsModel>() {
            @Override
            public void onResponse(Call<TechNewsModel> call, Response<TechNewsModel> response) {
                dialog.dismiss();

                //Set data to recycler view
                TechNewsModel techNewsModel = response.body();
                RecyclerView.Adapter newsAdapter = new RecycleFetchTechNewsAdapter(getContext(),techNewsModel);
                recyclerViewNews.setAdapter(newsAdapter);
            }

            @Override
            public void onFailure(Call<TechNewsModel> call, Throwable t) {
                dialog.dismiss();
                Misc.showToast(getContext(),"Couldn't Fetch news right now");
            }
        });
    }

}
