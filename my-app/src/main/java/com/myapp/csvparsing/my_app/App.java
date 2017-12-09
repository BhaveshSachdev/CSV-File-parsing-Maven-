package com.myapp.csvparsing.my_app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import com.myapp.csvparsing.my_app.CSVData;
import com.google.gson.*;


public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	try
        {
            BufferedReader reader = new BufferedReader(new FileReader(
                "interview_data_final.csv"));

            // read file line by line
            String line = null;
            Scanner scanner = null;
            int index = 0;
            int key = 0;
            
            //creating Array list structure to hold csv data based on age
            List<CSVData> listBelow30 = new ArrayList<CSVData>();
            List<CSVData> listAbove30 = new ArrayList<CSVData>();
            
            //skipping the headers of csv file
            reader.readLine();
            
            //code to parse csv file
            while ((line = reader.readLine()) != null) 
            {
                CSVData csvData = new CSVData();
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                
                //extracting data from each entry
                while (scanner.hasNext()) 
                {
                    String data = scanner.next();
                    if (index == 0) {
                        csvData.setFirstName(data);
                    } else if (index == 1) {
                        csvData.setLastName(data);
                    } else if (index == 2) {
                       key = Integer.parseInt(data);
                       csvData.setAge(key);
                    } else if (index == 3) {
                        csvData.setState(data);
                    } else {
                        System.out.println("invalid data:" + data);
                    }
                    index++;
                }
                if(key >= 30)
                   listAbove30.add(csvData);
                else
                   listBelow30.add(csvData);
                index = 0;
            }
            
            //Sorting the lists to group by state
            Collections.sort(listAbove30, CSVData.StateComparator);
            Collections.sort(listBelow30, CSVData.StateComparator);
            
            //Creating JSon Objects using Gson API for each list
            String jsonAbove30Obj = new Gson().toJson(listAbove30);
            String jsonBelow30Obj = new Gson().toJson(listBelow30);
            
            //Displaying data after grouping by state
            System.out.println("30+ Years Old");
            System.out.println("---------------------------------------");

            //Code to display list1 containing data for inidviduals aged above or equal to 30, grouped by state, using System.out. 
            //Please uncomment the below lines of code to see output using System.out
//            String previousState = previousState = listAbove30.get(0).getState();;
//            System.out.println("\nState: " + previousState);
//
//            for(CSVData myData : listAbove30)
//            {
//
//                String currentState =  myData.getState();
//                if(!currentState.equals(previousState))
//                {
//                    System.out.println("\nState: " + currentState);
//                    previousState = currentState;
//                }
//                System.out.println("Document: " + myData.getFirstName() + " " + myData.getLastName() + ", " + myData.getState() + ", " + myData.getAge());
//            }
            
            //Cleaning json data  for printing in a formatted manner using Gson's pretyy print functionality
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(jsonAbove30Obj);
            String finalAbove30String = gson.toJson(je);
            System.out.println(finalAbove30String);
            
            System.out.println("\n \n< 30 Years Old");
            System.out.println("---------------------------------------");
            
            je = jp.parse(jsonBelow30Obj);
            String finalBelowString = gson.toJson(je);
            System.out.println(finalBelowString);

            //Code to display list2 containing data for inidviduals aged below 30, grouped by state, using System.out. 
            //Please uncomment the below lines of code to see output using System.out
//            previousState = listBelow30.get(0).getState();
//            System.out.println("\nState: " + previousState);
//
//            for(CSVData myData : listBelow30)
//            {
//
//                String currentState = myData.getState();
//                if(!currentState.equals(previousState))
//                {
//                    System.out.println("\nState: " + currentState);
//                    previousState = currentState;
//                }
//                System.out.println("Document: " + myData.getFirstName() + " " + myData.getLastName() + ", " + myData.getState() + ", " + myData.getAge());
//            }
        }
        
        catch(Exception e)
        {
            System.out.println("Exception occured" + e);
        }
    }
}
