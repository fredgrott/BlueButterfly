ActionBarProgress
=================

# Usage

```xml
<android.support.design.widget.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <ProgressBar
            android:id="@+id/progress"
            style="?android:attr/progressBarStyleHorizontal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom"
            android:indeterminateDuration="2000"
            android:maxHeight="3dp"
            android:minHeight="3dp" />

    </FrameLayout>

</android.support.design.widget.AppBarLayout>
```

```java
progressBar.setProgressDrawable(MaterialProgressDrawable.create(context));
progressBar.setIndeterminateDrawable(MaterialIndeterminateProgressDrawable.create(context));
```
