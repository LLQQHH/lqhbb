package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about keyboard
 * </pre>
 */
public final class KeyboardUtils {
    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Show the soft input.动态显示软键盘
     *
     * @param activity The activity.
     */
    public static void showSoftInput(final Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    public static void showSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        //noinspection ConstantConditions
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void showKeyboardDelay(View view, int delayTime) {
        showKeyboardDelay(view,delayTime,true);
    }
    public static void showKeyboardDelay(View view, int delayTime, boolean isNeedPost) {
        if (view==null) return;
        if (isNeedPost) {
            view.post(() -> {
                if (delayTime > 0) {
                    view.postDelayed(() -> {
                        view.requestFocus();
                        if(view instanceof EditText){
                            ((EditText) view).setSelection(((EditText) view).getText().toString().length());
                        }
                        KeyboardUtils.showSoftInput(view);
                    }, delayTime);
                } else {
                    view.requestFocus();
                    if(view instanceof EditText){
                        ((EditText) view).setSelection(((EditText) view).getText().toString().length());
                    }
                    KeyboardUtils.showSoftInput(view);
                }
            });
        } else {
            view.requestFocus();
            if(view instanceof EditText){
                ((EditText) view).setSelection(((EditText) view).getText().toString().length());
            }
            KeyboardUtils.showSoftInput(view);
        }
    }

    /**
     * Hide the soft input.动态隐藏软键盘
     *
     * @param activity The activity.
     */
    public static void hideSoftInput(final Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Toggle the soft input display or not.
     */
    public static void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        //noinspection ConstantConditions
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void showSoftInputDelay(View view, int delayTime, boolean isNeedPost) {
        if (view == null) return;
        if (isNeedPost) {
            view.post(() -> {
                if (delayTime > 0) {
                    view.postDelayed(() -> {
                        view.requestFocus();
                        if (view instanceof EditText) {
                            ((EditText) view).setSelection(((EditText) view).getText().toString().length());
                        }
                        KeyboardUtils.showSoftInput(view);
                    }, delayTime);
                } else {
                    view.requestFocus();
                    if (view instanceof EditText) {
                        ((EditText) view).setSelection(((EditText) view).getText().toString().length());
                    }
                    KeyboardUtils.showSoftInput(view);
                }
            });
        } else {
            view.requestFocus();
            if (view instanceof EditText) {
                ((EditText) view).setSelection(((EditText) view).getText().toString().length());
            }
            KeyboardUtils.showSoftInput(view);
        }
    }
}
