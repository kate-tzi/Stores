package com.one.example.katerina.stores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/*** Created by cathr on 24/6/2017. ***/


public class StoresFragment extends Fragment {
    StoresAdapter storesAdapter;

    public StoresFragment() {    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchStores();
    }

    private void fetchStores(){
        FetchStoresTask fetchStoresTask = new FetchStoresTask(storesAdapter);
        fetchStoresTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        storesAdapter = new StoresAdapter(getActivity(),new ArrayList<Store>());
        ListView storesListView = (ListView)rootView.findViewById(R.id.stores_list);
        storesListView.setAdapter(storesAdapter);

        return rootView;
    }








}