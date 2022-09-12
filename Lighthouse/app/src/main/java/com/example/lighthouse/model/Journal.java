package com.example.lighthouse.model;

import java.util.Date;

public class Journal {
    //Name the Emotion Section
    private String currentFeeling;
    private String resultantActions;
    //Identify the Case Section
    private String what;
    private String where;
    private String noticed;
    //Identify the Behavior and Thoughts Section
    private String whenIFelt;
    private String actionsTaken;
    private String theThoughtsThatCameToMind;
    //Challenge the Emotion
    private boolean appropriateReaction;
    private boolean isTheSituationControllable;
    private boolean canITolerateIt;
    private boolean doINeedToSetABoundary;
    private String boundaryToSet;

    //Technical Data for Timeline Feature
    private Date date;
    private int id;

    //Default Constructor
    public Journal() {
        //
        this.currentFeeling = "";
        this.resultantActions = "";
        //
        this.what = "";
        this.where = "";
        this.noticed = "";
        //
        this.whenIFelt = "";
        this.actionsTaken = "";
        this.theThoughtsThatCameToMind = "";
        //
        this.appropriateReaction = false;
        this.isTheSituationControllable = false;
        this.canITolerateIt = false;
        this.doINeedToSetABoundary = false;
        this.boundaryToSet = "";
        //
        this.date = new Date();
        //Need to Get entry id from timeline object list count
        this.id = 0;
    }

    //Parameterized Constructor
    public Journal(String currentFeeling, String resultantActions, String what, String where, String noticed, String whenIFelt, String actionsTaken, String theThoughtsThatCameToMind,
                   boolean appropriateReaction, boolean isTheSituationControllable, boolean canITolerateIt, boolean doINeedToSetABoundary, String boundaryToSet) {
        //
        this.currentFeeling = currentFeeling;
        this.resultantActions = resultantActions;
        //
        this.what = what;
        this.where = where;
        this.noticed = noticed;
        //
        this.whenIFelt = whenIFelt;
        this.actionsTaken = actionsTaken;
        this.theThoughtsThatCameToMind = theThoughtsThatCameToMind;
        //
        this.appropriateReaction = appropriateReaction;
        this.isTheSituationControllable = isTheSituationControllable;
        this.canITolerateIt = canITolerateIt;
        this.doINeedToSetABoundary = doINeedToSetABoundary;
        this.boundaryToSet = boundaryToSet;
        //
        this.date = new Date();
        //Need to Get entry id from timeline object list count
        //this.id = timeline.getCount() + 1;
    }
}
