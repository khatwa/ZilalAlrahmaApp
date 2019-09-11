package com.khatwa.zilalalrahmaapp.Presenter;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.khatwa.zilalalrahmaapp.View.RsedeeActivity;

/**
 * To balance transfer
 */
public class Rsedee {
    private String userTelephoneNumber;
    private String transactionAmount;

    public Rsedee() {
        transactionAmount = "1";
    }

    //USSD start;
    private void startUSSD(String USSD) {
        checkPermission();

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(ussdToCallableUri(USSD));
        try {
            RsedeeActivity rsedeeActivity = new RsedeeActivity();
            rsedeeActivity.startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void checkPermission() {
        RsedeeActivity rsedeeActivity = new RsedeeActivity();
        if (ActivityCompat.checkSelfPermission(rsedeeActivity.getApplication(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_DENIED) {
            requestForSpecificPermission(rsedeeActivity);
        }
    }

    private void requestForSpecificPermission(RsedeeActivity rsedeeActivity) {
        ActivityCompat.requestPermissions(rsedeeActivity,new String[Integer.parseInt(Manifest.permission.CALL_PHONE)], 101);
    }


    //Format the USSD.
    private Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if (!ussd.startsWith("tel:")) {
            uriString += "tel:";
        }

        for (char c : ussd.toCharArray()) {

            if (c == '#') {
                uriString += Uri.encode("#");
            } else {
                uriString += c;
            }
        }

        return Uri.parse(uriString);
    }

    //Sudani USSD transfer;
    public void sendToSudani() {
        userTelephoneNumber = "0122170298";
        String USSD = "*333*" + transactionAmount + "*" + userTelephoneNumber + "*0000#";
        startUSSD(USSD);
    }

    //MTN USSD transfer;
    public void sendToMTN() {
        userTelephoneNumber = "0967880410";
        String USSD = "*121*" + userTelephoneNumber + "*" + transactionAmount + "*00000#";
        startUSSD(USSD);
    }

    //Zain USSD transfer;
    public void sendToZain(String code) {
        userTelephoneNumber = "0967880410";

        String USSD = "*200*" + code + "*" + transactionAmount + "*" + userTelephoneNumber + "*" + userTelephoneNumber + "#";

        //
        startUSSD(USSD);
    }
}
