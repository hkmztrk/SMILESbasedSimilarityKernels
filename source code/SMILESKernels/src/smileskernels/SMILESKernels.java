package smileskernels;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ioops.readSMILES;

import java.io.*;
import java.util.*;

//import molops.runCommandPrompt;


/**
 *
 * @author hakime_asus
 */
public class SMILESKernels {

   
     public static ArrayList<String> smiles = new ArrayList<>();
     public  static ArrayList<String> compounds = new ArrayList<>();
     public static String folderpath = ".\\data\\"; /*put the data folder under SMILESKernels folder*/
     public static String outputpath = ".\\simmatrix\\"; /*results are gathered here*/
     public static int lingoLen = 4; /*change LINGO length for TF methods*/

     
    public static void main(String[] args) throws IOException, Exception {

    	
    	//runCommandPrompt.SimcompTool();
    	 long startTime = System.currentTimeMillis();

        String [] ligKers = {"edit", "substring", "lingo3", "lingo4", "lingo5", "smifp34Man","smifp34Tan", 
			     "smifp38Man", "smifp38Tan", "NLCS", "HLCS", "TF", "TFIDF"};

     
        String[] dataset = {"e", "gpcr", "nr", "ic"};
        

        for (String ligkernel_name : ligKers) {
            
            System.out.println("---------"+ligkernel_name+"------------------");
            
            for (String folder_name : dataset) {

                 System.out.println("---------"+folder_name+"------------------");
                 
                 smiles.clear(); compounds.clear();
                 
               

                readSMILES.readSMILESfromFolder(folderpath + folder_name + " - smi_u\\", compounds, smiles);
  
                 TFIDF tfidfKer = new TFIDF(smiles, lingoLen); 
                 TF tfKer = new TF(smiles,lingoLen);
                 SMIfp38 smifp38Ker = new SMIfp38(); 
                 SMIfp34 smifp34Ker = new SMIfp34(); 
                 SubstringKernel subKer = new SubstringKernel();
                 LINGO lingoKer = new LINGO();
                 CombinedLCS longestcs = new CombinedLCS();
                                                                      
                 String [][] sim_Matrix= simMatrix();

                 for (int i = 0; i < smiles.size(); i++) {

                     System.out.println(i);
                             
                     String smiles2 =  smiles.get(i);    
                     for (int j = 0; j < smiles.size(); j++) {

                      
                    	  String smiles1 =   smiles.get(j);    
                           double sim_score =0;

                         switch(ligkernel_name)
                         {

                            case "edit":
                                 sim_score = distance.edit.editDistance(smiles1, smiles2); 
                                 break;
                             case "substring":
                                 sim_score = subKer.substringKernelSim(smiles1, smiles2); // KERNEL #1
                                 break;
                             case "lingo2":
                                 sim_score = lingoKer.LINGOSim(smiles1, smiles2, 2);
                                  break;
                           case "lingo3":
                                 sim_score = lingoKer.LINGOSim(smiles1, smiles2,3); 
                                 break;
                             case "lingo4":
                                 sim_score = lingoKer.LINGOSim(smiles1, smiles2,4); 
                                 break;
                             case "lingo5":
                                 sim_score = lingoKer.LINGOSim(smiles1, smiles2,5); 
                                 break;
                           case "smifp34Euc":
                                 sim_score = smifp34Ker.smifp34EuclidSim(smiles1, smiles2); 
                                 break;
                             case "smifp34Man":
                                 sim_score = smifp34Ker.smifp34ManhattanSim(smiles1, smiles2);
                                 break;
                             case "smifp34Tan":
                                 sim_score = smifp34Ker.smifp34Tanimoto(smiles1, smiles2);
                                 break;
                             case "smifp38Euc":
                                 sim_score = smifp38Ker.smifp38EuclidSim(smiles1, smiles2); 
                                 break;
                             case "smifp38Man":
                                 sim_score = smifp38Ker.smifp38ManhattanSim(smiles1, smiles2);
                                 break;
                             case "smifp38Tan":
                                 sim_score = smifp38Ker.smifp38Tanimoto(smiles1, smiles2);
                                 break; 
                               case "NLCS":
                                 sim_score = longestcs.NormalizedLCS(smiles1, smiles2);
                                 break;
                             case "HLCS":
                                 sim_score = longestcs.HybridLCSKernel(smiles1, smiles2);
                                 break; 
                             case "TFIDF":
                                 sim_score = tfidfKer.getCosineSim(smiles1, smiles2);
                                 break;
                             case "TF":
                                 sim_score = tfKer.getCosineSim_(smiles1, smiles2);
                                 break;
                             case "empty":
                                 sim_score = 1;
                              break;
                              default:
                                 System.out.println("---");
                         }

                           //String result = String.format("%.6f", sim_score);
                           double finalValue = Math.round( sim_score * 100000.0 ) / 100000.0;
                        	
                          sim_Matrix[i+1][j+1] = String.valueOf(finalValue);

                    }
                 }

                 writeSimMatrix(sim_Matrix, smiles.size()+1, outputpath + "\\", ligkernel_name+ "_"+ folder_name + "_simmat_dc.txt");
                
             }
        }

        long stopTime = System.currentTimeMillis();
        System.out.println("Elapsed time was " + (stopTime - startTime) + " seconds."); 
    }
    
    

    
    public static String[][] simMatrix()
    {
       String [][] similarityMatrix = new String[compounds.size()+1][compounds.size()+1];
       
       similarityMatrix[0][0] = "data";

        for (int i = 0; i < compounds.size(); i++) {
            similarityMatrix[0][i+1] = compounds.get(i);
        }
        
        for (int i = 0; i < compounds.size(); i++) {
            similarityMatrix[i+1][0] = compounds.get(i);
        }
       
       
       return similarityMatrix;
    }
    
    
    public static void writeSimMatrix(String [][] sim, int size,String path, String filename) throws IOException
    {
       
        
        FileWriter fw = new FileWriter(path+"//"+filename);
        PrintWriter pw = new PrintWriter(fw);
        
        for (int i = 0; i < size; i++) {
            
            for (int j = 0; j < size; j++) {
            	if(j<size-1)
                 pw.print(sim[i][j] + "\t");
            	else if(j ==size-1)
            		pw.print(sim[i][j]);
            }
            pw.println();
        }
        
        pw.close();
       
        
    }
    
 
       


 
    
    
}
    
    
   