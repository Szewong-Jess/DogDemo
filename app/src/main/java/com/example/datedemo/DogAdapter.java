package com.example.datedemo;

import android.os.Build;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datedemo.databinding.LayoutDogitemBinding;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {

    //How ViewBinding works in recycle view
    List<Dog> DogList;
    OnItemClickListener onItemClickListener;

    public DogAdapter(List<Dog> dogList, OnItemClickListener onItemClickListener) {
        DogList = dogList;
        this.onItemClickListener = onItemClickListener;
    }

    public DogAdapter(List<Dog> dogList) {
        DogList = dogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate external layout use view binding,
        LayoutDogitemBinding binding = LayoutDogitemBinding.
                inflate(LayoutInflater.
                from(parent.getContext()),
                parent,
                false);

        // create holder obj
        DogViewHolder holder = new DogViewHolder(binding);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

        //return holder obj
        return holder;


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        holder.binding.txtViewId.setText(
                String.valueOf(
                        DogList.get(position).getId())
        );
        holder.binding.txtViewBreed.setText(
                DogList.get(position).getDogBreed()
        );
        holder.binding.txtViewName.setText(
                DogList.get(position).getDogName()
        );
        holder.binding.imgViewDogPic.setImageResource(
                DogList.get(position).getDogPicDrawable()
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dobStr = formatter.format(DogList.get(position).getDogDob());
        holder.binding.txtViewDOB.setText(dobStr);
    }

    @Override
    public int getItemCount() {
        return DogList.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        //Declare binding as a data field for
        //ViewHolder class
        //TextView txtViewId;  you do not need this if you have view binding**

        LayoutDogitemBinding binding;

        //change the view to binding
        public DogViewHolder(@NonNull LayoutDogitemBinding itemBinding) {


            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }

    //set onClick pass it to MainActivity
    public interface OnItemClickListener{
        public  void onItemClick(int i);
    }

}
