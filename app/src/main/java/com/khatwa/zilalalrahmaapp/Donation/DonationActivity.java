package com.khatwa.zilalalrahmaapp.Donation;

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

import com.khatwa.zilalalrahmaapp.R;

public class DonationActivity extends AppCompatActivity implements DonationContract.View{

    private int amount = 0;
    private EditText editTextAmount;
    private RadioGroup radioGroupSIMType;
    private DonationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        radioGroupSIMType = findViewById(R.id.radioGroupSIMType);
        editTextAmount = findViewById(R.id.editTextAmount);
        Button buttonDonation = findViewById(R.id.buttonDonation);
        presenter = new DonationPresenter(this);

        buttonDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUSSD(getCompanyName(),editTextAmount.getText().toString(),"0000"); // code parameter for zain users
            }
        });
    }

    @Override
    public void showDialer(String USSD) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(ussdToCallableUri(USSD));
        startActivity(intent);
    }

    @Override
    public void showInputError(String errorMessage) {
        Toast.makeText(this,errorMessage, Toast.LENGTH_LONG).show();
    }

    private String getCompanyName() {
        if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonSudani).getId()) {
            return "Sudani";
        }
        else if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonMTN).getId()) {
            return "MTN";
        }
        else if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonZain).getId()) {
            return "Zain";
        }
        else return "null";
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }
}
