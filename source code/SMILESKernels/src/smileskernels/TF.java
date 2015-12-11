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
    
    
    private double [] createFeatureVector(String smi1)
    {
        
          double [] featureVector = new double[allLingos_.size()];

         Map<String, Integer> smi_splitted_terms = LINGO.findLINGOs(smi1, q);
         
         Integer i =0; 
           
           for (Map.Entry<String, Integer> term_ : allLingos_.entrySet()) {
               String lingo_term = term_.getKey();
      
               if(smi_splitted_terms.containsKey(lingo_term))
               {
                   int freq = smi_splitted_terms.get(lingo_term);                   
                   featureVector[i]=  1 + Math.log10(freq);
               }
               else
               {
                   featureVector[i]= 0;
               }
               
               
               i++;
           }   
           
        return featureVector;
        
       
    }
    

    public double cosineSimilarity_(double [] vect1,double [] vect2)
    {
        double sim_score =0.0;
        double denoma=0.0;
        double denomb=0.0;


        if(vect1.length == vect2.length)
        {
            
            for (int i = 0; i < vect1.length; i++) {
                
               sim_score = sim_score + Double.valueOf(vect1[i] * vect2[i]);
               
               denoma = denoma + Double.valueOf(vect1[i] * vect1[i]);
               denomb = denomb + Double.valueOf(vect2[i] * vect2[i]);
               
              
            }
      
        }
        

        double denomf = Math.sqrt(denoma * denomb); //Math.sqrt(denoma*denomb); //* (double)Math.sqrt(denomb);
        if(denomf!=0)
        	return Double.valueOf(sim_score/denomf);
        else
        	return 0;
     
    }
    
    

}

    

