package com.example.lighthouse.ui.affirmations;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lighthouse.MainActivity;
import com.example.lighthouse.R;
import com.example.lighthouse.databinding.FragmentAffirmationsBinding;
import com.example.lighthouse.model.Affirmation;
import com.example.lighthouse.model.AffirmationEntries;
import com.example.lighthouse.model.Journal;

import java.io.*;


import java.util.ArrayList;

import static java.lang.System.in;

public class AffirmationsFragment extends Fragment {

    private FragmentAffirmationsBinding binding;
    private String filePath = "/data/data/com.example.lighthouse/files/lighthouseData/affirmationEntries.txt";
    private AffirmationEntries affirmationEntries;
    private ArrayList<Button> affirmationButtons;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //-------------------------- Bindings & Instantiations ------------------------------------------
        AffirmationsViewModel affirmationsViewModel = new ViewModelProvider(this).get(AffirmationsViewModel.class);
        binding = FragmentAffirmationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LinearLayout ll = binding.mainAffirmationsLayout;
        EditText affirmationInput = binding.addAffirmation;
        Button commitButton = binding.commitButton;
        ScrollView affirmationsScrollView = binding.affirmationsScrollView;
        LinearLayout affirmationsLinearLayout = binding.affirmationsLinearLayout;
        TextView noAffirmationsYet = binding.noAffirmationsYetText;
        TextView legend = binding.legend;


        //Creating the notification state to be pushed to the user's device
        String notification_channel_id = "0425";
        NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notification_channel_id, getContext().getString(R.string.affirmation), NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), notification_channel_id);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int)(System.currentTimeMillis()/1000), mBuilder.build());



        affirmationEntries = new AffirmationEntries();
        affirmationButtons = new ArrayList<>();
        //-------------------------- End of Bindings & Instantiations -----------------------------------

        //For when there are no affirmations
        if (affirmationEntries.getAffirmations() == null && affirmationEntries.getSize() == 0) {
            noAffirmationsYet.setText("You don't currently have any affirmations");
        } else {
            //--------------------- On Create View, load all previously written affirmations into the linear layout for viewing ---------------------------------------
            if (affirmationEntries.getAffirmations() != null) {
                for (Affirmation a : affirmationEntries.getAffirmations()) {
                    System.out.println();
                    Button affirmation = new Button(getContext());
                    TextView space = new TextView(getContext());
                    space.setHeight(10);
                    affirmation.setText(a.getText());
                    affirmation.setGravity(Gravity.CENTER);
                    affirmation.setPadding(100, 10, 100, 10);

                    if(a.getNeedsReminding()){
                        a.setNeedsReminding(true);
                        affirmation.setBackgroundColor(Color.CYAN);
                    } else {
                        a.setNeedsReminding(false);
                        affirmation.setBackgroundColor(Color.GRAY);
                    }
                    affirmation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("clicked affirmation: " + a.getText() + " | " + a.getNeedsReminding());
                            if(a.getNeedsReminding()){
                                a.setNeedsReminding(false);
                                affirmation.setBackgroundColor(Color.GRAY);
                            } else {
                                affirmation.setBackgroundColor(Color.CYAN);
                                a.setNeedsReminding(true);
                                /*
                                 *   Add notification push code here
                                 */
                                mBuilder.setContentTitle("Hey There! Friendly Reminder...");
                                mBuilder.setContentText(a.getText());
                                // notificationID allows you to update the notification later on.
                                mNotificationManager.notify(a.getId(), mBuilder.build());
                            }
                            affirmationEntries.saveAffirmation(a);
                        }
                    });
                    //Create a small gap between the buttons
                    affirmationButtons.add(affirmation);
                    affirmationsLinearLayout.addView(affirmation);
                    affirmationsLinearLayout.addView(space);

                }
            }
            //---------------------------------------------------------------------------------------------------------------------------------------------------------

            //---------------- Set commit button functionality -----------------------
            commitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Initial if check is to make sure they don't submit an empty affirmation.
                    if (!affirmationInput.getText().toString().equals("")) {
                        //Save Affirmation to list
                        Affirmation na = new Affirmation(affirmationInput.getText().toString());
                        na.setId(affirmationEntries.getAffirmations().size()+1);
                        na.setNeedsReminding(false); /* User can decide later */
                        System.out.println("onClick na text: " + na.getText() + " onClick na id: " + na.getId() + " needsReminding: " + na.getNeedsReminding());
                        //Insert the affirmation into the persisted list
                        affirmationEntries.saveAffirmation(na);

                        //Create visual representation of the affirmation for display
                        Button affirmation = new Button(getContext());
                        affirmation.setId(na.getId());
                        affirmation.setText(na.getText());
                        affirmation.setGravity(Gravity.CENTER);
                        affirmation.setPadding(100, 10, 100, 10);
                        affirmation.setBackgroundColor(Color.CYAN);


                        affirmation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("clicked affirmation: " + na.getText() + " | " + na.getNeedsReminding());
                                if(na.getNeedsReminding() == true){
                                    na.setNeedsReminding(false);
                                    affirmation.setBackgroundColor(Color.GRAY);
                                    /*
                                     *
                                     *  Here is where I will need to put my notification flagging
                                     *
                                     */
                                } else {
                                    affirmation.setBackgroundColor(Color.CYAN);
                                    na.setNeedsReminding(true);
                                    /*
                                     *
                                     *  Here is where I will need to remove any sort of notifications for a certain affirmation
                                     *
                                     */
                                }
                                //NOTE: I don't know how to do it yet, so both previous comments are just guesses
                                affirmationEntries.saveAffirmation(na);
                            }
                        });

                        //Visuals housekeeping - adding to view, resetting any necessary components
                        affirmationsLinearLayout.addView(affirmation);
                        TextView space = new TextView(getContext());
                        space.setHeight(10);
                        affirmationsLinearLayout.addView(space);
                        //Remove the notifcation that there are no affirmations
                        noAffirmationsYet.setText("");
                        //Clear out input box
                        affirmationInput.setText("");
                    }
                }
            });
            //------------------------------------------------------------------------
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}