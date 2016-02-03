package com.xuhao.downselect;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class DownSelectedActivity extends Activity implements OnClickListener {
	private EditText et;
	private ImageView iv;
	private ArrayList<String> lists = new ArrayList<String>();
	private int popwindowHeight = 300;
	private ListView listView;
	private MyBaseAdapter adapter;
	private PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initListener();
		initData();

	}

	private void initView() {
		setContentView(R.layout.activity_down_selected);
		et = (EditText) findViewById(R.id.et);
		iv = (ImageView) findViewById(R.id.iv);

	}

	private void initListener() {
		iv.setOnClickListener(this);		
	}

	private void initData() {
		for (int i = 0; i < 15; i++) {
			lists.add(90000 + i + "");
		}
		initListView();
	}

	private void initListView() {
		listView = new ListView(this);
		listView.setBackgroundColor(Color.WHITE);
		listView.setVerticalScrollBarEnabled(false);// Òþ²ØlistviewµÄ¹ö¶¯Ìõ
		adapter = new MyBaseAdapter();
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				et.setText(lists.get(position));
				popupWindow.dismiss();
			}
		});
	}

	private void showNumberLists() {
		if (popupWindow == null) {
			popupWindow = new PopupWindow(listView, et.getWidth(),
					popwindowHeight);
		}

		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		popupWindow.showAsDropDown(et, 0, 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv:
			showNumberLists();
			break;
		}
	}

	class MyBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final View view = View.inflate(DownSelectedActivity.this,
					R.layout.adapter_item, null);
			TextView tv = (TextView) view.findViewById(R.id.tv);
			ImageButton ivs = (ImageButton) view.findViewById(R.id.iv);
			tv.setText(lists.get(position));
			ivs.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					lists.remove(position);
					notifyDataSetChanged();

					int listviewHeight = lists.size() * view.getHeight();
					popupWindow.update(et.getWidth(),
							listviewHeight > popwindowHeight ? popwindowHeight
									: listviewHeight);

					if (lists.size() == 0) {
						popupWindow.dismiss();
						iv.setVisibility(View.GONE);
					}
				}
			});
			return view;
		}

		@Override
		public Object getItem(int position) {
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}

}
