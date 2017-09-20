package algorithms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;







public class sa {
	//----------------------------------
	//----------------------------------
	//----------------------------------
	//adddd
	static Map<Integer, List<Integer>> treeRow = new HashMap<Integer, List<Integer>>();//row,col
	static Map<Integer, List<Integer>> treeCol = new HashMap<Integer, List<Integer>>();//col,row
	static Map<Integer, List<Integer>> treeDia1 = new HashMap<Integer, List<Integer>>();//x+y,col
	static Map<Integer, List<Integer>> treeDia2 = new HashMap<Integer, List<Integer>>();//x-y,col
	
	static Map<Integer, List<Integer>> lizardRow = new HashMap<Integer, List<Integer>>();//row,col
	static Map<Integer, List<Integer>> lizardCol = new HashMap<Integer, List<Integer>>();//col,row
	static Map<Integer, List<Integer>> lizardDia1 = new HashMap<Integer, List<Integer>>();//x+y,col
	static Map<Integer, List<Integer>> lizardDia2 = new HashMap<Integer, List<Integer>>();//x-y,col
	
	//adddd
	
	//----------------------------------
	//----------------------------------
	//----------------------------------
	
	/**
	 * @author ji
	 * the definition of global variables
	 */
	static String methodType = null;//BFS,DFS,SA
	static int squareWidth = -1;
	static int lizardNumber = -1;
	static Set<Point> treeNode = new HashSet<Point>();//store the tree nodes
	
	
	
	
	static double temperature = 50;
	static double minTemperature = 0;
	static double coolingRate = 0.98;  
	static double delta = Double.MAX_VALUE;
	static List<Point> saCurSquare =  new ArrayList<Point>();
	static List<Point> saNxtSquare =  new ArrayList<Point>();
	static double curCost = Double.MAX_VALUE;
	static double nxtCost = Double.MAX_VALUE;
	//test
	static float test_sacal_time = 0;
	//
	/**
	 * @author ji
	 * SA method
	 * @throws IOException
	 */
	private static void sa_putlizard() throws IOException{
		
		//test
		
		int loopcount=0;
		int dcount=0;
		int pcount=0;
		//
	
		int removeLizardX = -1, removeLizardY = -1, replaceLizardPos = -1;
		int newAddLizardX = -1, newAddLizardY = -1;
		
		//≥ı ºªØsaCurSquare
		Random r = new Random();
		for(int i=0; i<lizardNumber; i++){//generate the correct number of lizards
			
			if(saCurSquare.isEmpty()){
				int ranX = r.nextInt(squareWidth);
				int ranY = r.nextInt(squareWidth);
				Point p = new Point(ranX,ranY);//randomly first-picked node
				while(treeNode.contains(p)){
					ranX = r.nextInt(squareWidth);
					ranY = r.nextInt(squareWidth);
					p = new Point(ranX,ranY);//randomly picked node
				}
				saCurSquare.add(new Point(ranX,ranY));
				updateAddLizardHashmap(ranX,ranY);
				
			}else{
				int ranX = r.nextInt(squareWidth);
				int ranY = r.nextInt(squareWidth);
				Point p = new Point(ranX,ranY);//randomly picked node
				while(treeNode.contains(p) || saCurSquare.contains(p)){//guarantee it is a new lizard position
					ranX = r.nextInt(squareWidth);
					ranY = r.nextInt(squareWidth);
					p = new Point(ranX,ranY);//randomly picked node
				}
				saCurSquare.add(new Point(ranX,ranY));		
				updateAddLizardHashmap(ranX,ranY);
			}												
		}
		
		
		
		
		while(temperature >= minTemperature){
			
		
			curCost = calculateSACostHash(saCurSquare);
			if(curCost==0){					
				List<Point> saOutSquare = new ArrayList<Point>(saCurSquare);
				outputSquare(saOutSquare);
				
				//test output hashmap lizard
				for (Entry<Integer, List<Integer>> ee : lizardRow.entrySet()) {
					Integer key = ee.getKey();
				    List<Integer> values = ee.getValue();
				    
				    System.out.print("row key: " + key +"  ; ");
				    for(Integer i : values){
				    	System.out.print("values: " + i);
				    }
				    System.out.println();
				}
				for (Entry<Integer, List<Integer>> ee : lizardCol.entrySet()) {
					Integer key = ee.getKey();
				    List<Integer> values = ee.getValue();
				    
				    System.out.print("col key: " + key +"  ; ");
				    for(Integer i : values){
				    	System.out.print("values: " + i);
				    }
				    System.out.println();
				}
				break;
			}
			
			
			
			
			//update saNxtSquare status
			saNxtSquare =  new ArrayList<Point>(saCurSquare);
			//random way
		
			int ranX = r.nextInt(squareWidth);
			int ranY = r.nextInt(squareWidth);
			Point p = new Point(ranX,ranY);//randomly picked node
			
			while(treeNode.contains(p) || saCurSquare.contains(p)){
				ranX = r.nextInt(squareWidth);
				ranY = r.nextInt(squareWidth);
				p = new Point(ranX,ranY);//randomly picked node				
			}
			
			int replaceSetIndex = r.nextInt(lizardNumber);
			//adddd
			removeLizardX = saCurSquare.get(replaceSetIndex).getX();
			removeLizardY = saCurSquare.get(replaceSetIndex).getY();
			updateDeleteLizardHashmap(removeLizardX,removeLizardY);
		
			replaceLizardPos = replaceSetIndex;
			saNxtSquare.set(replaceSetIndex, p);//use list can directly replace
			
			updateAddLizardHashmap(ranX,ranY);
			newAddLizardX = ranX;
			newAddLizardY = ranY;
			//adddd	

			
			//calculate the cost of the states
			nxtCost = calculateSACostHash(saNxtSquare);
			delta = curCost - nxtCost;
						
			//test
			System.out.println("curCost: " + curCost+ "; nxtCost: " + nxtCost+ "; delta: " + delta);		
			//
			
			
			double ranP = Math.random(); 
			double probability = Math.exp(delta / temperature);		
			if(delta > 0){//update current square			
				List<Point> tmpSquare =  new ArrayList<Point>(saNxtSquare);
				saCurSquare = tmpSquare;			
				//test
				
				
				dcount++;
				
				//
				
			}else if(ranP <= probability){//update current square		
				List<Point> tmpSquare =  new ArrayList<Point>(saNxtSquare);
				saCurSquare = tmpSquare;					
				//test
				
				
				pcount++;
				
				//
			}else{//refuse to update current square, still trying to find next from current
				
				//List<Point> tmpSquare =  new ArrayList<Point>(saCurSquare);
				//saNxtSquare = tmpSquare;
				updateDeleteLizardHashmap(newAddLizardX,newAddLizardY);
				updateAddLizardHashmap(removeLizardX,removeLizardY);
			}
		
			temperature = temperature * coolingRate;
			
			
			//test
			
			loopcount ++;
			
			
			//
			
			//may be never find an answer
			long endTime_sa=System.currentTimeMillis();
	        float excTime_sa=(float)(endTime_sa-startTime_sa)/1000;
	        if(excTime_sa > 290){//4min50seconds
	        	//test
	        	System.out.println("sa out of time--------------------");
	        	System.out.println("calculate time: "+test_sacal_time + " s");
	        	//
	        	outputFail();
	        	return;
	        }
		
		
		}//end while
		
		
		//test
		System.out.println("calculate time: "+test_sacal_time + " s");
		System.out.println("loop count: "+loopcount);
		System.out.println("delta count: "+dcount);
		System.out.println("pro count: "+pcount);
		System.out.println("run out of times");
		//
		return;
	}
		
	   
		
