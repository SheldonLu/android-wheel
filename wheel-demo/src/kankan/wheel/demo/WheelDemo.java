package kankan.wheel.demo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class WheelDemo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        findViewById(R.id.btn_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WheelDemo.this,DateActivity.class);
                //启动方式为回调方式，100在回调函数体现      （onActivityResult ）
                startActivityForResult(intent,100);
            }
        });
        findViewById(R.id.btn_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WheelDemo.this,TimeActivity.class);
                //启动方式为回调方式，101在回调函数体现      （onActivityResult ）
                startActivityForResult(intent,101);
            }
        });
    }

    //事件回调，取回选择的内容
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
        String time;
        if(resultCode==RESULT_OK){
           switch (requestCode){
               case 100:
//                   取回选择的日期 启动方式必须是  startActivityForResult(intent,100)  ，其中 100 对应 case 100
                   time=data.getStringExtra("time");
                   Toast.makeText(WheelDemo.this,"你选择的日期："+time, Toast.LENGTH_SHORT).show();
                   break;
               case 101:
//                   取回选择的日期 启动方式必须是  startActivityForResult(intent,101)  ，其中 101 对应 case 101
                   time=data.getStringExtra("time");
                   Toast.makeText(WheelDemo.this,"你选择的时间："+time, Toast.LENGTH_SHORT).show();
                   break;
           }
        }

    }
}
