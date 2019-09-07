package io.yugoal.lib_common_ui.base;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import io.yugoal.lib_common_ui.utils.StatusBarUtil;

/**
 * @author caoyu
 * date  2019/9/5
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this);
    }

}
