package ut.mpc.kdt;

import ut.mpc.phenom.LocationWrapper;
import java.sql.Timestamp;

public class Temporal {
	private double x;
	private double y;
	private long timeStamp;
	
	public Temporal(long timeStamp){
		this.timeStamp = timeStamp;
	}
	
	public Temporal(long timeStamp, double x, double y){
		this.x = x;
		this.y = y;
		this.timeStamp = timeStamp;
	}
	
	public double[] getCoords(){
		return new double[]{this.x,this.y};
	}
	
	public double getXCoord(){
		return this.x;
	}
	
	public double getYCoord(){
		return this.y;
	}
	
	public long getTimeStamp(){
		return this.timeStamp;
	}
	
	//linear decay function with slope of 1
	public double getTimeRelevance(long reference){
		return (double) this.timeStamp / (double) reference;
	}
	
	//linear decay function with any value for slope
	public double getTimeRelevance(long current, long reference, double time_decay){
		long nowOffset = this.timeStamp - reference;
		long currentOffset = current - reference;
		double temp = (double) nowOffset / ((double) currentOffset * time_decay);
		if(temp > 1)
			System.out.println(temp);
		return (double) nowOffset / ((double) currentOffset * time_decay);
		//return 1.0;
	}
	
}