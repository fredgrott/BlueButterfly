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
package com.github.shareme.bluebutterfly.core.beta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.github.shareme.bluebutterfly.core.R;
import com.github.shareme.bluebutterfly.core.widget.LinearLayout;
import com.github.shareme.bluebutterfly.core.widget.Spinner;
import com.github.shareme.bluebutterfly.core.widget.TableView;
import com.github.shareme.bluebutterfly.core.widget.TextView;


/**
 * Created by Marcin on 2015-12-18.
 */
@SuppressWarnings("unused")
public class TableLayout extends LinearLayout {
    private TableView table;
    Toolbar toolbar;
    LinearLayout header;
    View footer;
    Spinner rowNumber;
    private TextView pageNumbers;

    public TableLayout(Context context) {
        super(context);
        initTableLayout();
    }

    public TableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTableLayout();
    }

    public TableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTableLayout();
    }

    private void initTableLayout() {
        View.inflate(getContext(), R.layout.carbon_tablelayout, this);
        setOrientation(VERTICAL);

        toolbar = (Toolbar) findViewById(R.id.carbon_tableToolbar);
        header = (LinearLayout) findViewById(R.id.carbon_tableHeader);
        table = (TableView) findViewById(R.id.carbon_table);
        footer = findViewById(R.id.carbon_tableFooter);
        rowNumber = (Spinner) findViewById(R.id.carbon_tableRowNumber);
        rowNumber.setItems(new String[]{"10", "20", "50"});
        pageNumbers = (TextView) findViewById(R.id.carbon_tablePageNumbers);
    }

    public TableView getTableView() {
        return table;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public View getHeader() {
        return header;
    }

    public View getFooter() {
        return footer;
    }

    @SuppressLint("SetTextI18n")
    public void setAdapter(TableView.Adapter adapter) {
        table.setAdapter(adapter);
        header.removeAllViews();
        for (int i = 0; i < adapter.getColumnCount(); i++) {
            View headerCell = View.inflate(getContext(), R.layout.carbon_tablelayout_header, null);
            TextView tv = (TextView) headerCell.findViewById(R.id.carbon_tableHeaderText);
            tv.setText(adapter.getColumnName(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, adapter.getColumnWeight(i));
            header.addView(headerCell, params);
        }
        rowNumber.setText("10");
        pageNumbers.setText("1-" + adapter.getItemCount() + " of " + adapter.getItemCount());
    }
}
