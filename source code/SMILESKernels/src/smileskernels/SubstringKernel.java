/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smileskernels;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author hakime_asus
 */
public class SubstringKernel {
    
    public double substringKernelSim(String smiles1, String smiles2)
    {
        double sim_score =0;
        int q = 2;
        
        Map<String, Integer> subs1 = findSubs(smiles1, q);
        Map<String, Integer> subs2 = findSubs(smiles2, q);
        
        sim_score = calculateSim(subs1, subs2);
        
        //System.out.println(sim_score);
        
        return sim_score;
    }
    
    
    private Map<String, Integer> findSubs(String smiles, int q)
    {
        Map<String, Integer> subsList = new LinkedHashMap<>();
        
        if(smiles.length() < q)
            q = smiles.length();
        
        while(q<=smiles.length())
        {
        
            for (int i = 0; i < smiles.length()-(q-1); i++) {

                String sub= smiles.substring(i,i+q);
                //System.out.println(sub);
                
                if(!subsList.containsKey(sub))
                    subsList.put(sub, 1);
                else
                {
                    int freq = subsList.get(sub);
                    subsList.remove(sub);
                    subsList.put(sub, freq+1);
                    
                }
                        }
            q = q +1;
        }
        
        return subsList;
    }
    
    private double calculateSim(Map<String, Integer> subList1, Map<String, Integer> subList2)
    {
        double sim =0;
        
        if(subList1.size() > subList2.size())
        {
            for (Map.Entry<String, Integer> entry : subList1.entrySet()) {
                    String key = entry.getKey();
                    Integer freq1 = entry.getValue();
                    
                    if(subList2.containsKey(key))
                    {
                        Integer freq2= subList2.get(key);
                        
                        sim = sim + (freq1*freq2);
                    }
                   
                }
        }
        else
        {
              for (Map.Entry<String, Integer> entry : subList2.entrySet()) {
                    String key = entry.getKey();
                    Integer freq1 = entry.getValue();
                    
                    if(subList1.containsKey(key))
                    {
                        Integer freq2= subList1.get(key);
                        
                        sim = sim + (freq1*freq2);
                    }
                   
                }
        }
        
        return sim;
    }
    
    
    
}
