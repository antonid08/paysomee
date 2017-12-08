package com.paysomee.terminal.ui;

import com.paysomee.terminal.R;
import com.paysomee.terminal.ui.payment.PaymentActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity with field to input amount of payment and button to process payment.
 *
 * @author antonid08
 */
public class MainActivity extends AppCompatActivity {

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

        sum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null
                    && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    PaymentActivity.start(MainActivity.this, Float.valueOf(sum.getText().toString()));
                }
                return false;
            }
        });
    }

}
