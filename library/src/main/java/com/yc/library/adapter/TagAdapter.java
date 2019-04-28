package com.yc.library.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.yc.library.listener.OnFlexSelectListener;
import com.yc.library.listener.TagClickListener;
import com.yc.library.widget.BaseTagView;
import com.yc.library.widget.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TagAdapter<V extends BaseTagView<T>, T> {

    private Context context;
    private TagFlowLayout rootView;
    private List<T> items;
    private List<T> selectItems;
    //view和tag的对应关系
    private Map<V, T> viewMap;
    private int mode;
    private int maxSelection;
    protected int itemSelectDrawable;
    protected int itemDefaultDrawable;
    protected int itemSelectTextColor;
    protected int itemDefaultTextColor;
    //是否展示选中效果
    private boolean isShowHighlight = true;
    private OnFlexSelectListener<T> onFlexSelectListener;

    public void setOnFlexSelectListener(OnFlexSelectListener<T> onFlexSelectListenerr) {
        this.onFlexSelectListener = onFlexSelectListenerr;
    }

    public TagAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
        viewMap = new ArrayMap<>();
    }

    public TagAdapter(Context context, List<T> source, List<T> selectItems) {
        this.context = context;
        this.items = source;
        this.selectItems = selectItems;
        viewMap = new ArrayMap<>();
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setSelectItems(List<T> selectItems) {
        this.selectItems = selectItems;
    }

    public List<T> getItems() {
        return items;
    }

    public List<T> getSelectItems() {
        return selectItems;
    }

    protected Context getContext() {
        return context;
    }

    /**
     * 绑定控件
     */
    public void bindView(TagFlowLayout rootView) {
        if (rootView == null) {
            throw new NullPointerException("未初始化TagFlowLayout");
        }
        this.rootView = rootView;
        isShowHighlight = rootView.isShowHighlight();
        itemDefaultDrawable = rootView.getItemDefaultDrawable();
        itemSelectDrawable = rootView.getItemSelectDrawable();
        itemDefaultTextColor = rootView.getItemDefaultTextColor();
        itemSelectTextColor = rootView.getItemSelectTextColor();
        maxSelection = rootView.getMaxSelection();
        mode = rootView.getMode();
    }

    /**
     * 设置标签组
     */
    public void addTags() {
        if (items == null || items.size() <= 0) return;
        viewMap.clear();
        rootView.removeAllViews();
        for (T item : items) {
            if (item == null) continue;
            final BaseTagView<T> view = addTag(item);
            initSelectedViews((V) view);
            // 单个item的点击监听
            view.setListener(new TagClickListener<T>() {
                @Override
                public void onClick(T item) {
                    if (mode == TagFlowLayout.MODE_SINGLE_SELECT) {
                        if (isShowHighlight) view.selectItemChangeColorState();
                        singleSelectMode(item);
                    } else {
                        List<T> selectList = getSelectedList();
                        if ((maxSelection <= selectList.size() && maxSelection > 0) && !view.isItemSelected()) {
                            Toast.makeText(getContext(), "最多选择" + maxSelection + "个标签", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (isShowHighlight) view.selectItemChangeColorState();
                    }
                    if (onFlexSelectListener != null) {
                        onFlexSelectListener.onSelect(getSelectedList());
                    }
                }
            });
            viewMap.put((V) view, item);
            rootView.addView(view);
        }
    }

    /**
     * 初始化默认选中标签
     */
    private void initSelectedViews(V view) {
        if (!isShowHighlight) return;
        if (selectItems == null || selectItems.size() <= 0) return;
        for (T select : selectItems) {
            if (checkIsItemNull(select)) continue;
            if (checkIsItemSame(view, select)) {
                view.setItemSelected(true);
                break;
            }
        }
    }

    /**
     * 单选操作模式
     */
    private void singleSelectMode(T item) {
        if (!isShowHighlight) return;
        for (BaseTagView<T> view : viewMap.keySet()) {
            if (checkIsItemSame((V) view, item)) {
                view.setItemSelected(true);
            } else {
                view.setItemSelected(false);
            }
        }
    }

    /**
     * 刷新数据
     */
    public void notifyDataSetChanged() {
        addTags();
    }

    /**
     * 对于相同item的判断条件
     */
    protected abstract boolean checkIsItemSame(V view, T item);

    /**
     * 检查item是否是空指针
     */
    protected abstract boolean checkIsItemNull(T item);

    /**
     * 添加单个标签
     */
    protected abstract BaseTagView<T> addTag(T item);

    /**
     * 获取所有item的数量
     */
    protected int getCount() {
        return items == null ? 0 : items.size();
    }

    /**
     * 获取已选标签
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    private List<T> getSelectedList() {
        List<T> selectedList = new ArrayList<>();
        for (BaseTagView<T> view : viewMap.keySet()) {
            if (view.isItemSelected()) {
                T item = viewMap.get(view);
                selectedList.add(item);
            }
        }
        return selectedList;
    }
}