		//------------------------------------------------------------methods about input & output------------------------------------------------------------
		
		/**
		 * @author ji
		 * the method is used for read files and initialize then global variables
		 * @throws IOException
		 */
		private static void readFile() throws IOException{
			String line = null;
			FileReader fr = new FileReader("input.txt");
			BufferedReader br = new BufferedReader(fr);
			int readLine = 1;
			
			boolean isSquareLengthSame = false;
			
			int lineNumber = 0;//the count for input matrix
			
			while( ((line = br.readLine())!=null) && line.length() > 0 ){
				
				if(1 == readLine){
					methodType = line;
					readLine = readLine + 1;
					
				}else if(2 == readLine){
					squareWidth = Integer.parseInt(line);
					readLine = readLine + 1;
					
				}else if(3 == readLine){
					lizardNumber = Integer.parseInt(line);
					readLine = -1;
					
				}else{
					
					//test if the square width and the length of one line is equal		
					if(!isSquareLengthSame){
						if(squareWidth == line.length()){
							isSquareLengthSame = true;
						}else{
							outputFail();
							return;
						}		
					}
					
					//set treeNode Set
					for(int i=0; i<squareWidth ; i++){
						if('2'==(line.charAt(i))){
							Point p = new Point(lineNumber, i);
							treeNode.add(p);
						}
					}
					
					lineNumber  = lineNumber+1;
					
				}//end else
			}//end while
			
			br.close();
			fr.close();
		}
		
