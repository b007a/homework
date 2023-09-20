
#include <iostream>
#include <fstream>
#include <string>

using namespace std;


void processing(ifstream& inPut, ofstream& outFile1, ofstream& outFile2, int thrVal, int numCols, int numRows){
		
	int pixelVal;
	int count=0;
	int lineCount=0;
		//read integers from the input file
		
	inPut >> pixelVal;
		
		
	while(!inPut.fail()){

		inPut >> pixelVal;
		if(pixelVal >= thrVal) {
            outFile1 << "1 "; // 1 w a blank
             outFile2 << pixelVal << " "; // pixelval w a blank
				

		}
		else {
			outFile1 << "0 ";// 0 w a blank
			outFile2 << "0 "; // 0 w a blank
		}
		count++;
						
		if(count > numCols) {
			outFile1 << "\n";
			outFile2 << "\n";
			count=0;
			lineCount++;
		}
				
		}
			
}

int main(int argc, const char* argv[]){

    int numRows;
	int numCols;
	int minVal;
	int maxVal;
	int thrVal;
	int count=0;
    

    ifstream inFile;
    inFile.open(argv[1]);
    
    ofstream outFile1, outFile2;
    outFile1.open(argv[2]);
    outFile2.open(argv[3]);

    inFile >> numRows;
    inFile >> numCols;
    inFile >> minVal;
    inFile >> maxVal;
    cout << "What's the threshold? " << endl;
    cin >> thrVal;

    outFile1 << numRows << " ";
    outFile1 << numCols << " ";
    outFile1 << 0 << " ";
    outFile1 << 1 << endl;

    outFile2 << numRows << " ";
    outFile2 << numCols << " ";
    outFile2 << 0 << " ";
    outFile2 << maxVal << endl;

    processing(inFile, outFile1, outFile2, thrVal, numCols, numRows);


    inFile.close();
    outFile1.close();
    outFile2.close();

}
	

	