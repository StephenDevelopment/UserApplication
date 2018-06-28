package zoho.task.userapplication.base;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Stephenraj
 * @version 1.0.0
 */
public class BaseRecyclerViewHolder<VO> extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    @CallSuper
    public void bindVO(VO valueObject) {}
}
