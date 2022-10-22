package com.example.lighthouse.model;

import java.io.Serializable;
import java.util.Date;

public class Journal implements Serializable {
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
    private String appropriateReaction;
    private String isTheSituationControllable;
    private String canITolerateIt;
    private String doINeedToSetABoundary;
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
        this.appropriateReaction = "";
        this.isTheSituationControllable = "";
        this.canITolerateIt = "";
        this.doINeedToSetABoundary = "";
        this.boundaryToSet = "";
        //
        this.date = new Date();
        //Need to Get entry id from timeline object list count
        this.id = 0;
    }

    //Parameterized Constructor
    public Journal(String currentFeeling, String resultantActions, String what, String where, String noticed, String whenIFelt, String actionsTaken, String theThoughtsThatCameToMind,
                   String appropriateReaction, String isTheSituationControllable, String canITolerateIt, String doINeedToSetABoundary, String boundaryToSet) {
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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("CurrentFeeling: " + currentFeeling + " \n");
        sb.append("resultantActions: " + resultantActions + " \n");
        sb.append("what: " + what + " \n");
        sb.append("where: " + where + " \n");
        sb.append("noticed: " + noticed + " \n");
        sb.append("whenIFelt: " + whenIFelt + " \n");
        sb.append("actionsTaken: " + actionsTaken + " \n");
        sb.append("theThoughtsThatCameToMind: " + theThoughtsThatCameToMind + " \n");
        sb.append("appropriateReaction: " + appropriateReaction + " \n");
        sb.append("isTheSituationControllable: " + isTheSituationControllable + " \n");
        sb.append("canITolerateIt: " + canITolerateIt + " \n");
        sb.append("doINeedToSetABoundary: " + doINeedToSetABoundary + " \n");
        sb.append("boundaryToSet: " + boundaryToSet + " \n");
        sb.append("date: " + date.toString() + "\n");
        return sb.toString();
    }

    public String getCurrentFeeling() {
        return currentFeeling;
    }

    public void setCurrentFeeling(String currentFeeling) {
        this.currentFeeling = currentFeeling;
    }

    public String getResultantActions() {
        return resultantActions;
    }

    public void setResultantActions(String resultantActions) {
        this.resultantActions = resultantActions;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getNoticed() {
        return noticed;
    }

    public void setNoticed(String noticed) {
        this.noticed = noticed;
    }

    public String getWhenIFelt() {
        return whenIFelt;
    }

    public void setWhenIFelt(String whenIFelt) {
        this.whenIFelt = whenIFelt;
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public void setActionsTaken(String actionsTaken) {
        this.actionsTaken = actionsTaken;
    }

    public String getTheThoughtsThatCameToMind() {
        return theThoughtsThatCameToMind;
    }

    public void setTheThoughtsThatCameToMind(String theThoughtsThatCameToMind) {
        this.theThoughtsThatCameToMind = theThoughtsThatCameToMind;
    }

    public String isAppropriateReaction() {
        return appropriateReaction;
    }

    public void setAppropriateReaction(String appropriateReaction) {
        this.appropriateReaction = appropriateReaction;
    }

    public String getAppropriateReaction() {
        return appropriateReaction;
    }

    public String getIsTheSituationControllable() {
        return isTheSituationControllable;
    }

    public String getCanITolerateIt() {
        return canITolerateIt;
    }

    public String getDoINeedToSetABoundary() {
        return doINeedToSetABoundary;
    }

    public String isTheSituationControllable() {
        return isTheSituationControllable;
    }

    public void setTheSituationControllable(String theSituationControllable) {
        isTheSituationControllable = theSituationControllable;
    }

    public String isCanITolerateIt() {
        return canITolerateIt;
    }

    public void setCanITolerateIt(String canITolerateIt) {
        this.canITolerateIt = canITolerateIt;
    }

    public String isDoINeedToSetABoundary() {
        return doINeedToSetABoundary;
    }

    public void setDoINeedToSetABoundary(String doINeedToSetABoundary) {
        this.doINeedToSetABoundary = doINeedToSetABoundary;
    }

    public String getBoundaryToSet() {
        return boundaryToSet;
    }

    public void setBoundaryToSet(String boundaryToSet) {
        this.boundaryToSet = boundaryToSet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
