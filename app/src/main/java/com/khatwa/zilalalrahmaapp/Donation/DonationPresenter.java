package com.khatwa.zilalalrahmaapp.Donation;

public class DonationPresenter implements DonationContract.Presenter {

    private DonationContract.View view;

    public DonationPresenter(DonationContract.View view) {
        this.view = view;
    }

    @Override
    public void sendUSSD(String companyName, String amount,String code) {
        if (!amount.equals("") && Integer.parseInt(amount) > 0 && !companyName.equals("null")) {
            String USSD="" ;
            if (companyName.equals("Sudani"))
            USSD = "*303*" + amount + "*" + "0000000000" + "*0000#"; // todo replace 0000000000 with real phone number

            if (companyName.equals("MTN"))
                USSD = "*121*" + "090000000" + "*" + amount + "*00000#";

            if (companyName.equals("Zain"))
             USSD = "*200*" + code + "*" + amount + "*" + "090000000" + "*" + "090000000" + "#";

            view.showDialer(USSD);
        }
        else {
            view.showInputError("check inputs");
        }
    }

    @Override
    public void dropView() {
        view = null;
    }
}
