package aslanchen.uimenuview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Aslanchen on 2017/3/6.
 */

public class UIInputView extends LinearLayout implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {
    private TextView tv_title;
    private EditText et_input;
    private ImageView iv_clear;
    private Context context;

    private float minNumber = 0;
    private float maxNumber = -1;
    private OnMyFocusChangeListener onFocisChangeListener;

    public UIInputView(Context context) {
        this(context, null);
    }

    public UIInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);

        LayoutInflater.from(context).inflate(R.layout.layout_ui_input, this);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.UIInputView);

        //Title
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        CharSequence title = typeArray.getText(R.styleable.UIInputView_uiiv_title);
        if (title != null) {
            tv_title.setText(title);
        }

        ColorStateList textColorTitle = typeArray
                .getColorStateList(R.styleable.UIInputView_uiiv_textColorTitle);
        if (textColorTitle != null) {
            tv_title.setTextColor(textColorTitle);
        }

        float textSizeTitle = typeArray
                .getDimension(R.styleable.UIInputView_uiiv_textSizeTitle, -1);
        if (textSizeTitle != -1) {
            tv_title.setTextSize(textSizeTitle);
        }

        //Input
        et_input = (EditText) this.findViewById(R.id.et_input);
        ColorStateList textColorInput = typeArray
                .getColorStateList(R.styleable.UIInputView_uiiv_textColorInput);
        if (textColorInput != null) {
            et_input.setTextColor(textColorInput);
        }

        float textSizeInput = typeArray
                .getDimension(R.styleable.UIInputView_uiiv_textSizeInput, -1);
        if (textSizeInput != -1) {
            et_input.setTextSize(textSizeInput);
        }

        CharSequence hint = typeArray.getText(R.styleable.UIInputView_uiiv_hint);
        if (title != null) {
            et_input.setHint(hint);
        }

        // Input Type
        int input_type = typeArray.getInt(R.styleable.UIInputView_uiiv_inputType, 1);

        switch (input_type) {
            case 1:
                // normal
                break;
            case 2:
                // numberDecimal
                et_input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et_input.setKeyListener(new DigitsKeyListener(false, true));
                break;
            case 3:
                // text
                et_input.setSingleLine(false);
                InputFilter maxLengthFilter = new InputFilter.LengthFilter(200);
                et_input.setFilters(new InputFilter[]{maxLengthFilter});
                et_input.setMaxLines(3);
                // 三星手机此方法无效
                et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                    }
                });
                break;
            case 4:
                // phone
                et_input.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 5:
                // number
                et_input.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 6:
                // textPassword
                et_input.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 7:
                // numberPassword
                et_input.setInputType(
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
        }

        et_input.addTextChangedListener(this);
        et_input.setOnFocusChangeListener(this);

        // uiiv_maxLength
        int maxLength = typeArray.getInt(R.styleable.UIInputView_uiiv_maxLength, 0);
        if (maxLength != 0) {
            InputFilter maxLengthFilter = new InputFilter.LengthFilter(maxLength);
            et_input.setFilters(new InputFilter[]{maxLengthFilter});
        }

        // uiiv_maxNumber
        maxNumber = typeArray.getInt(R.styleable.UIInputView_uiiv_maxNumber, -1);
        if (maxNumber != -1) {
            setInputMaxNumber(maxNumber);
        }

        // 设置清空按钮
        iv_clear = (ImageView) this.findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        iv_clear.setVisibility(View.INVISIBLE);

        // 回收
        typeArray.recycle();
    }

    public void setInputMaxNumber(float maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (maxNumber != -1) {
            if (start > 1) {
                if (minNumber != -1 && maxNumber != -1) {
                    float num = 0;
                    try {
                        num = Float.valueOf(charSequence.toString());
                    } catch (NumberFormatException e) {
                        num = 0;
                    }
                    if (num > maxNumber) {
                        charSequence = String.valueOf(maxNumber);
                        et_input.setText(charSequence);
                    } else if (num < minNumber)
                        charSequence = String.valueOf(minNumber);
                    return;
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null && editable.length() > 0) {
            iv_clear.setVisibility(View.VISIBLE);
        } else {
            iv_clear.setVisibility(View.INVISIBLE);
        }

        if (maxNumber != -1) {
            if (TextUtils.isEmpty(editable) == false) {
                if (minNumber != -1 && maxNumber != -1) {
                    float markVal = 0;
                    try {
                        markVal = Float.valueOf(editable.toString());
                    } catch (NumberFormatException e) {
                        markVal = 0;
                    }
                    if (markVal > maxNumber) {
                        et_input.setText(String.valueOf(maxNumber));
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_clear:
                et_input.setText("");
                break;
            case R.id.tv_title:
                et_input.setFocusable(true);
                et_input.setFocusableInTouchMode(true);
                et_input.requestFocus();
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus == true && et_input.getText().length() > 0) {
            iv_clear.setVisibility(View.VISIBLE);
        } else {
            iv_clear.setVisibility(View.INVISIBLE);
        }

        if (onFocisChangeListener != null) {
            onFocisChangeListener.onMyFocusChange(view, hasFocus);
        }
    }

    public void setTransformationMethod(PasswordTransformationMethod instance) {
        et_input.setTransformationMethod(instance);
    }

    public void setTransformationMethod(HideReturnsTransformationMethod instance) {
        et_input.setTransformationMethod(instance);
    }

    /**
     * Interface definition for a callback to be invoked when the focus state of
     * a view changed.
     */
    public interface OnMyFocusChangeListener {
        /**
         * Called when the focus state of a view has changed.
         *
         * @param v        The view whose state has changed.
         * @param hasFocus The new focus state of v.
         */
        void onMyFocusChange(View v, boolean hasFocus);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener l) {
        et_input.setOnEditorActionListener(l);
    }

    public void setTextInput(CharSequence text) {
        et_input.setText(text);
    }

    public CharSequence getTextInput(CharSequence text) {
        return et_input.getText();
    }

    public void setTextTitle(CharSequence text) {
        tv_title.setText(text);
    }

    public void setTextColorTitle(int color) {
        tv_title.setTextColor(color);
    }

    public void setTextColorInput(int color) {
        et_input.setTextColor(color);
    }
}
