package com.paysomee.terminal.ui;

import java.util.ArrayList;
import java.util.List;

import com.paysomee.terminal.R;
import com.paysomee.terminal.ui.payment.PaymentActivity;
import com.paysomee.terminal.utils.Utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity with field to input amount of payment and button to process payment.
 *
 * @author antonid08
 */
public class MainActivity extends AppCompatActivity {

    private EditText sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        sum = findViewById(R.id.sum);

        findViewById(R.id.process).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPaymentActivity();
            }
        });

        sum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null
                    && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    showPaymentActivity();
                }
                return false;
            }
        });
    }

    private void showPaymentActivity() {
        List<String> errors = validateFields();
        if (errors.isEmpty()) {
            PaymentActivity.start(MainActivity.this, Float.valueOf(sum.getText().toString()));
        } else {
            Toast.makeText(MainActivity.this, Utils.listToString(errors, "\n"), Toast.LENGTH_SHORT).show();
        }
    }


    private List<String> validateFields() {
        List<String> errors = new ArrayList<>();

        if (sum.getText().toString().isEmpty()) {
            errors.add(getString(R.string.empty_sum_error));
        }

        return errors;
    }

}
