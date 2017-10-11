import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;


public class homework2 {
	
	
	/*
	 * matrix means the input 
	 * valueMatrix store the value in each cell
	 * */
	char[][] matrix=null;
	char[][] outputMatrix=null;
	int[][] valueMatrix=null;
	String filename="input.txt";
	
	int Width;
	int NumberOfTypes;
	float times;
	
	int nextCol;
	int nextRow;

	public homework2(){
		
	}
	public static void main(String[] args){
		 homework2 hw=new homework2();
			hw.parseInput();
			hw.runAlgorithm();
		    hw.generateOutput();
	}
	
	//parse the input file
	public void parseInput(){
		try{
			BufferedReader input=new BufferedReader(new FileReader(filename));
			Width=Integer.parseInt(input.readLine());
			NumberOfTypes=Integer.parseInt(input.readLine());
			times=Float.parseFloat(input.readLine());
			matrix=new char[Width][Width];
			outputMatrix=new char[Width][Width];
		    valueMatrix=new int[Width][Width];
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
	
	//generate the output file
	public void generateOutput(){
		try{
			BufferedWriter output=new BufferedWriter(new FileWriter("output.txt"));
			    char col=(char)(nextCol+'A');
			    output.write(col+""+(nextRow+1)+"\n");
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
	
	public void runAlgorithm(){
	    info result=minimax(0,1,matrix,new info(0,0,0));
		nextCol=result.MoveCol;
		nextRow=result.MoveRow;
		changeState(nextRow,nextCol,outputMatrix);
		//int a=changeState(2,1,matrix);
	//	System.out.println(a);
	}
	
	/*
	 * role ==0 for other role ==1 for me
	 * depth <=3
	 * */
	public class info{
		public int MoveRow;
		public int MoveCol;
		public int Score;
		public info(int r,int c,int s){
			this.MoveRow=r;
			this.MoveCol=c;
			this.Score=s;
		}
	}
	public info minimax(int depth,int role,char[][] state,info cur){
		//当到达叶子结点深度为3时候不计算直接返回
		if(depth==3||isGameOver(state)==true){
			return cur;
		}
		//保存一开始的state
		char[][] rootState=new char[Width][Width];
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				rootState[i][j]=state[i][j];
			}
		}
		
		
		//当我的turn的时候，希望得到最大的那个结果.
		if(role==1){
			//记录最优info
			info best=new info(cur.MoveRow,cur.MoveCol,Integer.MIN_VALUE);
			//尝试每一个位置
			for(int i=0;i<Width;i++){
				for(int j=0;j<Width;j++){
					
					//如果碰到*的情况,直接跳过
					if(state[i][j]=='*'){
						continue;
					}
					else{//如果是fruit的时候
						//返回点击i,j以后增加的分数,并且改变state
						int score=changeState(i,j,state);
						info item=minimax(depth+1,0,state,new info(i,j,cur.Score+score));
						if(item.Score>best.Score){
							best.MoveRow=i;
							best.MoveCol=j;
							best.Score=item.Score;
						//	replace(outputMatrix,state);
						}
						replace(state,rootState);
					}
					
				}
			}
			return best;
		}
		//当对手的turn的时候，对方希望我的值最小.
		else{
			//记录最优info
			info best=new info(cur.MoveRow,cur.MoveCol,Integer.MAX_VALUE);
			for(int i=0;i<Width;i++){
				for(int j=0;j<Width;j++){
					if(state[i][j]=='*')continue;
					int score=changeState(i,j,state);
					info item=minimax(depth+1,0,state,new info(i,j,cur.Score-score));
					if(item.Score<best.Score){
						best.MoveRow=i;
						best.MoveCol=j;
						best.Score=item.Score;
					//	replace(outputMatrix,state);
					}
					replace(state,rootState);
				}
			}
			return best;
		}
		
	}
	
	private boolean isGameOver(char[][] state) {
		// TODO Auto-generated method stub
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				if(state[i][j]!='*')return false;
			}
		}
		return true;
	}
	private void replace(char[][] state, char[][] rootState) {
		// TODO Auto-generated method stub
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				state[i][j]=rootState[i][j];
			}
		}
		return;
	}
	int[][] dir={
			{0,-1},
			{0,1},
			{1,0},
			{-1,0}
	};
	//实现更改棋盘并且返回更改操作所产生的新得分
	public int changeState(int i, int j, char[][] state) {
		// TODO Auto-generated method stub
		int number=0;
		char type=state[i][j];
		Queue<Integer> queue=new LinkedList<Integer>();
		queue.offer(i*Width+j);
		while(queue.isEmpty()==false){
			int item=queue.poll();
			int rowId=item/Width;
			int colId=item%Width;
			state[rowId][colId]='*';
			for(int index=0;index<4;index++){
				int newrow=rowId+dir[index][0];
				int newcol=colId+dir[index][1];
				if(newrow<0||newrow>=Width
						||newcol<0||newcol>=Width
						||state[newrow][newcol]!=type){
					continue;
				}
				else{
					queue.offer(newrow*Width+newcol);
				}
				
			}
			number++;
		}
		
		/*
		 * just the code for print the matrix
		 * */
		System.out.println("before we use gravity");
		printState(state);
		
		/*
		 * we then apply the gravity to the state
		 * */
		//traverse each col
		for(int col=0;col<Width;col++){
			for(int row=Width-1;row>=0;row--){
				if(state[row][col]!='*'){
					continue;
				}
				else{
					int tempr=row-1;
					while(tempr>=0){
						if(state[tempr][col]!='*'){
							break;
						}
						tempr--;
					}
					if(tempr>=0){
						char temp=state[tempr][col];
						state[tempr][col]='*';
						state[row][col]=temp;
					}
				}
			}
		}
		
		System.out.println("after we apply gravity");
		printState(state);
				
		return number*number;
	}
	
	public void printState(char[][] state){
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				System.out.print(state[i][j]+" ");
			}
			System.out.println();
		}
	}

}
