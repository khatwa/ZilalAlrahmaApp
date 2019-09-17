package com.khatwa.zilalalrahmaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DonationActivity extends AppCompatActivity {

    private int amount = 0;
    private EditText editTextAmount;
    private RadioGroup radioGroupSIMType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        radioGroupSIMType = findViewById(R.id.radioGroupSIMType);
        editTextAmount = findViewById(R.id.editTextAmount);
        Button buttonDonation = findViewById(R.id.buttonDonation);

        buttonDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataValid()) {
                    amount = Integer.parseInt(editTextAmount.getText().toString());
                    radioSelect();
                } else {
                    Toast.makeText(getApplication(), "invalid data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isDataValid() {
        return !editTextAmount.getText().toString().equals("") && radioGroupSIMType.getCheckedRadioButtonId() != -1;
    }

    private void radioSelect() {
        if (amount != 0) {
            if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonSudani).getId()) {
                sendToSudani(amount);
            }
            else if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonMTN).getId()) {
                sendToMTN(amount);
            }
            else if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonZain).getId()) {
                  sendToZain("0000", amount);
            }

        }
        else
            Toast.makeText(getApplication(), "can not send amount of '0' ", Toast.LENGTH_LONG).show();
    }


    public void sendToSudani(int amount) {
        String userTelephoneNumber = "0116557824";
        String USSD = "*303*" + amount + "*" + userTelephoneNumber + "*0000#";
        Log.e("ussd ", USSD);
        runUSSD(USSD);
    }

    public void sendToMTN(int amount) {
        String userTelephoneNumber = "0967880410";
        String USSD = "*121*" + userTelephoneNumber + "*" + amount + "*00000#";
        runUSSD(USSD);
    }

    public void sendToZain(String code, int amount) {
        String userTelephoneNumber = "096xxxxxxx";
        String USSD = "*200*" + code + "*" + amount + "*" + userTelephoneNumber + "*" + userTelephoneNumber + "#";
        runUSSD(USSD);
    }

    private void runUSSD(String USSD) {
        String ussdCode = USSD;
        // Intent intent = new Intent(Intent.ACTION_CALL);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(ussdToCallableUri(ussdCode));
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if (!ussd.startsWith("tel:"))
            uriString += "tel:";

        for (char c : ussd.toCharArray()) {

            if (c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }

        return Uri.parse(uriString);
    }

}
