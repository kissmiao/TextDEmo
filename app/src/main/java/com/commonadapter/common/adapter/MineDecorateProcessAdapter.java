package com.commonadapter.common.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

public class MineDecorateProcessAdapter extends PagerAdapter {

private List<View> pages;
private int currentPosition;
private DecorateProgressSelectListener onSelect;


public void setOnSelect(DecorateProgressSelectListener onSelect) {
	this.onSelect = onSelect;
}


	public void setCurrentPosition(int currentPosition) {
	this.currentPosition = currentPosition;
}


	public int getCurrentPosition() {
		return currentPosition;
	}


	public void setPages(List<View> pages) {
		this.pages = pages;
		notifyDataSetChanged();
	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (position < pages.size()) {
			container.removeView(pages.get(position));
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final int current = position;
		
		container.addView(pages.get(position));
		return pages.get(position);
	}
	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	/**
	 * 设置一个Item显示多少个页面，如显示3个，就返回0.333f
	 */
//	@Override
//	public float getPageWidth(int position) {
//		// TODO Auto-generated method stub
//		return (float)1/3;
//	}
	
}
