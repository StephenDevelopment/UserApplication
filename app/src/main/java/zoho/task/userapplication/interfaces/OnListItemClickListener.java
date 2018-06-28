package zoho.task.userapplication.interfaces;

import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static zoho.task.userapplication.interfaces.OnListItemClickListener.ViewTouchType.CLICK;
import static zoho.task.userapplication.interfaces.OnListItemClickListener.ViewTouchType.LONG_CLICK;
import static zoho.task.userapplication.interfaces.OnListItemClickListener.ViewTouchType.TOUCH;

/**
 * @author Stephenraj
 * @version 1.0.0
 */
public interface OnListItemClickListener<T> {

    void onItemClick(View view, T model, int position, @ViewTouchType int touchType);

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({TOUCH, CLICK, LONG_CLICK})
    @interface ViewTouchType {
        int TOUCH = 0;
        int CLICK = 1;
        int LONG_CLICK = 2;
    }
}
