package com.example.lenovo.myapplication;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2018/7/23/023.
 */

public class ChangeFragment {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.rb_tijian:
                fragment = new TijianFragment();
                break;
            case R.id.rb_analyze:
                fragment = new AnalyzeFragment();
                break;
            case R.id.rb_zixun:
                fragment = new ZixunFragment();
                break;
            case R.id.rb_my:
                fragment = new MyFragment();
                break;
        }
        return fragment;
    }
}
