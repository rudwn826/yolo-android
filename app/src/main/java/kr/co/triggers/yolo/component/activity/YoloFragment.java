package kr.co.triggers.yolo.component.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import kr.co.triggers.yolo.inject.component.AppComponent;

public class YoloFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public AppComponent getAppComponent() {

        Activity activity = getActivity();

        if (activity == null || !(activity instanceof YoloView))
            return null;

        return ((YoloView) activity).getAppComponent();
    }
}
