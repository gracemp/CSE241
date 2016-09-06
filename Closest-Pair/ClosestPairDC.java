
public class ClosestPairDC {
	public final static double INF = java.lang.Double.POSITIVE_INFINITY;
	static XYPoint closest1 = null;
	static XYPoint closest2=null;
	static double dist=INF;

//method to find the closest pair between two points, from a set of n points
	public static void findClosestPair(XYPoint pointsByX[], 
			XYPoint pointsByY[],
			boolean print){

		int nPoints = pointsByX.length;
// Base case when the length of the array is 2
		if(nPoints==2){
			double distA=pointsByX[0].dist(pointsByX[1]);
			if (distA<dist){
				dist=distA;
				closest1=pointsByX[0];
				closest2=pointsByX[1];
			}
			return;
		}
		//base case if the array is length 1
		if (nPoints==1){
			return;
		}
//create a midpoint, create 4 arrays-- left and right of mid, and the same points sorted by Y.
		int mid=nPoints/2;
		XYPoint[] XL= new XYPoint[mid];
		XYPoint[] XR= new XYPoint[nPoints-mid];
		XYPoint[] YL= new XYPoint[mid];
		XYPoint[] YR= new XYPoint[nPoints-mid];
//fill X arrays
		for (int j=0; j<mid; j++){
			XL[j]=pointsByX[j];
			
		}
		for ( int k=0; k<nPoints-mid; k++){
			XR[k]=pointsByX[k+mid];
		}
		//fill Y arrays 
		int n=0;
		int p=0;
		for (int l=0; l<nPoints; l++){
			if (pointsByY[l].isLeftOf(pointsByX[mid])){
				YL[n]=pointsByY[l];
				n++;
			}
			else{
				YR[p]=pointsByY[l];
				p++;
			}
		}
		findClosestPair(XR, YR, false);
		findClosestPair(XL,YL, false);

	 Combine(pointsByY, pointsByX,  mid);
	 if (print) {
		System.out.println("DC" + " " + "("+ closest1.x +"," + closest1.y +")" +  " " + "(" + closest2.x +","+ closest2.y +")" + "  " + dist );
	 }	
	return;
	}
	//Combine method
	public static void Combine(XYPoint pointsbyY[],XYPoint pointsbyX[],int mid){
		//method checks the points that are close to the midline.
		//determine the length of the YStrip
		int counter=0;
		int i=0;
		for (int j=0; j<pointsbyX.length; j++){
			if ((Math.abs(pointsbyY[j].x-pointsbyX[mid].x)<dist)){
				counter++;
			}
		}
		//fill the YStrip
			XYPoint[] YStrip= new XYPoint[counter];
			for (int k=0; k<pointsbyX.length; k++){
				if ((Math.abs(pointsbyY[k].x-pointsbyX[mid].x)<dist)){
					YStrip[i]=pointsbyY[k];
					i++;
				}
				
			}
		
		int k;
		//k is used to keep the place in the new array.
		for (int j=0; j<i-1;j++){
			k=j+1;
			//check the points crossing the midline for shortest dist
			while ((k<i) && (Math.abs(YStrip[j].y-YStrip[k].y)<dist)){
				double d=(YStrip[j].dist(YStrip[k]));
				if (d<dist){
					dist=d;
					closest1=YStrip[j];
					closest2=YStrip[k];
				}
			k++;	
			}
		}
		return;
	}

}
