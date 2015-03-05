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
public class edit {
    
   
     public static double editDistance(String s, String t) {
        
        int m = s.length();
        int n = t.length();
        
        double[][] matrix = new double[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            matrix[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                
                if (s.charAt(i - 1) == t.charAt(j - 1)) 
                {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } 
                else 
                {
                    double replace = matrix[i - 1][j - 1] + 1;
                    double insert = matrix[i][j - 1] + 1;
                    double delete =matrix[i - 1][j] + 1;
                    
                    matrix[i][j] = min(delete, insert, replace);
                }
            }
        }
        double a = 1 - (Double.valueOf(matrix[m][n]) / Double.valueOf(Math.max(m, n)) ); //EDITED**
        return a;
    }

    public static double min(double a, double b, double c) {
        return (Math.min(Math.min(a, b), c));
    }
    
 
    
   
}
