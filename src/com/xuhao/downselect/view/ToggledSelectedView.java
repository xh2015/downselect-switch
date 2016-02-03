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
 * �Զ��忪��
 * 
 * @author json
 * 
 */
public class ToggledSelectedView extends View {
	private ToggleState toggleState = ToggleState.Open;// ����״̬
	private Bitmap sildeBg;
	private Bitmap switchBg;
	private int currentX;
	private boolean silding = false;

	// ���view��Ҫ��java�����ж�̬���������������
	public ToggledSelectedView(Context context) {
		super(context);
	}

	// ������ڲ�����ʹ��ֻ��Ҫ��д������з���
	public ToggledSelectedView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public enum ToggleState {
		Open, Close;
	}

	/**
	 * ���û�������
	 * 
	 * @param on
	 */
	public void setSildeBackgroundResource(int sildeBackground) {
		sildeBg = BitmapFactory.decodeResource(getResources(), sildeBackground);
	}

	/**
	 * ���û����鱳��
	 * 
	 * @param off
	 */
	public void setSwitchBackgroundResource(int switchBackground) {
		switchBg = BitmapFactory.decodeResource(getResources(),
				switchBackground);
	}

	/**
	 * ���ÿ���״̬
	 * 
	 * @param open
	 */
	public void setToggleState(ToggleState state) {
		toggleState = state;
	}

	/**
	 * 1.����
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(sildeBg.getWidth(), sildeBg.getHeight());

	}

	/**
	 * 3.����
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 1.���Ʊ���ͼƬ
		canvas.drawBitmap(sildeBg, 0, 0, null);
		// 2.���ƿ���
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

	// ͨ���Զ���ӿڰ����ݴ��ݸ������������ڹ۲���ģʽ
	public interface OnToggleButtonStateListener {
		void setOnToggleButtonChange(ToggleState state);
	}

}
