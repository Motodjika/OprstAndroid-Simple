package com.tyaa.OPRST.Share;

import android.support.v4.app.Fragment;

import com.tyaa.OPRST.R;
import com.tyaa.OPRST.SingleFragmentActivity;

/**
 * Класс активности экрана создания ссылки на страницу события в "социальных сетях"
 */
public class ShareActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ShareFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
}
