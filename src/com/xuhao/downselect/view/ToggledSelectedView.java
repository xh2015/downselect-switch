package com.xuhao.downselect.view;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义开关
 * 
 * @author json
 * 
 */
public class ToggledSelectedView extends View {
	private ToggleState toggleState = ToggleState.Open;// 开关状态
	private Bitmap sildeBg;
	private Bitmap switchBg;
	private int currentX;
	private boolean silding = false;

	// 如果view想要在java代码中动态创建，走这个方法
	public ToggledSelectedView(Context context) {
		super(context);
	}

	// 如果是在布局中使用只需要重写这个狗仔方法
	public ToggledSelectedView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public enum ToggleState {
		Open, Close;
	}

	/**
	 * 设置滑动背景
	 * 
	 * @param on
	 */
	public void setSildeBackgroundResource(int sildeBackground) {
		sildeBg = BitmapFactory.decodeResource(getResources(), sildeBackground);
	}

	/**
	 * 设置滑动块背景
	 * 
	 * @param off
	 */
	public void setSwitchBackgroundResource(int switchBackground) {
		switchBg = BitmapFactory.decodeResource(getResources(),
				switchBackground);
	}

	/**
	 * 设置开关状态
	 * 
	 * @param open
	 */
	public void setToggleState(ToggleState state) {
		toggleState = state;
	}

	/**
	 * 1.测量
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(sildeBg.getWidth(), sildeBg.getHeight());

	}

	/**
	 * 3.绘制
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 1.绘制背景图片
		canvas.drawBitmap(sildeBg, 0, 0, null);
		// 2.绘制开关
		if (silding) {
			int left = currentX - switchBg.getWidth() / 2;
			if (left < 0)
				left = 0;
			if (left > sildeBg.getWidth() - switchBg.getWidth()) {
				left = sildeBg.getWidth() - switchBg.getWidth();
			}
			canvas.drawBitmap(switchBg, left, 0, null);
		} else {

			if (toggleState == ToggleState.Open) {
				canvas.drawBitmap(switchBg,
						sildeBg.getWidth() - switchBg.getWidth(), 0, null);
			} else {
				canvas.drawBitmap(switchBg, 0, 0, null);
			}

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		currentX = (int) event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			silding = true;
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			silding = false;
			int centerX = sildeBg.getWidth() / 2;
			if (currentX > centerX) {
				if (toggleState != ToggleState.Open) {
					toggleState = ToggleState.Open;
					if (buttonStateListener != null) {
						buttonStateListener
								.setOnToggleButtonChange(toggleState);
					}
				}

			} else {
				if (toggleState != ToggleState.Close) {
					toggleState = ToggleState.Close;
					if (buttonStateListener != null) {
						buttonStateListener
								.setOnToggleButtonChange(toggleState);
					}
				}

			}
			break;
		}
		invalidate();
		return true;
	}

	private OnToggleButtonStateListener buttonStateListener;

	public void setOnToggleButtonChangeListener(
			OnToggleButtonStateListener buttonStateListener) {
		this.buttonStateListener = buttonStateListener;
	};

	// 通过自定义接口把数据传递给调用者类似于观察者模式
	public interface OnToggleButtonStateListener {
		void setOnToggleButtonChange(ToggleState state);
	}

}
