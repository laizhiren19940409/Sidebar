package cn.com.lich.sidebar.frame;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import cn.com.lich.sidebar.R;

/**
 * 侧边栏的基类，继承自{@link FrameLayout}。
 *
 * @author Lich
 */
public abstract class SidebarFrame extends FrameLayout {
    public SidebarFrame(Context context) {
        super(context);
        initialize();
    }

    public SidebarFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.sidebar_frame);
        setAttachAlign(typedArray.getInt(R.styleable.sidebar_frame_attach_align, 0));
        typedArray.recycle();
    }

    /**
     * 该方法用于内部调用，初始化侧边栏的各个成员。
     */
    private void initialize() {
        mState = STATE_VISIBLE;
    }

    /**
     * 侧边栏的依附方向，从上下左右中指定一个值。
     */
    private int mAttachAlign = ATTACH_ALIGN_LEFT;

    public static final int ATTACH_ALIGN_LEFT = 0;

    public static final int ATTACH_ALIGN_TOP = 1;

    public static final int ATTACH_ALIGN_RIGHT = 2;

    public static final int ATTACH_ALIGN_BOTTOM = 3;

    /**
     * @return 侧边栏的依附方向
     */
    public int getAttachAlign() {
        return mAttachAlign;
    }

    /**
     * 该方法用于设置侧边栏的依附方向。
     *
     * @param attachAlign 侧边栏的依附方向
     */
    public void setAttachAlign(int attachAlign) {
        if (attachAlign == ATTACH_ALIGN_LEFT || attachAlign == ATTACH_ALIGN_TOP || attachAlign == ATTACH_ALIGN_RIGHT || attachAlign == ATTACH_ALIGN_BOTTOM)
            mAttachAlign = attachAlign;
        else
            throw new IllegalArgumentException("attachAlign must be one of the left,top,right,bottom");
    }

    /**
     * 侧边栏的实际显示位置X坐标。
     */
    private float mVisibleX = Float.MAX_VALUE;

    /**
     * @return 侧边栏的实际显示位置X坐标
     */
    public float getVisibleX() {
        return mVisibleX;
    }

    /**
     * 侧边栏的实际显示位置Y坐标。
     */
    private float mVisibleY = Float.MAX_VALUE;

    /**
     * @return 侧边栏的实际显示位置Y坐标
     */
    public float getVisibleY() {
        return mVisibleY;
    }

    /**
     * 侧边栏的实际显示宽度。
     */
    private int mVisibleWidth = Integer.MIN_VALUE;

    /**
     * @return 侧边栏的实际显示宽度
     */
    public int getVisibleWidth() {
        return mVisibleWidth;
    }

    /**
     * 侧边栏的实际显示高度。
     */
    private int mVisibleHeight = Integer.MIN_VALUE;

    /**
     * @return 侧边栏的实际显示高度
     */
    public int getVisibleHeight() {
        return mVisibleHeight;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mVisibleX == Float.MAX_VALUE && mVisibleY == Float.MAX_VALUE && mVisibleWidth == Integer.MIN_VALUE && mVisibleHeight == Integer.MIN_VALUE) {
            mVisibleX = getX();
            mVisibleY = getY();
            mVisibleWidth = getWidth();
            mVisibleHeight = getHeight();
            hide();
        }
    }

    /**
     * 侧边栏的状态。
     */
    private int mState = STATE_GONE;

    public static final int STATE_GONE = 0;

    public static final int STATE_VISIBLE = 1;

    /**
     * @return 侧边栏的状态
     */
    public int getState() {
        return mState;
    }

    /**
     * 侧边栏状态变更事件的监听器。
     */
    private OnSideBarStateChangeListener mOnSideBarStateChangeListener = null;

    /**
     * 该方法用于设置侧边栏状态变更事件的监听器。
     *
     * @param onSideBarStateChangeListener 侧边栏状态变更事件的监听器
     */
    public void setOnSideBarStateChangeListener(OnSideBarStateChangeListener onSideBarStateChangeListener) {
        mOnSideBarStateChangeListener = onSideBarStateChangeListener;
    }

    /**
     * 该方法用于外部调用，用于显示侧边栏，会触发onSidebarStateChange事件。
     */
    public void show() {
        onShow();
        mState = STATE_VISIBLE;

        if (mOnSideBarStateChangeListener != null)
            mOnSideBarStateChangeListener.onSidebarStateChange(this, mState);
    }

    /**
     * 该抽象方法用于实现侧边栏显示时的状态。
     */
    protected abstract void onShow();

    /**
     * 该方法用于外部调用，用于隐藏侧边栏，会触发onSidebarStateChange事件。
     */
    public void hide() {
        onHide();
        mState = STATE_GONE;

        if (mOnSideBarStateChangeListener != null)
            mOnSideBarStateChangeListener.onSidebarStateChange(this, mState);
    }

    /**
     * 该抽象方法用于实现侧边栏隐藏时的状态。
     */
    protected abstract void onHide();

    /**
     * 侧边栏状态变更事件的监听器接口。
     *
     * @author Lich
     */
    public interface OnSideBarStateChangeListener {
        void onSidebarStateChange(SidebarFrame sidebarFrame, int state);
    }
}