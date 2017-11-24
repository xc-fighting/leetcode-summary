package OnlineAssessment;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution2 {
	
	static int getSize(int[] students,int i,int j){
		int size=0;
		int root_i=FindRoot(students,i);
		int root_j=FindRoot(students,j);
		for(int index=0;index<students.length;index++){
			if(FindRoot(student,index)==root_i||FindRoot(student,index)==root_j){
				size++;
			}
		}

		return size;
		
	}
	
	public static int[] student=null;
	static void pgGuess3(int[] students, int i, int j, String operation) {
		if(operation.equals("friends")){
			makeFriend(students,i,j);
			for(int e:student){
				System.out.print(e+" ");
			}
			System.out.println();
			System.out.println("done");
		}
		else if(operation.equals("total")){
			System.out.println(getSize(students,i,j));
		}
	}
	
	static void makeFriend(int[] students,int i,int j){
		int root_i=FindRoot(students,i);
		int root_j=FindRoot(students,j);
		if(root_i==root_j){
			return; 
		}
		else{
			student[root_j]=root_i;
			return;
		}
	}
	
	static int FindRoot(int[] students,int i){
		if(students[i]==i){
			return i;
		}
		return FindRoot(students,students[i]);
	}

	public static void main(String[] args){
		student=new int[8];
		for(int i=0;i<8;i++){
			student[i]=i;
		}
		while(true){
			@SuppressWarnings("resource")
			String str=new Scanner(System.in).nextLine();
			@SuppressWarnings("resource")
			int op1=new Scanner(System.in).nextInt();
			@SuppressWarnings("resource")
			int op2=new Scanner(System.in).nextInt();
			pgGuess3(student,op1,op2,str);
		}
		
	}
}
