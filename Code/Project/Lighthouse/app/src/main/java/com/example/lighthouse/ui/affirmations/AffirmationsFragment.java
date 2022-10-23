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
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
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
import com.example.lighthouse.model.NotificationPusher;

import java.io.*;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;

import static com.example.lighthouse.model.NotificationPusher.NOTIFICATION;
import static java.lang.System.in;

public class AffirmationsFragment extends Fragment {

    private FragmentAffirmationsBinding binding;
    private String filePath = "/data/data/com.example.lighthouse/files/lighthouseData/affirmationEntries.txt";
    private AffirmationEntries affirmationEntries;
    private ArrayList<Button> affirmationButtons;
    public static final String notification_channel_id = "0425";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private NotificationCompat.Builder builder;

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


        //Creating the notification channel to be pushed to the user's device
        mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notification_channel_id, getContext().getString(R.string.affirmation), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.CYAN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        mBuilder = new NotificationCompat.Builder(getContext(), notification_channel_id);
        mBuilder.setSmallIcon(R.mipmap.lighthouse_launcher);



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
                    Button ab = new Button(getContext());
                    TextView space = new TextView(getContext());
                    space.setHeight(10);
                    ab.setText(a.getText());
                    ab.setGravity(Gravity.CENTER);
                    ab.setPadding(100, 10, 100, 10);
                    if (a.getNeedsReminding()) {
                        ab.setBackgroundColor(Color.CYAN);
                        ab.setTextColor(Color.BLACK);
                        scheduleNotification(getNotification(a), a);
                        System.out.println("Notification for " + a.getText() + " scheduled.");
                    } else {
                        ab.setBackgroundColor(Color.GRAY);
                        ab.setTextColor(Color.WHITE);
                        cancelNotifications(getNotification(a));
                        System.out.println("Notification for " + a.getText() + " cancelled.");
                    }
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setAffirmationButtonFunctionality(a, ab);
                        }
                    });
                    //For demonstration purposes...
                    ab.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if(a.getNeedsReminding()){
                                Toast.makeText(getContext(),"Don't need a reminder for this!",Toast.LENGTH_LONG).show();
                            } else {
                                mBuilder.setContentTitle("Hey There! Friendly Reminder...");
                                mBuilder.setContentText(a.getText());
                                mBuilder.setSmallIcon(R.mipmap.lighthouse_launcher);
                                // notificationID allows you to update the notification later on.
                                mNotificationManager.notify(a.getId(), mBuilder.build());
                            }
                            return false;
                        }
                    });

                    //Create a small gap between the buttons
                    affirmationButtons.add(ab);
                    affirmationsLinearLayout.addView(ab);
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
                        na.setId(affirmationEntries.getAffirmations().size() + 1);
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
                        affirmation.setBackgroundColor(Color.GRAY);
                        affirmation.setTextColor(Color.WHITE);
                        affirmation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setAffirmationButtonFunctionality(na, affirmation);
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


    private void scheduleNotification(Notification notification, Affirmation a) {
        System.out.println("Scheduling Notification");

        //Determine a time to send the reminder each day
        int hour = randBetween(8, 20); //Reminders should only be sent during appropriate times, aka between 8AM and 8PM
        int minute = randBetween(0,59);
        //Get the current time when the button is clicked
        Calendar date = Calendar.getInstance();
        System.out.println("Current Time: " + date.getTime());
        date.set(Calendar.HOUR_OF_DAY, hour);
        date.set(Calendar.MINUTE, minute);
        System.out.println("Alarm trigger time set for: " + hour + ":" + minute);

        //Schedule the notification

        Intent notificationIntent = new Intent(getContext(), NotificationPusher.class);
        notificationIntent.setAction(a.getText());
        notificationIntent.putExtra(NotificationPusher.NOTIFICATION_ID, a.getId());
        notificationIntent.putExtra(NOTIFICATION, notification);

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }else {
            pendingIntent = PendingIntent.getBroadcast(getContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        //Create the alarm manager object to trigger the notification
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        //After the initial trigger, set the interval for 24 hours, 1000 milliseconds in a second, 60 seconds in a minute, 60 minutes in a hour, 24 hours in a day
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        System.out.println("date.getTimeInMillis: " + date.getTimeInMillis() + " in readable terms " + date.getTime());
    }

    private void cancelNotifications(Notification n){
        System.out.println("Canceling Notification");
        Intent intent = new Intent(getContext(), NotificationPusher.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }else {
            pendingIntent = PendingIntent.getBroadcast(getContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public static int randBetween(int start, int end) {
        Random r = new Random();
        int result = r.nextInt(end - start + 1) + start;
        return result;
    }

    public Date addHoursToDate(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    //This function generates the content for the notification that gets pushed to the users device
    private Notification getNotification(Affirmation a) {
        //Set the affirmations content
        mBuilder.setContentTitle("Hey There! Friendly reminder to...");
        mBuilder.setContentText(a.getText());

        //Set the notification Icon
        mBuilder.setSmallIcon(R.mipmap.lighthouse_launcher);

        //Set the channel the notification is posted to
        mBuilder.setChannelId(notification_channel_id);

        return mBuilder.build();
    }

    //This function simplifies defining the affirmation buttons functionality in multiple spots, by calling the function instead of copy pasting it
    public void setAffirmationButtonFunctionality(Affirmation a, Button b){
        System.out.println("clicked affirmation: " + a.getText() + " | " + a.getNeedsReminding());
        //Switching the affirmation off
        if (a.getNeedsReminding() == true) {
            //Sets the backend state of the affirmation to indicate that they don't want reminders
            a.setNeedsReminding(false);

            //Sets the button from cyan to gray to indicate that it's no longer active
            b.setBackgroundColor(Color.GRAY);
            b.setTextColor(Color.WHITE);

            //Cancels the reminder notifications
            cancelNotifications(getNotification(a));
        }
        //Switching the affirmation to on, aka the user wants the daily reminders
        else {
            //Sets the button from gray to cyan to indicate that it's active
            b.setBackgroundColor(Color.CYAN);
            b.setTextColor(Color.BLACK);
            //Change its backend state to match it's active state
            a.setNeedsReminding(true);
            //Schedule the notification
            scheduleNotification(getNotification(a), a);
        }
        //Save the Affirmation state to the file
        affirmationEntries.saveAffirmation(a);
    }
}


