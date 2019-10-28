package io.yugoal.lib_pullalive;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import static android.os.Build.VERSION_CODES.N;

/**
 * @author caoyu
 * date  2019/10/25
 * 一个轻量的后台job service,利用空闲时间执行一些小事情，提高进程不被回收的概率
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AliveJobService extends JobService {
    private static final String TAG = AliveJobService.class.getName();


    private JobScheduler jobScheduler;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    Log.d(TAG, "pull alive.");
                    jobFinished((JobParameters) msg.obj, false);
                    break;
            }
        }
    };


    public static void start(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(context, AliveJobService.class);
            context.startService(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo info = initJobInfo(startId);
        if (jobScheduler.schedule(info) <= 0) {
            Log.d(TAG, "AliveJobService failed");
        } else {
            Log.d(TAG, "AliveJobService success");
        }
        return START_STICKY;
    }

    private JobInfo initJobInfo(int startId) {
        JobInfo.Builder builder = new JobInfo.Builder(startId, new ComponentName(getPackageName(), AliveJobService.class.getName()));
        if (Build.VERSION.SDK_INT >= N) {
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS); //执行的最小延迟时间
            builder.setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);  //执行的最长延时时间
            builder.setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS,
                    JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
        }
        builder.setPersisted(false);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        builder.setRequiresCharging(false);
        return builder.build();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        mHandler.sendMessage(Message.obtain(mHandler,1,params));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mHandler.sendEmptyMessage(1);
        return false;
    }
}
