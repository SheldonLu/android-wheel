package kankan.wheel.demo;

import java.util.Calendar;


import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DateActivity extends Activity {
    private Button mBtnSeclet;
    private Button mBtnCancel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_layout);

        Calendar calendar = Calendar.getInstance();
//        创建月份组件
        final WheelView month = (WheelView) findViewById(R.id.month);
//        创建年份
        final WheelView year = (WheelView) findViewById(R.id.year);
//        创建天
        final WheelView day = (WheelView) findViewById(R.id.day);

//      天数监听，大小月份，二月份处理
        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
            }
        };

        // month
        int curMonth = calendar.get(Calendar.MONTH);
        String months[] = new String[] {"一月", "二月", "三月", "四月", "五月",
                "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);
    
        // year
        final int curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new DateNumericAdapter(this,curYear - 20, curYear + 20, 20));
        year.setCurrentItem(20);
        year.addChangingListener(listener);
        
        //day
        updateDays(year, month, day);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);

        mBtnCancel=(Button) findViewById(R.id.but_cancel);
        mBtnSeclet=(Button) findViewById(R.id.but_select);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBtnSeclet.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                Toast.makeText(DateActivity.this,(curYear-20+year.getCurrentItem())+"-"+(month.getCurrentItem()+1)+"-"+(day.getCurrentItem()+1),Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                String m=month.getCurrentItem()+1+"";
                if(m.length()==1){
                    m="0"+m;
                }
                String d=day.getCurrentItem()+1+"";
                if(d.length()==1){
                    d="0"+d;
                }
                intent.putExtra("time",(curYear-20+year.getCurrentItem())+"-"+m+"-"+d);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    
    /**
     * 更新时间组件
     * Updates day wheel. Sets max days according to selected month and year
     */
    void updateDays(WheelView year, WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());
        
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }
    
    /**
     * 时间数字数据适配器
     */
    private class DateNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(16);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
    
    /**
     * 时间数据适配器
     */
    private class DateArrayAdapter extends ArrayWheelAdapter<String> {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateArrayAdapter(Context context, String[] items, int current) {
            super(context, items);
            this.currentValue = current;
            setTextSize(16);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
}
