package molops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class runCommandPrompt {

/* you need to have SIMCOMP tool installed in your computer to be able run these functions */
    
    public static void SimcompTool(String foldername) throws IOException
    {
    	
        long startTime = System.currentTimeMillis();
        
    	String outputPath = "simcomp1" +folder_name +".txt";
    	String folderPath = "kcfs\\"+folder_name + "\\";
    	
    	
    	FileWriter fw = new FileWriter(outputPath);
    	PrintWriter pw = new PrintWriter(fw);
    	
    	File folder = new File(folderPath);
	    File[] listOfFiles = folder.listFiles();

	    
	     for (int i = 0; i < listOfFiles.length; i++) {
	    	    File file1 = listOfFiles[i];
	    	    String c1= file1.getName();
	    	for (int j = 0; j < listOfFiles.length; j++) {
	            
	    		File file2 = listOfFiles[j];
	    		String c2= file2.getName();
	     
	      
		      String query = "simcompp " +c1+" "+c2;
		         
	          ProcessBuilder builder = new ProcessBuilder(
	           "cmd.exe", "/c", "cd \"kcfs\\ic\" &&" + query);  /*TODO folder name */
		       builder.redirectErrorStream(true);
		       Process p = builder.start();
		       BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		       String line;
		       
		       while (true) {
		           line = r.readLine();
		           if (line == null) {  break; }
		           System.out.println(line);
		           pw.println(line);
	           } 
       
	      }
	    }
    	  
	     pw.close();
         
        long stopTime = System.currentTimeMillis();
        System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
        
        
    }
    
    
    public static void molsFromFolder(String foldername) throws IOException /*TO-DO: CONVERT MOLS TO KCF in E, GPCR, ION, NR*/
    {
    	String folderPath = "data\\" + foldername;

    	
    	File folder = new File(folderPath);
	    File[] listOfFiles = folder.listFiles();
	    
	    for (int i = 0; i < listOfFiles.length; i++) {
    	    File file1 = listOfFiles[i];
    	    String c1= file1.getName().replace(".mol", "");
    	    
    	    try {
				String mols = convertMoltoKcf(c1);
				
				FileWriter fw = new FileWriter(foldername+"_kcf\\" + c1 + ".kcf"); /* make sure you have folders named e_kcf, gpcr_kf etc..*/
		        PrintWriter pw = new PrintWriter(fw);
				pw.write(mols);

				pw.close();
			} 
    	    catch (IOException e) {e.printStackTrace();}
    	    
	    }

    }
    
    public static String convertMoltoKcf(String fileName) throws IOException
    {
    	 
         String molver = "";
   
        String query = "curl -F molfile=@"+fileName+ ".mol  http://rest.genome.jp/mol2kcf/";
        
          ProcessBuilder builder = new ProcessBuilder(
           "cmd.exe", "/c", "cd \"C:\\Users\\Hakimee\\Desktop\\COMPOUND\\nr\" &&" + query);
       builder.redirectErrorStream(true);
       Process p = builder.start();
       BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
       String line;
       int flag =0;
       
       while (true) {
           line = r.readLine();
           if (line == null) { break; }
           System.out.println(line);
           int pos = line.indexOf("ENTRY");
           if(pos != -1 && flag ==0)
           {
        	   flag=1;
        	   line = line.substring(pos,line.length());
           }
           if(flag==1)
        	   molver = molver + line + "\n";
       }
       
      
       return molver;
    }
    
    


}
