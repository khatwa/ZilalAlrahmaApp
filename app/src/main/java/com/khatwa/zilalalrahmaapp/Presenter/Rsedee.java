package com.khatwa.zilalalrahmaapp.Presenter;

import android.content.Intent;
import android.net.Uri;

/**
 * To balance transfer
 */
public class Rsedee {


    //USSD start;
    private void startUSSD(String USSD) {
        //
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(ussdToCallableUri(USSD));

        //
        try {
            //ToDo: Put Activity here;
            //startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
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
    private void sendToSudani(int userTelephoneNumber, int transactionAmount) {

        //USSD to send;
        String USSD = "*333*" + transactionAmount + "*" + userTelephoneNumber + "*0000#";

        //
        startUSSD(USSD);
    }

    //MTN USSD transfer;
    private void sendToMTN(int userTelephoneNumber, int transactionAmount) {

        //USSD to send;
        String USSD = "*121*" + userTelephoneNumber + "*" + transactionAmount + "*00000#";

        //
        startUSSD(USSD);
    }

    //Zain USSD transfer;
    private void sendToZain(int userTelephoneNumber, int transactionAmount, int code) {

        //USSD to send;
        String USSD = "*200*" + code + "*" + transactionAmount + "*" + userTelephoneNumber + "*" + userTelephoneNumber + "#";

        //
        startUSSD(USSD);
    }
}
