/*
Copyright 2015 Marcin Korniluk 'Zielony'
Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */
package com.github.shareme.bluebutterfly.core.widget;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.shareme.bluebutterfly.core.CarbonContextWrapper;
import com.github.shareme.bluebutterfly.core.R;


/**
 * Created by Marcin on 2015-06-25.
 */
public class NavigationView extends RecyclerView {
    private Menu menu;

    public NavigationView(Context context) {
        super(context);
        initNavigationView();
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initNavigationView();
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initNavigationView();
    }

    private void initNavigationView() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void setMenu(int resId) {
        Menu menu = new MenuBuilder(new CarbonContextWrapper(getContext()));
        MenuInflater inflater = new MenuInflater(getContext());
        inflater.inflate(resId, menu);
        setMenu(menu);
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        if (getAdapter() == null) {
            setAdapter(new Adapter());
        }
        ((Adapter) getAdapter()).setItems(menu);
    }

    public Menu getMenu() {
        return menu;
    }

    public static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Menu items = null;

        public MenuItem getItem(int position) {
            return items.getItem(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.carbon_navigation_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tv.setText(items.getItem(position).getTitle());
            holder.iv.setImageDrawable(items.getItem(position).getIcon());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(Menu items) {
            this.items = items;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.carbon_itemText);
            iv = (ImageView) itemView.findViewById(R.id.carbon_itemIcon);
        }
    }
}
