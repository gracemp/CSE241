

public class ClosestPairNaive {

	public final static double INF = java.lang.Double.POSITIVE_INFINITY;

	//
	// findClosestPair()
	//
	// Given a collection of nPoints points, find and ***print***
	//  * the closest pair of points
	//  * the distance between them
	// in the form "NAIVE (x1, y1) (x2, y2) distance"
	//

	// INPUTS:
	//  - points sorted in nondecreasing order by X coordinate
	//  - points sorted in nondecreasing order by Y co ordinate
	//

	public static void findClosestPair(XYPoint points[], boolean print)
	{
		XYPoint closest1 = null;
		XYPoint closest2=null;
		int nPoints = points.length;
		double mindist=INF;
		int k=0;
		//check each points against every other point for shortest distance
		for (int j=0; j<nPoints-1;j++){
			k=j+1;
			while (k<nPoints){ 
				double d=(points[j].dist(points[k]));
				if (d<mindist){
					mindist=d;
					closest1= points[j];
					closest2=points[k];
				}
				k++;
			}
		}
				if(print){
					System.out.println("NAIVE" + " " +  "("+ closest1.x +", " + closest1.y +")" + " " + "(" + closest2.x +", "+ closest2.y +")" + "  " + mindist );
				
			
		}
	}
}

