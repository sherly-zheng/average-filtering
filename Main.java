import java.io.*;
import java.util.Scanner;

public class Main{
	public static void main (String args[]) throws Exception{
		int rows, cols, min, max;
		
		Scanner Input1 = new Scanner(new FileReader(args[0]));
		BufferedWriter Output1 = new BufferedWriter(new FileWriter(args[2]));
		BufferedWriter Output2 = new BufferedWriter(new FileWriter(args[3]));
		BufferedWriter Output3 = new BufferedWriter(new FileWriter(args[4]));
		rows = Input1.nextInt();
		cols = Input1.nextInt();
		min = Input1.nextInt();
		max = Input1.nextInt();
		imageProcessing image = new imageProcessing(rows, cols, min, max);		
		int thr_value = Integer.parseInt(args[1]);

		image.loadImage(Input1);
		image.ComputeHistogram();
		image.printHist(Output1);
		image.mirrorFramed();
		image.computeAVG3x3();
		image.computeThreshold(thr_value);
		image.prettyPrint(Output3);
		image.printHeader(Output2);
		image.printTempAry(Output2);
		
		Input1.close();
		Output1.close();
		Output2.close();
		Output3.close();
	}
}