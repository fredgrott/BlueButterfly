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
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Chatikyan on 18.10.2016.
 */

class VerticalIntroPagerAdapter extends FragmentPagerAdapter {

    private List<VerticalIntroItem> verticalIntroItemList;

    VerticalIntroPagerAdapter(FragmentManager fm, List<VerticalIntroItem> verticalIntroItemList) {
        super(fm);
        this.verticalIntroItemList = verticalIntroItemList;
    }

    @Override
    public Fragment getItem(int position) {
        VerticalIntroItem verticalIntroItem = verticalIntroItemList.get(position);
        return VerticalIntroFragment.newInstance(verticalIntroItem);
    }

    @Override
    public int getCount() {
        return verticalIntroItemList.size();
    }
}
