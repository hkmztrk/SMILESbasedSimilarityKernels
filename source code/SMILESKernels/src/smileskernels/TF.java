/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smileskernels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Hakimee
 */
public class TF {
    
    private static  Map<String, Integer> allLingos_ = new LinkedHashMap<>();
    private static Integer q;
    
    
    public TF(ArrayList<String> smilesList, int a)
    {
         q=a;
         allLingos_ = LINGO.findAllLINGOs(smilesList, q);

         
    }
    
   
    public double getCosineSim_(String smiles1, String smiles2)
    {
        double [] fVec1 = createFeatureVector(smiles1);
        double [] fVec2 = createFeatureVector(smiles2);
        
        return cosineSimilarity_(fVec1, fVec2);
    }
    
  /*  public double getTanimotoSim_(String smiles1, String smiles2)
    {
        Map<String, Integer> smi1_lingos = LINGO.findLINGOs(smiles1, q);
        Map<String, Integer> smi2_lingos = LINGO.findLINGOs(smiles2, q);
       
        
        return tanimotoSimilarity(smi1_lingos, smi2_lingos);
    }*/
    
    private double [] createFeatureVector(String smi1)
    {
        
        double [] featureVector = new double[allLingos_.size()];

         Map<String, Integer> smi_splitted_terms = LINGO.findLINGOs(smi1, q);
         
         Integer i =0; /*TO DO GET FREQ FROM ALL LINGOS*/
         Double sum = (double)0;
           
           for (Map.Entry<String, Integer> term_ : allLingos_.entrySet()) {
               String lingo_term = term_.getKey();
             //  Double common_tf = Double.valueOf(term_.getValue()) / allLingos_.size();
               
               if(smi_splitted_terms.containsKey(lingo_term))
               {
                   Integer freq = smi_splitted_terms.get(lingo_term);
                   Double tf = (Double.valueOf(freq));
                   
                    featureVector[i]= tf;
                    sum = sum + (tf * tf);
               }
               else
               {
                   featureVector[i]= 0;
                   sum = sum + 0;
               }
               
               
               i++;
           }
        
           
             Double denom = Math.sqrt(sum);
           
             for (int j = 0; j < featureVector.length; j++) {
            
              featureVector[j] = featureVector[j] / denom;
        }
           
           
        return featureVector;
        
       
    }
    

    public double cosineSimilarity_(double [] vect1,double [] vect2)
    {
        double sim_score =0;

        if(vect1.length == vect2.length)
        {
            
            for (int i = 0; i < vect1.length; i++) {
                
               sim_score = sim_score + (vect1[i] * vect2[i]);
            }
        
        }
        
        return sim_score;
     
    }
    
    
 /*       private double tanimotoSimilarity(Map<String, Integer> lingoArray1, Map<String, Integer> lingoArray2)
    {
        double sim =0;
        int shared =0;
        
       for (Map.Entry<String, Integer> lingo_ : lingoArray1.entrySet()) {
           
          String key = lingo_.getKey();
          Integer freq1 = lingo_.getValue();
          Integer freq2 = 0;
          Double common_freq = (double)1;
                    
          if(lingoArray2.containsKey(key))
          {
                freq2= lingoArray2.get(key); 
                shared = shared + 1;
                common_freq = Double.valueOf(allLingos_.get(key)) / allLingos_.size();
          }
          
          double tanimoto = 1 - (Double.valueOf(Math.abs(freq1-freq2)) / Double.valueOf(Math.abs(freq1 + freq2)));
          
          sim = sim + (tanimoto * common_freq);

        }
        int denom = lingoArray1.size() + lingoArray2.size() - shared;
        return (Double.valueOf(sim)/ Double.valueOf(denom));
    }*/
}

    

