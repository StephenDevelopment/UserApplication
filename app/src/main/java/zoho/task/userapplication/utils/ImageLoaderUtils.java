package zoho.task.userapplication.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public final class ImageLoaderUtils {

    private ImageLoaderUtils() {
        throw new AssertionError("Never instantiate a utility class.");
    }

    public static void loadImage(Context context, Object url, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .into(imageView);
    }
}
