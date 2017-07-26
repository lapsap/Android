package net.nctucs.lapsap.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.view.ViewGroup;

public class activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        Intent intent = getIntent();
        String la_msg = intent.getStringExtra("lapsap.string");
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(la_msg);
    }




}
