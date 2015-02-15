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
	 * ͼƬ��Դ��ID
	 */
	private final int[] imageIds = {
			R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.p,
	};
	 //ͼƬ���⼯��
		private final String[] imageDescriptions = {
				"���������ף��ҾͲ��ܵ���",
				"�����ֻ��������ٳ������ϸ������˴�ϳ�",
				"���ر�����Ӱ�������",
				"������TV�������",
				"��Ѫ��˿�ķ�ɱ"
		};
		
	private ArrayList<ImageView> imageList;
	/**
	 * ��һ��ҳ��
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
			
			//��ʼ��ͼƬ��Դ
			ImageView image = new ImageView(this);
			image.setBackgroundResource(imageIds[i]);
			imageList.add(image);

			//���ָʾ��
			ImageView point = new ImageView(this);
			/**
			 * ������Ҫ���View��ʲô���־ʹ���ʲô���֣�������ӽ�ȥ
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
			 * ҳ���л������
			 * position �µ�ҳ��λ��
			 */
			@Override
			public void onPageSelected(int position) {
				position = position%imageList.size();
				//����������������
				imageDesc.setText(imageDescriptions[position]);
				//�ı�ָʾ���״̬
				//�ѵ�ǰ��enbale Ϊtrue
				pointGroup.getChildAt(position).setEnabled(true);
				//����һ��������Ϊfale
				pointGroup.getChildAt(lastPointPosition).setEnabled(false);
				lastPointPosition =position;
			}
			
			/**
			 * ҳ�����ڻ�����ʱ��ص�
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			/**
			 * ��ҳ��ת̬�����仯��ʱ�򣬻ص�
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		/**
		 * �Զ�ѭ��
		 * 1��ʱ����Timer
		 * 2.�����̣߳�while trueѭ��
		 * ColckManager
		 * 4��handler ������ʱ��Ϣ,ʵ��ѭ��
		 */
		isRunning =true;
		handler.sendEmptyMessageDelayed(0, 2000);
	}
	/**
	 * �ж��Ƿ��Զ�����
	 */
	private boolean isRunning = false;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//��������һҳ
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
		// ���ҳ�������
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		
		/**
		 * ��ȡ��Ӧλ�õ�view
		 * container view����������ʵ����ViewPage����
		 * position ��Ӧ��λ��
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//��container�������
			container.addView(imageList.get(position%imageList.size()));
			
			return imageList.get(position%imageList.size());
		}
		@Override
		/**
		 * �ж�view��object�Ķ�Ӧ��ϵ
		 */
		public boolean isViewFromObject(View view, Object object) {
			return view == object;//�ж���û�ж�Ӧ�Ĺ�ϵ���Ƿ����
		}
		@Override
		/**
		 * ���ٶ�Ӧλ���ϵ�object
		 */
		public void destroyItem(ViewGroup container, int position, Object object) {

//			super.destroyItem(container, position, object);
			container.removeView((View) object);
			object = null;//���ͷ�
		}

	}
}
