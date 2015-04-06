package cn.com.lich.sidebar.frame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.com.lich.sidebar.container.SlideSidebarContainerFrame;

/**
 * 简单滑动侧边栏类，继承自{@link SlideSidebarFrame}。
 *
 * @author Lich
 */
public class SimpleSlideSidebarFrame extends SlideSidebarFrame {
    public SimpleSlideSidebarFrame(Context context) {
        super(context);
    }

    public SimpleSlideSidebarFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onShow() {
        setTranslationX(0);
        setTranslationY(0);
        setVisibility(VISIBLE);
    }

    @Override
    public void onHide() {
        switch (getAttachAlign()) {
            case ATTACH_ALIGN_LEFT: {
                setTranslationX(-getVisibleX() - getVisibleWidth());
            }
            break;

            case ATTACH_ALIGN_TOP: {
                setTranslationY(-getVisibleY() - getVisibleHeight());
            }
            break;

            case ATTACH_ALIGN_RIGHT: {
                setTranslationX(((SlideSidebarContainerFrame) getParent()).getWidth() - getVisibleX());
            }
            break;

            case ATTACH_ALIGN_BOTTOM: {
                setTranslationY(((SlideSidebarContainerFrame) getParent()).getHeight() - getVisibleY());
            }
            break;

            default:
                break;
        }

        setVisibility(GONE);
    }

    @Override
    public boolean isSlideDistanceEffective(MotionEvent e1, MotionEvent e2) {
        float slideX = e2.getX() - e1.getX();
        float slideY = e2.getY() - e1.getY();

        switch (getAttachAlign()) {
            case ATTACH_ALIGN_LEFT:
                return getState() == STATE_VISIBLE ? slideX < 0 && slideX > -getVisibleX() - getVisibleWidth() : slideX > 0 && slideX < getVisibleX() + getVisibleWidth();
            case ATTACH_ALIGN_TOP:
                return getState() == STATE_VISIBLE ? slideY < 0 && slideY > -getVisibleY() - getVisibleHeight() : slideY > 0 && slideY < getVisibleY() + getVisibleHeight();
            case ATTACH_ALIGN_RIGHT:
                return getState() == STATE_VISIBLE ? slideX > 0 && slideX < ((SlideSidebarContainerFrame) getParent()).getWidth() - getVisibleX() : slideX < 0 && slideX > getVisibleX() - ((SlideSidebarContainerFrame) getParent()).getWidth();
            case ATTACH_ALIGN_BOTTOM:
                return getState() == STATE_VISIBLE ? slideY > 0 && slideY < ((SlideSidebarContainerFrame) getParent()).getHeight() - getVisibleY() : slideY < 0 && slideY > getVisibleY() - ((SlideSidebarContainerFrame) getParent()).getHeight();
            default:
                return false;
        }
    }

