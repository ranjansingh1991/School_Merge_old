package in.semicolonindia.schoolcrm.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * Created by Rupesh on 08-08-2017.
 */
@SuppressWarnings("ALL")
@SuppressLint("AppCompatCustomView")
public class DirectionButton extends ImageView {

    public DirectionButton(Context context) {
        super(context);

        setBackgroundResource(getThemeSelectableBackgroundId(context));
    }

    public void setColor(int color) {
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(enabled ? 1f : 0.1f);
    }

    private static int getThemeSelectableBackgroundId(Context context) {
        //Get selectableItemBackgroundBorderless defined for AppCompat
        int colorAttr = context.getResources().getIdentifier(
                "selectableItemBackgroundBorderless", "attr", context.getPackageName());

        if (colorAttr == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                colorAttr = android.R.attr.selectableItemBackgroundBorderless;
            } else {
                colorAttr = android.R.attr.selectableItemBackground;
            }
        }

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.resourceId;
    }
}
