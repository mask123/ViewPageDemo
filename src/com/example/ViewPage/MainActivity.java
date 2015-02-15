package com.example.ViewPage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager viewPager;

	private LinearLayout pointGroup;

	private TextView imageDesc;
	
	/**
	 * 图片资源的ID
	 */
	private final int[] imageIds = {
			R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.p,
	};
	 //图片标题集合
		private final String[] imageDescriptions = {
				"巩俐不低俗，我就不能低俗",
				"扑树又回来啦！再唱经典老歌引万人大合唱",
				"揭秘北京电影如何升级",
				"乐视网TV版大派送",
				"热血屌丝的反杀"
		};
		
	private ArrayList<ImageView> imageList;
	/**
	 * 上一个页面
	 * 
	 */
	private int lastPointPosition;

	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pointGroup = (LinearLayout) findViewById(R.id.point_group);
		imageDesc = (TextView) findViewById(R.id.msg);
		imageDesc.setText(imageDescriptions[0]);
		imageList = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			
			//初始化图片资源
			ImageView image = new ImageView(this);
			image.setBackgroundResource(imageIds[i]);
			imageList.add(image);

			//添加指示点
			ImageView point = new ImageView(this);
			/**
			 * 当你想要添加View到什么布局就创建什么布局，并且添加进去
			 */
			int dp = DensityUtil.px2dip(this, 5);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp,dp);
			point.setLayoutParams(params);
			
			params.rightMargin = 20;
			point.setBackgroundResource(R.drawable.point_bg);
			if(i==0){
				
				point.setEnabled(true);
			}else{
			point.setEnabled(false);
			}
			pointGroup.addView(point);
		}
		viewPager.setAdapter(new MyPagerAdapter());
//		viewPager.setCurrentItem(Integer.MAX_VALUE - 3);
		viewPager.setCurrentItem(Integer.MAX_VALUE/2-(Integer.MIN_VALUE/2%imageList.size()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			
			/**
			 * 页面切换后调用
			 * position 新的页面位置
			 */
			@Override
			public void onPageSelected(int position) {
				position = position%imageList.size();
				//设置文字描述内容
				imageDesc.setText(imageDescriptions[position]);
				//改变指示点的状态
				//把当前点enbale 为true
				pointGroup.getChildAt(position).setEnabled(true);
				//把上一个点设置为fale
				pointGroup.getChildAt(lastPointPosition).setEnabled(false);
				lastPointPosition =position;
			}
			
			/**
			 * 页面正在滑动的时候回调
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			/**
			 * 当页面转态发生变化的时候，回调
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		/**
		 * 自动循环
		 * 1定时器，Timer
		 * 2.开子线程，while true循环
		 * ColckManager
		 * 4用handler 发送延时信息,实现循环
		 */
		isRunning =true;
		handler.sendEmptyMessageDelayed(0, 2000);
	}
	/**
	 * 判断是否自动滚动
	 */
	private boolean isRunning = false;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//滑动到下一页
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			if(isRunning){
				handler.sendEmptyMessageDelayed(0, 2000);
				
			}
		};
	};
	protected void onDestroy() {
		isRunning =false;
	};
	private class MyPagerAdapter extends PagerAdapter {
		// 获得页面的总数
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		
		/**
		 * 获取相应位置的view
		 * container view的容器，其实就是ViewPage自身
		 * position 相应的位置
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//给container添加内容
			container.addView(imageList.get(position%imageList.size()));
			
			return imageList.get(position%imageList.size());
		}
		@Override
		/**
		 * 判断view和object的对应关系
		 */
		public boolean isViewFromObject(View view, Object object) {
			return view == object;//判断有没有对应的关系，是否相等
		}
		@Override
		/**
		 * 销毁对应位置上的object
		 */
		public void destroyItem(ViewGroup container, int position, Object object) {

//			super.destroyItem(container, position, object);
			container.removeView((View) object);
			object = null;//被释放
		}

	}
}
