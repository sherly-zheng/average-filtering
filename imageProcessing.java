import java.io.*;
import java.util.Scanner;

public class imageProcessing{
	private int numRows;
	private int numCols;
	private int minVal;
	private int maxVal;
	private int newMin;
	private int newMax;
	private int[][] imgInAry;
	private int[][] imgOutAry;
	private int[][] mirrorFramedAry;
	private int[][] tempAry;
	private int[] hist;
	private int[] neighborAry;
	
	public imageProcessing(int rows, int cols, int min, int max){
		numRows = rows;
		numCols = cols;
		minVal = min;
		maxVal = max;
		newMin = max;
		newMax = min;
		imgInAry = new int[numRows][numCols];
		imgOutAry = new int[numRows][numCols];
		mirrorFramedAry = new int[numRows+2][numCols+2];
		tempAry = new int[numRows+2][numCols+2];
		neighborAry = new int[9];		
	}
	
	public void mirrorFramed(){
		for(int c = 0; c < numCols+2; c++){
			mirrorFramedAry[0][c] = mirrorFramedAry[1][c];
			mirrorFramedAry[numRows+1][c] = mirrorFramedAry[numRows][c];
		}
		for(int r = 0; r < numRows+2; r++){
			mirrorFramedAry[r][0] = mirrorFramedAry[r][1];
			mirrorFramedAry[r][numCols+1] = mirrorFramedAry[r][numCols];
		}
	}
	
	public void loadImage(Scanner Input1){
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numCols; j++){
				int temp = Input1.nextInt();
				imgInAry[i][j] = temp;
				mirrorFramedAry[i+1][j+1] = temp;
			}
		}
	}
	
	public void loadNeighbors(int i, int j){
		int n = 0;
		for(int row = i-1; row <= i+1; row++){
			for(int col = j-1; col <= j+1; col++){
				neighborAry[n] = mirrorFramedAry[row][col];
				n++;
			}
		}
	}
	
	public void ComputeHistogram(){
		hist = new int[maxVal+1];
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				hist[imgInAry[i][j]]++;
			}
		}
	}
	
	public void printHist(BufferedWriter Output1) throws Exception {
		Output1.write(numRows + " " + numCols + " " + minVal + " " + maxVal);
		Output1.newLine();
		for(int i = 0; i <= maxVal; i++){
			Output1.write(i + " " + hist[i]);
			Output1.newLine();
		}
	}
	
	public void computeAVG3x3(){
		for(int i = 1; i < numRows+1; i++){
			for(int j = 1; j < numCols+1; j++){
				loadNeighbors(i,j);
				tempAry[i][j] = Avg3x3();
				if(tempAry[i][j] < newMin)
					newMin = tempAry[i][j];
				if(tempAry[i][j] > newMax)
					newMax = tempAry[i][j];
			}
		} 
	}
	
	public int Avg3x3(){
		int sum = 0;
		for (int i = 0; i < 9; i++){
			sum += neighborAry[i];
		}
		return sum/9;
	}
	
	public void computeThreshold(int thr_value){
		for(int i = 1; i < numRows+1; i++){
			for(int j = 1; j < numCols+1; j++){
				int p = tempAry[i][j];
				if(p >= thr_value)
					imgOutAry[i-1][j-1] = 1;
				else
					imgOutAry[i-1][j-1] = 0;
			}
		}
	}
	
	public void prettyPrint(BufferedWriter Output3) throws Exception {
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				int p = imgOutAry[i][j];
 				 if(p > 0)
					Output3.write(p + " ");
				else
					Output3.write("  ");
			}
			Output3.newLine();
		}
	}
	
	public void printHeader(BufferedWriter Output2) throws Exception {
		Output2.write(numRows + " " + numCols + " " + newMin + " " + newMax);
		Output2.newLine();
	}
	
	public void printTempAry(BufferedWriter Output2) throws Exception {
		for(int i = 1; i < numRows+1; i++){
			for(int j = 1; j < numCols+1; j++){
				Output2.write(tempAry[i][j] + " ");
			}
			Output2.newLine();
		}
	}
}