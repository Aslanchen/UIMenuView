package aslanchen.uimenuview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Aslanchen on 2017/3/6.
 */

public class UIMenuView extends RelativeLayout {
    protected TextView title;
    protected EditText subTitle;
    protected Context context;

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
        if (typeArray == null) {
            return;
        }

        // Title
        title = this.findViewById(R.id.title);
        CharSequence title = typeArray.getText(R.styleable.UIMenuView_uimv_title);
        if (TextUtils.isEmpty(title) == false) {
            setTitle(title);
        }

        //Title-Color
        int textColorTitle = typeArray.getColor(R.styleable.UIMenuView_uimv_textColorTitle, ContextCompat.getColor(context, android.R.color.background_dark));
        setTextColorTitle(textColorTitle);

        //Title-Size
        float textSizeTitle = typeArray.getDimension(R.styleable.UIMenuView_uimv_textSizeTitle, -1);
        if (textSizeTitle != -1) {
            this.title.setTextSize(textSizeTitle);
        }

        //Title-Image
        Drawable drawableTitle = typeArray.getDrawable(R.styleable.UIMenuView_uimv_image);
        if (drawableTitle != null) {
            setImageTitle(drawableTitle);
        }

        //Title-drawablePaddingTitle
        int drawablePaddingTitle = typeArray.getDimensionPixelSize(R.styleable.UIMenuView_uimv_drawablePaddingTitle, 5);
        this.title.setCompoundDrawablePadding(drawablePaddingTitle);

        // SubTitle
        subTitle = this.findViewById(R.id.subTitle);
        subTitle.setEnabled(false);
        subTitle.setBackground(null);
        CharSequence subTitle = typeArray.getText(R.styleable.UIMenuView_uimv_subTitle);
        if (TextUtils.isEmpty(subTitle) == false) {
            setSubTitle(subTitle);
        }

        //SubTitle-Color
        int textColorSubTitle = typeArray.getColor(R.styleable.UIMenuView_uimv_textColorSubTitle, ContextCompat.getColor(context, android.R.color.background_dark));
        setTextColorSubTitle(textColorSubTitle);

        //SubTitle-Size
        float textSizeSubTitle = typeArray.getDimension(R.styleable.UIMenuView_uimv_textSizeSubTitle, -1);
        if (textSizeSubTitle != -1) {
            this.subTitle.setTextSize(textSizeSubTitle);
        }

        //SubTitle-Image
        Drawable drawableSubTitle = typeArray.getDrawable(R.styleable.UIMenuView_uimv_subImage);
        if (drawableSubTitle != null) {
            setImageSubTitle(drawableSubTitle);
        }

        //SubTitle-drawablePaddingSubTitle
        int drawablePaddingSubTitle = typeArray.getDimensionPixelSize(R.styleable.UIMenuView_uimv_drawablePaddingSubTitle, 5);
        this.subTitle.setCompoundDrawablePadding(drawablePaddingSubTitle);

        //SubTitle-single
        Boolean single = typeArray.getBoolean(R.styleable.UIMenuView_uimv_single, true);
        setSingleLine(single);

        //SubTitle-maxLength
        int maxLength = typeArray.getInt(R.styleable.UIMenuView_uimv_maxLength, 0);
        setMaxLength(maxLength);

        //SubTitle-isShowSub
        Boolean isShowSub = typeArray.getBoolean(R.styleable.UIMenuView_uimv_showSub, false);
        setShowSub(isShowSub);

        //SubTitle-uimv_hint
        CharSequence hint = typeArray.getText(R.styleable.UIMenuView_uimv_hint);
        setHint(hint);

        //SubTitle-inputType
        int input_type = typeArray.getInt(R.styleable.UIMenuView_uimv_inputType, 1);
        switch (input_type) {
            case 1:
                // normal
                this.subTitle.setInputType(InputType.TYPE_NULL);
                break;
            case 2:
                // numberDecimal
                this.subTitle.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case 3:
                // phone
                this.subTitle.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 4:
                // number
                this.subTitle.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 5:
                // textPassword
                this.subTitle.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 6:
                // numberPassword
                this.subTitle.setInputType(
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
        }

        // 回收
        typeArray.recycle();
    }

    public void setTitle(CharSequence text) {
        title.setText(text);
    }

    public void setTitle(@StringRes int resid) {
        title.setText(resid);
    }

    public void setSubTitle(CharSequence text) {
        subTitle.setText(text);
    }

    public void setSubTitle(@StringRes int resid) {
        subTitle.setText(resid);
    }

    public void setImageTitle(Drawable drawable) {
        title.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setImageTitle(@DrawableRes int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        setImageTitle(drawable);
    }

    public void setImageSubTitle(Drawable drawable) {
        subTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void setImageSubTitle(@DrawableRes int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        setImageSubTitle(drawable);
    }

    public void setTextColorTitle(@ColorInt int color) {
        title.setTextColor(color);
    }

    public void setTextColorSubTitle(@ColorInt int color) {
        subTitle.setTextColor(color);
    }

    public void setSingleLine(Boolean single) {
        subTitle.setSingleLine(single);
        if (single == true) {
            subTitle.setMaxLines(1);
        }
    }

    public void setMaxLength(int maxLength) {
        if (maxLength <= 0) {
            return;
        }
        InputFilter maxLengthFilter = new InputFilter.LengthFilter(maxLength);
        subTitle.setFilters(new InputFilter[]{maxLengthFilter});
    }

    public void setShowSub(boolean isShow) {
        subTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setHint(CharSequence text) {
        subTitle.setHint(text);
    }

    public void setHint(@StringRes int resid) {
        subTitle.setHint(resid);
    }
}
