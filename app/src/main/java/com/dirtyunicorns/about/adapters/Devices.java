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
import com.dirtyunicorns.about.helpers.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Devices extends RecyclerView.Adapter<Devices.ViewHolder> {

    private List<Util> developers;
    Context context;
    Util devcards;

    public Devices(List<Util> developers) {
        this.developers = developers;
    }

    @Override
    public Devices.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View developersView = inflater.inflate(R.layout.fragment_devices, parent, false);
        return new ViewHolder(developersView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        devcards = developers.get(position);

        ImageView deviceImageOne = holder.deviceImageOne;
        Picasso.with(context).load(devcards.getUri().getDeviceImageOne()).into(deviceImageOne);

        ImageView deviceImageTwo = holder.deviceImageTwo;
        Picasso.with(context).load(devcards.getUri().getDeviceImageTwo()).into(deviceImageTwo);

        TextView deviceNameOne = holder.deviceNameOne;
        deviceNameOne.setText(devcards.getDeviceNameOne());

        TextView deviceNameTwo = holder.deviceNameTwo;
        deviceNameTwo.setText(devcards.getDeviceNameTwo());
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView deviceImageOne;
        public ImageView deviceImageTwo;

        public TextView deviceNameOne;
        public TextView deviceNameTwo;

        public ViewHolder(View itemView) {
            super(itemView);

            deviceImageOne = (ImageView) itemView.findViewById(R.id.device_one);
            deviceImageTwo = (ImageView) itemView.findViewById(R.id.device_two);

            deviceNameOne = (TextView) itemView.findViewById(R.id.device_label_one);
            deviceNameTwo = (TextView) itemView.findViewById(R.id.device_label_two);
        }
    }
}
