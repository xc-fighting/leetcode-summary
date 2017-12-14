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
	
	/*
	 * �����������ͨ���������еĲ���
	 * */

	
	
	//ÿ�ζ���״̬��ʼ��������
	public Map<Integer,List<int[]>> initConnectList(char[][] state){
		//������ͨ���������з�֧����
		Map<Integer,List<int[]>> ConnectedList=new HashMap<Integer,List<int[]>>();
		char[][] rootState=new char[Width][Width];
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				rootState[i][j]=state[i][j];
			}
		}
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				if(state[i][j]=='*')continue;
				List<int[]> item=getConnectComponent(state,i,j);
				ConnectedList.put(i*Width+j,item);
				
			}
		}
		//��ɲ����󣬻�ԭԭ����state
		replace(state,rootState);
		
		
		return ConnectedList;
		
	}
	
	
	
	//��ȡ��ǰ���������ͨ��������������bfs�ķ�ʽ����
	public List<int[]> getConnectComponent(char[][] state,int i,int j){
		List<int[]> result=new ArrayList<int[]>();
		char type=state[i][j];
		Queue<Integer> queue=new LinkedList<Integer>();
		queue.offer(i*Width+j);
		state[i][j]='*';
		while(queue.isEmpty()==false){
			int item=queue.poll();
			int rowId=item/Width;
			int colId=item%Width;
			result.add(new int[]{rowId,colId});
		//	state[rowId][colId]='*';
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
					state[newrow][newcol]='*';
				}
				
			}
		
		}
		return result;
	}
	
	/*-----------------------------------------------------------------------------------------------------------*/
	/*
	 * �������ⲿ�֣������Ϸ���̲�������Ҫ������ģ�����Ժ�״̬���Լ�Ӧ��gravity������״̬
	 * */
	//�ú�����������ģ����һ����ͨ�����Ժ��Ч��,ͬʱ���ص�ǰ��õķ���
	public int AfterClick(int key,char[][] state,Map<Integer,List<int[]>> ConnectedList){
		List<int[]> temp=ConnectedList.get(key);
		for(int[] pos:temp){
			state[pos[0]][pos[1]]='*';
		}
		return temp.size()*temp.size();
	}
	
	//�����������������stateӦ������Ч��
	public void ApplyGravity(char[][] state){
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
	}
	
	
	
	/*-----------------------------------------------------------------------------------------------------------*/
	
	/*
	 * ����Ϊ��Ϸ���п��
	 * */
	
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
	    info result=minimax(0,1,matrix,new info(-1,-1,0),new info(-1,-1,Integer.MIN_VALUE),new info(-1,-1,Integer.MAX_VALUE));
	    nextCol=result.MoveCol;
		nextRow=result.MoveRow;
	    Map<Integer,List<int[]>>temp=initConnectList(outputMatrix);
	    int score=AfterClick(nextRow*Width+nextCol,outputMatrix,temp);
	    System.out.println(score);
	    System.out.println("ѡ���к�"+nextCol);
	    System.out.println("ѡ���к�"+nextRow);
	    ApplyGravity(outputMatrix);
	
	}
	
	
	
	/*---------------------------------------------------------------------------------------------------------*/
	
	
	/*
	 * �����㷨��������
	 * */
	
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
		public info(info other){
			this.MoveRow=other.MoveRow;
			this.MoveCol=other.MoveCol;
			this.Score=other.Score;
		}
	}
	public info minimax(int depth,int role,char[][] state,info cur,info alpha,info beta){
		//������Ҷ�ӽ�����Ϊ3ʱ�򲻼���ֱ�ӷ���
		if(depth==3||isGameOver(state)==true){
			return cur;
		}
		//����һ��ʼ��state
		char[][] rootState=new char[Width][Width];
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				rootState[i][j]=state[i][j];
			}
		}
		
		Map<Integer,List<int[]>> ConnectedList=initConnectList(state);
		
		//���ҵ�turn��ʱ��ϣ���õ������Ǹ����.
		if(role==1){
			//��¼����info
		//	info best=new info(cur.MoveRow,cur.MoveCol,Integer.MIN_VALUE);
			//����ÿһ����������
			for(int key:ConnectedList.keySet()){
						//���ص��i,j�Ժ����ӵķ���,���Ҹı�state
						int score=AfterClick(key,state,ConnectedList);
						ApplyGravity(state);
					
						info item=minimax(depth+1,0,state,new info(key/Width,key%Width,cur.Score+score),new info(alpha),new info(beta));
						if(item.Score>alpha.Score){
							alpha.MoveRow=key/Width;
							alpha.MoveCol=key%Width;
							alpha.Score=item.Score;
						
						}
						replace(state,rootState);
						if(alpha.Score>=beta.Score){
							break;
						}
			}
			
			return alpha;
		}
		//�����ֵ�turn��ʱ�򣬶Է�ϣ���ҵ�ֵ��С.
		else{
			//��¼����info
		//	info best=new info(cur.MoveRow,cur.MoveCol,Integer.MAX_VALUE);
			for(int key:ConnectedList.keySet()){
					
					int score=AfterClick(key,state,ConnectedList);
					ApplyGravity(state);
					
					info item=minimax(depth+1,1,state,new info(key/Width,key%Width,cur.Score-score),new info(alpha),new info(beta));
					if(item.Score<beta.Score){
						beta.MoveRow=key/Width;
						beta.MoveCol=key%Width;
						beta.Score=item.Score;
					
					}
					replace(state,rootState);
					if(alpha.Score>=beta.Score)break;
				
		    }
			return beta;
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
	
	public void printState(char[][] state){
		for(int i=0;i<Width;i++){
			for(int j=0;j<Width;j++){
				System.out.print(state[i][j]+" ");
			}
			System.out.println();
		}
	}

}