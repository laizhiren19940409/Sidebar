package cn.com.lich.sidebar.container;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import cn.com.lich.sidebar.frame.SidebarFrame;
import cn.com.lich.sidebar.frame.SlideSidebarFrame;

/**
 * 滑动侧边栏容器类，用于承载滑动侧边栏并监听屏幕的滑动事件和执行滑动侧边栏的滑动效果，继承自{@link SidebarContainerFrame}，实现了{@link GestureDetector.OnGestureListener}接口。
 *
 * @author Lich
 */
public class SlideSidebarContainerFrame extends SidebarContainerFrame implements GestureDetector.OnGestureListener {
    public SlideSidebarContainerFrame(Context context) {
        super(context);
        initialize();
    }

    public SlideSidebarContainerFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    /**
     * 该方法用于内部调用，初始化滑动侧边栏容器的各个成员。
     */
    private void initialize() {
        mGestureDetector = new GestureDetector(getContext(), this);
        mGestureDetector.setIsLongpressEnabled(true);
    }

    private GestureDetector mGestureDetector = null;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int count = getSidebarFrameChildCount();

        for (int n = 0; n < count; n++) {
            SidebarFrame sidebarFrame = getSidebarFrameChildAt(n);

            if (sidebarFrame instanceof SlideSidebarFrame && ((SlideSidebarFrame) sidebarFrame).getSlideState() != SlideSidebarFrame.SLIDE_STATE_ONAUTOSLIDE && onTouchEvent(ev))
                return true;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int count = getSidebarFrameChildCount();

        for (int n = 0; n < count; n++) {
            SidebarFrame sidebarFrame = getSidebarFrameChildAt(n);

            if (event.getAction() == MotionEvent.ACTION_UP && sidebarFrame instanceof SlideSidebarFrame && ((SlideSidebarFrame) sidebarFrame).getSlideState() == SlideSidebarFrame.SLIDE_STATE_ONSLIDE)
                ((SlideSidebarFrame) sidebarFrame).autoSlide(((SlideSidebarFrame) sidebarFrame).judgeIsSlideIn());
        }

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        boolean isEventHandle = false;
        int count = getSidebarFrameChildCount();

        for (int n = 0; n < count; n++) {
            SidebarFrame sidebarFrame = getSidebarFrameChildAt(n);

            if (sidebarFrame instanceof SlideSidebarFrame && ((SlideSidebarFrame) sidebarFrame).slide(e1, e2, distanceX, distanceY))
                isEventHandle = true;
        }

        return isEventHandle;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
