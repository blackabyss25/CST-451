package com.example.lighthouse.model;

import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.in;

public class JournalEntries {

    private ArrayList<Journal> memories;
    private String filePath = "/data/data/com.example.lighthouse/files/lighthouseData/journalEntries.txt";

    public JournalEntries() {
        this.memories = new ArrayList<Journal>();
    }

    public ArrayList<Journal> getJournalEntries(){
        return memories;
    }

    public ArrayList<Journal> readAllEntries() throws IOException {
        //Fetch data from file
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            while(objectIn != null){
                Journal entry = (Journal) objectIn.readObject();
                memories.add(entry);
                System.out.println("The Object has been read from the file");
            }
            objectIn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return memories;
    }

    public void saveMemory(Journal newEntry) throws IOException {
        File saveFile = new File(filePath);
        //Add new memory to journal object
        memories.add(newEntry);
        try
        {
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(memories);
            out.close();
            fileOut.close();
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }
}
