package com.github.shareme.bluebutterfly.core.widget;

/**
 * Created by Marcin on 2016-07-28.
 */
@SuppressWarnings("unused")
public interface InputView {
    boolean isValid();

    void validate();

    void addOnValidateListener(OnValidateListener listener);

    void removeOnValidateListener(OnValidateListener listener);

    void clearOnValidateListeners();
}
