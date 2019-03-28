package com.example.myapplication;

import android.support.v4.app.Fragment;

/**
 * Created by Lenovo on 2018/6/10.
 */

public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
    Fragment fragment = null;
    switch (index) {
        case R.id.btn_day:
            fragment = new DayFragment();
            break;
        case R.id.btn_week:
            fragment = new WeekFragment();
            break;
        case R.id.btn_month:
            fragment = new MonthFragment();
            break;
        case R.id.btn_year:
            fragment = new YearFragment();
            break;
    }
    return fragment;
}
}
