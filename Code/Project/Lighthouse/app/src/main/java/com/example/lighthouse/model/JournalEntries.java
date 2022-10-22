package com.example.lighthouse.model;

import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.in;

public class JournalEntries {

    private ArrayList<Journal> journals;
    private String filePath = "/data/data/com.example.lighthouse/files/lighthouseData/journals/";
    private int size;

    public JournalEntries() {
        this.journals = new ArrayList<>();
        this.size = 0;
        this.journals = readAllEntries();

    }

    public Journal getEntry(int id) {
        Journal result = new Journal();
        try {
            File dirPath = new File(filePath);
            File[] entries = dirPath.listFiles();
            for (File entry : entries) {

                String entryFilePath = filePath + entry.getName();
                FileInputStream fis = new FileInputStream(entryFilePath);
                ObjectInputStream in = new ObjectInputStream(fis);
                if (entry.getName().equals(id + ".txt")) {
                    result = (Journal) in.readObject();
                }
                in.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Journal> readAllEntries() {
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
                journals.add((Journal) in.readObject());
                System.out.println("entry added:"+ entryFilePath);
                in.close();
                fis.close();
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
        finally {
            /*
            System.out.println("Finally check journals contents");
            for (Journal j : this.journals) {
                System.out.println(j.toString());
            }
            */
        }
        return journals;
    }

    public void saveEntry(Journal ne) throws IOException {
        journals.add(journals.size(), ne);
        File saveFile = new File(filePath+ ne.getId()+".txt");
        try{
            saveFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ne);
            oos.close();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }




    public ArrayList<Journal> getJournalEntries(){
        return journals;
    }



















}