    @Override
    protected boolean onSlide(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float slideX = e2.getX() - e1.getX();
        float slideY = e2.getY() - e1.getY();
        setVisibility(VISIBLE);

        switch (getAttachAlign()) {
            case ATTACH_ALIGN_LEFT: {
                setTranslationX(getState() == STATE_VISIBLE ? slideX : -getVisibleX() - getVisibleWidth() + slideX);
            }
            break;

            case ATTACH_ALIGN_TOP: {
                setTranslationY(getState() == STATE_VISIBLE ? slideY : -getVisibleY() - getVisibleHeight() + slideY);
            }
            break;

            case ATTACH_ALIGN_RIGHT: {
                setTranslationX(getState() == STATE_VISIBLE ? slideX : ((SlideSidebarContainerFrame) getParent()).getWidth() - getVisibleX() + slideX);
            }
            break;

            case ATTACH_ALIGN_BOTTOM: {
                setTranslationY(getState() == STATE_VISIBLE ? slideY : ((SlideSidebarContainerFrame) getParent()).getHeight() - getVisibleY() + slideY);
            }
            break;

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean judgeIsSlideIn() {
        switch (getAttachAlign()) {
            case ATTACH_ALIGN_LEFT:
                return getTranslationX() > (-getVisibleX() - getVisibleWidth()) / 2;

            case ATTACH_ALIGN_TOP:
                return getTranslationY() > (-getVisibleY() - getVisibleHeight()) / 2;

            case ATTACH_ALIGN_RIGHT:
                return getTranslationX() < (((SlideSidebarContainerFrame) getParent()).getWidth() - getVisibleX()) / 2;

            case ATTACH_ALIGN_BOTTOM:
                return getTranslationY() < (((SlideSidebarContainerFrame) getParent()).getHeight() - getVisibleY()) / 2;

            default:
                return false;
        }
    }

    @Override
    protected long countAutoSlideDurationTime(boolean isSlideIn, long slideDurationTime) {
        switch (getAttachAlign()) {
            case ATTACH_ALIGN_LEFT:
                return (long) (isSlideIn ? slideDurationTime * -getTranslationX() / (getVisibleX() + getVisibleWidth()) : slideDurationTime * (1 + getTranslationX() / (getVisibleX() + getVisibleWidth())));

            case ATTACH_ALIGN_TOP:
                return (long) (isSlideIn ? slideDurationTime * -getTranslationY() / (getVisibleY() + getVisibleHeight()) : slideDurationTime * (1 + getTranslationY() / (getVisibleY() + getVisibleHeight())));

            case ATTACH_ALIGN_RIGHT:
                return (long) (isSlideIn ? slideDurationTime * getTranslationX() / (((SlideSidebarContainerFrame) getParent()).getWidth() - getVisibleX()) : slideDurationTime * (1 - getTranslationX() / (((SlideSidebarContainerFrame) getParent()).getWidth() - getVisibleX())));

            case ATTACH_ALIGN_BOTTOM:
                return (long) (isSlideIn ? slideDurationTime * getTranslationY() / (((SlideSidebarContainerFrame) getParent()).getHeight() - getVisibleY()) : slideDurationTime * (1 - getTranslationY() / (((SlideSidebarContainerFrame) getParent()).getHeight() - getVisibleY())));

            default:
                return -1;
        }
    }

    @Override
    protected void onAutoSlide(boolean isSlideIn, long slideDurationTime, long autoSlideStartTime, long autoSlideDurationTime, long callbackBeginTime) {
        float slideTime = (slideDurationTime - autoSlideDurationTime + callbackBeginTime - autoSlideStartTime) / (float) slideDurationTime;

        switch (getAttachAlign()) {
            case ATTACH_ALIGN_LEFT: {
                float slideX = (getVisibleX() + getVisibleWidth()) * slideTime;
                setTranslationX(isSlideIn ? -getVisibleX() - getVisibleWidth() + slideX : -slideX);
            }
            break;

            case ATTACH_ALIGN_TOP: {
                float slideY = (getVisibleY() + getVisibleHeight()) * slideTime;
                setTranslationY(isSlideIn ? -getVisibleY() - getVisibleHeight() + slideY : -slideY);
            }
            break;

            case ATTACH_ALIGN_RIGHT: {
                float slideX = (((SlideSidebarContainerFrame) getParent()).getWidth() - getVisibleX()) * slideTime;
                setTranslationX(isSlideIn ? ((SlideSidebarContainerFrame) getParent()).getWidth() - getVisibleX() - slideX : slideX);
            }
            break;

            case ATTACH_ALIGN_BOTTOM: {
                float slideY = (((SlideSidebarContainerFrame) getParent()).getHeight() - getVisibleY()) * slideTime;
                setTranslationY(isSlideIn ? ((SlideSidebarContainerFrame) getParent()).getHeight() - getVisibleY() - slideY : slideY);
            }
            break;

            default:
                break;
        }
    }
}
