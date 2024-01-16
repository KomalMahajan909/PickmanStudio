package com.itechvision.ecrobo.pickman.widget;



import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.itechvision.ecrobo.pickman.R;


public class RadioListView extends FrameLayout implements Checkable {

	protected RadioButton mRadioButton = null;
	protected String mValue = null;
	 
	public RadioListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RadioListView);
		initialize(a.getString(R.styleable.RadioListView_inner_layout), a.getString(R.styleable.RadioListView_select_radio));
	}
 
	public RadioListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RadioListView);
		initialize(a.getString(R.styleable.RadioListView_inner_layout), a.getString(R.styleable.RadioListView_select_radio));
	}
	
	public String getValue() {
		return mValue;
	}
	 
	protected void initialize(String rowLayoutIdName, String radioIdName) {
		// レイアウトを追加する
 		int id1 = this.getContext().getResources().getIdentifier(rowLayoutIdName, "layout", this.getContext().getPackageName());
		addView(inflate(this.getContext(), id1, null));
		if (radioIdName != null && "".equals(radioIdName) == false) {
			int id2= this.getContext().getResources().getIdentifier(radioIdName, "id", this.getContext().getPackageName());
			mRadioButton = (RadioButton) findViewById(id2);
		}
	}
 
	@Override
	public boolean isChecked() {
		if (mRadioButton != null) {
			mValue = mRadioButton.getText().toString();
			return mRadioButton.isChecked();
		} else {
			return false;
		}
	}
 
	@Override
	public void setChecked(boolean checked) {
		if (mRadioButton != null) {
			if (checked)
				mValue = mRadioButton.getText().toString();
			mRadioButton.setChecked(checked);
		}
	}
 
	@Override
	public void toggle() {
	}
}