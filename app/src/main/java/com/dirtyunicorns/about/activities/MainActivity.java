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

package com.dirtyunicorns.about.activities;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dirtyunicorns.about.R;
import com.dirtyunicorns.about.fragments.Developers;
import com.dirtyunicorns.about.fragments.Devices;
import com.dirtyunicorns.about.fragments.GeneralInfo;
import com.dirtyunicorns.about.helpers.Network;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    Drawer drawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.toolbar_title_color, null));
            toolbar.setSubtitle(getString(R.string.general_info_title));
            toolbar.setSubtitleTextAppearance(this, R.style.SubTitleText);
        }

        if (toolbar != null) drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSelectedItemByPosition(1)
                .withHeader(R.layout.nav_drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.info)).withTextColor(getResources().getColor(R.color.nav_drawer_text, null)).withSelectedColor(getResources().getColor(R.color.nav_drawer_selector, null)).withSelectedTextColor(getResources().getColor(R.color.nav_drawer_selected_text, null)).withIcon(R.drawable.general_info).withIdentifier(1).withSelectable(true).withIconTintingEnabled(true).withSelectedIconColorRes(R.color.nav_drawer_icon_tint_color).withIconColorRes(R.color.nav_drawer_icon_color),
                        new PrimaryDrawerItem().withName(getString(R.string.developers_title)).withTextColor(getResources().getColor(R.color.nav_drawer_text, null)).withSelectedColor(getResources().getColor(R.color.nav_drawer_selector, null)).withSelectedTextColor(getResources().getColor(R.color.nav_drawer_selected_text, null)).withIcon(R.drawable.developers).withIdentifier(2).withSelectable(true).withIconTintingEnabled(true).withSelectedIconColorRes(R.color.nav_drawer_icon_tint_color).withIconColorRes(R.color.nav_drawer_icon_color),
                        new PrimaryDrawerItem().withName(getString(R.string.device_title)).withTextColor(getResources().getColor(R.color.nav_drawer_text, null)).withSelectedColor(getResources().getColor(R.color.nav_drawer_selector, null)).withSelectedTextColor(getResources().getColor(R.color.nav_drawer_selected_text, null)).withIcon(R.drawable.devices).withIdentifier(3).withSelectable(true).withIconTintingEnabled(true).withSelectedIconColorRes(R.color.nav_drawer_icon_tint_color).withIconColorRes(R.color.nav_drawer_icon_color),
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            boolean isConnected = Network.isConnected(MainActivity.this);
                            toolbar.setSubtitleTextAppearance(getApplicationContext(), R.style.SubTitleText);
                            if (drawerItem.getIdentifier() == 1) {
                                changeFragment(new GeneralInfo());
                                toolbar.setSubtitle(getString(R.string.general_info_title));
                            } else if (drawerItem.getIdentifier() == 2) {
                                if (isConnected) {
                                    changeFragment(new Developers());
                                    toolbar.setSubtitle(getString(R.string.developers_title));
                                } else {
                                    snackBar();
                                }
                            } else if (drawerItem.getIdentifier() == 3) {
                                if (isConnected) {
                                    changeFragment(new Devices());
                                    toolbar.setSubtitle(getString(R.string.device_title));
                                } else {
                                    snackBar();
                                }
                            }
                        }
                        return false;
                    }
                }).withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .build();

        if (drawer != null) {
            drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        }

        Fragment fragment = new GeneralInfo();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, fragment);
        transaction.commit();
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.getCurrentSelection() != 1) {
            drawer.setSelection(1);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.hide_app_icon).setChecked(!isLauncherIconEnabled());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hide_app_icon:
                boolean checked = item.isChecked();
                item.setChecked(!checked);
                setLauncherIconEnabled(checked);
                break;
        }
        return true;
    }

    public void setLauncherIconEnabled(boolean enabled) {
        int newState;
        PackageManager pm = getPackageManager();
        if (enabled) {
            newState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        } else {
            newState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        }
        pm.setComponentEnabledSetting(new ComponentName(this, LauncherActivity.class), newState, PackageManager.DONT_KILL_APP);
    }

    public boolean isLauncherIconEnabled() {
        PackageManager pm = getPackageManager();
        return (pm.getComponentEnabledSetting(new ComponentName(this, LauncherActivity.class)) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    public void snackBar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.fragment),
                R.string.no_connection, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}