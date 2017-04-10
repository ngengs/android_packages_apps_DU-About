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

package com.dirtyunicorns.about.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dirtyunicorns.about.R;
import com.dirtyunicorns.about.helpers.CircleTransform;
import com.dirtyunicorns.about.helpers.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Developers extends RecyclerView.Adapter<Developers.ViewHolder> {

    private List<Util> developers;
    Context context;
    Util devcards;

    public Developers(List<Util> developers) {
        this.developers = developers;
    }

    @Override
    public Developers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View developersView = inflater.inflate(R.layout.fragment_developers, parent, false);
        return new ViewHolder(developersView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        devcards = developers.get(position);

        ImageView cb = holder.background;
        Picasso.with(context).load(devcards.getUri().getCardBackground()).into(cb);

        ImageView ca = holder.avatar;
        Picasso.with(context).load(devcards.getAvatar().getCardAvatar()).transform(new CircleTransform()).into(ca);

        TextView dev_name = holder.dev_name;
        dev_name.setText(devcards.getDevName());

        TextView dev_title = holder.dev_title;
        dev_title.setText(devcards.getDevTitle());

        TextView dev_devices = holder.dev_devices;
        dev_devices.setText(devcards.getDevDevices());
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView background;
        public ImageView avatar;
        public TextView dev_name;
        public TextView dev_title;
        public TextView dev_devices;

        public ViewHolder(View itemView) {
            super(itemView);

            background = (ImageView) itemView.findViewById(R.id.image);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_image);

            dev_name = (TextView) itemView.findViewById(R.id.dev_name);
            dev_title = (TextView) itemView.findViewById(R.id.dev_title);
            dev_devices = (TextView) itemView.findViewById(R.id.dev_devices);
        }
    }
}
