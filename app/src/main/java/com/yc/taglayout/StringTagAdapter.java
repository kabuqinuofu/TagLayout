package com.yc.taglayout;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.yc.library.adapter.TagAdapter;

import java.util.List;

public class StringTagAdapter extends TagAdapter<StringTagView, String> {

    StringTagAdapter(Context context, List<String> data) {
        this(context, data, null);
    }

    StringTagAdapter(Context context, List<String> data, List<String> selectItems) {
        super(context, data, selectItems);
    }

    /**
     * 检查item和所选item是否一样
     */
    @Override
    protected boolean checkIsItemSame(StringTagView view, String item) {
        return TextUtils.equals(view.getItem(), item);
    }

    /**
     * 检查item是否是空指针
     */
    @Override
    protected boolean checkIsItemNull(String item) {
        return TextUtils.isEmpty(item);
    }

    /**
     * 添加标签
     */
    @Override
    protected StringTagView addTag(String item) {
        StringTagView tagView = new StringTagView(getContext());
        tagView.setPadding(30, 10, 30, 10);

        TextView textView = tagView.getTextView();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        textView.setGravity(Gravity.CENTER);

        tagView.setItemDefaultDrawable(itemDefaultDrawable);
        tagView.setItemSelectDrawable(itemSelectDrawable);
        tagView.setItemDefaultTextColor(itemDefaultTextColor);
        tagView.setItemSelectTextColor(itemSelectTextColor);
        tagView.setItem(item);
        return tagView;
    }
}