package com.khatwa.zilalalrahmaapp.ui.Donation;

public interface DonationContract {

   public interface View {
        void showDialer(String USSD);
       void showInputError(String errorMessage);
    }

  public interface Presenter{
        void sendUSSD(String companyName , String amount,String code);  // code parameter for Zain only
        void dropView();
    }
}
