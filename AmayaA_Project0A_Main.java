import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class AmayaA_Project0A_Main {

	
	public static void main(String[] args) throws IOException {

		int numRows;
		int numCols;
		int minVal;
		int maxVal;
		int thrVal;
		int count=0;


		Scanner inFile = new Scanner(new FileReader(args[0]));
		
		FileWriter outFile1 = new FileWriter(args[1]);
		FileWriter outFile2 = new FileWriter(args[2]);
		numRows = inFile.nextInt();

		numCols = inFile.nextInt();
		minVal = inFile.nextInt();
		maxVal = inFile.nextInt();
		
		System.out.println("What is the Threshold?");
		Scanner in = new Scanner(System.in);
		thrVal = in.nextInt();
		in.close();
		
		//first line in the binary image for the dimensions and values
		outFile1.write(Integer.toString(numRows)+" ");
		outFile1.write(Integer.toString(numCols)+ " ");
		outFile1.write("0 ");
		outFile1.write("1\n");
		
		//first line in the non-binary image for the dimensions and values
		outFile2.write(Integer.toString(numRows)+" ");
		outFile2.write(Integer.toString(numCols)+" ");
		outFile2.write("0 ");
		outFile2.write(maxVal + "\n");
		
		processing(inFile, outFile1, outFile2, thrVal, numCols, numRows);
		
		inFile.close();
		outFile1.flush();
		outFile1.close();
		outFile2.flush();
		outFile2.close();
	}
	
	public static void processing(Scanner inPut, FileWriter outFile1, FileWriter outFile2, int thrVal, int numCols, int numRows) throws IOException{
		
		int pixelVal;
		int count=0;
		int lineCount=0;
			//read integers from the input file
		
		pixelVal= inPut.nextInt();
		
		
		while(inPut.hasNextInt()==true){
			pixelVal= inPut.nextInt();
			if(pixelVal >= thrVal) {
				
				outFile1.write("1 ");// write 1 w a blank
				outFile2.write(Integer.toString(pixelVal) + " "); // pixelVal w a blank
			}
			else {
				outFile1.write("0 ");// 0 w a blank
				outFile2.write("0 "); // 0 w a blank
			}
			count++;
						
			if(count > numCols) {
				outFile1.write("\n");
				outFile2.write("\n");
				count=0;
				lineCount++;
			}
				
		}
			
	}
	
}