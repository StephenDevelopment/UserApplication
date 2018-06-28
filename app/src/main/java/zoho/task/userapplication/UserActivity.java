package zoho.task.userapplication;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import zoho.task.userapplication.base.BaseCompatActivity;
import zoho.task.userapplication.interfaces.UserView;
import zoho.task.userapplication.listeners.LoadMoreDataScrollListener;
import zoho.task.userapplication.listeners.RecyclerTouchListener;
import zoho.task.userapplication.models.UserItemsVO;
import zoho.task.userapplication.presenter.UserPresenter;
import zoho.task.userapplication.ui.UserRefreshableListLayout;
import zoho.task.userapplication.utils.WindowUtils;

import static zoho.task.userapplication.base.UserApplication.getApp;

public class UserActivity extends BaseCompatActivity<UserPresenter> implements UserView {
    private UserRefreshableListLayout mUserListLayout;
    private RecyclerTouchListener mOnTouchListener;
    private UserListAdapter mRunHistoryAdapter;
    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
    }

    @Override
    protected UserPresenter onCreatePresenter() {
        return new UserPresenter(this, getApp().getRetrofitInterface());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user;
    }

    private void bindView() {
        mUserListLayout = findViewById(R.id.user_list);

        mUserListLayout.addItemListDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mUserListLayout.setItemListLayoutManager(new LinearLayoutManager(this));
        mUserListLayout.setItemListAdapter(mRunHistoryAdapter = new UserListAdapter(this, R.layout.user_item_layout));
        mUserListLayout.setRefreshListListener(new UserRefreshableListLayout.OnRefreshListListener() {
            @Override
            public void onRefresh() {
                if (WindowUtils.isInternetConnected(getApplicationContext())) {
                    mCurrentPage = 0;
                    getPresenter().getUserData(++mCurrentPage);
                }
            }
        });

        mUserListLayout.addItemListScrollListener(mLoadMoreScrollListener);

        mOnTouchListener = new RecyclerTouchListener(this, mUserListLayout.getRecyclerView());
        mOnTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                // row clicked.
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        });
        mUserListLayout.setItemListProgressBarVisibility(true);

        UserPresenter presenter = getPresenter();
        if (WindowUtils.isInternetConnected(this)){
            if (presenter != null) {
                presenter.getUserData(++mCurrentPage);
            }
        }else {
            // load data from the db.
            presenter.getUserListFromDB();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserListLayout.addOnItemTouchListener(mOnTouchListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mUserListLayout.removeOnItemTouchListener(mOnTouchListener);
    }

    @Override
    public void onDestroy() {
        mUserListLayout.removeItemListScrollListener(mLoadMoreScrollListener);
        super.onDestroy();
    }

    @Override
    public void onProgressVisible(boolean visible) {
        mUserListLayout.setItemListProgressBarVisibility(visible);
    }

    @Override
    public void onLoadUsers(UserItemsVO itemsVO) {
        if(mCurrentPage > 1){
            mRunHistoryAdapter.appendItems(itemsVO.getUserItemInfos());
        }else {
            mRunHistoryAdapter.setItems(itemsVO.getUserItemInfos());
        }
        mLoadMoreScrollListener.canLoadMore(itemsVO.isLast());
        mUserListLayout.setItemListProgressBarVisibility(false);
        mLoadMoreScrollListener.setLoading(false);
        if (!itemsVO.isLast()){
            getPresenter().saveUserDataIntoDB(mRunHistoryAdapter.getAllItems());
        }
    }

    private final LoadMoreDataScrollListener mLoadMoreScrollListener = new LoadMoreDataScrollListener() {
        @Override
        protected void onLoadMore(RecyclerView recyclerView, LoadMoreDataScrollListener listener) {
            mLoadMoreScrollListener.setLoading(true);
            getPresenter().getUserData(++mCurrentPage);
        }
    };

}
