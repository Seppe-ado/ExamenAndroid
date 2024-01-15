package com.example.examenandroid.Adapters;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenandroid.MainActivity;
import com.example.examenandroid.R;
import com.example.examenandroid.model.CountriesInfo;
import com.example.examenandroid.viewmodel.CountriesViewModel;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    class CountriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView textView;

        public CountriesViewHolder(@NonNull View view){
            super(view);
            textView= view.findViewById(R.id.textViewCountryName);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
               TextView info= view.findViewById(R.id.textViewCountryName);
               String name= info.getText().toString();
               //MainActivity.goDetails(name);
                MainActivity.getInstance().goDetails(name);
               //Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                // intent.putExtra("details",name);
               //startActivity(intent);
            }
        }

    }
    private final List<CountriesInfo> countriesNameList;

    public CountriesAdapter(List<CountriesInfo> data){
        this.countriesNameList= data;

    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_list_item,parent,false);
       // return new CountriesViewHolder(view);

        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.countries_list_item,parent,false);
        CountriesViewHolder viewHolder= new CountriesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CountriesAdapter.CountriesViewHolder holder, int position) {
        String name= countriesNameList.get(position).getName();
        TextView textView= holder.textView;
        textView.setText(name);

        //CountriesViewHolder.textView.setText(name);

    }

    @Override
    public int getItemCount() {

        return countriesNameList.size();
    }
}
