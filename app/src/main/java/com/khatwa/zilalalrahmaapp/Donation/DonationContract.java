package com.khatwa.zilalalrahmaapp.Donation;

public interface DonationContract {

    interface View {
        void showDialer(String USSD);
       void showInputError(String errorMessage);
    }

    interface Presenter{
        void sendUSSD(String companyName , String amount,String code);  // code parameter for Zain only
        void dropView();
    }
}
