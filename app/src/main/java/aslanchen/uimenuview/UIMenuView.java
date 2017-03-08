package aslanchen.uimenuview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Aslanchen on 2017/3/6.
 */

public class UIMenuView extends RelativeLayout {
    private TextView tv_title, tv_subTitle;
    private Context context;

    public UIMenuView(Context context) {
        this(context, null);
    }

    public UIMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.layout_ui_menu, this);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.UIMenuView);

        // Title
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        CharSequence title = typeArray.getText(R.styleable.UIMenuView_uimv_title);
        if (title != null) {
            tv_title.setText(title);
        }

        //Title-Color
        ColorStateList textColorTitle = typeArray
                .getColorStateList(R.styleable.UIMenuView_uimv_textColorTitle);
        if (textColorTitle != null) {
            tv_title.setTextColor(textColorTitle);
        }

        //Title-Size
        float textSizeTitle = typeArray
                .getDimension(R.styleable.UIMenuView_uimv_textSizeTitle, -1);
        if (textSizeTitle != -1) {
            tv_title.setTextSize(textSizeTitle);
        }

        //Title-Image
        Drawable drawableTitle = typeArray.getDrawable(R.styleable.UIMenuView_uimv_image);
        tv_title.setCompoundDrawablesWithIntrinsicBounds(drawableTitle, null, null, null);

        // SubTitle
        Boolean isShowSub = typeArray.getBoolean(R.styleable.UIMenuView_uimv_showSub, false);
        tv_subTitle = (TextView) this.findViewById(R.id.tv_subTitle);
        if (isShowSub == true) {
            tv_subTitle.setVisibility(View.VISIBLE);

            CharSequence subTitle = typeArray.getText(R.styleable.UIMenuView_uimv_subTitle);
            if (subTitle != null) {
                tv_subTitle.setText(subTitle);
            }

            //SubTitle-Color
            ColorStateList textColorSubTitle = typeArray
                    .getColorStateList(R.styleable.UIMenuView_uimv_textColorSubTitle);
            if (textColorSubTitle != null) {
                tv_subTitle.setTextColor(textColorSubTitle);
            }

            //SubTitle-Size
            float textSizeSubTitle = typeArray
                    .getDimension(R.styleable.UIMenuView_uimv_textSizeSubTitle, -1);
            if (textSizeSubTitle != -1) {
                tv_subTitle.setTextSize(textSizeSubTitle);
            }

            //SubTitle-Image
            Drawable drawableSubTitle = typeArray.getDrawable(R.styleable.UIMenuView_uimv_subImage);
            tv_subTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableSubTitle, null);
        } else {
            tv_subTitle.setVisibility(View.GONE);
        }

        // 回收
        typeArray.recycle();
    }

    public void setTitle(CharSequence text) {
        tv_title.setText(text);
    }

    public void setSubTitle(CharSequence text) {
        tv_title.setText(text);
    }

    public void setImageTitle(Drawable drawable) {
        tv_title.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setImageSubTitle(Drawable drawable) {
        tv_subTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void setTextColorTitle(int color) {
        tv_subTitle.setTextColor(color);
    }

    public void setTextColorSubTitle(int color) {
        tv_subTitle.setTextColor(color);
    }
}
