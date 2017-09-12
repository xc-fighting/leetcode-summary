package algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

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
	
	public boolean check(int row,int col,List<int[]> positions){
		for(int[] pos:positions){
			//appear in the same row
			if(pos[0]==row){
				int[] Tpos=new int[]{-1,-1};
				//if there is a tree which has same row as cur
				for(int i=0;i<TreePos.size();i++){
					if(TreePos.get(i)[0]==row){
						Tpos[0]=TreePos.get(i)[0];
						Tpos[1]=TreePos.get(i)[1];
						break;
					}
				}
				if(Tpos[0]==-1){
					return false;
				}
				else{
					if((Tpos[1]-col)*(Tpos[1]-pos[1])>0)return false;
				}
			}
			//appear in the same col
			if(pos[1]==col){
				int[] Tpos=new int[]{-1,-1};
				//judge whether there is a tree which has the same col
				for(int i=0;i<TreePos.size();i++){
					if(TreePos.get(i)[1]==col){
						Tpos[0]=TreePos.get(i)[0];
						Tpos[1]=TreePos.get(i)[1];
						break;
					}
				}
				if(Tpos[0]==-1)return false;
				else{
					if((Tpos[0]-row)*(Tpos[0]-pos[0])>0)return false;
				}
			}
			if(pos[0]+pos[1]==row+col){
				int[] Tpos=new int[]{-1,-1};
				//judge whether there is a tree which has the same col
				for(int i=0;i<TreePos.size();i++){
					if(TreePos.get(i)[1]+TreePos.get(i)[0]==col+row){
						Tpos[0]=TreePos.get(i)[0];
						Tpos[1]=TreePos.get(i)[1];
						break;
					}
				}
				if(Tpos[0]==-1)return false;
				else{
					if((Tpos[1]-col)*(Tpos[1]-pos[1])>0)return false;
				}
			}
			if(pos[0]-pos[1]==row-col){
				int[] Tpos=new int[]{-1,-1};
				//judge whether there is a tree which has the same col
				for(int i=0;i<TreePos.size();i++){
					if(TreePos.get(i)[0]-TreePos.get(i)[1]==row-col){
						Tpos[0]=TreePos.get(i)[0];
						Tpos[1]=TreePos.get(i)[1];
						break;
					}
				}
				if(Tpos[0]==-1)return false;
				else{
					if((Tpos[1]-col)*(Tpos[1]-pos[1])>0)return false;
				}
			}
		}
		return true;
	}
	//input is the 2d array and output is a 2d array too
	//first consider the condition of no trees
	//diagnal1 is from top right to bottom left

	
	
	public void runDFS(){
		//initialize the map for that
		List<int[]> positions=new ArrayList<int[]>();
		putLizard(0,0,positions);
	}
	
	//try to put a lizard in position(row,col)
	void putLizard(int row,int col,List<int[]> positions){
		//set the sign of isSuccess in order to guarantee return and not search any more if you find it.
		  if(isSuccess==true)return;
		 // we can conclude that we find the solution if we got current number of lizards position equals num of lizard
		  if(positions.size()==NumOfLizard){
			  makeOutPutMatrix(positions);
			  print(outputMatrix);
			  isSuccess=true;
			  return;
		  }		  
		  if(row>=Width)return;
		  for(int i=col;i<Width;i++){
			  if(matrix[row][i]=='2')continue;
			  if(check(row,i,positions)){
					  positions.add(new int[]{row,i});				 
					  putLizard(row+1,0,positions);
					  positions.remove(positions.size()-1);	
					  
					  positions.add(new int[]{row,i});
					  putLizard(row,col+1,positions);
					  positions.remove(positions.size()-1);
					  
				}		  
		  }
		  
	}
	
	Queue<List<int[]>> bfsQueue=new LinkedList<List<int[]>>();
	
	//consider the condition without tree first.
	public void runBFS(){
		//first initialize for the first level
		for(int i=0;i<Width;i++){
			if(matrix[0][i]=='2')continue;
			List<int[]> temp=new ArrayList<int[]>();
			int[] pos=new int[]{0,i};
			temp.add(pos);
			bfsQueue.add(temp);
		}
		//then find the solution of the problem
		int level=0;
		while(bfsQueue.isEmpty()==false){
			//we get the initial size of queue,we name it as size which is number of nodes in current level
			int size=bfsQueue.size();
			//then we pop it one by one from the queue
			for(int i=0;i<size;i++){
				List<int[]> poslist=bfsQueue.poll();
				if(level+1<Width){
					for(int j=0;j<Width;j++){
						if(matrix[level+1][j]=='2')continue;
						if(check(level+1,j,poslist)==true){
							int[] newpos=new int[]{level+1,j};
							poslist.add(newpos);
							//the pre judge condition of end the algorithm
							if(poslist.size()==NumOfLizard){
								//make the output matrix
								makeOutPutMatrix(poslist);
								print(outputMatrix);
								isSuccess=true;
								return;
							}
							List<int[]> newlist=new ArrayList<int[]>(poslist);							
							bfsQueue.offer(newlist);
						}
						else continue;
					}
					
				}
				else{
					if(poslist.size()==NumOfLizard){
						makeOutPutMatrix(poslist);
						isSuccess=true;
						return;
					}
					return;
				}
				
			}
			level++;
		}
	}
	
	public void runSA(){
		
	}
	
	
}
