package com.example.lighthouse.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/*
This class is for populating the list of journal entries on the second tab of the application.
Functions:
    - Save data to the device
    - Load data stored in device storage
 */
public class Timeline {




    //Currently investigating how to best implement this feature
    //Some resources suggest using app-specific storage, but caution limited space usage.  Still trying to figure out at what point the size becomes an issue, can't find clear answers to that
    /* --------------------Saving to app specific storage stuff goes here-------------------------- */

    //If that first plan doesn't work, writing the data to a file is the next obvious choice.
    File timelineContent;


    //Either plan requires a list of journal objects.
    private static List<Journal> timeline;

    public static List<Journal> getTimeline(){
        return timeline;
    }


    public ArrayList<Journal> populateTimeline(){
        ArrayList<Journal> entries = null;

        return entries;
    }


    public void submitJournal(Journal entry){
        timeline.add(entry);
        System.out.println(entry.toString());
    }

}
