package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 1;
    private boolean hasWhippedCream = false;
    private boolean hasChocolate = false;
    private String customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String createOrderSummary() {
        String summary =  getString(R.string.summary_name, customerName)+ "\n";
        summary += getString(R.string.summary_whipped_cream, ((hasWhippedCream)?getString(R.string.yes):getString(R.string.no))) + "\n";
        summary += getString(R.string.summary_chocolate, ((hasChocolate)?getString(R.string.yes):getString(R.string.no))) + "\n";
        summary += getString(R.string.summary_quantity, quantity) + "\n";
        summary += getString(R.string.summary_total, calculatePrice()) + "\n";
        summary += getString(R.string.summary_thank_you);
        return summary;
    }

    private int calculatePrice() {
        int price = 5;
        price += hasChocolate?2:0;
        price += hasWhippedCream?1:0;
        return price * quantity;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        hasChocolate = chocolate.isChecked();
        EditText name = (EditText) findViewById(R.id.customer_name_edit_text_view);
        customerName = name.getText().toString();

        sendIntent(createOrderSummary());
    }

    private void sendIntent(String orderSummary) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, customerName));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(this, getString(R.string.limit_100), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, getString(R.string.limit_1), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}