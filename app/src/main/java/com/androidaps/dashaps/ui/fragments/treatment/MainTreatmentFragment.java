package com.androidaps.dashaps.ui.fragments.treatment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidaps.dashaps.DashAapsService;
import com.androidaps.dashaps.MainActivity;
import com.androidaps.dashaps.R;
import com.androidaps.dashaps.data.Pod;
import com.androidaps.dashaps.enums.PodState;
import com.androidaps.dashaps.ui.command.CancelBolusUiCommand;
import com.androidaps.dashaps.ui.command.CancelTBRUiCommand;
import com.androidaps.dashaps.ui.command.PodCommandQueueUi;
import com.androidaps.dashaps.ui.command.SetBolusUiCommand;
import com.androidaps.dashaps.ui.command.SetTBRUiCommand;
import com.androidaps.dashaps.ui.command.UiStatusType;

import info.nightscout.androidaps.utils.OKDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainTreatmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainTreatmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainTreatmentFragment extends Fragment {

    private static final String TAG = "MainTreatmentFragment";
    private OnFragmentInteractionListener mListener;
    private static MainTreatmentFragment instance;

    private Button buttonBolusStart;
    private Button buttonBolusCancel;
    private EditText bolusAmount;
    private Button buttonTbrStart;
    private EditText tbrAmount;
    private Button buttonTbrCancel;


    public MainTreatmentFragment() {
        // Required empty public constructor
    }

    public static MainTreatmentFragment getInstance() {
        return instance;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TreatmentFragment.
     */
    public static MainTreatmentFragment newInstance() {
        MainTreatmentFragment fragment = new MainTreatmentFragment();
        instance = fragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_treatment, container, false);

        buttonBolusStart = rootView.findViewById(R.id.buttonBolusStart);
        buttonBolusStart.setOnClickListener(v -> {
            if (bolusAmount.getText().toString().length() == 0) {
                Log.e(TAG, "Need to set amount.");
                OKDialog.show(MainActivity.getInstance(), "Warning", "You need to set the amount, before you can start bolus.", null);
            } else {
                buttonBolusStart.setEnabled(false);
                new SetBolusUiCommand(Double.valueOf(bolusAmount.getText().toString())).execute();
            }
        });

        buttonBolusCancel = rootView.findViewById(R.id.buttonBolusCancel);
        buttonBolusCancel.setOnClickListener(v -> {
            buttonBolusCancel.setEnabled(false);
            new CancelBolusUiCommand().execute();
        });
        buttonBolusCancel.setEnabled(false);

        bolusAmount = rootView.findViewById(R.id.bolusAmount);


        buttonTbrStart = rootView.findViewById(R.id.buttonTbrStart);
        buttonTbrStart.setOnClickListener(v -> {
            if (tbrAmount.getText().toString().length() == 0) {
                Log.e(TAG, "Need to set amount.");
                OKDialog.show(MainActivity.getInstance(), "Warning", "You need to set the amount, before you can start bolus.", null);
            } else {
                buttonTbrStart.setEnabled(false);
                new SetTBRUiCommand(Double.valueOf(tbrAmount.getText().toString())).execute();
            }
        });

        buttonTbrCancel = rootView.findViewById(R.id.buttonTbrCancel);
        buttonTbrCancel.setOnClickListener(v -> {
            buttonTbrCancel.setEnabled(false);
            new CancelTBRUiCommand().execute();
        });
        buttonTbrCancel.setEnabled(false);

        tbrAmount = rootView.findViewById(R.id.editTextTbrAmount);

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void processCommand(PodCommandQueueUi podCommandQueueUi) {
        podCommandQueueUi.updateUi(this);
    }

    public void processCommandFinished(PodCommandQueueUi podCommandQueueUi) {
        podCommandQueueUi.updateUiOnFinalize(this);
    }

    public void setBolus(UiStatusType statusType) {
        getActivity().runOnUiThread(() -> {

            if (statusType == UiStatusType.AllDisabled) {
                buttonBolusStart.setEnabled(false);
                buttonBolusCancel.setEnabled(false);
            } else if (statusType == UiStatusType.StartEnabled) {
                buttonBolusStart.setEnabled(true);
                buttonBolusCancel.setEnabled(false);
            } else if (statusType == UiStatusType.CancelEnabled) {
                buttonBolusStart.setEnabled(false);
                buttonBolusCancel.setEnabled(true);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        setPod(DashAapsService.pod);
    }


    public void setPod(Pod pod) {
        if (pod != null && pod.getPodStateObject() == PodState.Active) {
            setBolus(UiStatusType.StartEnabled);
            setTBR(UiStatusType.StartEnabled);
        } else {
            setBolus(UiStatusType.AllDisabled);
            setTBR(UiStatusType.AllDisabled);
        }
    }

    public void setTBR(UiStatusType statusType) {
        getActivity().runOnUiThread(() -> {

            if (statusType == UiStatusType.AllDisabled) {
                buttonTbrStart.setEnabled(false);
                buttonTbrCancel.setEnabled(false);
            } else if (statusType == UiStatusType.StartEnabled) {
                buttonTbrStart.setEnabled(true);
                buttonTbrCancel.setEnabled(false);
            } else if (statusType == UiStatusType.CancelEnabled) {
                buttonTbrStart.setEnabled(false);
                buttonTbrCancel.setEnabled(true);
            }
        });
    }


}
