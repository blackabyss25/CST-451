package com.example.lighthouse.model;

import android.widget.Toast;

import com.example.lighthouse.ui.affirmations.AffirmationsFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AffirmationEntries {
    private ArrayList<Affirmation> affirmations;
    private String filePath = "/data/data/com.example.lighthouse/files/lighthouseData/affirmations/";
    private int size;

    public AffirmationEntries(){
        this.affirmations = new ArrayList<>();
        this.size = 0;
        this.affirmations = getAllAffirmations();
    }

    public ArrayList<Affirmation> getAllAffirmations(){
        try {
            File dirPath = new File(filePath);
            //System.out.println("dirPath:"+ dirPath);
            File [] entries = dirPath.listFiles();
            System.out.println("---------------------------------------------------------------------------------------------------------------------");
            System.out.println("entries: " + entries.length);
            for(File f : entries){
                System.out.println("f: "+f.getName());
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------");
            for (File entry : entries) {
                String entryFilePath = filePath + entry.getName();
                FileInputStream fis = new FileInputStream(entryFilePath);
                ObjectInputStream in = new ObjectInputStream(fis);
                affirmations.add((Affirmation) in.readObject());
                System.out.println("entry added:"+ entryFilePath);
                in.close();
                fis.close();
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
        finally {
            System.out.println("Finally check affirmations contents");
            for (Affirmation a : this.affirmations) {
                System.out.println(a.getId() + " | " + a.getText());
            }
        }
        return affirmations;
    }

    public void saveAffirmation(Affirmation na){
        affirmations.add(getSize(),na);
        File saveFile = new File(filePath+ na.getId()+".txt");
        try{
            saveFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(na);
            oos.close();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public void add(Affirmation na) {
        affirmations.add(na);
    }
    public Affirmation get(int i){
        return affirmations.get(i);
    }
    public ArrayList<Affirmation> getAffirmations() {
        return affirmations;
    }
    public void setAffirmations(ArrayList<Affirmation> affirmations) {
        this.affirmations = affirmations;
    }
}
