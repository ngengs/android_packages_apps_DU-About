/*
 * Copyright (C) 2017 The Dirty Unicorns Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dirtyunicorns.about.abstracts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dirtyunicorns.about.activities.SocialActivity;
import com.dirtyunicorns.about.helpers.ClickUtils;
import com.dirtyunicorns.about.helpers.Util;
import com.dirtyunicorns.about.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class Developers extends PreferenceFragmentCompat implements ClickUtils.OnItemClickListener {

    SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<Util> list = new ArrayList<>();
    com.dirtyunicorns.about.adapters.Developers adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        ClickUtils.addTo(recyclerView).setOnItemClickListener(this);
        adapter = new com.dirtyunicorns.about.adapters.Developers(list);

        assert recyclerView != null;
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);

        SwipeRefresh();
        downloadDevs();

        return rootView;
    }

    public abstract Call<List<Util>> getDevsCall();

    public void downloadDevs() {

        list.clear();
        adapter.notifyDataSetChanged();
        showRefresh();

        getDevsCall().enqueue(new Callback<java.util.List<Util>>() {
            @Override
            public void onResponse(Call<List<Util>> call, Response<List<Util>> response) {

                if (!response.isSuccessful()) {
                    onFailure(null, null);
                } else {
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    hideRefresh();
                }
            }

            @Override
            public void onFailure(Call<List<Util>> call, Throwable t) {
                hideRefresh();
            }
        });
    }

    private void showRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void hideRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void SwipeRefresh() {
        assert mSwipeRefreshLayout != null;
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.red,
                R.color.blue,
                R.color.yellow,
                R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadDevs();
            }
        });
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

        Util uri = list.get(position);

        Intent intent = new Intent(getActivity(), SocialActivity.class);
        intent.putExtra("social", uri);
        startActivity(intent);
    }
}
