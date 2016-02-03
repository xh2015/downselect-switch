package com.xuhao.downselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.xuhao.downselect.view.ToggledSelectedView;
import com.xuhao.downselect.view.ToggledSelectedView.OnToggleButtonStateListener;
import com.xuhao.downselect.view.ToggledSelectedView.ToggleState;

public class MainActivity extends Activity {
	private ToggledSelectedView tsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		setContentView(R.layout.activity_main);
		tsv = (ToggledSelectedView) findViewById(R.id.tsv);
	}

	private void initData() {
		tsv.setSildeBackgroundResource(R.drawable.on);
		tsv.setSwitchBackgroundResource(R.drawable.off);
		tsv.setToggleState(ToggleState.Open);
		tsv.setOnToggleButtonChangeListener(new OnToggleButtonStateListener() {

			@Override
			public void setOnToggleButtonChange(ToggleState state) {
				Toast.makeText(MainActivity.this,
						state == ToggleState.Open ? "¿ªÆô" : "¹Ø±Õ",
						Toast.LENGTH_SHORT).show();
				if(state == ToggleState.Open){
					MainActivity.this.startActivity(new Intent(MainActivity.this,DownSelectedActivity.class));
				}
			}
		});
	}

}
