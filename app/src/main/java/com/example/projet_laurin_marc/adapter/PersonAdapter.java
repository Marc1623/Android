package com.example.projet_laurin_marc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {
    private List<Person> persons = new ArrayList<>();
    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_ppl, parent, false);
        return new PersonHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        Person currentPerson = persons.get(position);
        holder.textViewAHV.setText(currentPerson.getAhv());
        holder.textViewFirstname.setText(currentPerson.getFirstname());
        holder.textViewLastname.setText(currentPerson.getLastname());
    }

    @Override
    public int getItemCount() {

        return persons.size();
    }

    public void setPersons(List<Person> persons){
        this.persons = persons;
        notifyDataSetChanged(); //you should not use it in the recyclerview
    }

    class PersonHolder extends RecyclerView.ViewHolder{
        private TextView textViewFirstname;
        private TextView textViewLastname;
        private TextView textViewAHV;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            textViewAHV = itemView.findViewById(R.id.ahv);
            textViewFirstname = itemView.findViewById(R.id.firstname);
            textViewLastname = itemView.findViewById(R.id.lastname);
        }
    }
}
