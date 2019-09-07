package io.yugoal.yu.view.home.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import io.yugoal.yu.model.CHANNEL;
import io.yugoal.yu.view.discory.DiscoryFragment;
import io.yugoal.yu.view.friend.FriendFragment;
import io.yugoal.yu.view.mine.MineFragment;
import io.yugoal.yu.view.video.VideoFragment;

/**
 * @author caoyu
 * date  2019/9/5
 * 首页适配器
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private CHANNEL[] mList;

    public HomePagerAdapter(FragmentManager fm, CHANNEL[] mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        int type = mList[position].getValue();
        switch (type) {
            case CHANNEL.MINE_ID:
                return MineFragment.newInstance();
            case CHANNEL.DISCORY_ID:
                return DiscoryFragment.newInstance();
            case CHANNEL.FRIEND_ID:
                return FriendFragment.newInstance();
            case CHANNEL.VIDEO_ID:
                return VideoFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.length;
    }
}
