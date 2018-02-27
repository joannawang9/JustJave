package com.example.android.justjave;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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

    int numberOfCoffees = 2;
    CheckBox check = (CheckBox) findViewById(R.id.checkbox);
    boolean hasCream = check.isChecked();
    CheckBox check2 = (CheckBox) findViewById(R.id.checkbox2);
    boolean hasChocolate = check2.isChecked();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (numberOfCoffees == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees.", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees = numberOfCoffees + 1;
        display(numberOfCoffees);
    }

    public void decrement(View view) {
        if (numberOfCoffees == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees = numberOfCoffees - 1;
        display(numberOfCoffees);
    }

    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.name);
        String name = text.getText().toString();
        int price = calculatePrice(hasCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean hasCream, boolean hasChocolate) {
        this.hasChocolate = hasChocolate;
        this.hasCream = hasCream;
        int basePrice = 5;
        if (hasCream) {
            basePrice = basePrice + 1;
        }
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        return numberOfCoffees * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream?" + addCream;
        priceMessage += "\nAdd chocolate?" + addChocolate;
        priceMessage += "\nQuantity: " + numberOfCoffees;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}