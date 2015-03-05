/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smileskernels;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author hakime_asus
 */
public class SMILESKernels {

   
     public static ArrayList<String> smiles = new ArrayList<>();
     public  static ArrayList<String> compounds = new ArrayList<>();
     public static String folderpath = "C:\\Users\\Hakimee\\Desktop\\"; /*EDIT HERE*/
     
    public static void main(String[] args) throws IOException, Exception {


     //   runCommandPrompt("e2");
    // long startTime = System.currentTimeMillis();
        

               
       // String [] ligKers = {"edit", "substring", "lingo3", "lingo4", "lingo5", "smifp34Man","smifp34Tan", 
	//		     "smifp38Man", "smifp38Tan", "NLCS", "HLCS", "TFIDF"};

        String [] ligKers = {"empty" };
     
        String[] dataset = {"e","ic","nr","gpcr"};

        for (String ligkernel_name : ligKers) {
            
            System.out.println("---------"+ligkernel_name+"------------------");
            
            for (String folder_name : dataset) {

                 System.out.println("---------"+folder_name+"------------------");
                 
                 smiles.clear(); compounds.clear();

                 readSMILES(folderpath + "COMPOUND\\"+ folder_name+ " - smi_u");
             
                 
                 TFIDF tfidfKer = new TFIDF(smiles, 4); // LINGO = 5
                 TF tfKer = new TF(smiles,4);
                 SMIfp38 smifp38Ker = new SMIfp38(); 
                 SMIfp34 smifp34Ker = new SMIfp34();
                 SubstringKernel subKer = new SubstringKernel();
                 LINGO lingoKer = new LINGO();
                 CombinedLCS longestcs = new CombinedLCS();
                                                  
               
                                                  
                 String [][] sim_Matrix= simMatrix();

                 for (int i = 0; i < smiles.size(); i++) {

                     System.out.println(i);
                     String smiles1 = smiles.get(i); // "[CH2]N=C=O";

                     for (int j = 0; j < smiles.size(); j++) {

                         String smiles2 = smiles.get(j);  //"CN=C=O"; 
                         
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
                           double finalValue = Math.round( sim_score * 1000000.0 ) / 1000000.0;
                          sim_Matrix[i+1][j+1] = String.valueOf(finalValue);

                    }
                 }

                 writeSimMatrix(sim_Matrix, smiles.size()+1, folderpath + "COMPOUND\\sim_matrix_u\\", ligkernel_name+ "_"+ folder_name + "_simmat_dc.txt");
                
             }
        }

        // long stopTime = System.currentTimeMillis();
        //System.out.println("Elapsed time was " + (stopTime - startTime) + " seconds."); 
    }
    
    
    public  static void readSMILES(String path) throws IOException
    {
      
       
       File folder = new File(path);
       File[] listOfFiles = folder.listFiles();

     for (int i = 0; i < listOfFiles.length; i++) {
      File file = listOfFiles[i];
      
      String compoundName= file.getName();
      compoundName = compoundName.replace(".smi", "");
      compounds.add(compoundName);
      
      
      if (file.isFile() && file.getName().endsWith(".smi")) {
          BufferedReader reader = null;
          try {
              reader = new BufferedReader(new FileReader(file));
          }  catch (FileNotFoundException ex) {
              Logger.getLogger(SMILESKernels.class.getName()).log(Level.SEVERE, null, ex);
          }
          String line = null;
            while ((line = reader.readLine()) != null) {
        
               /* if(line.contains("cccc") && line.length() < 30)
                    System.out.println(compoundName);*/
                   
                line= modifySMILES(line);
                smiles.add(line);
            }
       
        } 
      }
       
      
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
                 pw.print(sim[i][j] + "\t");
            }
            pw.println();
        }
        
        pw.close();
       
        
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
       
       
    public static void runCommandPrompt(String data) throws IOException
    {
           long startTime = System.currentTimeMillis();
         String query = "curl -F query_file=@"+ data+ ".mol -F target_file=@e1" + ".mol http://rest.genome.jp/simcomp2/";
         
           ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", "cd \"C:\\Users\\Hakimee\\Desktop\\COMPOUND\" &&" + query);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
        
   
        long stopTime = System.currentTimeMillis();
        System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
        
    }
    
}
    
    
   