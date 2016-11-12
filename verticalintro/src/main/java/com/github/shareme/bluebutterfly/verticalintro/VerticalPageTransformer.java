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

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Chatikyan on 18.10.2016.
 */

class VerticalPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {

        View text = view.findViewById(R.id.text);
        View title = view.findViewById(R.id.title);
        View image = view.findViewById(R.id.image);

        if (position < -1) {
            view.setAlpha(0);

        } else if (position <= 1) {
            view.setAlpha(1);
            view.setTranslationX(view.getWidth() * -position);

            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);

            text.setAlpha(1.0F - Math.abs(position * 2));
            image.setAlpha(1.0F - Math.abs(position * 2));
            title.setAlpha(1.0F - Math.abs(position * 2));

            float imageHeight = image.getHeight();

            image.setTranslationY((position * imageHeight * 1.2f));
        } else {
            view.setAlpha(0);
        }
    }

}
