/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smileskernels;
import java.util.ArrayList;
import java.util.List;


/**
 * LCS code: http://introcs.cs.princeton.edu/java/96optimization/LCS.java
 * 
 */
public class CombinedLCS {
    
    
    public double HybridLCSKernel(String smi1, String smi2)
    {
        double sim1= NormalizedLCS(smi1, smi2);
        double sim2= NormalizedMaxConsecutiveLCS(smi1, smi2);
        double sim3= NormalizedMaxConsecutiveLCSn(smi1, smi2);
        
        double weight=0.33;
        
        return (weight * sim1 + weight * sim2 + weight * sim3);
        
        
    }
    
    public double NormalizedLCS(String smi1, String smi2)
    {
           
        int M = smi1.length();
        int N = smi2.length();

        // opt[i][j] = length of LCS of x[i..M] and y[j..N]
        int[][] opt = new int[M+1][N+1];

        // compute length of LCS and all subproblems via dynamic programming
        for (int i = M-1; i >= 0; i--) {
            for (int j = N-1; j >= 0; j--) {
                if (smi1.charAt(i) == smi2.charAt(j))
                    opt[i][j] = opt[i+1][j+1] + 1;
                else 
                    opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
            }
        }

        // recover LCS itself and print it to standard output
        String LCS="";
        int i = 0, j = 0;
        while(i < M && j < N) {
            if (smi1.charAt(i) == smi2.charAt(j)) {
                LCS = LCS + String.valueOf(smi1.charAt(i));
                i++;
                j++;
            }
            else if (opt[i+1][j] >= opt[i][j+1]) i++;
            else                                 j++;
        }
       
        return Double.valueOf(Math.pow(LCS.length(),2)) / Double.valueOf(M * N);
    }
    
    
    public double NormalizedMaxConsecutiveLCS(String smi1, String smi2)
    {
        int smi1_n= smi1.length();
        int smi2_n = smi2.length();
        String smi1temp = smi1;
        
        String commonSeq="";
        String commonSeq2="";
        
        if(smi1.length() <= smi2.length())
        {
            while(smi1.length() >=0)
            {
                if(smi2.contains(smi1))
                {
                    commonSeq=smi1;
                    break;
                }
                else
                {
                    smi1= smi1.substring(0,smi1.length()-1);
 
                }
            }
        }
        else if(smi2.length() < smi1.length())
        {
            while(smi2.length()>=0)
            {
                if(smi1.contains(smi2))
                {
                    commonSeq=smi2;
                    break;
                }
                else
                {
                    smi2= smi2.substring(0,smi2.length()-1);
                }
            }
        }
        
        if(smi1temp.length() == smi2.length() && !smi1.equals(smi2))
        {
        	
        	 //COMPUTE 2
        	 while(smi2.length()>=0)
             {
                 if(smi1temp.contains(smi2))
                 {
                     commonSeq2=smi2;
                     break;
                 }
                 else
                 {
                     smi2= smi2.substring(0,smi2.length()-1);
                 }
             }
        	 
        	
        	if(commonSeq.length() < commonSeq2.length()) 
        		commonSeq = commonSeq2;
        }

        
        double sim_score = Double.valueOf(Math.pow(commonSeq.length(), 2)) / Double.valueOf(smi1_n * smi2_n);
        
        return sim_score;
        
    }
    
     public double NormalizedMaxConsecutiveLCSn(String smi1, String smi2)
    {
        int smi1_n= smi1.length();
        int smi2_n = smi2.length();
        
        String commonSeq="";
        
        if(smi1.length() < smi2.length())
        {
          List<String> ngrams = findSubs(smi1);
          
            for (int i = ngrams.size()-1; i >=0 ; i--) {
                commonSeq= ngrams.get(i);
                
                if(smi2.contains(commonSeq))
                    break;

            }
        }
        else 
        {
           List<String> ngrams = findSubs(smi2);
          
            for (int i = ngrams.size()-1; i >=0 ; i--) {
                commonSeq= ngrams.get(i);
                
                if(smi1.contains(commonSeq))
                    break;

            } 
        }
        
        double sim_score = Double.valueOf(Math.pow(commonSeq.length(), 2)) / Double.valueOf(smi1_n * smi2_n);
        
        return sim_score;
    }
     
     
         private List<String> findSubs(String smiles)
        {
          List<String> subsList = new ArrayList<String>();

           int q=1;

                while(q<=smiles.length())
                {

                    for (int i = 0; i < smiles.length()-(q-1); i++) {

                        String sub= smiles.substring(i,i+q);

                        if(!subsList.contains(sub))
                            subsList.add(sub);
                       
                                }
                    q = q +1;
                }
          

           return subsList;
       }
    
}
