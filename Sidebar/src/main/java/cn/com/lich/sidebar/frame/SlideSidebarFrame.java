package cn.com.lich.sidebar.frame;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.com.lich.sidebar.R;
import cn.com.lich.sidebar.container.SlideSidebarContainerFrame;

/**
 * 滑动侧边栏的基类，继承自{@link SidebarFrame}。
 *
 * @author Lich
 */
public abstract class SlideSidebarFrame extends SidebarFrame {
    public SlideSidebarFrame(Context context) {
        super(context);
        initialize();
    }

    public SlideSidebarFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.slide_sidebar_frame);
        setSlideDurationTime(typedArray.getInt(R.styleable.slide_sidebar_frame_slide_duration_time, 500));
        setListenerRange(typedArray.getFloat(R.styleable.slide_sidebar_frame_listener_range, 1));
        typedArray.recycle();
    }

    /**
     * 该方法用于内部调用，初始化滑动侧边栏的各个成员。
     */
    private void initialize() {
        mSlideDurationTime = 500;
        mListenerRange = 1;
        mSlideState = SLIDE_STATE_NONE;
    }

    /**
     * 滑动侧边栏的滑动持续时间。
     */
    private int mSlideDurationTime = 0;

    /**
     * @return 滑动侧边栏的滑动持续时间
     */
    public int getSlideDurationTime() {
        return mSlideDurationTime;
    }

    /**
     * 该方法用于设置滑动侧边栏的滑动持续时间。
     *
     * @param slideDurationTime 滑动侧边栏的滑动持续时间
     */
    public void setSlideDurationTime(int slideDurationTime) {
        if (slideDurationTime >= 0)
            mSlideDurationTime = slideDurationTime;
        else
            throw new IllegalArgumentException("slideDurationTime must be >= 0");
    }

    /**
     * 滑动侧边栏滑动触发事件的范围，值在0和1之间，0代表无范围，1代表全视图。
     */
    private float mListenerRange = 0;

    /**
     * @return 滑动侧边栏滑动触发事件的范围，值在0和1之间
     */
    public float getListenerRange() {
        return mListenerRange;
    }

    /**
     * 该方法用于设置滑动侧边栏滑动触发事件的范围。
     *
     * @param listenerRange 滑动侧边栏滑动触发事件的范围，值在0和1之间
     */
    public void setListenerRange(float listenerRange) {
        if (listenerRange >= 0 && listenerRange <= 1)
            mListenerRange = listenerRange;
        else
            throw new IllegalArgumentException("listenerRange must be bettween 0 to 1");
    }

    /**
     * 滑动侧边栏的滑动状态。
     */
    private int mSlideState = SLIDE_STATE_NONE;

    public static final int SLIDE_STATE_NONE = 0;

    public static final int SLIDE_STATE_ONSLIDE = 1;

    public static final int SLIDE_STATE_ONAUTOSLIDE = 2;

    /**
     * @return 滑动侧边栏的滑动状态
     */
    public int getSlideState() {
        return mSlideState;
    }

    /**
     * 滑动侧边栏滑动事件的监听器。
     */
    private OnSlideListener mOnSlideListener = null;

    /**
     * 该方法用于设置滑动侧边栏滑动事件的监听器。
     *
     * @param onSlideListener 滑动侧边栏滑动事件的监听器
     */
    public void setOnSlideListener(OnSlideListener onSlideListener) {
        mOnSlideListener = onSlideListener;
    }

    /**
     * 滑动侧边栏自动滑动事件的监听器。
     */
    private OnAutoSlideListener mOnAutoSlideListener = null;

    /**
     * 该方法用于设置滑动侧边栏自动滑动事件的监听器。
     *
     * @param onAutoSlideListener 滑动侧边栏自动滑动事件的监听器
     */
    public void setOnAutoSlideListener(OnAutoSlideListener onAutoSlideListener) {
        mOnAutoSlideListener = onAutoSlideListener;
    }

    /**
     * 该方法用于外部调用，判断一次触摸事件是否发生在滑动侧边栏滑动触发的范围内。
     *
     * @param e 标志一次滑动开始时的按下事件
     * @return 若返回true则代表触摸事件发生滑动侧边栏滑动触发的范围内
     */
    public boolean isTouchOnListenerRange(MotionEvent e) {
        SlideSidebarContainerFrame slideSidebarContainerFrame = (SlideSidebarContainerFrame) getParent();

        switch (getAttachAlign()) {
            case ATTACH_ALIGN_LEFT:
                return getState() == STATE_VISIBLE || e.getX() < slideSidebarContainerFrame.getWidth() * mListenerRange;
            case ATTACH_ALIGN_TOP:
                return getState() == STATE_VISIBLE || e.getY() < slideSidebarContainerFrame.getHeight() * mListenerRange;
            case ATTACH_ALIGN_RIGHT:
                return getState() == STATE_VISIBLE || e.getX() > slideSidebarContainerFrame.getWidth() * (1 - mListenerRange);
            case ATTACH_ALIGN_BOTTOM:
                return getState() == STATE_VISIBLE || e.getY() > slideSidebarContainerFrame.getHeight() * (1 - mListenerRange);
            default:
                return false;
        }
    }

    /**
     * 该抽象方法用于判断一次滑动事件是否为有效滑动。
     *
     * @param e1 标志一次滑动开始时的按下事件
     * @param e2 每一步滑动进行时的滑动事件
     * @return 若返回true则代表该滑动事件为有效滑动
     */
    public abstract boolean isSlideDistanceEffective(MotionEvent e1, MotionEvent e2);

    /**
     * 该方法用于外部调用，让滑动侧边栏处理滑动事件，会触发OnSlide事件。
     *
     * @param e1        标志一次滑动开始时的按下事件
     * @param e2        每一步滑动进行时的滑动事件
     * @param distanceX 每一步滑动事件沿X轴的水平滑动距离，注意这并不是{@code e1}和{@code e2}事件触发点的距离
     * @param distanceY 每一步滑动事件沿Y轴的垂直滑动距离，注意这并不是{@code e1}和{@code e2}事件触发点的距离
     * @return 若返回true则代表事件处理完毕不会再往下传递
     */
    public boolean slide(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (isTouchOnListenerRange(e1) && isSlideDistanceEffective(e1, e2)) {
            mSlideState = SLIDE_STATE_ONSLIDE;

            if (mOnSlideListener != null)
                mOnSlideListener.onSlide(this, e1, e2, distanceX, distanceY);

            return onSlide(e1, e2, distanceX, distanceY);
        } else
            return false;
    }

    /**
     * 该抽象方法用于实现滑动侧边栏对滑动事件的处理。
     *
     * @param e1        标志一次滑动开始时的按下事件
     * @param e2        每一步滑动进行时的滑动事件
     * @param distanceX 每一步滑动事件沿X轴的水平滑动距离，注意这并不是{@code e1}和{@code e2}事件触发点的距离
     * @param distanceY 每一步滑动事件沿Y轴的垂直滑动距离，注意这并不是{@code e1}和{@code e2}事件触发点的距离
     */
    protected abstract boolean onSlide(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

    /**
     * 该抽象方法用于判断滑动侧边栏是否正在滑出。
     *
     * @return 若为true则代表滑动侧边栏正在滑出
     */
    public abstract boolean judgeIsSlideIn();

    /**
     * 该抽象方法用于计算滑动侧边栏自动滑动任务剩余持续时间。
     *
     * @param isSlideIn         若为true则代表滑动侧边栏正在滑出
     * @param slideDurationTime 滑动侧边栏的滑动持续时间
     * @return 自动滑动任务剩余持续时间
     */
    protected abstract long countAutoSlideDurationTime(boolean isSlideIn, long slideDurationTime);

    /**
     * 该方法用于外部调用，让滑动侧边栏完成自动滑动，会触发OnAutoSlide事件。
     *
     * @param isSlideIn 若为true则代表滑动侧边栏正在滑出
     */
    public void autoSlide(boolean isSlideIn) {
        post(new AutoSlideTask(isSlideIn));
    }

    /**
     * 该抽象方法用于实现滑动侧边栏如何完成自动滑动。
     *
     * @param isSlideIn             若为true则代表滑动侧边栏正在滑出
     * @param autoSlideStartTime    自动滑动任务开始时间
     * @param autoSlideDurationTime 自动滑动任务剩余持续时间
     * @param callbackBeginTime     自动滑动任务每步回调方法开始执行时间
     */
    protected abstract void onAutoSlide(boolean isSlideIn, long slideDurationTime, long autoSlideStartTime, long autoSlideDurationTime, long callbackBeginTime);

    /**
     * 自动滑动任务类，用于实现滑动侧边栏的自动滑动效果，实现{@link Runnable}接口。
     *
     * @author Lich
     */
    private class AutoSlideTask implements Runnable {
        /**
         * 自动滑动任务画面刷新间隔。
         */
        private static final int AUTO_SLIDE_REFILDAYS = 25;

        /**
         * 滑动侧边栏是否正在滑出。
         */
        private boolean mIsSlideIn = false;

        /**
         * 自动滑动任务开始时间。
         */
        private long mAutoSlideStartTime = 0;

        /**
         * 自动滑动任务剩余持续时间。
         */
        private long mAutoSlideDurationTime = 0;

        /**
         * 自动滑动任务每步回调方法开始执行时间。
         */
        private long mCallbackBeginTime = 0;

        /**
         * 自动滑动任务每步回调方执行法结束时间。
         */
        private long mCallbackEndTime = 0;

        public AutoSlideTask(boolean isSlideIn) {
            mSlideState = SLIDE_STATE_ONAUTOSLIDE;
            mIsSlideIn = isSlideIn;
            mAutoSlideStartTime = System.currentTimeMillis();
            mAutoSlideDurationTime = countAutoSlideDurationTime(mIsSlideIn, mSlideDurationTime);
        }

        @Override
        public void run() {
            mCallbackBeginTime = System.currentTimeMillis();
            onAutoSlide(mIsSlideIn, mSlideDurationTime, mAutoSlideStartTime, mAutoSlideDurationTime, mCallbackBeginTime);

            if (mOnAutoSlideListener != null)
                mOnAutoSlideListener.onAutoSlide(SlideSidebarFrame.this, mIsSlideIn, mSlideDurationTime, mAutoSlideStartTime, mAutoSlideDurationTime, mCallbackBeginTime);

            mCallbackEndTime = System.currentTimeMillis();

            if (mCallbackEndTime - mAutoSlideStartTime < mAutoSlideDurationTime)
                postDelayed(this, AUTO_SLIDE_REFILDAYS - mCallbackEndTime + mCallbackBeginTime);
            else {
                if (mIsSlideIn)
                    show();
                else
                    hide();

                removeCallbacks(this);
                mSlideState = SLIDE_STATE_NONE;
            }
        }
    }

    /**
     * 滑动侧边栏滑动事件的监听器接口。
     *
     * @author Lich
     */
    public interface OnSlideListener {
        void onSlide(SlideSidebarFrame slideSidebarFrame, MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    }

    /**
     * 滑动侧边栏自动滑动事件的监听器接口。
     *
     * @author Lich
     */
    public interface OnAutoSlideListener {
        void onAutoSlide(SlideSidebarFrame slideSidebarFrame, boolean isSlideIn, long slideDurationTime, long autoSlideStartTime, long autoSlideDurationTime, long callbackBeginTime);
    }
}