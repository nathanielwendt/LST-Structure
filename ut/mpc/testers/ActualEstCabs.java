package ut.mpc.testers;

import java.io.BufferedReader;
import java.io.FileReader;

import ut.mpc.kdt.ArrayTree;
import ut.mpc.kdt.KDTTree;
import ut.mpc.kdt.STStore;
import ut.mpc.setup.Init;
import KDTree.*;

public class ActualEstCabs {
	public static KDTTree kdActual;
	public static KDTTree kdEst;
	public static long timer;
	
	public static void main(String[] args){
		Init.setCabsDefaults();
		kdActual = new KDTTree(2,false);
		kdEst = new KDTTree(2,false);
		
		STStore[] trees = new STStore[]{kdActual,kdEst};
		
		try {
	        long start = System.currentTimeMillis();
			CabSpottingWrapper.fillPointsFromFile(trees,args);
	        System.out.println("Time: " + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Helpers.prove("trees match in size",kdActual.getSize() == kdEst.getSize());
		getStable();
		Init.DEBUG_LEVEL3 = true;
		System.out.println("Set Name >> " + args[0]);
		System.out.println("Size is: " + kdActual.getSize());
        System.out.println("[KDTree - Actual]");
        Helpers.startTimer();
        kdActual.windowQuery(false, 0);
        Helpers.endTimer(true);
        
        System.out.println("[KDTree - Estimated]");
        Helpers.startTimer();
        kdEst.windowQuery(false, 1);
        Helpers.endTimer(true);
        System.out.println("----------------------");
	}
	
	public static void getStable(){
		double time1 = 0;
		double time2 = 0;
		do {
			Init.DEBUG_LEVEL3 = false;
			Helpers.startTimer();
			kdEst.windowQuery(false,1);
			time1 = Helpers.endTimer(false);
			Helpers.startTimer();
			kdEst.windowQuery(false,1);
			time2 = Helpers.endTimer(false);
		} while(!Helpers.withinThreePercent(time1,time2));
	}
	
}
