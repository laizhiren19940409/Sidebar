package cn.com.lich.sidebar.container;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import cn.com.lich.sidebar.frame.SidebarFrame;

/**
 * 侧边栏容器类，用于承载侧边栏，继承自{@link android.widget.FrameLayout}。
 */
public class SidebarContainerFrame extends FrameLayout {
    public SidebarContainerFrame(Context context) {
        super(context);
        initialize();
    }

    public SidebarContainerFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        addToSidebarFrameChildrenListIfIs(child);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        addToSidebarFrameChildrenListIfIs(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    @Override
    protected void attachViewToParent(View child, int index, ViewGroup.LayoutParams params) {
        super.attachViewToParent(child, index, params);
        addToSidebarFrameChildrenListIfIs(child);
    }

    @Override
    public void removeView(View view) {
        removeFromSidebarFrameChildrenListIfIs(view);
        super.removeView(view);
    }

    @Override
    public void removeViewInLayout(View view) {
        removeFromSidebarFrameChildrenListIfIs(view);
        super.removeViewInLayout(view);
    }

    @Override
    public void removeViewAt(int index) {
        removeFromSidebarFrameChildrenListIfIs(getChildAt(index));
        super.removeViewAt(index);
    }

    @Override
    public void removeViewsInLayout(int start, int count) {
        for (int n = start; n < start + count; n++)
            removeFromSidebarFrameChildrenListIfIs(getChildAt(n));

        super.removeViewsInLayout(start, count);
    }

    @Override
    public void removeViews(int start, int count) {
        for (int n = start; n < start + count; n++)
            removeFromSidebarFrameChildrenListIfIs(getChildAt(n));

        super.removeViews(start, count);
    }

    @Override
    public void removeAllViewsInLayout() {
        mSidebarFrameChildrenList.clear();
        super.removeAllViewsInLayout();
    }

    @Override
    protected void detachViewFromParent(View child) {
        removeFromSidebarFrameChildrenListIfIs(child);
        super.detachViewFromParent(child);
    }

    @Override
    protected void detachViewFromParent(int index) {
        removeFromSidebarFrameChildrenListIfIs(getChildAt(index));
        super.detachViewFromParent(index);
    }

    @Override
    protected void detachViewsFromParent(int start, int count) {
        for (int n = start; n < start + count; n++)
            removeFromSidebarFrameChildrenListIfIs(getChildAt(n));

        super.detachViewsFromParent(start, count);
    }

    @Override
    protected void detachAllViewsFromParent() {
        mSidebarFrameChildrenList.clear();
        super.detachAllViewsFromParent();
    }

    /**
     * 该方法用于内部调用，初始化侧边栏容器的各个成员。
     */
    private void initialize() {
        mSidebarFrameChildrenList = new ArrayList<SidebarFrame>();
    }

    /**
     * 子侧边栏列表
     */
    private List<SidebarFrame> mSidebarFrameChildrenList = null;

    /**
     * 该方法用于内部调用，在添加子View时判断其是否为侧边栏，若是则添加到子侧边栏列表中。
     *
     * @param child 待添加的子View
     */
    private boolean addToSidebarFrameChildrenListIfIs(View child) {
        if (child != null && child instanceof SidebarFrame) {
            mSidebarFrameChildrenList.add((SidebarFrame) child);
            return true;
        } else
            return false;
    }

    /**
     * 该方法用于内部调用，在移除子View时判断其是否为侧边栏，若是则从子侧边栏列表中移除。
     *
     * @param view 待移除的子View
     */
    private boolean removeFromSidebarFrameChildrenListIfIs(View view) {
        return view != null && view instanceof SidebarFrame && mSidebarFrameChildrenList.remove(view);
    }

    /**
     * @return 子侧边栏个数
     */
    public int getSidebarFrameChildCount() {
        return  mSidebarFrameChildrenList.size();
    }

    /**
     * @param index 子侧边栏索引
     * @return 子侧边栏
     */
    public SidebarFrame getSidebarFrameChildAt(int index) {
        return mSidebarFrameChildrenList.get(index);
    }
}
