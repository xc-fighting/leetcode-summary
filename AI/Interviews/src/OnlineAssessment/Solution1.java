package OnlineAssessment;

public class Solution1 {
	public boolean canReach(int x1,int y1,int x2,int y2){
		if(x1>x2||y1>y2){
			return false;
		}
		if(x1==x2&&y1==y2){
			return true;
		}
		return canReach(x1,x1+y1,x2,y2)|canReach(x1+y1,y1,x2,y2);
	}
	
	public static void main(String[] args){
		
	}

}
