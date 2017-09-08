package algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class homework {

	char[][] matrix=null;
	char[][] output=null;
	String filename="input.txt";
	String algorithmType="";
	int NumOfLizard=0;
	int Width=0;
	boolean isSuccess=true;
	public homework(){
		
	}
	public void parseInput()throws FileNotFoundException,IOException{
		BufferedReader input=new BufferedReader(new FileReader(filename));
		algorithmType=input.readLine();
		Width=Integer.parseInt(input.readLine());
		NumOfLizard=Integer.parseInt(input.readLine());
		matrix=new char[Width][Width];
		output=new char[Width][Width];
		String line="";
		int index=0;
		while((line=input.readLine())!=null){
			for(int i=0;i<Width;i++){
				matrix[index][i]=line.charAt(i);
			}
			index++;
		}
		input.close();
	}
	
	public void generateOutput(){
		
	}
}
