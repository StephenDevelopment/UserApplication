package zoho.task.userapplication;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zoho.task.userapplication.base.BaseListAdapter;
import zoho.task.userapplication.base.BaseRecyclerViewHolder;
import zoho.task.userapplication.interfaces.OnListItemClickListener;
import zoho.task.userapplication.models.UserItemInfo;
import zoho.task.userapplication.utils.ImageLoaderUtils;

/**
 * @author Stephenraj
 * @version 1.0.0
 */
public class UserListAdapter extends BaseListAdapter<UserListAdapter.UserItemViewHolder, UserItemInfo> {

    @LayoutRes private final int mRowItemLayoutRes;

    UserListAdapter(Context context, @LayoutRes int rowItemLayoutRes) {
        super(context);
        mRowItemLayoutRes = rowItemLayoutRes;
    }

    @Override
    public UserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserItemViewHolder(getLayoutInflater().inflate(mRowItemLayoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(UserItemViewHolder holder, int position) {
        holder.bindVO(getItem(position));
    }

    class UserItemViewHolder extends BaseRecyclerViewHolder<UserItemInfo> implements View.OnClickListener, View.OnLongClickListener {

        final TextView mUserName;
        final ImageView mUserImage;

        UserItemViewHolder(View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.user_name);
            mUserImage = itemView.findViewById(R.id.user_avatar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bindVO(UserItemInfo valueObject) {
            super.bindVO(valueObject);
            String user_name = valueObject.mFirstName+" "+valueObject.mLastName;
            mUserName.setText(user_name);
            ImageLoaderUtils.loadImage(getContext(), valueObject.mAvatar, mUserImage);
        }

        @Override
        public void onClick(View v) {
            notifySelection(v, OnListItemClickListener.ViewTouchType.CLICK);
        }

        @Override
        public boolean onLongClick(View v) {
            notifySelection(v, OnListItemClickListener.ViewTouchType.LONG_CLICK);
            return true;
        }

        private void notifySelection(View v, int clickType) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v, getItem(position), position, clickType);
            }
        }
    }
}
