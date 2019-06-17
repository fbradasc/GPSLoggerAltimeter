/**
 * FragmentPlacemarkDialog - Java Class for Android
 * Created by G.Capelli (BasicAirData) on 9/7/2016
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

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

public class FragmentPlacemarkDialog extends DialogFragment {

    EditText DescEditText;
    EditText Film_Data_EditText;
    EditText P_Data_Sv_EditText;
    EditText P_Data_Ev_EditText;
    EditText P_Data_Fv_EditText;
    EditText P_Data_Tv_EditText;
    EditText P_Data_Av_EditText;
    EditText P_Data_Z0_EditText;
    EditText P_Data_Z1_EditText;
    EditText P_Data_Z2_EditText;
    EditText P_Data_Z3_EditText;
    EditText P_Data_Z4_EditText;
    EditText P_Data_Z5_EditText;
    EditText P_Data_Z6_EditText;
    EditText P_Data_Z7_EditText;
    EditText P_Data_Z8_EditText;
    EditText P_Data_Z9_EditText;
    EditText P_Data_ZA_EditText;

    static String mDesc      = "";
    static String mFilm_Data = "";
    static String mP_Data_Sv = "";
    static String mP_Data_Ev = "";
    static String mP_Data_Fv = "";
    static String mP_Data_Tv = "";
    static String mP_Data_Av = "";
    static String mP_Data_Z0 = "";
    static String mP_Data_Z1 = "";
    static String mP_Data_Z2 = "";
    static String mP_Data_Z3 = "";
    static String mP_Data_Z4 = "";
    static String mP_Data_Z5 = "";
    static String mP_Data_Z6 = "";
    static String mP_Data_Z7 = "";
    static String mP_Data_Z8 = "";
    static String mP_Data_Z9 = "";
    static String mP_Data_ZA = "";

    //@SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder createPlacemarkAlert = new AlertDialog.Builder(getActivity(), R.style.StyledDialog);

        createPlacemarkAlert.setTitle(R.string.dlg_add_placemark);
        createPlacemarkAlert.setIcon(R.mipmap.ic_add_location_white_24dp);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = (View) inflater.inflate(R.layout.fragment_placemark_dialog, null);

        DescEditText       = (EditText) view.findViewById(R.id.placemark_description);
        Film_Data_EditText = (EditText) view.findViewById(R.id.film_data);
        P_Data_Sv_EditText = (EditText) view.findViewById(R.id.pdata_speed);
        P_Data_Ev_EditText = (EditText) view.findViewById(R.id.pdata_ev);
        P_Data_Fv_EditText = (EditText) view.findViewById(R.id.pdata_fv);
        P_Data_Tv_EditText = (EditText) view.findViewById(R.id.pdata_tv);
        P_Data_Av_EditText = (EditText) view.findViewById(R.id.pdata_av);
        P_Data_Z0_EditText = (EditText) view.findViewById(R.id.pdata_zs0);
        P_Data_Z1_EditText = (EditText) view.findViewById(R.id.pdata_zs1);
        P_Data_Z2_EditText = (EditText) view.findViewById(R.id.pdata_zs2);
        P_Data_Z3_EditText = (EditText) view.findViewById(R.id.pdata_zs3);
        P_Data_Z4_EditText = (EditText) view.findViewById(R.id.pdata_zs4);
        P_Data_Z5_EditText = (EditText) view.findViewById(R.id.pdata_zs5);
        P_Data_Z6_EditText = (EditText) view.findViewById(R.id.pdata_zs6);
        P_Data_Z7_EditText = (EditText) view.findViewById(R.id.pdata_zs7);
        P_Data_Z8_EditText = (EditText) view.findViewById(R.id.pdata_zs8);
        P_Data_Z9_EditText = (EditText) view.findViewById(R.id.pdata_zs9);
        P_Data_ZA_EditText = (EditText) view.findViewById(R.id.pdata_zs10);

        P_Data_Ev_EditText.postDelayed(new Runnable()
        {
            public void run()
            {
                if (isAdded()) {
                    if (!mFilm_Data.isEmpty()) Film_Data_EditText.setText(mFilm_Data);
                    if (!mP_Data_Sv.isEmpty()) P_Data_Sv_EditText.setText(mP_Data_Sv);
                    if (!mP_Data_Ev.isEmpty()) P_Data_Ev_EditText.setText(mP_Data_Ev);
                    if (!mP_Data_Tv.isEmpty()) P_Data_Tv_EditText.setText(mP_Data_Tv);
                    if (!mP_Data_Av.isEmpty()) P_Data_Av_EditText.setText(mP_Data_Av);
                    if (!mP_Data_Fv.isEmpty()) P_Data_Fv_EditText.setText(mP_Data_Fv);
                    if (!mP_Data_Z0.isEmpty()) P_Data_Z0_EditText.setText(mP_Data_Z0);
                    if (!mP_Data_Z1.isEmpty()) P_Data_Z1_EditText.setText(mP_Data_Z1);
                    if (!mP_Data_Z2.isEmpty()) P_Data_Z2_EditText.setText(mP_Data_Z2);
                    if (!mP_Data_Z3.isEmpty()) P_Data_Z3_EditText.setText(mP_Data_Z3);
                    if (!mP_Data_Z4.isEmpty()) P_Data_Z4_EditText.setText(mP_Data_Z4);
                    if (!mP_Data_Z5.isEmpty()) P_Data_Z5_EditText.setText(mP_Data_Z5);
                    if (!mP_Data_Z6.isEmpty()) P_Data_Z6_EditText.setText(mP_Data_Z6);
                    if (!mP_Data_Z7.isEmpty()) P_Data_Z7_EditText.setText(mP_Data_Z7);
                    if (!mP_Data_Z8.isEmpty()) P_Data_Z8_EditText.setText(mP_Data_Z8);
                    if (!mP_Data_Z9.isEmpty()) P_Data_Z9_EditText.setText(mP_Data_Z9);
                    if (!mP_Data_ZA.isEmpty()) P_Data_ZA_EditText.setText(mP_Data_ZA);
                    if (!mDesc     .isEmpty()) DescEditText      .setText(mDesc     );

                    P_Data_Ev_EditText.requestFocus();
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.showSoftInput(P_Data_Ev_EditText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }, 200);

        createPlacemarkAlert.setView(view)

                //.setPositiveButton(R.string.conti_nue, new DialogInterface.OnClickListener() {
                .setPositiveButton(R.string.dlg_button_add, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (isAdded()) {
                            mFilm_Data = Film_Data_EditText.getText().toString().trim();
                            mP_Data_Ev = P_Data_Ev_EditText.getText().toString().trim();
                            mP_Data_Tv = P_Data_Tv_EditText.getText().toString().trim();
                            mP_Data_Av = P_Data_Av_EditText.getText().toString().trim();
                            mP_Data_Fv = P_Data_Fv_EditText.getText().toString().trim();
                            mP_Data_Sv = P_Data_Sv_EditText.getText().toString().trim();
                            mP_Data_Z0 = P_Data_Z0_EditText.getText().toString().trim();
                            mP_Data_Z1 = P_Data_Z1_EditText.getText().toString().trim();
                            mP_Data_Z2 = P_Data_Z2_EditText.getText().toString().trim();
                            mP_Data_Z3 = P_Data_Z3_EditText.getText().toString().trim();
                            mP_Data_Z4 = P_Data_Z4_EditText.getText().toString().trim();
                            mP_Data_Z5 = P_Data_Z5_EditText.getText().toString().trim();
                            mP_Data_Z6 = P_Data_Z6_EditText.getText().toString().trim();
                            mP_Data_Z7 = P_Data_Z7_EditText.getText().toString().trim();
                            mP_Data_Z8 = P_Data_Z8_EditText.getText().toString().trim();
                            mP_Data_Z9 = P_Data_Z9_EditText.getText().toString().trim();
                            mP_Data_ZA = P_Data_ZA_EditText.getText().toString().trim();
                            mDesc      = DescEditText      .getText().toString().trim();
                            String PlacemarkDescription =
                                    mFilm_Data + "\n" +
                                    mP_Data_Z0 + "," +
                                    mP_Data_Z1 + "," +
                                    mP_Data_Z2 + "," +
                                    mP_Data_Z3 + "," +
                                    mP_Data_Z4 + "," +
                                    mP_Data_Z5 + "," +
                                    mP_Data_Z6 + "," +
                                    mP_Data_Z7 + "," +
                                    mP_Data_Z8 + "," +
                                    mP_Data_Z9 + "," +
                                    mP_Data_ZA + "\n" +
                                    mP_Data_Sv + "/" +
                                    mP_Data_Ev + "/" +
                                    mP_Data_Tv + "/" +
                                    mP_Data_Av + "/" +
                                    mP_Data_Fv + "\n" +
                                    mDesc;
                            final GPSApplication GlobalVariables = (GPSApplication) getActivity().getApplicationContext();
                            GlobalVariables.setPlacemarkDescription(PlacemarkDescription.trim());
                            EventBus.getDefault().post(EventBusMSG.ADD_PLACEMARK);
                            //Log.w("myApp", "[#] FragmentPlacemarkDialog.java - posted ADD_PLACEMARK: " + PlacemarkDescription);
                        }
                    }
                })
                //.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                .setNegativeButton(R.string.dlg_button_cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return createPlacemarkAlert.create();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    // TODO: to remove ?
    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }
}
