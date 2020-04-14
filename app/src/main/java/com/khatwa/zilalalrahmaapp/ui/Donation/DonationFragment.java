package com.khatwa.zilalalrahmaapp.ui.Donation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.khatwa.zilalalrahmaapp.BuildConfig;
import com.khatwa.zilalalrahmaapp.MyApplication;
import com.khatwa.zilalalrahmaapp.R;
import com.khatwa.zilalalrahmaapp.di.component.DaggerDonationComponent;
import com.khatwa.zilalalrahmaapp.di.module.DonationMvpModule;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DonationFragment extends Fragment implements DonationContract.View {

    private static final String TAG = "DonationFragment";
    private static final String SUDANI_KEY = "sudani";
    private static final String ZAIN_KEY = "zain";
    private static final String MTN_KEY = "mtn";
    private View view;
    private EditText editTextAmount;
    private RadioGroup radioGroupSIMType;
    //  private DonationContract.Presenter presenter;

    @Inject
    DonationPresenter presenter;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private String sudaniPhoneNubmer;
    private String zainPhoneNubmer;
    private String mtnPhoneNubmer;

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

        DaggerDonationComponent.builder()
                .appComponent(MyApplication.get(getActivity()).component())
                .donationMvpModule(new DonationMvpModule(this))
                .build()
                .inject(this);
        radioGroupSIMType = view.findViewById(R.id.radioGroupSIMType);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        Button buttonDonation = view.findViewById(R.id.buttonDonation);
        // presenter = new DonationPresenter(this);    // replaced by dagger2 injection

        buttonDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRemoteConfigValues();
                presenter.sendUSSD(getCompanyName(), editTextAmount.getText().toString(), "0000"); // todo code parameter for zain users
            }
        });

        // Get Firebase Remote Config instance.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings.Builder configBuilder = new FirebaseRemoteConfigSettings.Builder();


        if (BuildConfig.DEBUG) {
            long cacheInterval = 0;  //For developer mode set cacheInterval 0  (zero) second
            configBuilder.setMinimumFetchIntervalInSeconds(cacheInterval);
        }

        mFirebaseRemoteConfig.setConfigSettingsAsync(configBuilder.build());

        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        sudaniPhoneNubmer = mFirebaseRemoteConfig.getString(SUDANI_KEY);
        zainPhoneNubmer = mFirebaseRemoteConfig.getString(ZAIN_KEY);
        mtnPhoneNubmer = mFirebaseRemoteConfig.getString(MTN_KEY);
        Log.e(TAG, "phones:" + sudaniPhoneNubmer + " | " + zainPhoneNubmer + " | " + mtnPhoneNubmer);

        getRemoteConfigValues();

        return view;
    }

    private void getRemoteConfigValues() {

        sudaniPhoneNubmer = mFirebaseRemoteConfig.getString(SUDANI_KEY);
        zainPhoneNubmer = mFirebaseRemoteConfig.getString(ZAIN_KEY);
        mtnPhoneNubmer = mFirebaseRemoteConfig.getString(MTN_KEY);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.e(TAG, "values updated: " + updated + sudaniPhoneNubmer + "  " + zainPhoneNubmer + "  " + mtnPhoneNubmer);

                        } else {
                            Log.e(TAG, task.getException().getMessage());
                        }

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
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private String getCompanyName() {
        if (radioGroupSIMType.getCheckedRadioButtonId() == view.findViewById(R.id.radioButtonSudani).getId()) {
            return "Sudani";
        } else if (radioGroupSIMType.getCheckedRadioButtonId() == view.findViewById(R.id.radioButtonMTN).getId()) {
            return "MTN";
        } else if (radioGroupSIMType.getCheckedRadioButtonId() == view.findViewById(R.id.radioButtonZain).getId()) {
            return "Zain";
        } else return "null";
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
