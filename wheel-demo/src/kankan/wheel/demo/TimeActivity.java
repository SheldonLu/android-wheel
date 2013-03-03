package kankan.wheel.demo;

import java.util.Calendar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import android.app.Activity;
import android.os.Bundle;

public class TimeActivity extends Activity {

    private Button mBtnSeclet;
    private Button mBtnCancel;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.time_layout);
	
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
	
		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		mins.setCyclic(true);



		// set current time
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);
	
		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);
	
		// add listeners
//		addChangingListener(mins, "分");
//		addChangingListener(hours, "时");
	

		OnWheelClickedListener click = new OnWheelClickedListener() {
            public void onItemClicked(WheelView wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        hours.addClickingListener(click);
        mins.addClickingListener(click);

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
                String h=hours.getCurrentItem()+"";
                if(h.length()==1){
                    h="0"+h;
                }
                String m=mins.getCurrentItem()+"";
                if(m.length()==1){
                    m="0"+m;
                }
                intent.putExtra("time",h+":"+m);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
	}

//	/**
//	 * Adds changing listener for wheel that updates the wheel label
//	 * @param wheel the wheel
//	 * @param label the wheel label
//	 */
//	private void addChangingListener(final WheelView wheel, final String label) {
//		wheel.addChangingListener(new OnWheelChangedListener() {
//			public void onChanged(WheelView wheel, int oldValue, int newValue) {
//				wheel.setLabel(newValue != 1 ? label + "s" : label);
//			}
//		});
//	}
}
