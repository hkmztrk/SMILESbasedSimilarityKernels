# SMILESbasedSimilarityKernels

Source code containing different SMILES similarity functions written in Java, Eclipse.
****************************************************************

# Folders 

"data" folder contains the chemical data that is used.

- e: contains unique smiles created from mol files of enzyme data set
	
- gpcr: contains unique smiles created from mol files of enzyme data set
	
- ic: contains unique smiles created from mol files of enzyme data set
	
- nr: contains unique smiles created from mol files of enzyme data set
	
- sim_matrix: contains similarity matrices created by SMILES based algorithms.
	
"source code" folder contains java source code.

# How to Run the Jar File

First, please place "data" folder under the same directory with SMILESSim.jar.

Then, you can run the code below in the command line to see the results for the four data sets,

	java -jar SMILESSim.jar

If you want to calculate similarity of your own SMILES set, then you have to put SMILES files (.smi) under data folder with name newFolder. Then you can run the code below in the command line,

	java -jar SMILESSim.jar newFolder
	


# How to Run the Source Code
You can open the project in Eclipse or NetBeans to run.

##Input
You need to put data folder under source code/SMILESKernels folder for program the read data.

You can place your own folder (newFolder), containing SMILES files (.smi) under data folder with name newFolder.

Then you need to manually add the folder to SMILESKernels.java in the following line,

	String[] dataset = {"e", "gpcr", "nr", "ic", "newFolder"};
	
or

	String[] dataset = {"newFolder"};


##Output

The program will produce text files containing each containing a similarity matrix of the given inputs under the "simmatrix" folder.