		/**
		 * @author ji
		 * method of outputting FAIL information
		 * @throws IOException
		 */
		private static void outputFail() throws IOException{
			FileWriter fw = null;
			fw = new FileWriter("output.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("FAIL");
			bw.flush();
			bw.close();
			fw.close();	
		}
		
		/**
		 * @author ji
		 * the method is used when there is a solution and output the solution
		 * @param finalSquare
		 * @throws IOException
		 */
		private static void outputSquare(List<Point> square) throws IOException{
			FileWriter fw = null;
			fw = new FileWriter("output.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("OK");
			bw.write("\r\n");
			
			int[][] outputArray = new int[squareWidth][squareWidth];
			for(int i=0;i<squareWidth;i++){
				for(int j=0;j<squareWidth;j++){
					outputArray[i][j] = 0;
				}
			}
			
			Iterator<Point> treeIter = treeNode.iterator();
			while(treeIter.hasNext()){
				Point treep = treeIter.next();
				
				outputArray[treep.x][treep.y] = 2;
			}
			
			Iterator<Point> liter = square.iterator();
			while(liter.hasNext()){
				Point lp = liter.next();
				outputArray[lp.x][lp.y] = 1;
			}
			
			for(int i=0;i<squareWidth;i++){
				for(int j=0;j<squareWidth;j++){
					//write data into the file
					
					bw.write(outputArray[i][j]+"");
				}
				//change a line
				bw.write("\r\n");
			}
			
			bw.flush();
			bw.close();
			fw.close();
		}
		
		//------------------------------------------------------------main method------------------------------------------------------------
		
		static long startTime_sa = 0;//for controling sa time
		
		public static void main(String[] args) throws IOException{
			//1.read file
			try {
				readFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//2.clearly no results conditions
			if(lizardNumber > (squareWidth * squareWidth-treeNode.size() ) || lizardNumber <=0 || squareWidth <=0 ){				
				outputFail();	
				return;
			}
			
			//3.decide use which method to put lizards
			if("BFS".equals(methodType)){
				
			}else if("DFS".equals(methodType)){
				
				
			}else if("SA".equals(methodType)){
				
				//test
				startTime_sa=System.currentTimeMillis();
				//
				//adddd
				putTreeInHash();
				//adddd
				sa_putlizard();
				
				//test
				long endTime_sa=System.currentTimeMillis();
		        float excTime_sa=(float)(endTime_sa-startTime_sa)/1000;
		        System.out.println("sa total timeÔº?"+excTime_sa+"s");
		        //
				
			}
			
		}
		//------------------------------------------------------------class for contain tree nodes------------------------------------------------------------
		private static class Point{
			int x;
			int y;
			
			public int getX() {
				return x;
			}

			public void setX(int x) {
				this.x = x;
			}

			public int getY() {
				return y;
			}

			public void setY(int y) {
				this.y = y;
			}

			public Point(int x, int y){
				this.x = x;
				this.y = y;
			}
			
			public boolean equals(Object obj) {  
		        if (!(obj instanceof Point))  
		            return false;     
		        if (obj == this)  
		            return true;  
		        boolean ret = ((Point)obj).x == this.x && ((Point)obj).y == this.y;
		        return ret;  
		    }  
			
			 public int hashCode() {
			        long bits = java.lang.Double.doubleToLongBits((double)this.x);
			        bits ^= java.lang.Double.doubleToLongBits((double)this.y) * 31;
			        return (((int) bits) ^ ((int) (bits >> 32)));
			    }
			
		}
		
		//------------------------------------------------------------add method for hash map------------------------------------------------------------
		//addddd
		/**
		 * use in the main method, to put tree list into hashmap
		 * @author ji
		 */
		private static void putTreeInHash(){
			
			for(Point tp :  treeNode){
				
				int tpx = tp.getX();
				int tpy = tp.getY();
				
				if(treeRow.get(tpx) == null){
					treeRow.put(tpx, new ArrayList<Integer>());
					treeRow.get(tpx).add(tpy);
				}else{
					treeRow.get(tpx).add(tpy);
				}
				
				if(treeCol.get(tpy) == null){
					treeCol.put(tpy, new ArrayList<Integer>());
					treeCol.get(tpy).add(tpx);
				}else{
					treeCol.get(tpy).add(tpx);
				}
				
				if(treeDia1.get(tpx + tpy) == null){
					treeDia1.put(tpx + tpy, new ArrayList<Integer>());
					treeDia1.get(tpx + tpy).add(tpy);
				}else{
					treeDia1.get(tpx + tpy).add(tpy);
				}
				
				if(treeDia2.get(tpx - tpy) == null){
					treeDia2.put(tpx - tpy, new ArrayList<Integer>());
					treeDia2.get(tpx - tpy).add(tpy);
				}else{
					treeDia2.get(tpx - tpy).add(tpy);
				}
			}
			
		}
		//addddd
		
		//addddd
		private static void updateAddLizardHashmap(int tpx, int tpy){
			if(lizardRow.get(tpx) == null){
				lizardRow.put(tpx, new ArrayList<Integer>());
				lizardRow.get(tpx).add(tpy);
			}else{
				lizardRow.get(tpx).add(tpy);
			}
			
			if(lizardCol.get(tpy) == null){
				lizardCol.put(tpy, new ArrayList<Integer>());
				lizardCol.get(tpy).add(tpx);
			}else{
				lizardCol.get(tpy).add(tpx);
			}
			
			if(lizardDia1.get(tpx + tpy) == null){
				lizardDia1.put(tpx + tpy, new ArrayList<Integer>());
				lizardDia1.get(tpx + tpy).add(tpy);
			}else{
				lizardDia1.get(tpx + tpy).add(tpy);
			}
			
			if(lizardDia2.get(tpx - tpy) == null){
				lizardDia2.put(tpx - tpy, new ArrayList<Integer>());
				lizardDia2.get(tpx - tpy).add(tpy);
			}else{
				lizardDia2.get(tpx - tpy).add(tpy);
			}
		}
		
		
		private static void updateDeleteLizardHashmap(int tpx, int tpy){
			lizardRow.get(tpx).remove((Integer)tpy);
			lizardCol.get(tpy).remove((Integer)tpx);
			lizardDia1.get(tpx + tpy).remove((Integer)tpy);
			lizardDia2.get(tpx - tpy).remove((Integer)tpy);
			
		}
		
		/**
		 * to tell whether a position(x,y) can be put a new lizard based on the current situation
		 * @param x
		 * @param y
		 * @return
		 */
		private static boolean isPutableHashmap(int x, int y){
			
			//(x,y) is not in a tree position
			if(treeNode.contains(new Point(x,y))){
				return false;
			}
			
			//same row
			if(lizardRow.get(x) != null){//if there is a lizard in the same row already
				if(treeNode.isEmpty()){//if there is no tree node at all,so there must be a collision
					return false;
				}
				List<Integer> sameRowLizard = lizardRow.get(x);//lizards in the same row
				if(treeRow.get(x) != null){//if there is tree in the same row
					List<Integer> sameRowTree= treeRow.get(x);				
					int totalXTree = sameRowTree.size();//the total number of lizards in the same row
					int xTree = 0;
					for(Integer lCol : sameRowLizard){//for every lizard in the same row
						int maxY, minY;//
						if(lCol > y){
							maxY = lCol;
							minY = y;
						}else{
							maxY = y;
							minY = lCol;
						}
						for(Integer tCol : sameRowTree){
							if(!(minY < tCol && maxY > tCol)){//if the tree is not in between. Can't return true here, because there may be other collisions in other directions
								xTree = xTree + 1;
							}
						}
					}
					if(totalXTree == xTree){//if the lizards in the same row are all not between the lizards
						return false;
					}
				}else{//no tree in the same row, must return false
					return false;
				}
								
			}
			//same col
			if(lizardCol.get(y) != null){
				if(treeNode.isEmpty()){//if there is no tree node at all,so there must be a collision
					return false;
				}
				List<Integer> sameColLizard = lizardCol.get(y);
				if(treeCol.get(y) != null){
					List<Integer> sameColTree= treeCol.get(y);
					int totalYTree = sameColTree.size();
					int yTree = 0;
					for(Integer lRow : sameColLizard){
						int maxX, minX;
						if(lRow > x){
							maxX = lRow;
							minX = x;
						}else{
							maxX = x;
							minX = lRow;
						}
						for(Integer tRow : sameColTree){
							if(!(minX < tRow && maxX > tRow)){
								yTree = yTree + 1;
							}
						}
					}
					if(totalYTree == yTree){
						return false;
					}
				}else{//no tree in the same col
					return false;
				}
								
			}
			//same dia1
			int dia1 = x+y;
			if(lizardDia1.get(dia1) != null){
				if(treeNode.isEmpty()){//if there is no tree node at all,so there must be a collision
					return false;
				}
				List<Integer> sameDia1Lizard = lizardDia1.get(dia1);
				if(treeDia1.get(dia1) != null){
					List<Integer> sameDia1Tree = treeDia1.get(dia1);
					int totalDia1Tree = sameDia1Tree.size();
					int dia1Tree = 0;
					for(Integer dia1Col : sameDia1Lizard){
						int maxY, minY;
						if(dia1Col > y){
							maxY = dia1Col;
							minY = y;
						}else{
							maxY = y;
							minY = dia1Col;
						}
						for(Integer tCol : sameDia1Tree){
							if(!(minY < tCol && maxY > tCol)){
								dia1Tree = dia1Tree + 1;
							}
						}
					}
					if(totalDia1Tree == dia1Tree){
						return false;
					}
				}else{// no tree in the same dia1
					return false;
				}
				
			}
			//same dia2
			int dia2 = x-y;
			if(lizardDia2.get(dia2) != null){
				if(treeNode.isEmpty()){//if there is no tree node at all,so there must be a collision
					return false;
				}
				List<Integer> sameDia2Lizard = lizardDia2.get(dia2);
				if(treeDia2.get(dia2) != null){
					List<Integer> sameDia2Tree = treeDia2.get(dia2);
					int totalDia2Tree = sameDia2Tree.size();
					int dia2Tree = 0;
					for(Integer dia2Col : sameDia2Lizard){
						int maxY, minY;
						if(dia2Col > y){
							maxY = dia2Col;
							minY = y;
						}else{
							maxY = y;
							minY = dia2Col;
						}
						for(Integer tCol : sameDia2Tree){
							if(!(minY < tCol && maxY > tCol)){
								dia2Tree = dia2Tree + 1;
							}
						}
					}
					if(totalDia2Tree == dia2Tree){
						return false;
					}
				}else{// no tree in the same dia2
					return false;
				}			
			}
			
			return true;
		}
		
		//addddd
		
		 //adddd
		private static double calculateSACostHash(List<Point> l){
			
	        double cost = 0.0;

	        if(treeNode.isEmpty()){
	        	for(Point p : l){
	        		int px = p.getX();
	        		int py = p.getY();
	        		if(lizardRow.get(px).size() >1 
	        				||lizardCol.get(py).size() > 1
	        				||lizardDia1.get(px+py).size() > 1
	        				||lizardDia2.get(px-py).size() > 1){
	        			cost = cost + 1;
	        		}	        		
	        	}
	        	
	        	/*int rowCost = Integer.MAX_VALUE, colCost=Integer.MAX_VALUE, dia1Cost=Integer.MAX_VALUE, dia2Cost=Integer.MAX_VALUE;
	        	for (Entry<Integer, List<Integer>> ee : lizardRow.entrySet()) {
	    		    Integer key = ee.getKey();
	    		    rowCost = ee.getValue().size()*(ee.getValue().size()-1);	    		    
	    		}
	        	for (Entry<Integer, List<Integer>> ee : lizardCol.entrySet()) {
	    		    Integer key = ee.getKey();
	    		    colCost = ee.getValue().size()*(ee.getValue().size()-1);	    		    
	    		}
	        	for (Entry<Integer, List<Integer>> ee : lizardDia1.entrySet()) {
	    		    Integer key = ee.getKey();
	    		    dia1Cost = ee.getValue().size()*(ee.getValue().size()-1);	    		    
	    		}
	        	for (Entry<Integer, List<Integer>> ee : lizardDia2.entrySet()) {
	    		    Integer key = ee.getKey();
	    		    dia2Cost = ee.getValue().size()*(ee.getValue().size()-1);	    		    
	    		}
	        	cost = 0.25*(rowCost + colCost + dia1Cost + dia2Cost);*/
	        	           
	        }else{	        
	        	for(Point p : l){
	        		int px = p.getX();
	        		int py = p.getY();
	        		updateDeleteLizardHashmap(px,py);
	        		if(isPutableHashmap(px,py)){	        			
	        		}else{
	        			cost = cost +1;
	        		}
	        		updateAddLizardHashmap(px,py);
	        	}
	        	
	        }	        
	        //this is due to double counting
	        cost = cost / 2;
	        return cost;
		}
		
		//adddd
	
}
