/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smileskernels;

/**
 * Original SMIfp source code is used in getSMIfp function
 * http://pubs.acs.org/doi/suppl/10.1021/ci400206h/suppl_file/ci400206h_si_001.pdf
 */
public class SMIfp34 {
    
        public double smifp34EuclidSim(String smiles1, String smiles2) throws Exception
    {
      
        int [] smifp1= getSMIfp(smiles1);
        int [] smifp2= getSMIfp(smiles2);
        
        return distance.euclid.euclidDistance(smifp1, smifp2);
        
       
    }
    
      public double smifp34ManhattanSim(String smiles1, String smiles2) throws Exception
    {
        
        
        int [] smifp1= getSMIfp(smiles1);
        int [] smifp2= getSMIfp(smiles2);
        
        return distance.manhattan.manhattanDistance(smifp1, smifp2);
        
        
      
    }
      
        public double smifp34Tanimoto(String smiles1, String smiles2) throws Exception
    {
      
        int [] smifp1= getSMIfp(smiles1);
        int [] smifp2= getSMIfp(smiles2);
        
        return distance.tanimoto.tanimotoSimilarity(smifp1, smifp2);
        
       
    }
    
    
    
     private int[] getSMIfp(String SMILES) throws Exception { 
        
            int BrkRndOp = 0, BrkSqrOp = 0; /*Brackets*/ 
            boolean SqrBrk = false; /*keeps track whether an Square Bracket is currently Open*/ 
            int SB = 0, DB = 0, TB = 0; /*Bond types*/ 
            
            int ChargePlus = 0, ChargeMinus = 0; /*Charges*/ 
            int AtomB = 0, AtomC = 0, AtomN = 0, AtomO = 0, AtomP = 0, AtomS = 0, AtomF = 0, AtomCl = 0, 
            AtomBr = 0, AtomI = 0, AtomH = 0; 
            int Atomc = 0, Atomo = 0, Atoms = 0, Atomn = 0, Atomp = 0; 
            int AtomOther = 0; 
 
            
            /*Rings*/ 
            int[] RingIdx = new int[9]; 

            
            for (int i = 0; i < RingIdx.length; i++) { 
            RingIdx[i] = 0; 
            } 
            
            int PerCent = 0; 
            for (int i = 0; i < SMILES.length(); i++) {
            char ThisChar = SMILES.charAt(i); 
            /* Next Character in SMILES has to be considered*/ 
            char NextChar; 
            if (i != (SMILES.length() - 1)) { 
            NextChar = SMILES.charAt(i + 1); 
            } else { 
            NextChar = ' '; 
            } 
            /* Without Square Brackets, only the following Atoms are allowed: 
            * B,C,N,O,P,S,F,Cl,Br,I 
            * and ofcourse also their aromatic Version: 
            * c,n,o,s 
            */ 
            if (!SqrBrk) { 
            /*Atoms First*/ 
            if (ThisChar == 'C') { 
            if (NextChar == 'l') { 
            AtomCl++; 
            i++; 
            } else { 
            AtomC++; 
            } 
            } else if (ThisChar == 'B') { 
            if (NextChar == 'r') { 
            AtomBr++; 
            i++; 
            } else { 
            AtomB++; 
            } 
            } else if (ThisChar == 'N') { 
            AtomN++; 
            } else if (ThisChar == 'O') { 
            AtomO++; 
            } else if (ThisChar == 'P') { 
            AtomP++; 
            } else if (ThisChar == 'S') { 
            AtomS++; 
            } else if (ThisChar == 'F') { 
            AtomF++; 
            } else if (ThisChar == 'I') { 
            AtomI++; 
            } else if (ThisChar == 'c') { 
            Atomc++; 
            } else if (ThisChar == 'o') { 
            Atomo++; 
            } else if (ThisChar == 's') { 
            Atoms++; 
            } else if (ThisChar == 'n') { 
            Atomn++; 
            } else if (ThisChar == 'p') { 
            Atomp++; 
            } else if (ThisChar == '(') { /*Brackets*/ 
            BrkRndOp++; 
            } else if (ThisChar == '[') { 
            BrkSqrOp++; 
            SqrBrk = true; 
            } else if (ThisChar == '=') { /*Bonds*/ 
           
            DB++; 
            } else if (ThisChar == '#') { 
            TB++; 
            } else if (ThisChar == '1') { /*Rings*/ 
            RingIdx[0]++; 
            } else if (ThisChar == '2') { 
            RingIdx[1]++; 
            } else if (ThisChar == '3') { 
            RingIdx[2]++; 
            } else if (ThisChar == '4') { 
            RingIdx[3]++; 
            } else if (ThisChar == '5') { 
            RingIdx[4]++; 
            } else if (ThisChar == '6') { 
            RingIdx[5]++; 
            } else if (ThisChar == '7') { 
            RingIdx[6]++; 
            } else if (ThisChar == '8') { 
            RingIdx[7]++; 
            } else if (ThisChar == '9') { 
            RingIdx[8]++; 
            } else if (ThisChar == '0') { 
            RingIdx[9]++; 
            } else if (ThisChar == '%') { 
             PerCent++; 
             i += 2; 
            } else if (ThisChar == '-') { 
            SB++; 
            } 
            } else if (SqrBrk) { 
            if (ThisChar == ']') { 
            SqrBrk = false; 
            } else if (ThisChar == '-') { 
            if ('1' <= NextChar && NextChar<= '9') { 
            ChargeMinus += NextChar - '0'; 
            } else { 
            ChargeMinus++; 
            } 
            } else if (ThisChar == '+') { 
            if ('1' <= NextChar && NextChar<= '9') { 
            ChargePlus += NextChar - '0'; 
            } else { 
            ChargePlus++; 
            } 
            } else if (this.isSmallLetter(ThisChar)) { 
            if (ThisChar == 'o') { 
            Atomo++; 
            } else if (ThisChar == 'c') { 
            Atomc++; 
            } else if (ThisChar == 'n') { 
            Atomn++; 
            } else if (ThisChar == 'p') { 
            Atomp++; 
            } else if (ThisChar == 's') { 
            Atoms++; 
            } 
            } else if (this.isCaptialLetter(ThisChar)) { 
            if (ThisChar == 'C') { 
            if (this.isSmallLetter(NextChar)) { 
            if (NextChar == 'l') { 
            AtomCl++; 
            i++; 
            } else { 
            AtomOther++; 
            i++; 
            } 
            } else { 
            AtomC++; 
            } 
            } else if (ThisChar == 'B') { 
            if (this.isSmallLetter(NextChar)) { 
            if (NextChar == 'r') { 
            AtomBr++; 
            i++; 
            } else { 
            AtomOther++; 
            i++; 
            } 
            } else { 
            AtomB++; 
            } 
            } else if (ThisChar == 'N') { 
            if (this.isSmallLetter(NextChar)) { 
            AtomOther++; 
            i++; 
            } else { 
            AtomN++; 
            } 
            } else if (ThisChar == 'O') { 
            if (this.isSmallLetter(NextChar)) { 
            AtomOther++; 
            i++; 
            } else { 
            AtomO++; 
            } 
            } else if (ThisChar == 'P') { 
            if (this.isSmallLetter(NextChar)) { 
            AtomOther++; 
            i++; 
            } else { 
            AtomP++; 
            } 
            } else if (ThisChar == 'S') { 
            if (this.isSmallLetter(NextChar)) { 
            AtomOther++; 
            i++; 
            } else { 
            AtomS++; 
            } 
            } else if (ThisChar == 'F') { 
            if (this.isSmallLetter(NextChar)) { 
            AtomOther++; 
            i++; 
            } else { 
            AtomF++; 
            } 
            } else if (ThisChar == 'I') { 
            if (this.isSmallLetter(NextChar)) { 
            AtomOther++; 
            i++; 
            } else { 
            AtomI++; 
            } 
            } else if (ThisChar == 'H') { 
            if (this.isSmallLetter(NextChar)) { 
            AtomOther++; 
            i++; 
            } else if ('1' <= NextChar && NextChar <= '9') { 
            AtomH += NextChar - '0'; 
            } else { 
            AtomH++; 
            } 
            } else { 
            AtomOther++; 
            if (this.isSmallLetter(NextChar)) { 
            i++; 
            } 
            } 
            } 
            } 
            } 

            int[] SMIfp = new int[34]; // added two chirals, two directional bonds and replace % with .  
            
            
            SMIfp[0] = BrkRndOp; //Only count opening Brackets /*00*/ 
            SMIfp[13] = BrkSqrOp; //Only count opening Brackets /*01*/ 
            SMIfp[19] = SB; /*02*/ 
            SMIfp[2] = DB; /*03*/ 
            SMIfp[9] = TB; /*04*/ 
            SMIfp[8] = RingIdx[0]; //Ring1/*05*/ 
            
            if (RingIdx[0] != 0) { 
            SMIfp[8] = RingIdx[0] / 2; 
            } 
            SMIfp[7] = RingIdx[1]; //Ring2/*06*/ 
            if (RingIdx[1] != 0) { 
            SMIfp[7] = RingIdx[1] / 2; 
            } 
            SMIfp[10] = RingIdx[2]; //Ring3/*07*/ 

            if (RingIdx[2] != 0) { 
            SMIfp[10] = RingIdx[2] / 2; 
            } 
            SMIfp[12] = RingIdx[3]; //Ring4/*08*/ 
            if (RingIdx[3] != 0) { 
            SMIfp[12] = RingIdx[3] / 2; 
            } 
            SMIfp[18] = RingIdx[4]; //Ring5/*09*/ 
            if (RingIdx[4] != 0) { 
            SMIfp[18] = RingIdx[4] / 2; 
            } 
            SMIfp[22] = RingIdx[5]; //Ring6/*10*/ 
            if (RingIdx[5] != 0) { 
            SMIfp[22] = RingIdx[5] / 2; 
            } 
            SMIfp[23] = RingIdx[6]; //Ring7/*11*/ 
            if (RingIdx[6] != 0) { 
            SMIfp[23] = RingIdx[6] / 2; 
            } 
            SMIfp[31] = RingIdx[7]; //Ring8/*12*/ 
            if (RingIdx[7] != 0) { 
            SMIfp[31] = RingIdx[7] / 2; 
            } 
            SMIfp[32] = RingIdx[8]; //Ring9/*13*/ 
            if (RingIdx[8] != 0) { 
            SMIfp[32] = RingIdx[8] / 2; 
            } 
            SMIfp[26] = PerCent; /*14*/  //Replace % with .
            SMIfp[21] = ChargePlus; /*15*/ 
            SMIfp[20] = ChargeMinus; /*16*/ 
            SMIfp[28] = AtomB; /*17*/ 
            SMIfp[5] = AtomC; /*18*/ 
            SMIfp[3] = AtomN; /*19*/ 
            SMIfp[1] = AtomO; /*20*/ 
            SMIfp[27] = AtomP; /*21*/ 
            SMIfp[11] = AtomS; /*22*/ 
            SMIfp[24] = AtomF; /*23*/ 
            SMIfp[17] = AtomCl; /*24*/ 
            SMIfp[25] = AtomBr; /*25*/ 
            SMIfp[30] = AtomI; /*26*/ 
            SMIfp[4] = Atomc; /*27*/ 
            SMIfp[14] = Atomo; /*28*/ 
            SMIfp[16] = Atoms; /*29*/ 
            SMIfp[6] = Atomn; /*30*/ 
            SMIfp[33] = Atomp; /*31*/ 
            SMIfp[15] = AtomH; /*32*/ 
            SMIfp[29] = AtomOther; /*33*/ 
        
            return SMIfp; 
            } 
    
    
    
            private boolean isCaptialLetter(char Letter) { 
            if ('A' <= Letter && Letter <= 'Z') { 
            return true; 
            } 
            return false; 
            } 
            
            
            private boolean isSmallLetter(char Letter) 
            { 
                if ('a' <= Letter && Letter <= 'z') 
                { 
                    return true; 
                } 
                   return false; 
             } 
   

    
}
