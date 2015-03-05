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
public class euclid {
    
    // for vectors
    
    public static double euclidDistance(int[] point1, int[] point2)  {
    
      if (point1.length == point2.length) {
        
      Double sum = 0D;
      
      for (int i = 0; i < point1.length; i++) {
        sum = sum
            + (Integer.valueOf(point2[i]).doubleValue() - Integer.valueOf(point1[i]).doubleValue())
            * (Integer.valueOf(point2[i]).doubleValue() - Integer.valueOf(point1[i]).doubleValue());
      }
      return Double.valueOf(1)/Double.valueOf(sum+1);
    }
        return 0;
   
  }
}
