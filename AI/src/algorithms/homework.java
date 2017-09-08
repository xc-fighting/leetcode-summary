package algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class homework {

	char[][] matrix=null;
	char[][] outputMatrix=null;
	String filename="input.txt";
	String algorithmType="";
	int NumOfLizard=0;
	int Width=0;
	boolean isSuccess=false;
	public homework(){
		
	}
	
	//step1
	public void parseInput(){
		try{
			BufferedReader input=new BufferedReader(new FileReader(filename));
			algorithmType=input.readLine();
			Width=Integer.parseInt(input.readLine());
			NumOfLizard=Integer.parseInt(input.readLine());
			matrix=new char[Width][Width];
			outputMatrix=new char[Width][Width];
			String line="";
			int index=0;
			while((line=input.readLine())!=null){
				for(int i=0;i<Width;i++){
					matrix[index][i]=line.charAt(i);
					outputMatrix[index][i]=line.charAt(i);
				}
				index++;
			}
			input.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//step 3
	public void generateOutput(){
		try{
			BufferedWriter output=new BufferedWriter(new FileWriter("output.txt"));
			String heading="";
			if(isSuccess){
				heading="OK";
			}
			else heading="FAIL";
			output.write(heading+"\n");
			for(int i=0;i<Width;i++){
				String temp=new String(outputMatrix[i]);
				output.write(temp+"\n");
			}
			output.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//step2
	public void selector(){
		switch(algorithmType){
			case "DFS":{
				runDFS();
			}break;
			case "BFS":{
				runBFS();
			}break;
			case "SA":{
				runSA();
			}break;
		}
	}
	
	//input is the matrix and output is a matrix too
	//first consider the condition of no trees
	//diagnal1 is from top right to bottom left
	boolean[] col,diagnal1,diagnal2;
	public void runDFS(){
		//initialize the map for that
		col=new boolean[Width];
		diagnal1=new boolean[2*Width-1];
		diagnal2=new boolean[2*Width-1];
		List<int[]> positions=new ArrayList<int[]>();
		putLizard(0,positions);
	}
	
	//try to put a lizard in index th row
	void putLizard(int index,List<int[]> positions){
		  if(isSuccess==true)return;
		  if(index==Width){
			  for(int i=0;i<positions.size();i++){
				  int rowIndex=positions.get(i)[0];
				  int colIndex=positions.get(i)[1];
				  outputMatrix[rowIndex][colIndex]='1';
				  
				
			  }
			  for(int i=0;i<Width;i++){
                   for(int j=0;j<Width;j++){
					  System.out.print(outputMatrix[i][j]);
				  }
                   System.out.println();
			  }
				  
			  isSuccess=true;
			  return;
		  }
		  else{
			  for(int i=0;i<Width;i++){
				  if(matrix[index][i]=='2'||matrix[index][i]=='1')continue;
				  if(col[i]==false && diagnal1[index+i]==false && diagnal2[index-i+Width-1]==false){
						  positions.add(new int[]{index,i});
						  col[i]=true;
						  diagnal1[index+i]=true;
						  diagnal2[index-i+Width-1]=true;
						  putLizard(index+1,positions);
						  col[i]=false;
						  diagnal1[index+i]=false;
						  diagnal2[index-i+Width-1]=false;
						  positions.remove(positions.size()-1);
				   }		  
			  }
		  }
	}
	
	
	
	
	
	
	public void runBFS(){
		
	}
	
	public void runSA(){
		
	}
	
	
}
