package com.khatwa.zilalalrahmaapp.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsedee);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        Button buttonPay = findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                radioSelect();
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
            sendToSudani();
        }else if(radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonMTN).getId()){
            //TODO:Trans to MTN
            Toast.makeText(getApplication(),"MTN",Toast.LENGTH_LONG).show();
            sendToMTN();

        }else if (radioGroupSIMType.getCheckedRadioButtonId() == findViewById(R.id.radioButtonZain).getId()) {
            //TODO:trans to Zain
            Toast.makeText(getApplication(),"Zain",Toast.LENGTH_LONG).show();
            sendToZain("0000");
        }else {
            Toast.makeText(getApplication(),"PLZ select one",Toast.LENGTH_LONG).show();
        }
    }


    

    public void sendToSudani() {
        String userTelephoneNumber = "0122170298";
        String USSD = "*333*" + "1" + "*" + userTelephoneNumber + "*0000#";
        runUSSD(USSD);
    }

    public void sendToMTN() {
        String userTelephoneNumber = "0967880410";
        String USSD = "*121*" + userTelephoneNumber + "*" + "1" + "*00000#";
        runUSSD(USSD);
    }

    public void sendToZain(String code) {
        String userTelephoneNumber = "096xxxxxxx";

        String USSD = "*200*" + code + "*" + "1" + "*" + userTelephoneNumber + "*" + userTelephoneNumber + "#";

        //
        runUSSD(USSD);
    }
    private void runUSSD(String USSD) {
        String ussdCode = USSD;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(ussdToCallableUri(ussdCode));
        try{
            startActivity(intent);
        } catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if(!ussd.startsWith("tel:"))
            uriString += "tel:";

        for(char c : ussd.toCharArray()) {

            if(c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }

        return Uri.parse(uriString);
    }
}
