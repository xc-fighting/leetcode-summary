package algorithms;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphPath {
    Map<Integer,List<Integer>> graph=null;
    boolean[] visited=new boolean[4];
	List<List<Integer>> pathFind(int[][] edges,int source,int dst){
		 graph=new HashMap<Integer,List<Integer>>();
		 for(int i=0;i<edges.length;i++){
			 int node1=edges[i][0];
			 int node2=edges[i][1];
			 if(graph.containsKey(node1)==false){
				 graph.put(node1, new ArrayList<Integer>());
			 }
			 if(graph.containsKey(node2)==false){
				 graph.put(node2,new ArrayList<Integer>());
			 }
			 graph.get(node1).add(node2);
			 graph.get(node2).add(node1);
		 }
		 List<List<Integer>> paths=new ArrayList<List<Integer>>();
		/* List<Integer> curPath=new ArrayList<Integer>();
		 curPath.add(source);
		 visited[source-1]=true;
		 dfs(graph,curPath,paths,source,dst);*/
		 bfs(graph,paths,source,dst);
		 return paths;
	}
	void print(List<List<Integer>> paths){
		for(int i=0;i<paths.size();i++){
			System.out.println("the route:"+i);
			for(int j=0;j<paths.get(i).size();j++){
				System.out.print(paths.get(i).get(j)+" ");
			}
			System.out.println();
		}
		return;
	}
	void dfs(Map<Integer,List<Integer>> graph,List<Integer> curPath,List<List<Integer>> paths,int source,int dst){
		 if(source==dst){
			
			 paths.add(new ArrayList<Integer>(curPath));
			 return;
		 }
		 else{		 
			 for(int node:graph.get(source)){
				 if(visited[node-1])continue;
				 else{
					 visited[node-1]=true;
					 curPath.add(node);
					 dfs(graph,curPath,paths,node,dst);
					 curPath.remove(curPath.size()-1);
					 visited[node-1]=false;
				 }
				
			 }
		 }
		
	}
	
	void bfs(Map<Integer,List<Integer>> graph,List<List<Integer>> paths,int source,int dst){
		Queue<Integer> queue=new LinkedList<Integer>();
		List<Integer> cur=new ArrayList<Integer>();
		cur.add(source);
		queue.offer(source);
		visited[source-1]=true;

		while(queue.isEmpty()==false){
			 int size=queue.size();
			 for(int i=0;i<size;i++){
				 int node=queue.peek();
				 for(int next:graph.get(node)){
					 
				 }
				 queue.poll();
			 }
		}
	}
	
}
