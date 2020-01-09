package com.androidaps.dashaps.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidaps.dashaps.DashAapsService;
import com.androidaps.dashaps.R;
import com.androidaps.dashaps.data.Pod;
import com.androidaps.dashaps.enums.PodState;
import com.androidaps.dashaps.ui.command.ActivatePodUiCommand;
import com.androidaps.dashaps.ui.command.DeactivatePodUiCommand;
import com.androidaps.dashaps.ui.util.DashUIUtil;

import org.joda.time.LocalDateTime;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PodFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView textActivation;
    private TextView textAddress;
    private TextView textExpiration;
    private TextView textStatus;
    private TextView textPodVersion;
    private TextView textBLE;
    private TextView textLOT;

    LocalDateTime podActivation;

    private OnFragmentInteractionListener mListener;

    private static PodFragment instance;
    private Button buttonInitPod;
    private Button buttonDeactivatePod;


    public static PodFragment getInstance() {
        return instance;
    }

    public PodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PodFragment newInstance(String param1, String param2) {
        PodFragment fragment = new PodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        instance = fragment;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pod, container, false);

        textActivation = rootView.findViewById(R.id.textActivation);
        textAddress = rootView.findViewById(R.id.textAddress);
        textExpiration = rootView.findViewById(R.id.textExpiration);
        textStatus = rootView.findViewById(R.id.textStatus);
        textBLE = rootView.findViewById(R.id.textBLE);
        textLOT = rootView.findViewById(R.id.textLOT);
        textPodVersion = rootView.findViewById(R.id.textPodVersion);

        buttonInitPod = rootView.findViewById(R.id.buttonInitPod);
        buttonInitPod.setOnClickListener(this);
        buttonDeactivatePod = rootView.findViewById(R.id.buttonDeactivatePod);
        buttonDeactivatePod.setOnClickListener(this);
        buttonDeactivatePod.setEnabled(false);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onResume() {
        super.onResume();
        if (DashAapsService.pod!=null) {
            setPod(DashAapsService.pod);
            setLocalDateTime(new LocalDateTime());
        }
    }



    @Override
    public void onClick(View v) {

        Log.d("ddd", "On click view " + v );

        if (v.getId()==buttonInitPod.getId()) {
            ActivatePodUiCommand command = new ActivatePodUiCommand();
            command.execute();
        } else if (v.getId()==buttonDeactivatePod.getId()) {
            DeactivatePodUiCommand command = new DeactivatePodUiCommand();
            command.execute();
        }
    }


    // sets every minute
    public void setLocalDateTime(LocalDateTime ldt) {
        getActivity().runOnUiThread(() -> {
            if (this.podActivation!=null) {
                this.textExpiration.setText(DashUIUtil.getTimeDifference(this.podActivation, ldt));
            }
        });
    }

    // sets when pod is set
    public void setPod(Pod pod) {
        LocalDateTime ldt = new LocalDateTime(pod.getActivationTime());
        this.podActivation = ldt;

        getActivity().runOnUiThread(() -> {
            this.textAddress.setText(pod.getAddress());
            this.textStatus.setText(pod.getPodStateObject().name());
            this.textActivation.setText(DashUIUtil.getDateTimeAsString(ldt));
            this.textExpiration.setText(DashUIUtil.getTimeDifference(this.podActivation, this.podActivation));
            this.textBLE.setText(pod.getBleVersion());
            this.textPodVersion.setText(pod.getPodVersion());
            this.textLOT.setText(""+ pod.getLotNumber());
            if (pod.getPodStateObject()== PodState.Deactivated) {
                this.buttonInitPod.setEnabled(true);
                this.buttonDeactivatePod.setEnabled(false);
            } else {
                this.buttonInitPod.setEnabled(false);
                this.buttonDeactivatePod.setEnabled(true);
            }
        });
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
