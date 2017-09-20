package algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;


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

	Map<Integer,List<Integer>> Trow=null;
	Map<Integer,List<Integer>> Tcol=null;
	Map<Integer,List<Integer>> Tdiag1=null;
	Map<Integer,List<Integer>> Tdiag2=null;
	
	
	long checkTime=0;
	public homework(){
		
	}
	public static void main(String[] args){
		 homework hw=new homework();
			hw.parseInput();
			hw.selector();
		
		    hw.generateOutput();
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
	
			Trow=new HashMap<Integer,List<Integer>>();
			Tcol=new HashMap<Integer,List<Integer>>();
			Tdiag1=new HashMap<Integer,List<Integer>>();
			Tdiag2=new HashMap<Integer,List<Integer>>();
			
			for(int i=0;i<Width;i++){
				Trow.put(i,new ArrayList<Integer>());
			}
			for(int i=0;i<Width;i++){
				Tcol.put(i,new ArrayList<Integer>());
			}
			for(int i=0;i<2*Width-1;i++){
				Tdiag1.put(i,new ArrayList<Integer>());
			}
			for(int i=0;i<Width;i++){
				Tdiag2.put(-i,new ArrayList<Integer>());
				Tdiag2.put(i,new ArrayList<Integer>());
			}
			
			
			
			
			
			String line="";
			int index=0;
			while((line=input.readLine())!=null){
				for(int i=0;i<Width;i++){		
					matrix[index][i]=line.charAt(i);
					
					if(matrix[index][i]=='2'){
						//add four information of a tree
						hasTree=true;
						//TreePos.add(new int[]{index,i});
						
						Trow.get(index).add(i);
						Tcol.get(i).add(index);
						Tdiag1.get(index+i).add(i);
						Tdiag2.get(index-i).add(i);	
							
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
	//return false if the position not valid,true if it is valid
	public boolean check(int row,int col,List<int[]> positions){
		if(row<0||row>=Width||col<0||col>=Width)return false;
		if(matrix[row][col]=='2')return false;
		for(int[] pos:positions){
			
			//appear in the same row
			if(pos[0]==row){
				//which means no tree in this row
				if(Trow.get(row).size()==0)return false;
				else{
						//then there is a condition that there are trees,we compare the col
						List<Integer> colpos=Trow.get(row);
					
						boolean flag=false;
						for(int e:colpos){
								//if((col-e)*(pos[1]-e)<0){
							   if((col>e&&pos[1]<e)||(col<e&&pos[1]>e)){
									flag=true;
									break;
								}
							}
						if(flag==false)return false;
					}
					
			}
			
			
			//appear in the same col
			if(pos[1]==col){
				
				if(Tcol.get(col).size()==0)return false;
				else{
						
						List<Integer> rowpos=Tcol.get(col);
						
						boolean flag=false;
						for(int e:rowpos){
								//if((row-e)*(pos[0]-e)<0){
							  if((row>e&&pos[0]<e)||(row<e&&pos[0]>e)){
									flag=true;
									break;
								}
							}
						if(flag==false)return false;
					}
				
			}
			
			//appear in diag1
			if(pos[0]+pos[1]==row+col){
				
				if(Tdiag1.get(row+col).size()==0)return false;
				else{
						
						List<Integer> diagpos1=Tdiag1.get(row+col);
						
						boolean flag=false;
						for(int e:diagpos1){
								//if((col-e)*(pos[1]-e)<0){
							    if((col>e&&pos[1]<e)||(col<e&&pos[1]>e)){
									flag=true;
									break;
								}
							}
						if(flag==false)return false;
					}	
			}
			
			//appear in diag2
			if(pos[0]-pos[1]==row-col){
				if(Tdiag2.get(row-col).size()==0)return false;
				else{
						
						List<Integer> diagpos2=Tdiag2.get(row-col);
						
						boolean flag=false;
						for(int e:diagpos2){
								//if((col-e)*(pos[1]-e)<0){
							 if((col<e&&pos[1]>e)||(col>e&&pos[1]<e)){
									flag=true;
									break;
								}
							}
						if(flag==false)return false;
					}	
				
			}
		}
		return true;
	}
	//input is the 2d array and output is a 2d array too
	//first consider the condition of no trees
	//diagnal1 is from top right to bottom left

	
	/*
	 * below is dfs part.
	 * */
	
	public void runDFS(){
		//initialize the map for that
		long pre=System.currentTimeMillis();
		List<int[]> positions=new ArrayList<int[]>();
		for(int i=0;i<Width;i++)
			for(int j=0;j<Width;j++){
				if(matrix[i][j]=='2')continue;
				putLizard(i,j,positions);
			}
		
		long cur=System.currentTimeMillis();
		System.out.println("time:"+(cur-pre));
	}
	
	//try to put a lizard in position(row,col)
	void putLizard(int row,int col,List<int[]> positions){
		//set the sign of isSuccess in order to guarantee return and not search any more if you find it.
		  if(isSuccess==true)return;
		 // we can conclude that we find the solution if we got current number of lizards position equals num of lizard
		 
	//	  if(check(row,col,positions)==false)return;
		  positions.add(new int[]{row,col});
		  if(positions.size()==NumOfLizard){
			  makeOutPutMatrix(positions);
			  print(outputMatrix);
			  System.out.println("check time"+checkTime);
			  isSuccess=true;
			  return;
		  }
		  
		  
		    if(Trow.get(row).size()!=0){
		    	 for(int j=col+1;j<Width;j++){	
		    		  long time1=System.currentTimeMillis();
		    		  boolean flag=check(row,j,positions);
		    		  checkTime+=System.currentTimeMillis()-time1;
				      if(flag)
				      {
				    	  putLizard(row,j,positions);
				      }
					  
			    } 
		    }
			
			 
		       
		  for(int i=row+1;i<Width;i++){
			  for(int j=0;j<Width;j++){	
				  long time1=System.currentTimeMillis();
				  boolean flag=check(i,j,positions);
				  checkTime+=System.currentTimeMillis()-time1;
				  if(flag){
					  putLizard(i,j,positions);
				  }
				 
			  }
			 
		  }
		  positions.remove(positions.size()-1);
	}
	
	
	/*
	 * below is bfs part
	 * */
	Queue<List<int[]>> bfsQueue=new LinkedList<List<int[]>>();
	
	//consider the condition without tree first.
	public void runBFS(){
		//first initialize for the first level
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				if(matrix[i][j]=='2')continue;
				List<int[]> temp=new ArrayList<int[]>();
				int[] pos=new int[]{i,j};
				temp.add(pos);
				bfsQueue.add(temp);
			}	
		}
		System.out.println("the first level has"+bfsQueue.size());
		//then find the solution of the problem
		
		while(bfsQueue.isEmpty()==false){
			//we get the initial size of queue,we name it as size which is number of nodes in current level
			int size=bfsQueue.size();
			//then we pop it one by one from the queue
			for(int i=0;i<size;i++){
				//we get the head list name it poslist
				List<int[]> poslist=bfsQueue.poll();
				//then prejudge whether the size equals number of lizard if so,then we make out put
				if(poslist.size()==NumOfLizard){
					makeOutPutMatrix(poslist);
					print(outputMatrix);
					isSuccess=true;
					return;
				}
				
				int[] pos=poslist.get(poslist.size()-1);
				int row=pos[0];
				int col=pos[1];
			//	if(Width*Width-((row+1)*Width+col)<NumOfLizard-poslist.size())continue;
			
			   if(Trow.get(row).size()!=0){
				   for(int j=col+1;j<Width;j++){
						if(check(row,j,poslist)==true){
							if(poslist.size()+1==NumOfLizard){
								poslist.add(new int[]{row,j});
								makeOutPutMatrix(poslist);
								print(outputMatrix);
								isSuccess=true;
								return;
							}
							else{
								List<int[]> newlist=new ArrayList<int[]>(poslist);	
								newlist.add(new int[]{row,j});
								bfsQueue.offer(newlist);
							}
						}
				   } 
			   }
			  
			  for(int r=row+1;r<Width;r++){
				  for(int j=0;j<Width;j++){
					  if(check(r,j,poslist)==true){
							if(poslist.size()+1==NumOfLizard){
								poslist.add(new int[]{r,j});
								makeOutPutMatrix(poslist);
								print(outputMatrix);
								isSuccess=true;
								return;
							}
							else{
								List<int[]> newlist=new ArrayList<int[]>(poslist);	
								newlist.add(new int[]{r,j});
								bfsQueue.offer(newlist);
							}
						}
				  }
				 
			  }
					
				
			
			}
			
		}
	}
	
	
	
	/*
	 * 
	 * below is the sa part
	 * 
	 * */
	float eps=0.00001f;
	float delta=0.98f;
	float T=50f;
	Random seed=new Random();
	public boolean checkValidPlace(int rowIndex,int colIndex,List<int[]> poslist){

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
					if(Trow.get(rowi).size()==0){
						conflict++;
					}
					else{
						List<Integer> rpos=Trow.get(rowi);
						boolean TreeInside=false;
						for(int e:rpos){
							if((coli-e)*(colj-e)<0){
								TreeInside=true;
								break;
							}
						}
						if(TreeInside==false){
							conflict++;
						}
					}
				}
				//appear in the same col
				if(coli==colj){
					if(Tcol.get(coli).size()==0){
						conflict++;
					}
					else{
						List<Integer> cpos=Tcol.get(coli);
						boolean TreeInside=false;
						for(int e:cpos){
							if((rowi-e)*(rowj-e)<0){
								TreeInside=true;
								break;
							}
						}
						if(TreeInside==false){
							conflict++;
						}
					}
				}
				if(rowi+coli==rowj+colj){
					if(Tdiag1.get(rowi+coli).size()==0){
						conflict++;
					}
					else{
						List<Integer> dpos1=Tdiag1.get(rowi+coli);
						boolean TreeInside=false;
						for(int e:dpos1){
							if((coli-e)*(colj-e)<0){
								TreeInside=true;
								break;
							}
						}
						if(TreeInside==false){
							conflict++;
						}
					}
				}
				if(rowj-colj==rowi-coli){
					if(Tdiag2.get(rowi-coli).size()==0){
						conflict++;
					}
					else{
						List<Integer> dpos2=Tdiag2.get(rowi-coli);
						boolean TreeInside=false;
						for(int e:dpos2){
							if((coli-e)*(colj-e)<0){
								TreeInside=true;
								break;
							}
						}
						if(TreeInside==false){
							conflict++;
						}
					}
				}
			}
		}
		return conflict;
	}
	
	public boolean judge(float p){
		float v=seed.nextFloat();
		if(v<=p)return true;
		else return false;
	}
	//the way is you need to random select one node and place it randomly into the chess board into random valid position
	//because when you set the pattern and directions of lizard it may stuck into some pattern 
	public void runSA(){
		
           long pre=System.currentTimeMillis();
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
			for(int[] p:lizardpos){
				System.out.print("("+p[0]+","+p[1]+") ");
			}
			System.out.println();
			//then we get the list of lizard positions 
			
			int curValue=Integer.MAX_VALUE;
			
		   while(curValue!=0){
			   if(System.currentTimeMillis()-pre>270000)return;
			  float t=T;
			//   System.out.println(t);
			   while(t>eps){
				 
				    	  curValue=value(lizardpos);
							if(curValue==0){
								makeOutPutMatrix(lizardpos);
								System.out.println("OK");
								print(outputMatrix);
								isSuccess=true;
								long curTime=System.currentTimeMillis();
								System.out.println("running time:"+(curTime-pre)/1000);
								return;
							}
							//select random lizard
							int index=seed.nextInt(NumOfLizard);
							//and make random valid move.
							int prerow=lizardpos.get(index)[0];
							int precol=lizardpos.get(index)[1];
							
							int row=seed.nextInt(Width);
							int col=seed.nextInt(Width);
							while(checkValidPlace(row,col,lizardpos)==false){
								
								row=seed.nextInt(Width);
								col=seed.nextInt(Width);
						//		System.out.println("invalid place:"+row+" "+col);
							}
							
							lizardpos.get(index)[0]=row;
							lizardpos.get(index)[1]=col;
							int nextValue=value(lizardpos);
							if(nextValue==0){
								makeOutPutMatrix(lizardpos);
								System.out.println("OK");
								print(outputMatrix);
								isSuccess=true;
								long curTime=System.currentTimeMillis();
								System.out.println("running time:"+(curTime-pre)/1000);
								return;
							}
							int E=nextValue-curValue;
							if(E>0){
								float p=(float)(Math.exp(-E/t));
								if(judge(p)==false){
									lizardpos.get(index)[0]=prerow;
									lizardpos.get(index)[1]=precol;
								}
							}
				   
						
						t=t*delta;
						
					}
			 
		   }
			
		//	makeOutPutMatrix(lizardpos);
		//	print(outputMatrix);
         
		
	}
	
	
}
