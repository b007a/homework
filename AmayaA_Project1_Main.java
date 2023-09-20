import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;

public class AmayaA_Project1_Main{

    int numRows;
    int numCols;
    int minVal;
    int maxVal;

    int[] histArray;

    double[] gaussArray;

    int biGaussThrVal;
    int maxHeight;


    public static void main(String[] args) throws IOException {
        AmayaA_Project1_Main program = new AmayaA_Project1_Main();
        program.run(args);
    }
    public void run(String[] args) throws IOException{

        Scanner inFile = new Scanner(new FileReader(args[0]));

        FileWriter output1 = new FileWriter(args[1]);
        FileWriter deBugFile = new FileWriter(args[2]);

        numRows = inFile.nextInt();
        numCols = inFile.nextInt();
        minVal = inFile.nextInt();
        maxVal = inFile.nextInt();

        output1.write(Integer.toString(numRows) + " ");
        output1.write(Integer.toString(numCols) + " "); 
        output1.write(Integer.toString(minVal) + " ");
        output1.write(Integer.toString(maxVal) + "\n");



        
        histArray = new int[maxVal+1];
        gaussArray = new double[maxVal+1];
        maxHeight = loadHist(histArray, inFile);
        dispHist(histArray, output1);
        biGaussThrVal = biGauss(histArray, gaussArray, maxHeight, minVal, maxVal, deBugFile);
        output1.write("The biGaussThrVal is " + Integer.toString(biGaussThrVal)); //w caption 


        inFile.close();
        output1.flush();
        output1.close();
        deBugFile.flush();
        deBugFile.close();
        
    }

    int loadHist(int[] histogramArray, Scanner readIn){
        //reads the first 4 digits in the top row
        int numRows = readIn.nextInt();
        int numCols = readIn.nextInt();
        int minVal = readIn.nextInt();
        int maxVal = readIn.nextInt();
        readIn.nextLine();
        int highest=0;
        //highest is to find the max frequency
        
        while(readIn.hasNextLine()){
            String line = readIn.nextLine();
            String[] parts = line.split(" ");

            if (parts.length == 2){
                histogramArray[Integer.parseInt(parts[0])] = Integer.parseInt(parts[1]);
                if(highest < Integer.parseInt(parts[1])){
                    highest = Integer.parseInt(parts[1]);
                }
            }

        }
        return highest;
    }

    void dispHist(int[] histogramArray, FileWriter output) throws IOException{
        for(int i=0; i<histogramArray.length; i++){
            output.write(Integer.toString(i) + " ("+ Integer.toString(histogramArray[i]) + "):");
            for(int j=0; j<histogramArray[i]; j++){
                output.write("+");
            }
            output.write("\n");
        }

    }

    int[] setZero(int[] histogramArray){
        //sets the array to zero
        for(int i=0; i<histogramArray.length; i++){
            histogramArray[i]=0;
        }
        return histogramArray;
    }

    int biGauss(int[] histogramArray, double[] gaussArray, int maxHeight, int minVal, int maxVal, FileWriter debugFile) throws IOException{
        debugFile.write("Entering biGaussian method \n");

        double sum1;
        double sum2;
        double total;
        double minSumDiff;
        
        int offset = (maxVal-minVal)/10;
        int dividePt = offset;
        int bestThr = dividePt;
        minSumDiff = 99999.0;
        
        while(dividePt<(maxVal-offset)){
            setZero(histogramArray); //reset in every iteration
            sum1 = fitGauss(0, dividePt, histogramArray, gaussArray, debugFile); //first gaussian curve
            sum2 = fitGauss(dividePt, maxVal, histogramArray, gaussArray, debugFile); // second gaussian curve

            total = sum1 + sum2;
            if(total<minSumDiff){
                minSumDiff=total;
                bestThr=dividePt;
            }

            debugFile.write("dividePt is " + Integer.toString(dividePt) + "\n");
            debugFile.write("sum1 is " + Double.toString(sum1) + "\n");
            debugFile.write("sum2 is " + Double.toString(sum2) + "\n");
            debugFile.write("total is " + Double.toString(total) + "\n");
            debugFile.write("minSumDiff is " + Double.toString(minSumDiff) + "\n");
            dividePt++;
        }
        debugFile.write("Leaving biGauss method, the minSumDiff is " + Double.toString(minSumDiff)+" the bestThr is " + Integer.toString(bestThr));
        return bestThr;


    }

    double computeMean(int leftIndex, int rightIndex, int maxHeight, int[] histogramArray, FileWriter debugFile) throws IOException{
        debugFile.write("Entering computeMean method \n");
        maxHeight=0;
        double sum=0;
        double numPixels=0;
        int index=leftIndex;

        while(index<rightIndex){
            sum += (histogramArray[index]*index);
            numPixels += histogramArray[index];

            if(histogramArray[index] > maxHeight){
                maxHeight = histogramArray[index];
            }
            index++;
        }
        double result = sum/numPixels;

        debugFile.write("Leaving computeMean method, the maxHeight is " + Integer.toString(maxHeight) + " and the result is " + Double.toString(result) + "\n");
        return result;

    }

    double computeVar(int leftIndex, int rightIndex, double mean, int[] histogramArray, FileWriter debugFile) throws IOException{
        debugFile.write("Entering computeVar method \n");
        double sum = 0.0;
        double sumPixels = 0.0;
        int index = leftIndex;
        

        while(index<rightIndex){
            sum += histogramArray[index] * (Math.pow((index-mean), 2));
            sumPixels += histogramArray[index];

            index++;
        }

        double result = sum/sumPixels;
        debugFile.write("Leaving computeVar method returning result " + Double.toString(result) + "\n");
        return result;

    }

    double fitGauss(int leftIndex, int rightIndex, int[] histogramArray, double[] gaussArray, FileWriter debugFile) throws IOException{
        debugFile.write("Entering fitGauss method \n");

        double var;
        double sum = 0.0;
        double gVal;
        double maxVal;
        int index=leftIndex;

        double thismean = computeMean(leftIndex, rightIndex, maxHeight, histogramArray, debugFile);
        var = computeVar(leftIndex, rightIndex, thismean, histogramArray, debugFile);
        
        while(index<=rightIndex){
            gVal = modifiedGauss(index, thismean, var, maxHeight);
            sum+=Math.abs(gVal-histogramArray[index]);
            gaussArray[index]=gVal;
            index++;
        }

        debugFile.write("Leaving fitGauss method, the sum is " + Double.toString(sum) + "\n");
        return sum;

    }

    double modifiedGauss(int x, double mean, double var, int maxHeight){
        return (maxHeight*Math.exp((Math.pow((x-mean),2)/(2*var))));

    }

}