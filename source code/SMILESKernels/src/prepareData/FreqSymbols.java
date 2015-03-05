/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prepareData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import smileskernels.SMILESKernels;

/**
 *
 * @author hakime_asus
 */
public class FreqSymbols {
    
   public static Map<String, Integer> characters = new HashMap<String, Integer>();
    
    public static void findFreqSymbols(String path) throws IOException
    {
      File folder = new File(path);
       File[] listOfFiles = folder.listFiles();

     for (int i = 0; i < listOfFiles.length; i++) {
         
      File file = listOfFiles[i];
      
      String compoundName= file.getName();
  

      if (file.isFile() && file.getName().endsWith(".smi")) {
          BufferedReader reader = null;
          try {
              reader = new BufferedReader(new FileReader(file));
          }  catch (FileNotFoundException ex) {
              Logger.getLogger(SMILESKernels.class.getName()).log(Level.SEVERE, null, ex);
          }
            String line = null;
            while ((line = reader.readLine()) != null) {
        
                for (int j = 0; j < line.length(); j++) {
                    if(!characters.containsKey(String.valueOf(line.charAt(j))))
                    {
                        characters.put(String.valueOf(line.charAt(j)), 1);
                    }
                    else
                    {
                        Integer fr = characters.get(String.valueOf(line.charAt(j)));
                        fr += 1;
                        characters.remove(String.valueOf(line.charAt(j)));
                        characters.put(String.valueOf(line.charAt(j)), fr);
                    }
                }
            }
       
        } 
      }
     
     
        FileWriter fw = new FileWriter(path + "chFreq.txt");
        PrintWriter pw = new PrintWriter(fw);
        
        for (Map.Entry<String, Integer> entry : characters.entrySet()) 
        {
            pw.println(entry.getKey() + "\t" + entry.getValue());
         }
    
        
        pw.close();
    }
    
}
