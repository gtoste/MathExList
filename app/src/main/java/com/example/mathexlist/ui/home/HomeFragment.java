package com.example.mathexlist.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mathexlist.Card.CardAdapter;
import com.example.mathexlist.Card.CardModel;
import com.example.mathexlist.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    String TAG = "TEST";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        ArrayList<CardModel> cardModelArrayList = new ArrayList<CardModel>();
        cardModelArrayList.add(new CardModel("Exercises Done", 0));
        cardModelArrayList.add(new CardModel("Exercises Left", 0));
        cardModelArrayList.add(new CardModel("Time Left", 0));
        cardModelArrayList.add(new CardModel("Percentage", 0));
        cardModelArrayList.add(new CardModel("Total Exercises Done", 0));
        CardAdapter cardAdapter = new CardAdapter(getContext(), 0, cardModelArrayList);
        binding.gridView.setAdapter(cardAdapter);

//        Toast.makeText(getContext(), "",Toast.LENGTH_LONG).show();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}