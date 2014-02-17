package ut.mpc.testers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.management.ClassLoadingMXBean;
import java.util.ArrayList;

import ut.mpc.kdt.ArrayTree;
import ut.mpc.kdt.KDTTree;
import ut.mpc.kdt.STStore;
import ut.mpc.kdt.Temporal;
import ut.mpc.setup.Init;
import KDTree.*;

public class ResolutionMobi {
	public static KDTTree kdActual;
	public static KDTTree kdEst;
	public static long timer;
	
	public static void main(String[] args){
		Init.setMobilityDefaults();
		//Init.SPACE_RADIUS = Double.parseDouble(args[0]);
		args = new String[]{"KAIST003.txt"};
		kdActual = new KDTTree(2,false);
		kdEst = new KDTTree(2,false);
		
		STStore[] trees = new STStore[]{kdActual,kdEst};
		
		try {
	        long start = System.currentTimeMillis();
			MobilityWrapper.fillPointsFromFile(trees,args);
	        System.out.println("Time: " + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Temporal> temps = kdActual.getTrajectory();
		for(int i = 0; i < temps.size(); ++i){
			System.out.println(temps.get(i).getTimeStamp());
		}
		kdActual.print();
		Helpers.prove("trees match in size",kdActual.getSize() == kdEst.getSize());
		getStable();
		Init.DEBUG_LEVEL3 = true;
		System.out.println("Set Name >> " + args[0]);
		System.out.println("Size is: " + kdActual.getSize());
        System.out.println("[KDTree - Actual]");
        Helpers.startTimer();
        kdActual.windowQuery(false, 1);
        Helpers.endTimer(true);
        
        System.out.println("[KDTree - Estimated]");
        Helpers.startTimer();
        kdEst.windowQuery(false, 0);
        Helpers.endTimer(true);
	}
	
	public static void getStable(){
		long time1 = 0;
		long time2 = 0;
		do {
			Init.DEBUG_LEVEL3 = false;
			Helpers.startTimer();
			kdEst.windowQuery(false,1);
			time1 = Helpers.endTimer(false);
			Helpers.startTimer();
			kdActual.windowQuery(false,1);
			time2 = Helpers.endTimer(false);
		} while(!Helpers.withinOnePercent(time1,time2));
	}
	
}