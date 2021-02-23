/**
 * FragmentTrack - Java Class for Android
 * Created by G.Capelli (BasicAirData) on 4/6/2016
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.fbradasc.trekking.gpslogger;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentTrack extends Fragment {

    private PhysicalDataFormatter phdformatter = new PhysicalDataFormatter();

    private TextView TVDuration;
    private TextView TVTrackName;
    private TextView TVTrackID;
    private TextView TVDistance;
    private TextView TVDistanceUM;
    private TextView TVMaxSpeed;
    private TextView TVMaxSpeedUM;
    private TextView TVAverageSpeed;
    private TextView TVAverageSpeedUM;
    private TextView TVAltitudeGap;
    private TextView TVAltitudeGapUM;
    private TextView TVAltitudeMin;
    private TextView TVAltitudeMinUM;
    private TextView TVAltitudeMax;
    private TextView TVAltitudeMaxUM;
    private TextView TVAltitudeUp;
    private TextView TVAltitudeUpUM;
    private TextView TVAltitudeDown;
    private TextView TVAltitudeDownUM;
    private TextView TVOverallDirection;
    private TextView TVTrackStatus;
    private TextView TVDirectionUM;

    private TableLayout TLTrack;
    private TableLayout TLDuration;
    private TableLayout TLSpeedMax;
    private TableLayout TLSpeedAvg;
    private TableLayout TLDistance;
    private TableLayout TLAltitudeGap;
    private TableLayout TLAltitudeMin;
    private TableLayout TLAltitudeMax;
    private TableLayout TLAltitudeUp;
    private TableLayout TLAltitudeDown;
    private TableLayout TLOverallDirection;

    private PhysicalData phdDuration;
    private PhysicalData phdSpeedMax;
    private PhysicalData phdSpeedAvg;
    private PhysicalData phdDistance;
    private PhysicalData phdAltitudeGap;
    private PhysicalData phdAltitudeMin;
    private PhysicalData phdAltitudeMax;
    private PhysicalData phdAltitudeUp;
    private PhysicalData phdAltitudeDown;
    private PhysicalData phdOverallDirection;

    private String FTrackID = "";
    private String FTrackName = "";

    final GPSApplication gpsApplication = GPSApplication.getInstance();


    public FragmentTrack() {
        // Required empty public constructor
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onEvent(Short msg) {
        if (msg == EventBusMSG.UPDATE_TRACK) {
            Update();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);

        // TextViews
        TVDuration          = view.findViewById(R.id.id_textView_Duration);
        TVTrackID           = view.findViewById(R.id.id_textView_TrackIDLabel);
        TVTrackName         = view.findViewById(R.id.id_textView_TrackName);
        TVDistance          = view.findViewById(R.id.id_textView_Distance);
        TVMaxSpeed          = view.findViewById(R.id.id_textView_SpeedMax);
        TVAverageSpeed      = view.findViewById(R.id.id_textView_SpeedAvg);
        TVAltitudeGap       = view.findViewById(R.id.id_textView_AltitudeGap);
        TVAltitudeMin       = view.findViewById(R.id.id_textView_AltitudeMin);
        TVAltitudeMax       = view.findViewById(R.id.id_textView_AltitudeMax);
        TVAltitudeUp        = view.findViewById(R.id.id_textView_AltitudeUp);
        TVAltitudeDown      = view.findViewById(R.id.id_textView_AltitudeDown);
        TVOverallDirection  = view.findViewById(R.id.id_textView_OverallDirection);
        TVTrackStatus       = view.findViewById(R.id.id_textView_TrackStatus);
        TVDirectionUM       = view.findViewById(R.id.id_textView_OverallDirectionUM);
        TVDistanceUM        = view.findViewById(R.id.id_textView_DistanceUM);
        TVMaxSpeedUM        = view.findViewById(R.id.id_textView_SpeedMaxUM);
        TVAverageSpeedUM    = view.findViewById(R.id.id_textView_SpeedAvgUM);
        TVAltitudeGapUM     = view.findViewById(R.id.id_textView_AltitudeGapUM);
        TVAltitudeMinUM     = view.findViewById(R.id.id_textView_AltitudeMinUM);
        TVAltitudeMaxUM     = view.findViewById(R.id.id_textView_AltitudeMaxUM);
        TVAltitudeUpUM      = view.findViewById(R.id.id_textView_AltitudeUpUM);
        TVAltitudeDownUM    = view.findViewById(R.id.id_textView_AltitudeDownUM);

        // TableLayouts
        TLTrack             = view.findViewById(R.id.id_tableLayout_TrackName);
        TLDuration          = view.findViewById(R.id.id_tableLayout_Duration);
        TLSpeedMax          = view.findViewById(R.id.id_tableLayout_SpeedMax);
        TLDistance          = view.findViewById(R.id.id_tableLayout_Distance);
        TLSpeedAvg          = view.findViewById(R.id.id_tableLayout_SpeedAvg);
        TLAltitudeGap       = view.findViewById(R.id.id_tableLayout_AltitudeGap);
        TLAltitudeMin       = view.findViewById(R.id.id_tableLayout_AltitudeMin);
        TLAltitudeMax       = view.findViewById(R.id.id_tableLayout_AltitudeMax);
        TLAltitudeUp        = view.findViewById(R.id.id_tableLayout_AltitudeUp);
        TLAltitudeDown      = view.findViewById(R.id.id_tableLayout_AltitudeDown);
        TLOverallDirection  = view.findViewById(R.id.id_tableLayout_OverallDirection) ;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Workaround for Nokia Devices, Android 9
        // https://github.com/BasicAirData/GPSLogger/issues/77
        if (EventBus.getDefault().isRegistered(this)) {
            //Log.w("myApp", "[#] FragmentTrack.java - EventBus: FragmentTrack already registered");
            EventBus.getDefault().unregister(this);
        }

        EventBus.getDefault().register(this);
        Update();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    private Track track;
    private int prefDirections;
    private boolean EGMAltitudeCorrection;
    private boolean isValidAltitude;

    public void Update() {
        track = gpsApplication.getCurrentTrack();
        prefDirections = gpsApplication.getPrefShowDirections();
        EGMAltitudeCorrection = gpsApplication.getPrefEGM96AltitudeCorrection();

        if (isAdded()) {
            if ((track != null) && (track.getNumberOfItems() > 0)) {

                FTrackID = getString(R.string.track_id) + " " + String.valueOf(track.getId());
                FTrackName = track.getName();
                phdDuration = phdformatter.format(track.getPrefTime(),PhysicalDataFormatter.FORMAT_DURATION);
                phdSpeedMax = phdformatter.format(track.getSpeedMax(),PhysicalDataFormatter.FORMAT_SPEED);
                phdSpeedAvg = phdformatter.format(track.getPrefSpeedAverage(),PhysicalDataFormatter.FORMAT_SPEED_AVG);
                phdDistance = phdformatter.format(track.getPrefEstimatedDistance(),PhysicalDataFormatter.FORMAT_DISTANCE);
                phdAltitudeGap = phdformatter.format(track.getEstimatedAltitudeGap(EGMAltitudeCorrection),PhysicalDataFormatter.FORMAT_ALTITUDE);
                phdAltitudeMin = phdformatter.format(track.getEstimatedAltitudeMin(EGMAltitudeCorrection),PhysicalDataFormatter.FORMAT_ALTITUDE);
                phdAltitudeMax = phdformatter.format(track.getEstimatedAltitudeMax(EGMAltitudeCorrection),PhysicalDataFormatter.FORMAT_ALTITUDE);
                phdAltitudeUp  = phdformatter.format(track.getEstimatedAltitudeUp(EGMAltitudeCorrection),PhysicalDataFormatter.FORMAT_ALTITUDE);
                phdAltitudeDown = phdformatter.format(track.getEstimatedAltitudeDown(EGMAltitudeCorrection),PhysicalDataFormatter.FORMAT_ALTITUDE);
                phdOverallDirection = phdformatter.format(track.getBearing(),PhysicalDataFormatter.FORMAT_BEARING);

                TVTrackID.setText(FTrackID);
                TVTrackName.setText(FTrackName);
                TVDuration.setText(phdDuration.Value);
                TVMaxSpeed.setText(phdSpeedMax.Value);
                TVAverageSpeed.setText(phdSpeedAvg.Value);
                TVDistance.setText(phdDistance.Value);
                TVAltitudeGap.setText(phdAltitudeGap.Value);
                TVAltitudeMin.setText(phdAltitudeMin.Value);
                TVAltitudeMax.setText(phdAltitudeMax.Value);
                TVAltitudeUp.setText(phdAltitudeUp.Value);
                TVAltitudeDown.setText(phdAltitudeDown.Value);
                TVOverallDirection.setText(phdOverallDirection.Value);

                TVMaxSpeedUM.setText(phdSpeedMax.UM);
                TVAverageSpeedUM.setText(phdSpeedAvg.UM);
                TVDistanceUM.setText(phdDistance.UM);
                TVAltitudeGapUM.setText(phdAltitudeGap.UM);
                TVAltitudeMinUM.setText(phdAltitudeMin.UM);
                TVAltitudeMaxUM.setText(phdAltitudeMax.UM);
                TVAltitudeUpUM.setText(phdAltitudeUp.UM);
                TVAltitudeDownUM.setText(phdAltitudeDown.UM);

                // Colorize the Altitude Gap textview depending on the altitude filter
                isValidAltitude = track.isValidAltitude();
                TVAltitudeGap.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeGapUM.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeMin.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeMinUM.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeMax.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeMaxUM.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeUp.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeUpUM.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeDown.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));
                TVAltitudeDownUM.setTextColor(isValidAltitude ? getResources().getColor(R.color.textColorPrimary) : getResources().getColor(R.color.textColorSecondary));

                TVTrackStatus.setVisibility(View.INVISIBLE);

                TVDirectionUM.setVisibility(prefDirections == 0 ? View.GONE : View.VISIBLE);

                TLTrack.setVisibility(FTrackName.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLDuration.setVisibility(phdDuration.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLSpeedMax.setVisibility(phdSpeedMax.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLSpeedAvg.setVisibility(phdSpeedAvg.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLDistance.setVisibility(phdDistance.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLOverallDirection.setVisibility(phdOverallDirection.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLAltitudeGap.setVisibility(phdAltitudeGap.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLAltitudeMin.setVisibility(phdAltitudeMin.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLAltitudeMax.setVisibility(phdAltitudeMax.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLAltitudeUp.setVisibility(phdAltitudeUp.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
                TLAltitudeDown.setVisibility(phdAltitudeDown.Value.equals("") ? View.INVISIBLE : View.VISIBLE);

            } else {
                TVTrackStatus.setVisibility(View.VISIBLE);

                TLTrack.setVisibility(View.INVISIBLE);
                TLDuration.setVisibility(View.INVISIBLE);
                TLSpeedMax.setVisibility(View.INVISIBLE);
                TLSpeedAvg.setVisibility(View.INVISIBLE);
                TLDistance.setVisibility(View.INVISIBLE);
                TLOverallDirection.setVisibility(View.INVISIBLE);
                TLAltitudeGap.setVisibility(View.INVISIBLE);
                TLAltitudeMin.setVisibility(View.INVISIBLE);
                TLAltitudeMax.setVisibility(View.INVISIBLE);
                TLAltitudeUp.setVisibility(View.INVISIBLE);
                TLAltitudeDown.setVisibility(View.INVISIBLE);
            }
        }
    }
}
