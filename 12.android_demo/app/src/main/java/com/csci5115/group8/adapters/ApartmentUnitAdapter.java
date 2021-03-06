package com.csci5115.group8.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csci5115.group8.R;
import com.csci5115.group8.data.apartment.ApartmentUnit;

import java.util.List;

public class ApartmentUnitAdapter extends RecyclerView.Adapter<ApartmentUnitAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View parent;
        TextView unitNumber;
        TextView numBedrooms;
        TextView numBathrooms;
        TextView areaInSqFt;
        TextView isLeased;
        Button deleteUnit;

        ViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
            unitNumber = itemView.findViewById(R.id.unitNumberHolder);
            numBedrooms = itemView.findViewById(R.id.numBedroomsHolder);
            numBathrooms = itemView.findViewById(R.id.numBathroomsHolder);
            areaInSqFt = itemView.findViewById(R.id.areaInSqFtHolder);
            isLeased = itemView.findViewById(R.id.isLeasedHolder);
            deleteUnit = itemView.findViewById(R.id.deleteUnit);
            deleteUnit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    ApartmentUnit getItem(int id) {
        return data.get(id);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private List<ApartmentUnit> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    public ApartmentUnitAdapter(Context context, List<ApartmentUnit> data, ItemClickListener itemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_create_unit_in_apartment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ApartmentUnit apartmentUnit = data.get(position);
        holder.unitNumber.setText("# " + apartmentUnit.unitNumber);
        holder.numBedrooms.setText(apartmentUnit.numBedrooms + "Bed");
        holder.numBathrooms.setText(apartmentUnit.numBathrooms + "Bath");
        holder.areaInSqFt.setText(apartmentUnit.areaInSqFt + "SqFt");
        holder.isLeased.setText(apartmentUnit.isLeased ? "Leased" : "Available");
        holder.parent.setBackgroundColor(apartmentUnit.isLeased ? Color.rgb(128, 0, 0) : Color.rgb(0, 128, 0));
        if (itemClickListener == null) {
            holder.deleteUnit.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}