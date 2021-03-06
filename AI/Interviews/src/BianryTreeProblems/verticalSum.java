package BianryTreeProblems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class verticalSum {
	

	public class TreeNode{
		public TreeNode left;
		public TreeNode right;
		public int val;
		public TreeNode(int v){
			this.val=v;
			left=null;
			right=null;
		}
	}
	
	//define a class which have the information of Node and width
	public class Info{
		public TreeNode node;
		public int width;
		public Info(TreeNode n,int w){
			this.width=w;
			this.node=n;
		}
	}
	//define a queue to store Info node
	Queue<Info> InfoQueue=new LinkedList<Info>();
	//define a map to store the information of each line and its nodes
	Map<Integer,List<TreeNode>> infoMap=new HashMap<Integer,List<TreeNode>>();
	//the variable to store the min line number
	int min=Integer.MAX_VALUE;
	public int getVerticalSum(int line,TreeNode root){
		if(root==null||line<0)return 0;
		Info item=new Info(root,0);
		InfoQueue.offer(item);
		while(InfoQueue.isEmpty()==false){
			int size=InfoQueue.size();
			for(int i=0;i<size;i++){
				Info temp=InfoQueue.poll();
				min=Math.min(min,temp.width);
				if(infoMap.containsKey(temp.width)==false){
					infoMap.put(temp.width,new ArrayList<TreeNode>());
				}
				infoMap.get(temp.width).add(temp.node);
				if(temp.node.left!=null){
					InfoQueue.offer(new Info(temp.node.left,temp.width-1));
				}
				if(temp.node.right!=null){
					InfoQueue.offer(new Info(temp.node.right,temp.width+1));
				}
			}
		}
		int sum=0;
		for(TreeNode e:infoMap.get(min+line-1)){
			sum+=e.val;
		}
		return sum;
	}
	/*
	 * another version of this problem above is get the max width of the binary tree,which means this time you only need
	 * to record the number of nodes in each level.
	 * */
	
	public static void main(String[] args){
		verticalSum vs=new verticalSum();
		verticalSum.TreeNode root=vs.new TreeNode(1);
		root.left=vs.new TreeNode(2);
		root.right=vs.new TreeNode(3);
		root.left.left=vs.new TreeNode(4);
		root.left.right=vs.new TreeNode(5);
		root.right.left=vs.new TreeNode(7);
		root.right.right=vs.new TreeNode(6);
		System.out.println(vs.getVerticalSum(5, root));
		
		
	}

}
