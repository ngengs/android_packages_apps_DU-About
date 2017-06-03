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

    private List<Util> devices;
    Context context;
    Util deviceCards;

    public Devices(List<Util> device) {
        this.devices = device;
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

        deviceCards = devices.get(position);

        ImageView deviceImageOne = holder.deviceImage;
        Picasso.with(context).load(deviceCards.getUri().getDeviceImage()).into(deviceImageOne);

        TextView deviceNameOne = holder.deviceName;
        deviceNameOne.setText(deviceCards.getDeviceName());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView deviceImage;

        public TextView deviceName;

        public ViewHolder(View itemView) {
            super(itemView);

            deviceImage = (ImageView) itemView.findViewById(R.id.device);

            deviceName = (TextView) itemView.findViewById(R.id.device_label);
        }
    }
}
