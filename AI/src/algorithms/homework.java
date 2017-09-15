package algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
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
					  
					  if(hasTree==true){
						  positions.add(new int[]{row,i});
						  putLizard(row,col+1,positions);
						  positions.remove(positions.size()-1);
					  }
					  
					  
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
				if(poslist.size()==NumOfLizard){
					makeOutPutMatrix(poslist);
					print(outputMatrix);
					isSuccess=true;
					return;
				}
				
				if(hasTree==true){
					//first we try for the same level
					for(int[] pos:poslist){
						
						int row=pos[0];
						int col=pos[1];
						
						for(int index=col+1;index<Width;index++){
							
							if(matrix[row][index]=='2')continue;
							//if it can be place in the current row,then place it
							if(check(row,index,poslist)){
								
								List<int[]> newlist=new ArrayList<int[]>(poslist);
								newlist.add(new int[]{row,index});
								if(newlist.size()==NumOfLizard){
									makeOutPutMatrix(newlist);
									print(outputMatrix);
									isSuccess=true;
									return;
								}
								
								bfsQueue.offer(newlist);
								
							}
							else continue;
							
						}
					}
				}
				
				
				//then we try for the next level
				if(level+1<Width){
					for(int j=0;j<Width;j++){
						if(matrix[level+1][j]=='2')continue;
						if(check(level+1,j,poslist)==true){
							int[] newpos=new int[]{level+1,j};				
							//the pre judge condition of end the algorithm
							if(poslist.size()+1==NumOfLizard){
								//make the output matrix
								poslist.add(newpos);
								makeOutPutMatrix(poslist);
								print(outputMatrix);
								isSuccess=true;
								return;
							}
							List<int[]> newlist=new ArrayList<int[]>(poslist);	
							newlist.add(newpos);
							bfsQueue.offer(newlist);
						}
						else continue;
					}
					
				}
			/*	else{
					if(poslist.size()==NumOfLizard){
						makeOutPutMatrix(poslist);
						isSuccess=true;
						return;
					}
					return;
				}*/
				
			}
			level++;
		}
	}
	
	float eps=0.00000001f;
	float delta=0.98f;
	float T=100.0f;
	Random seed=new Random();
	public boolean checkValidPlace(int rowIndex,int colIndex,List<int[]> poslist){
		if(rowIndex<0||rowIndex>=Width||colIndex<0||colIndex>=Width)return false;
		if(matrix[rowIndex][colIndex]=='2')return false;
		for(int i=0;i<poslist.size();i++){
			int[] pos=poslist.get(i);
			
			if(pos[0]==rowIndex && pos[1]==colIndex)return false;
		}
		return true;
	}
	public int value(List<int[]> poslist){
		int conflict=0;
		for(int i=0;i<poslist.size()-1;i++){
			for(int j=i+1;j<poslist.size();j++){
				//appear in the same row
				int rowi=poslist.get(i)[0];
				int rowj=poslist.get(j)[0];
				int coli=poslist.get(i)[1];
				int colj=poslist.get(j)[1];
				if(rowi==rowj){
					int[] Tpos=new int[]{-1,-1};
					//if there is a tree which has same row as cur
					for(int index=0;index<TreePos.size();index++){
						if(TreePos.get(index)[0]==rowi){
							Tpos[0]=TreePos.get(index)[0];
							Tpos[1]=TreePos.get(index)[1];
							break;
						}
					}
					if(Tpos[0]==-1){
						conflict++;
					}
					else{
						if((Tpos[1]-coli)*(Tpos[1]-colj)>0)
							conflict++;
					}
				}
				//appear in the same col
				if(coli==colj){
					int[] Tpos=new int[]{-1,-1};
					//judge whether there is a tree which has the same col
					for(int index=0;index<TreePos.size();index++){
						if(TreePos.get(index)[1]==coli){
							Tpos[0]=TreePos.get(index)[0];
							Tpos[1]=TreePos.get(index)[1];
							break;
						}
					}
					if(Tpos[0]==-1)conflict++;
					else{
						if((Tpos[0]-rowi)*(Tpos[0]-rowj)>0)conflict++;
					}
				}
				if(rowi+coli==rowj+colj){
					int[] Tpos=new int[]{-1,-1};
					//judge whether there is a tree which has the same col
					for(int index=0;index<TreePos.size();index++){
						if(TreePos.get(index)[1]+TreePos.get(index)[0]==rowi+coli){
							Tpos[0]=TreePos.get(index)[0];
							Tpos[1]=TreePos.get(index)[1];
							break;
						}
					}
					if(Tpos[0]==-1)conflict++;
					else{
						if((Tpos[1]-coli)*(Tpos[1]-colj)>0)conflict++;
					}
				}
				if(rowj-colj==rowi-coli){
					int[] Tpos=new int[]{-1,-1};
					//judge whether there is a tree which has the same col
					for(int index=0;index<TreePos.size();index++){
						if(TreePos.get(index)[0]-TreePos.get(index)[1]==rowi-coli){
							Tpos[0]=TreePos.get(index)[0];
							Tpos[1]=TreePos.get(index)[1];
							break;
						}
					}
					if(Tpos[0]==-1)conflict++;
					else{
						if((Tpos[1]-coli)*(Tpos[1]-colj)>0)conflict++;
					}
				}
			}
		}
		return conflict;
	}
	
	public boolean judge(float p){
		float v=seed.nextInt(1);
		if(v<=p)return true;
		else return false;
	}
	//the way is you need to random select one node and place it randomly into the chess board into random valid position
	//because when you set the pattern and directions of lizard it may stuck into some pattern 
	public void runSA(){
		int num=0;
		while(num<1000000){
			List<int[]> lizardpos=new ArrayList<int[]>();
			//first initialize for the board
			for(int i=0;i<NumOfLizard;){
				int rowIndex=seed.nextInt(Width);
				int colIndex=seed.nextInt(Width);
				if(checkValidPlace(rowIndex,colIndex,lizardpos)){
					lizardpos.add(new int[]{rowIndex,colIndex});
					i++;
				}
				else continue;
				
			}
			//then we get the list of lizard positions 
			float t=T;
			int curValue=Integer.MAX_VALUE;
			while(t>eps){
				curValue=value(lizardpos);
				if(curValue<=eps){
					makeOutPutMatrix(lizardpos);
					print(outputMatrix);
					isSuccess=true;
					return;
				}
				//select random lizard
				int index=seed.nextInt(NumOfLizard);
				//and make random valid move.
				int prerow=lizardpos.get(index)[0];
				int precol=lizardpos.get(index)[1];
				
				int row=seed.nextInt(Width);
				int col=seed.nextInt(Width);
				while((row==prerow&&col==precol) ||checkValidPlace(row,col,lizardpos)==false){
					
					row=seed.nextInt(Width);
					col=seed.nextInt(Width);
				}
				
				lizardpos.get(index)[0]=row;
				lizardpos.get(index)[1]=col;
				int nextValue=value(lizardpos);
				int E=nextValue-curValue;
				if(E>0){
					float p=(float) Math.exp(-E/t);
					if(judge(p)==false){
						lizardpos.get(index)[0]=prerow;
						lizardpos.get(index)[1]=precol;
					}
				}
				t=t*delta;
			}
			num++;
		}
		
			
		
	}
	
	
}
