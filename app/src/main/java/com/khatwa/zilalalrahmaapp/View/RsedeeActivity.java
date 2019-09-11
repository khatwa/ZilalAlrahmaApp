package com.khatwa.zilalalrahmaapp.View;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.khatwa.zilalalrahmaapp.Presenter.Rsedee;
import com.khatwa.zilalalrahmaapp.R;

public class RsedeeActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsedee);

        addListenerOnButton();

    }

    private void addListenerOnButton() {

        Button buttonPay = findViewById(R.id.buttonPay);

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                if (editTextPhoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(getApplication(),"Empty",Toast.LENGTH_LONG).show();
                }else {
                    if (editTextPhoneNumber.getText().toString().length() == 10){
                        radioSelect();
                    }else {
                        Toast.makeText(getApplication(),"Number is not right",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void radioSelect() {
        RadioGroup radioGroupSIMType = findViewById(R.id.radioGroupSIMType);
        //Toast.makeText(getApplication(),"is work",Toast.LENGTH_LONG).show();
        Rsedee rsedee = new Rsedee();
        if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonSudani).getId()){
            //TODO:Trans to Sudani
            Toast.makeText(getApplication(),"Sudain",Toast.LENGTH_LONG).show();
            rsedee.sendToSudani();
        }else if(radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonMTN).getId()){
            //TODO:Trans to MTN
            Toast.makeText(getApplication(),"MTN",Toast.LENGTH_LONG).show();
            rsedee.sendToMTN();

        }else if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonZain).getId()) {
            //TODO:trans to Zain
            Toast.makeText(getApplication(),"Zain",Toast.LENGTH_LONG).show();
            //rsedee.sendToZain(code);
        }else {
            Toast.makeText(getApplication(),"PLZ select one",Toast.LENGTH_LONG).show();
        }
    }

}
