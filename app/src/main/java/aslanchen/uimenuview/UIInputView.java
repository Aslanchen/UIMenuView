package aslanchen.uimenuview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Aslanchen on 2017/3/6.
 */

public class UIInputView extends UIMenuView {

    public UIInputView(Context context) {
        this(context, null);
    }

    public UIInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        subTitle.setEnabled(true);
    }
}
