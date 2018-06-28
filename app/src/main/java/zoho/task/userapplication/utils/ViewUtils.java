package zoho.task.userapplication.utils;

import android.animation.Animator;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

/**
 * @author Stephenraj
 * @version 1.0.0
 */
public final class ViewUtils {

    private ViewUtils() {
    }

    public static void gone(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void visible(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void invisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    public static void setGone(View view, boolean gone) {
        view.setVisibility(gone ? View.GONE : View.VISIBLE);
    }

    public static void revealView(View rootLayout, long duration) {

        int width = rootLayout.getWidth();
        int height = rootLayout.getHeight();

        float finalRadius = (float) (Math.max(width, height) * 1.1);

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, width / 2, height / 2, 0, finalRadius);
        circularReveal.setDuration(duration);
        circularReveal.setInterpolator(new AccelerateInterpolator());

        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    public static Path getCurvedPath(RectF rect, int topLeftDiameter, int topRightDiameter, int bottomLeftDiameter, int bottomRightDiameter) {
        Path path = new Path();
        topLeftDiameter = topLeftDiameter < 0 ? 0 : topLeftDiameter;
        topRightDiameter = topRightDiameter < 0 ? 0 : topRightDiameter;
        bottomLeftDiameter = bottomLeftDiameter < 0 ? 0 : bottomLeftDiameter;
        bottomRightDiameter = bottomRightDiameter < 0 ? 0 : bottomRightDiameter;

        path.moveTo(rect.left + topLeftDiameter / 2, rect.top);
        path.lineTo(rect.right - topRightDiameter / 2, rect.top);
        path.quadTo(rect.right, rect.top, rect.right, rect.top + topRightDiameter / 2);
        path.lineTo(rect.right, rect.bottom - bottomRightDiameter / 2);
        path.quadTo(rect.right, rect.bottom, rect.right - bottomRightDiameter / 2, rect.bottom);
        path.lineTo(rect.left + bottomLeftDiameter / 2, rect.bottom);
        path.quadTo(rect.left, rect.bottom, rect.left, rect.bottom - bottomLeftDiameter / 2);
        path.lineTo(rect.left, rect.top + topLeftDiameter / 2);
        path.quadTo(rect.left, rect.top, rect.left + topLeftDiameter / 2, rect.top);
        path.close();
        return path;
    }
}
