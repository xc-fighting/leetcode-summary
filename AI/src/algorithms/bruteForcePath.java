package algorithms;

import java.util.ArrayList;
import java.util.List;

public class bruteForcePath {

	boolean[][] visited=null;
	
	List<int[]> finalPath=null;
	int[][] direction={
			{-1,0},
			{1,0},
			{0,-1},
			{0,1}
	};
	public List<int[]> getPath(int[][] map,int starti,int startj,int endi,int endj){
		//first we judge the valid of start and end
		if(map==null||map.length==0||map[0].length==0||map[starti][startj]==1||map[endi][endj]==1)return null;
		int m=map.length;
		int n=map[0].length;
		visited=new boolean[m][n];
		List<int[]> path=new ArrayList<>();
		dfsHelper(map,path,starti,startj,endi,endj);
		for(int[] pos:finalPath)
		System.out.println(pos[0]+","+pos[1]);
		return finalPath;
	}
	private void dfsHelper(int[][] map,List<int[]> path,int curi,int curj,int endi,int endj){
		//if invalid position of seaching point
		if(curi<0||curi>=map.length||curj<0||curj>=map[0].length)return;
		//if cur point is within the map but it is visited previously and it is a wall return
		if(visited[curi][curj]||map[curi][curj]==1)return;
		//if path find then
		if(curi==endi&&curj==endj){
			//judge if it is the first time find the path,if yes then add it 
			int[] temp=new int[2];
			temp[0]=endi;
			temp[1]=endj;
			path.add(temp);
			System.out.print("path found:");
			for(int[] pos:path){
				System.out.print("("+pos[0]+","+pos[1]+") ");
			}
			System.out.println();
			if(finalPath==null){
				finalPath=new ArrayList<int[]>(path);
			}
			//if no,then judege the new length with it
			else{
				if(path.size()<finalPath.size()){
					finalPath.clear();
					finalPath=new ArrayList<int[]>(path);
				}
			}
			path.remove(path.size()-1);
			return;
		}
		//if it is still on the way
		int[] coor=new int[2];
		coor[0]=curi;
		coor[1]=curj;
		path.add(coor);
		visited[curi][curj]=true;
		for(int i=0;i<4;i++){
			dfsHelper(map,path,curi+direction[i][0],curj+direction[i][1],endi,endj);
		}
		visited[curi][curj]=false;
		path.remove(path.size()-1);
		return;
	}
}
