package algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/*
 * input case:
 * 1.no tree and exact k lizards where k <= width guarantee there will be a solution
 * 2.no tree and exact k lizards where k>width guarantee there will be no solution
 * 3.have trees:then you have to consider all the possibilities because number of trees will affect the result
 * 
 * */
public class homework {

	char[][] matrix=null;
	char[][] outputMatrix=null;
	String filename="input.txt";
	String algorithmType="";
	int NumOfLizard=0;
	int Width=0;
	boolean isSuccess=false;
	boolean hasTree=false;
	//use tree pos to record the position of tree.
	List<int[]> TreePos=null;
	
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
			TreePos=new ArrayList<int[]>();
			String line="";
			int index=0;
			while((line=input.readLine())!=null){
				for(int i=0;i<Width;i++){		
					matrix[index][i]=line.charAt(i);
					if(matrix[index][i]=='2'){
						hasTree=true;
						TreePos.add(new int[]{index,i});
					}
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
				output.write(heading+"\n");
				for(int i=0;i<Width;i++){
					String temp=new String(outputMatrix[i]);
					output.write(temp+"\n");
				}
			}
			else {
				heading="FAIL";
				output.write(heading+"\n");
			}
			output.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//step2
	public void selector(){
		//guarantee has no solution return directly 
		if(hasTree==false&&NumOfLizard>Width){
			return;
		}
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
	
	//utility functions
	//makeoutput matrix and print
	private void makeOutPutMatrix(List<int[]> pos){
		 for(int i=0;i<pos.size();i++){
			  int rowIndex=pos.get(i)[0];
			  int colIndex=pos.get(i)[1];
			  outputMatrix[rowIndex][colIndex]='1';			
		  }
	}
	
	private void print(char[][] m){
		for(int i=0;i<Width;i++){
            for(int j=0;j<Width;j++){
				  System.out.print(m[i][j]);
			  }
            System.out.println();
		  }
	}
	
	//input is the 2d array and output is a 2d array too
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
		//set the sign of isSuccess in order to guarantee return and not search any more if you find it.
		  if(isSuccess==true)return;
		 // we can conclude that we find the solution if we got current number of lizards position equals num of lizard
		  if(positions.size()==NumOfLizard){
			  makeOutPutMatrix(positions);
			  print(outputMatrix);
			  isSuccess=true;
			  return;
		  }
		  if(index>=Width)return;
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
	
	
	
	public void runBFS(){
		
	}
	
	public void runSA(){
		
	}
	
	
}
