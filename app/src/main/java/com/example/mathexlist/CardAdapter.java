package com.example.mathexlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CardAdapter extends ArrayAdapter<CardModel> {

    public CardAdapter(@NonNull Context context, int resource, @NonNull List<CardModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;

        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.statistics_card_view, parent, false);
        }

        CardModel cardModel = getItem(position);
        TextView cardTitle = listitemView.findViewById(R.id.card_title);
        TextView cardValue = listitemView.findViewById(R.id.card_value);

        cardTitle.setText(cardModel.getTitle());
        cardValue.setText(cardModel.getValue());


        return listitemView;
    }
}
