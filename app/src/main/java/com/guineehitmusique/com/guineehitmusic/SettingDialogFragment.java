package com.guineehitmusique.com.guineehitmusic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by hackabot on 26/07/16.
 */
public class SettingDialogFragment extends DialogFragment {

    public String TAG = "SettingDialogFragment";

    interface shareListener{
        public void sharepageLoad(String url);
    }

    public static String NOTIF_CHECK = "com.guineehitmusique.com.guineehitmusic.pref.NOTIF_CHECK";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.setting_dialog, null);

        setupShareButtons(v);

        final CheckBox notif_checkBox = (CheckBox) v.findViewById(R.id.notif_chceckBox);
        notif_checkBox.setChecked(getNotifPref(getContext()));
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v);
        // Use the Builder class for convenient dialog construction
        builder
                .setTitle(R.string.action_settings)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG,  "builder.setPositiveButton requesting subscription be set" );
                        setNotifPref(getContext(), notif_checkBox.isChecked());
                        set_subscription(getContext());
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SettingDialogFragment.this.getDialog().cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public static boolean getNotifPref(Context ctx){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getBoolean(SettingDialogFragment.NOTIF_CHECK, true);
    }

    public  static void setNotifPref(Context ctx, boolean check){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor pref_editor = pref.edit();
        pref_editor.putBoolean(NOTIF_CHECK, check);
        pref_editor.apply();
    }

    public static void set_subscription(Context ctx){
       boolean toSubscribe = getNotifPref(ctx);
        if(toSubscribe){
            FirebaseMessaging.getInstance().subscribeToTopic(ctx.getString(R.string.NOTIF_TOPIC));
            Log.d("set_subscription", "Subscribed to topic : " + ctx.getString(R.string.NOTIF_TOPIC));
        }
        else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic(ctx.getString(R.string.NOTIF_TOPIC));
            Log.d("set_subscription", "UnSubscribed from topic : " + ctx.getString(R.string.NOTIF_TOPIC));
        }
    }

    void setupShareButtons(final View v){
        ImageButton fb_button = (ImageButton) v.findViewById(R.id.fb_share_button);
        ImageButton yt_button = (ImageButton) v.findViewById(R.id.twitter_share_button);
        ImageButton tw_button = (ImageButton) v.findViewById(R.id.youtube_subscribe_button);
        ImageButton ins_button = (ImageButton) v.findViewById(R.id.instagram_button);


        fb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((shareListener)getActivity()).sharepageLoad(v.getResources().getString(R.string.fb_share_url));
                dismiss();

            }
        });

        yt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((shareListener)getActivity()).sharepageLoad(v.getResources().getString(R.string.tw_share_url));
                dismiss();
            }
        });

        tw_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((shareListener)getActivity()).sharepageLoad(v.getResources().getString(R.string.yt_share_url));
                dismiss();
            }
        });
        ins_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((shareListener)getActivity()).sharepageLoad(v.getResources().getString(R.string.ins_button_url));
                dismiss();
            }
        });


    }
}
