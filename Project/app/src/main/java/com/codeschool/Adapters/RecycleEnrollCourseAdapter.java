package com.codeschool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Models.EnrollCourseModel;
import com.codeschool.project.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleEnrollCourseAdapter extends RecyclerView.Adapter<RecycleEnrollCourseAdapter.MyViewHolder>{

    Context context;
    List<EnrollCourseModel> enrollCourseModelList;

    public RecycleEnrollCourseAdapter(Context context,List<EnrollCourseModel> enrollCourseModelList){
        this.context = context;
        this.enrollCourseModelList = enrollCourseModelList;
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
        holder.courseName.setText(enrollCourseModelList.get(position).getCourseName());
        holder.courseInfo.setText(enrollCourseModelList.get(position).getCourseInfo());
        Glide.with(context).load(enrollCourseModelList.get(position).getCourseImage()).into(holder.courseImage);
    }

    @Override
    public int getItemCount() {
        return enrollCourseModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView courseName;
        private TextView courseInfo;
        private ImageView courseImage;

        public MyViewHolder(View itemView){
            super(itemView);

            courseImage = itemView.findViewById(R.id.courseImage);
            courseInfo = itemView.findViewById(R.id.courseInfo);
            courseName = itemView.findViewById(R.id.courseName);
        }
    }

}
