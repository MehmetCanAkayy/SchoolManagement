package com.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onur.easyspeakdemo.R;

import agency.tango.materialintroscreen.SlideFragment;

public class CustomSlide2 extends SlideFragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom_slide2, container, false);
        return view;

    }


    @Override
    public int backgroundColor() {

        return R.color.color_4CAF50;
    }

    @Override
    public int buttonsColor() {
        return R.color.custom_slide_buttons;
    }





}



