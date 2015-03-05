/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package distance;

/**
 *
 * @author hakime_asus
 */
public class tanimoto {
    
       public static double tanimotoSimilarity(int[] point1, int[] point2)  {
    
      if (point1.length == point2.length) {
        
      Double sum = 0D;
      double count = 0;
      
      for (int i = 0; i < point1.length; i++) {
        
          if(point1[i]==0 && point2[i] ==0)
          {
                sum = sum + 0;
          }
          else
          {
                sum = sum + (1-
            + Math.abs(Integer.valueOf(point2[i]).doubleValue() - Integer.valueOf(point1[i]).doubleValue())
            / Math.abs(Integer.valueOf(point2[i]).doubleValue() + Integer.valueOf(point1[i]).doubleValue()));
        
            count++;
          }
      }
      
      
      return Double.valueOf(sum)/ Double.valueOf(count);
    }
        return 0;
   
  }
    
}
