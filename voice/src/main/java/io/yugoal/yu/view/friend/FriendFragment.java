package io.yugoal.yu.view.friend;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.yugoal.lib_common_ui.recyclerview.wrapper.LoadMoreWrapper;
import io.yugoal.lib_network.okhttp.listener.DisposeDataListener;
import io.yugoal.yu.R;
import io.yugoal.yu.api.RequestCenter;
import io.yugoal.yu.model.friend.BaseFriendModel;
import io.yugoal.yu.model.friend.FriendBodyValue;
import io.yugoal.yu.view.discory.DiscoryFragment;
import io.yugoal.yu.view.friend.adapter.FriendRecyclerAdapter;

/**
 * @author caoyu
 * date  2019/9/6
 */
public class FriendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    /*
     * UI
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FriendRecyclerAdapter mAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    /*
     * data
     */
    private BaseFriendModel mRecommandData;
    private List<FriendBodyValue> mDatas = new ArrayList<>();

    public static Fragment newInstance() {
        return new DiscoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend_layout, null);
        mSwipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_red_light));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //发请求更新UI
        requestData();
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    //请求数据
    private void requestData() {

    }

    //更新UI
    private void updateView() {
//        mSwipeRefreshLayout.setRefreshing(false); //停止刷新
//        mDatas = mRecommandData.data.list;
//        mAdapter = new FriendRecyclerAdapter(mContext, mDatas);
//        //加载更多初始化
//        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
//        mLoadMoreWrapper.setOnLoadMoreListener(this);
//        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }

}
