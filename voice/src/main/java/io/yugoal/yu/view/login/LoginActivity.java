package io.yugoal.yu.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import io.yugoal.lib_common_ui.base.BaseActivity;
import io.yugoal.lib_network.okhttp.listener.DisposeDataListener;
import io.yugoal.yu.R;
import io.yugoal.yu.api.RequestCenter;
import io.yugoal.yu.model.user.User;
import io.yugoal.yu.utils.UserManager;

/**
 * @author caoyu
 * date  2019/9/17
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        findViewById(R.id.login_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestCenter.login(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        //处理正常逻辑
                        User user = (User) responseObj;
                        UserManager.getInstance().setUser(user);
                        EventBus.getDefault().post(new LoginEvent());
                        finish();
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Log.d(TAG, "onFailure: "+reasonObj.toString());
                    }
                });
            }
        });
    }
}
