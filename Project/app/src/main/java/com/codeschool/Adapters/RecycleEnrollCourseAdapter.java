package com.codeschool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Models.CoursesEnrolledModel;
import com.codeschool.project.R;
import com.codeschool.project.TopicsActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleEnrollCourseAdapter extends RecyclerView.Adapter<RecycleEnrollCourseAdapter.MyViewHolder>{

    Context context;
    CoursesEnrolledModel coursesEnrolledModel;

    public RecycleEnrollCourseAdapter(Context context,CoursesEnrolledModel coursesEnrolledModel){
        this.context = context;
        this.coursesEnrolledModel = coursesEnrolledModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.listitem_courses,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.courseName.setText(coursesEnrolledModel.getCoursesEnrolled().get(position));
        holder.courseInfo.setText(coursesEnrolledModel.getCoursesEnrolledInformation().get(position));
        Glide.with(context).load(coursesEnrolledModel.getCourseImage().get(position)).into(holder.courseImage);

        holder.startResumeCourse.setOnClickListener(v -> {
            Intent intent = new Intent(context, TopicsActivity.class);
            intent.putExtra("course",coursesEnrolledModel.getCoursesEnrolled().get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return coursesEnrolledModel.getCoursesEnrolled().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView courseName;
        private TextView courseInfo;
        private ImageView courseImage;
        private TextView startResumeCourse;

        public MyViewHolder(View itemView){
            super(itemView);

            startResumeCourse = itemView.findViewById(R.id.startResumeCourse);
            courseImage = itemView.findViewById(R.id.courseImage);
            courseInfo = itemView.findViewById(R.id.courseInfo);
            courseName = itemView.findViewById(R.id.courseName);
        }
    }

}
