package com.khatwa.zilalalrahmaapp.Donation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.khatwa.zilalalrahmaapp.R;

import androidx.fragment.app.Fragment;

public class DonationFragment extends Fragment implements DonationContract.View {

    private View view;
    private EditText editTextAmount;
    private RadioGroup radioGroupSIMType;
    private DonationContract.Presenter presenter;
    public DonationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_donation, container, false);
        radioGroupSIMType = view.findViewById(R.id.radioGroupSIMType);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        Button buttonDonation = view.findViewById(R.id.buttonDonation);
        presenter = new DonationPresenter(this);

        buttonDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUSSD(getCompanyName(),editTextAmount.getText().toString(),"0000"); // todo code parameter for zain users
            }
        });
        return view;
    }

    @Override
    public void showDialer(String USSD) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(ussdToCallableUri(USSD));
        startActivity(intent);
    }

    @Override
    public void showInputError(String errorMessage) {
        Toast.makeText(getActivity(),errorMessage, Toast.LENGTH_LONG).show();
    }

    private String getCompanyName() {
        if (radioGroupSIMType.getCheckedRadioButtonId() == view.findViewById(R.id.radioButtonSudani).getId()) {
            return "Sudani";
        }
        else if (radioGroupSIMType.getCheckedRadioButtonId() == view.findViewById(R.id.radioButtonMTN).getId()) {
            return "MTN";
        }
        else if (radioGroupSIMType.getCheckedRadioButtonId() == view.findViewById(R.id.radioButtonZain).getId()) {
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
}
