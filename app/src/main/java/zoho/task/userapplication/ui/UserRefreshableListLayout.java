package zoho.task.userapplication.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import zoho.task.userapplication.listeners.RecyclerTouchListener;
import zoho.task.userapplication.utils.ViewUtils;
import zoho.task.userapplication.utils.WindowUtils;

/**
 * @author Stephenraj
 * @version 1.0.0
 */

public class UserRefreshableListLayout extends SwipeRefreshLayout {

    private RecyclerView mItemsList;
    private ProgressBar mProgressBar;
    private Context mContext;

    public UserRefreshableListLayout(@NonNull Context context) {
        super(context);
        initialize(context, null);
    }

    public UserRefreshableListLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    void initialize(@NonNull Context context, @Nullable AttributeSet attrs) {
        mContext = context;

        FrameLayout frameLayout = new FrameLayout(context);

        mItemsList = new RecyclerView(context);
        frameLayout.addView(mItemsList, new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mProgressBar = new ProgressBar(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        frameLayout.addView(mProgressBar, params);

        addView(frameLayout, new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshing(false);
                if (refreshListListener != null) {
                    refreshListListener.onRefresh();
                }
            }
        });
    }

    public void setItemListProgressBarVisibility(boolean visible) {
        ViewUtils.setVisible(mProgressBar, visible);
    }

    public void addItemListScrollListener(RecyclerView.OnScrollListener scrollListener) {
        mItemsList.addOnScrollListener(scrollListener);
    }

    public void removeItemListScrollListener(RecyclerView.OnScrollListener scrollListener) {
        mItemsList.removeOnScrollListener(scrollListener);
    }

    public void addItemListDecoration(RecyclerView.ItemDecoration decoration) {
        mItemsList.addItemDecoration(decoration);
    }

    public void setItemListLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mItemsList.setLayoutManager(layoutManager);
    }

    public void setItemListHasFixedSize(boolean fixedSize) {
        mItemsList.setHasFixedSize(fixedSize);
    }

    public void setItemListAdapter(RecyclerView.Adapter<?> adapter) {
        mItemsList.setAdapter(adapter);
    }

    public void setListItemClipToPadding(boolean clip) {
        mItemsList.setClipToPadding(clip);
    }

    public void setListItemPadding(int left, int top, int right, int bottom) {
        mItemsList.setPadding(WindowUtils.toPx(mContext, left), WindowUtils.toPx(mContext, top), WindowUtils.toPx(mContext, right), WindowUtils.toPx(mContext, bottom));
    }

    public void addOnItemTouchListener(RecyclerTouchListener touchListener) {
        mItemsList.addOnItemTouchListener(touchListener);
    }

    public void removeOnItemTouchListener(RecyclerTouchListener touchListener) {
        mItemsList.removeOnItemTouchListener(touchListener);
    }

    public void setRefreshListListener(OnRefreshListListener listener) {
        this.refreshListListener = listener;
    }

    private OnRefreshListListener refreshListListener;

    public RecyclerView getRecyclerView() {
        return mItemsList;
    }

    public interface OnRefreshListListener {

        void onRefresh();
    }
}
