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
public class TFIDF {
    
    public static  Map<String, Integer> allLingos = new LinkedHashMap<>();
    public static Map<String, Double> lingo_idfs = new LinkedHashMap<>();
    public static Integer q;
    private static Map<String, Integer> term_doc_count = new LinkedHashMap<>();
    
    public TFIDF(ArrayList<String> smilesList, int a) throws IOException
    {
         q=a;
         allLingos = LINGO.findAllLINGOs(smilesList, q);
         lingo_idfs = findIDF(smilesList);
        // writeIDFtable();
         
    }
    
    public double getCosineSim(String smiles1, String smiles2)
    {
        double [] fVec1 = createFeatureVector(smiles1);
        double [] fVec2 = createFeatureVector(smiles2);
        
        return cosineSimilarity(fVec1, fVec2);
    }
    
    private double [] createFeatureVector(String smi1)
    {
        
        double [] featureVector = new double[allLingos.size()];

         Map<String, Integer> smi_splitted_terms = LINGO.findLINGOs(smi1, q);
         
         Integer i =0;
         Double sum = (double)0;
         
           for (Map.Entry<String, Integer> term_ : allLingos.entrySet()) {
               String lingo_term = term_.getKey();
               
               if(smi_splitted_terms.containsKey(lingo_term))
               {
                   Integer freq = smi_splitted_terms.get(lingo_term);
                  // Double tf = Double.valueOf(freq) / Double.valueOf(smi_splitted_terms.size());
                   Double tf = Double.valueOf(freq);
                  
                   Double idf = lingo_idfs.get(lingo_term);
                   
                    Double tf_idf = tf * idf; /*TO DO: NORMALIZE*/
                    featureVector[i]= tf_idf;
                    
                      sum = sum + ( tf_idf * tf_idf);

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
    
    
    private Map<String, Double> findIDF(ArrayList<String> documents ) // terms = lingos documents = smiles
    {
        
        Map<String, Double> term_idf_table = new LinkedHashMap<>();
         
        
          for (Map.Entry<String, Integer> term_ : allLingos.entrySet()) {
          
              String key = term_.getKey();
              Integer freq1 = 0;
                    
              for (String smi : documents) {
                  
                  Map<String, Integer> smi_splitted_terms = LINGO.findLINGOs(smi, q);
                  
                  if(smi_splitted_terms.containsKey(key))
                  {
                      freq1++;
                  }

              }
              double term_idf =  Math.log10(Double.valueOf(documents.size())/ Double.valueOf(freq1));
              term_idf_table.put(key, term_idf);
              term_doc_count.put(key,freq1);
              
          }
          
          return term_idf_table;
    }
    

    public double cosineSimilarity(double [] vect1,double [] vect2)
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
    
    private void writeIDFtable() throws IOException
    {
         FileWriter fw = new FileWriter("C:\\Users\\Hakimee\\Desktop\\"+"doc_num_ic.txt");
        PrintWriter pw = new PrintWriter(fw);
        
         for (Map.Entry<String, Integer> term_ : term_doc_count.entrySet()) {
               String lingo_term = term_.getKey();
               Integer idf_value = term_.getValue();
               
              // Double denom = Math.pow(10, idf_value);
               // Double doc_count = 54 / denom;
                  // pw.print(lingo_term+ "\t" + doc_count.intValue());
                   pw.print(lingo_term+ "\t" + idf_value);
                   pw.println();
         }
         
         pw.close();
    }
}
