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
package com.github.shareme.bluebutterfly.core.shadow;

/**
 * Created by Marcin on 2014-11-19.
 */
public interface ShadowView {
    /**
     * Gets the elevation.
     *
     * @return the elevation value.
     */
    float getElevation();

    /**
     * Sets the elevation value. There are useful values of elevation defined in xml as carbon_elevationFlat, carbon_elevationLow, carbon_elevationMedium, carbon_elevationHigh, carbon_elevationMax
     *
     * @param elevation can be from range [0 - 25]
     */
    void setElevation(float elevation);

    float getTranslationZ();

    void setTranslationZ(float translationZ);

    ShadowShape getShadowShape();

    Shadow getShadow();

    void invalidateShadow();
}
