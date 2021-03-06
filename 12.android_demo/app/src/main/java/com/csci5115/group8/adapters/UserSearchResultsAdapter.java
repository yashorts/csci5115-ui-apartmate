package com.csci5115.group8.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csci5115.group8.R;
import com.csci5115.group8.data.user.User;
import com.csci5115.group8.data.user.UserSRL;

import java.util.List;

public class UserSearchResultsAdapter extends RecyclerView.Adapter<UserSearchResultsAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView gender;
        TextView age;
        TextView commonAmenities;
        TextView commonAmenitiesHint;


        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameHolder);
            age = itemView.findViewById(R.id.ageHolder);
            gender = itemView.findViewById(R.id.genderHolder);
            commonAmenities = itemView.findViewById(R.id.commonAmenitiesHolder);
            commonAmenitiesHint = itemView.findViewById(R.id.commonAmenitiesHintHolder);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    private List<User> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    public UserSearchResultsAdapter(Context context, List<User> data, ItemClickListener itemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.itemClickListener = itemClickListener;
    }

    User getItem(int id) {
        return data.get(id);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_view_user_search_result, parent, false);
        return new ViewHolder(view);
    }

    private String truncate(String str, int maxLen) {
        if (str.length() > maxLen) {
            return str.substring(0, maxLen) + "...";
        } else {
            return str;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = data.get(position);
        holder.name.setText(truncate(user.name, 15));
        holder.name.setVisibility(UserSRL.nameVisible ? View.VISIBLE : View.GONE);
        holder.age.setText(truncate(java.lang.Integer.toString(user.age), 15));
        holder.age.setVisibility(UserSRL.ageVisible ? View.VISIBLE : View.GONE);
        holder.gender.setText(truncate(user.gender, 15));
        holder.gender.setVisibility(UserSRL.genderVisible ? View.VISIBLE : View.GONE);
        holder.commonAmenities.setText(truncate(
                "Max budget: " + java.lang.Integer.toString(user.maxBudget)
                        + "\nSmoke?     " + yesOrNo(user.preferences.doesSmoke)
                        + "              Drugs Okay?    " + yesOrNo(user.preferences.drugsOkay)
                        + "\nHas pets?  " + yesOrNo(user.preferences.hasPets)
                        + "            Parties Okay? " + yesOrNo(user.preferences.partiesOkay)
                        + "\nCan cook? " + yesOrNo(user.preferences.canCook)
                        + "            Has car?           " + yesOrNo(user.preferences.hasCar)
                        + "\nNeeds private bedroom? " + yesOrNo(user.preferences.needsPrivateBedroom)
                        + "\nNative Language:                " + user.nativeLanguage, 500));
        holder.commonAmenities.setVisibility(UserSRL.detailsVisible ? View.VISIBLE : View.GONE);
        holder.commonAmenitiesHint.setVisibility(UserSRL.detailsVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String yesOrNo(boolean a) {
        if (a) return " Yes";
        else return " No";
    }
}