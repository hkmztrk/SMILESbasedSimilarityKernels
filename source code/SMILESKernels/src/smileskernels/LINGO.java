/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smileskernels;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author hakime_asus
 */
public  class LINGO {
    
 
  public static Map<String, Double> lingoWeights = new LinkedHashMap<>();
  
    public LINGO(ArrayList<String> smilesList, int q, String filename) throws IOException
    {
        Map<String, Integer> allLingos = findAllLINGOs(smilesList,q);
        lingoWeights = calculateTF(allLingos);
        
       // writeDictionary(allLingos, path, filename);
        
    }
    
   public LINGO()
   {
       
   }
     
     private  Map<String, Double> calculateTF(Map<String, Integer> lingoFreq)
     {
          Map<String, Double> tfList = new LinkedHashMap<>();
          
           for (Map.Entry<String, Integer> lingo_ : lingoFreq.entrySet()) {
           
                String key = lingo_.getKey();
                Double freq1 = Double.valueOf(lingo_.getValue()) / Double.valueOf(lingoFreq.size());
                tfList.put(key, freq1);

        }
           
           return tfList;
          
          
     }
    public double LINGOSim(String smiles1, String smiles2, int q)
    {
        double sim_score =0;
        
        Map<String, Integer> lingo1_ = findLINGOs(smiles1, q);
        Map<String, Integer> lingo2_ = findLINGOs(smiles2, q);

        sim_score = calculateSim(lingo1_, lingo2_);
        
        return sim_score;
    }
    
  
    
    /*TO-DO: UNION of Arrays. completed*/
    
    private double calculateSim(Map<String, Integer> lingoArray1, Map<String, Integer> lingoArray2)
    {
        double sim =0;
        int shared =0;
       for (Map.Entry<String, Integer> lingo_ : lingoArray1.entrySet()) {
           
          String key = lingo_.getKey();
          Integer freq1 = lingo_.getValue();
          Integer freq2 = 0;
                    
          if(lingoArray2.containsKey(key))
          {
                freq2= lingoArray2.get(key); 
                shared = shared + 1;
                
          }
          
          double tanimoto = 1 - (Double.valueOf(Math.abs(freq1-freq2)) / Double.valueOf(Math.abs(freq1 + freq2)));
          
          sim = sim + tanimoto;

        }
        int denom = lingoArray1.size() + lingoArray2.size() - shared;
        return (Double.valueOf(sim)/ Double.valueOf(denom));
    }
    

    public static Map<String, Integer> findLINGOs(String smiles, int q)
    {
         Map<String, Integer> lingoList = new LinkedHashMap<>();
         
         smiles= modifySMILES(smiles);
        
        if(smiles.length() < q)
        {
            while(smiles.length() != q)
            smiles = smiles + "J";
        }
        
        for (int i = 0; i < smiles.length()-(q-1); i++) {
            
            String lingo = smiles.substring(i, i+q);
            
            if(!lingoList.containsKey(lingo))
                lingoList.put(lingo, 1);
            else
            {
                int freq = lingoList.get(lingo);
                lingoList.remove(lingo);
                lingoList.put(lingo, freq+1);
            }
        }
       
       return lingoList; 
    }
      private static String modifySMILES(String smi)
     {
         if(smi.contains("Br"))
             smi = smi.replace("Br", "R");
         
         if(smi.contains("Cl"))
             smi = smi.replace("Cl", "L");
         
          smi = smi.replaceAll("[1-9]", "0");
         
      
         
         return smi;
     }
      
      
       public static Map<String, Integer> findAllLINGOs(ArrayList<String> smilesList, int q)
    {
         Map<String, Integer> lingoList = new LinkedHashMap<>();
         
         for (int i = 0; i < smilesList.size(); i++) {
            
             String smiles_ = smilesList.get(i);
             smiles_ = modifySMILES(smiles_);
             
                if(smiles_.length() < q)
                {
                    while(smiles_.length() != q)
                    smiles_ = smiles_ + "J";
                }

           for (int j = 0; j < smiles_.length()-(q-1); j++) {

               String lingo = smiles_.substring(j, j+q);

               if(!lingoList.containsKey(lingo))
                   lingoList.put(lingo, 1);
               else
               {
                   int freq = lingoList.get(lingo);
                   lingoList.remove(lingo);
                   lingoList.put(lingo, freq+1);
               }
           }
             
        }
        
        
       
       return lingoList; 
    }
       
       
       private void writeDictionary(Map<String, Integer> lingos, String path, String filename) throws IOException
       {
            FileWriter fw = new FileWriter(path+"//"+filename+".txt");
            
      try (PrintWriter pw = new PrintWriter(fw)) {
          for (Map.Entry<String, Integer> lingo_ : lingos.entrySet()) {
              
              String key = lingo_.getKey();
              Integer freq1 = lingo_.getValue();
              
              pw.print(key + "\t" + freq1);
              pw.println();
          }
      }
       }
       
      /* public double LINGOWeightedSim(String smiles1, String smiles2, int q)
    {
        double sim_score =0;
        
        Map<String, Integer> lingo1_ = findLINGOs(smiles1, q);
        Map<String, Integer> lingo2_ = findLINGOs(smiles2, q);

        sim_score = calculateWeightedSim(lingo1_, lingo2_);
        
        return sim_score;
    }*/
       
         /*    private double calculateWeightedSim(Map<String, Integer> lingoArray1, Map<String, Integer> lingoArray2)
    {
        double sim =0;
        
       for (Map.Entry<String, Integer> lingo_ : lingoArray1.entrySet()) {
           
          String key = lingo_.getKey();
          Integer freq1 = lingo_.getValue();
          Integer freq2 = 0;
          double weight = 0;
                    
          if(lingoArray2.containsKey(key))
          {
              
                freq2= lingoArray2.get(key);  
                weight = lingoWeights.get(key);

          }
          
          double tanimoto = 1 - (Double.valueOf(Math.abs(freq1-freq2)) / Double.valueOf(Math.abs(freq1 + freq2))) ;
          sim = sim + tanimoto*weight;

        }
        
        return ( Double.valueOf(sim)/ Double.valueOf(lingoArray1.size()) );
    }*/
    
    
    

}
