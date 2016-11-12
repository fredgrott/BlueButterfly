/*
Copyright (c) 2016 Arman Chatikyan (https://github.com/armcha/Vertical-Intro).
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
package com.github.shareme.bluebutterfly.verticalintro;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class VerticalIntroFragment extends Fragment {

    private static final String VERTICAL_INTRO_ITEM_BUNDLE_KEY = "verticalIntroItemBundleKey";

    public static VerticalIntroFragment newInstance(VerticalIntroItem verticalIntroItem) {
        Bundle args = new Bundle();
        args.putParcelable(VERTICAL_INTRO_ITEM_BUNDLE_KEY, verticalIntroItem);
        VerticalIntroFragment fragment = new VerticalIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vertical_intro_base_layout, container, false);
        VerticalIntroItem verticalIntroItem = getArguments().getParcelable(VERTICAL_INTRO_ITEM_BUNDLE_KEY);
        if (verticalIntroItem != null) {
            TextView text = (TextView) view.findViewById(R.id.text);
            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            text.setText(verticalIntroItem.getText());
            title.setText(verticalIntroItem.getTitle());
            image.setImageResource(verticalIntroItem.getImage());
            view.setBackgroundColor(ContextCompat.getColor(getActivity(), verticalIntroItem.getBackgroundColor()));

            if (verticalIntroItem.getCustomTypeFace() != null) {
                text.setTypeface(verticalIntroItem.getCustomTypeFace());
                title.setTypeface(verticalIntroItem.getCustomTypeFace());
            }
        } else {
            Log.e(VerticalIntro.TAG, "Something went wrong");
        }
        return view;
    }
}
