package com.paysomee.terminal.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.paysomee.terminal.R;

/**
 * Activity with field to input amount of payment and button to process payment.
 *
 * @author antonid08
 */
public class MainActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent mainActivity = new Intent(context, MainActivity.class);
        context.startActivity(mainActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final EditText sum = findViewById(R.id.sum);

        findViewById(R.id.process).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentActivity.start(MainActivity.this, Float.valueOf(sum.getText().toString()));
            }
        });
    }

}
