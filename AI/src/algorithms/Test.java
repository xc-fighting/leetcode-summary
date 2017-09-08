package algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args){
	/*	int[][] map={
				{0,0,0,0},
				{0,1,0,0},
				{0,1,0,0},
				{0,0,0,0}
		};
		new bruteForcePath().getPath(map, 0, 0, 2,2);*/
	/*	int[][] edges={
				{1,2},
				{1,3},
				{2,3},
				{2,4}
		};
		GraphPath gp=new GraphPath();
		List<List<Integer>> paths=gp.pathFind(edges, 1, 4);
		gp.print(paths);*/
		try {
			new homework().parseInput();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
