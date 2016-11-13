Blaze
=====

# Usage

## MotionView

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:blaze="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.intrusoft.blaze.app.MainActivity">

    <com.github.shareme.bluebutterfly.blaze.MotionView
        android:id="@+id/motion_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        blaze:src="@drawable/your_image"
        blaze:translation_factor="3" />
</FrameLayout>
```
```xml
blaze:translation_factor 
<!-- defines the animation speed-->
```

```java
MotionView motionView = (MotionView) findViewById(R.id.motion_view);

// to set image from resources        
motionView.setImageResource(R.drawable.your_image);
                
// to set bitmap
motionView.setImageBitmap(yourBitmap);
        
// to set the animation speed
motionView.setTranslationFactor(4);    
```

## ZoomView

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:blaze="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.intrusoft.blaze.app.MainActivity">

    <com.github.shareme.bluebutterfly.blaze.ZoomView
        android:id="@+id/zoom_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        blaze:src="@drawable/place_holder"
        blaze:translation_factor="0.4" />
</FrameLayout>
```

```xml
blaze:translation_factor 
<!-- defines the animation speed-->
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:blaze="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.intrusoft.blaze.app.MainActivity">

    <com.github.shareme.bluebutterfly.blaze.ZoomView
        android:id="@+id/zoom_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        blaze:src="@drawable/place_holder"
        blaze:translation_factor="0.4" />
</FrameLayout>
```

```xml
blaze:translation_factor 
<!-- defines the animation speed-->
```
