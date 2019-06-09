package com.androidaps.dashaps.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

import com.androidaps.dashaps.DashAapsService;
import com.androidaps.dashaps.R;
import com.androidaps.dashaps.data.Pod;
import com.androidaps.dashaps.ui.util.DashUIUtil;

import org.joda.time.LocalDateTime;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {

    TextView timeView;
    private TextView textActivation;
    private TextView textAddress;
    private TextView textExpiration;
    private TextView textStatus;

    LocalDateTime podActivation;

    AbsoluteLayout[] sectionsView = new AbsoluteLayout[4];


    private OnFragmentInteractionListener mListener;

    private static OverviewFragment instance;
    private String TAG = "OverviewFragment";


    public static OverviewFragment getInstance() {
        return instance;
    }


    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment OverviewFragment.
     */
    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);

        timeView = rootView.findViewById(R.id.textTime);

        textActivation = rootView.findViewById(R.id.textActivation1);
        textAddress = rootView.findViewById(R.id.textAddress1);
        textExpiration = rootView.findViewById(R.id.textExpiration1);
        textStatus = rootView.findViewById(R.id.textStatus1);

        sectionsView[0] = (AbsoluteLayout)rootView.findViewById(R.id.timeView);
        sectionsView[1] = (AbsoluteLayout)rootView.findViewById(R.id.basalView);
        sectionsView[2] = (AbsoluteLayout)rootView.findViewById(R.id.treatmentsView);
        sectionsView[3] = (AbsoluteLayout)rootView.findViewById(R.id.podView);


        int width = rootView.getWidth();

        for (AbsoluteLayout absoluteLayout : sectionsView) {
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)absoluteLayout.getLayoutParams();

            Log.d(TAG, "Layout Parameters [x=" + layoutParams.x + ", y=" + layoutParams.y +
                    ", height=" + layoutParams.height + ", width=" + layoutParams.width);

            absoluteLayout.setLayoutParams(new AbsoluteLayout.LayoutParams(width-12, layoutParams.height, layoutParams.x, layoutParams.y));
            absoluteLayout.invalidate();
        }

        //resizeViewX(timeView, width);

        return rootView;
    }

    private void resizeViewX(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

//        Log.d(TAG, "Layout Parameters [x=" + layoutParams.x + ", y=" + layoutParams.y +
//                ", height=" + layoutParams.height + ", width=" + layoutParams.width);

        view.setLayoutParams(new ViewGroup.LayoutParams(width-12, layoutParams.height));
        //absoluteLayout.invalidate();
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

    @Override
    public void onResume() {
        super.onResume();
        if (DashAapsService.pod!=null) {
            setPod(DashAapsService.pod);
            setLocalDateTime(new LocalDateTime());
        }
    }

    public void setTime(int[] timeNew) {
        getActivity().runOnUiThread(() -> {
            timeView.setText(DashUIUtil.getDateTimeAsString(timeNew));
        });
    }

    // sets every minute
    public void setLocalDateTime(LocalDateTime ldt) {
        getActivity().runOnUiThread(() -> {
            if (podActivation!=null) {
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
        void onFragmentInteraction(Uri uri);
    }
}
