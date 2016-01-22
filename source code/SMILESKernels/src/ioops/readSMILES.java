package ioops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import smileskernels.SMILESKernels;

public class readSMILES {
	
	
	   public  static void readSMILESfromFolder(String path, ArrayList<String> compounds1, ArrayList<String>  smiles1) throws IOException
	    {
	      
	       
	       File folder = new File(path);
	       File[] listOfFiles = folder.listFiles();

	     for (int i = 0; i < listOfFiles.length; i++) {
	      File file = listOfFiles[i];
	      
	      String compoundName= file.getName();
	      compoundName = compoundName.replace(".smi", "");
	      compounds1.add(compoundName);
	      
	      
	      if (file.isFile() && file.getName().endsWith(".smi")) {
	          BufferedReader reader = null;
	          try {
	              reader = new BufferedReader(new FileReader(file));
	          }  catch (FileNotFoundException ex) {
	              Logger.getLogger(SMILESKernels.class.getName()).log(Level.SEVERE, null, ex);
	          }
	          String line = null;
	            while ((line = reader.readLine()) != null) {
	             
	                line= modifySMILES(line);
	                smiles1.add(line);
	            }
	       
	        } 
	      }
	       
	      
	    }
	   
	   
	      public  static void readSMILESfromList(String path, ArrayList<String> compounds1, ArrayList<String>  smiles1) throws IOException
	      {
	        
	            BufferedReader reader = null;
	            try {
	                reader = new BufferedReader(new FileReader(path));
	            }  catch (FileNotFoundException ex) {
	                Logger.getLogger(SMILESKernels.class.getName()).log(Level.SEVERE, null, ex);
	            }
	            String line = null;
	              while ((line = reader.readLine()) != null) {
	          
	                  String [] tokens = line.split("\t");
	                  
	                  compounds1.add(tokens[0]);
	                  smiles1.add(modifySMILES(tokens[1]));
	              }
	         
	         
	        
	        
	      }

	   
	   
	      private static String modifySMILES(String smi)
	      {
	        if(smi.contains("Br"))
	              smi = smi.replace("Br", "R");
	          
	          if(smi.contains("Cl"))
	              smi = smi.replace("Cl", "L");
	          
	          if(smi.contains("Ca"))
	              smi = smi.replace("Ca", "Q");
	          
	          if(smi.contains("Mg"))
	              smi = smi.replace("Mg", "Y");
	          
	          if(smi.contains("Na"))
	              smi = smi.replace("Na", "Z");
	          
	           if(smi.contains("Fe"))
	              smi = smi.replace("Fe", "Q");
	           
	           if(smi.contains("Co"))
	              smi = smi.replace("Co", "W");
	          
	         // smi = smi.replaceAll("[1-9]", "0");
	          
	       
	          
	          return smi;
	      }
	        

}
