package com.example.aplikasimahasiswa.repository;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasimahasiswa.Model.Vacancy;
import com.example.aplikasimahasiswa.R;

import java.util.ArrayList;

public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(Vacancy vacancy);
    }
    private final OnItemClickListener listener;
    private final ArrayList<Vacancy> VacancyList;

    public VacancyAdapter(ArrayList<Vacancy> threadList, OnItemClickListener listener) {
        this.VacancyList = threadList;
        this.listener = listener;
    }

    public class VacancyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
//        public TextView username;
//        public TextView description;
        public LinearLayout llnLayout;
        public VacancyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.vacancy_items_title);
            llnLayout = itemView.findViewById(R.id.vacancy_rl_layout);
        }
        public void bind(final Vacancy item, final OnItemClickListener listener) {
            title.setText(item.getVacancyName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @NonNull
    @Override
    public VacancyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacancy_item,parent,false);
        VacancyViewHolder vViewHolder = new VacancyViewHolder(view);



        return vViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VacancyViewHolder holder, int position) {
        Vacancy item = VacancyList.get(position);
        holder.bind(item,listener);
    }
    @Override
    public int getItemCount() {
        return VacancyList.size();
    }
}
