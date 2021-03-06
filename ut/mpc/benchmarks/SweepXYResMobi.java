package ut.mpc.benchmarks;

import ut.mpc.kdt.LSTTree;
import ut.mpc.kdt.STStore;
import ut.mpc.setup.Init;

public class SweepXYResMobi {
	public static LSTTree kdtree;
	public static long timer;
	
	public static void main(String[] args){
		Init.setMobilityDefaults();
		kdtree = new LSTTree(2,false);

		STStore[] trees = new STStore[]{kdtree};

		try {
	        long start = System.currentTimeMillis();
			MobilityWrapper.fillPointsFromFile(trees,args); //added comment to tester
	        System.out.println("Time: " + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getStable();
		Init.DEBUG_LEVEL3 = true;
		System.out.println("Set Name >> " + args[0]);
		System.out.println("Size is: " + kdtree.getSize());
        System.out.println("[KDTree] Trim Accurate - Grid=1");
        Init.CoverageWindow.X_GRID_GRAN = 1;
        Init.CoverageWindow.Y_GRID_GRAN = 1;
        kdtree.windowQuery(false, 1);
        Helpers.endTimer(true);
        
        System.out.println("[KDTree] Trim Standard - Grid=5");
        Init.CoverageWindow.X_GRID_GRAN = 5;
        Init.CoverageWindow.Y_GRID_GRAN = 5;
        Helpers.startTimer();
        kdtree.windowQuery(false, 1);
        Helpers.endTimer(true);
        
        System.out.println("[KDTree] Trim Opt - Grid=25");
        Init.CoverageWindow.X_GRID_GRAN = 25;
        Init.CoverageWindow.Y_GRID_GRAN = 25;
        Helpers.startTimer();
        kdtree.windowQuery(false, 1);
        Helpers.endTimer(true);
        
        System.out.println("[KDTree] Trim Speed - Grid=50");
        Init.CoverageWindow.X_GRID_GRAN = 50;
        Init.CoverageWindow.Y_GRID_GRAN = 50;
        Helpers.startTimer();
        kdtree.windowQuery(false, 1);
        Helpers.endTimer(true);
        System.out.println("-----------------------");
	}
	
	public static void getStable(){
		double time1 = 0;
		double time2 = 0;
		do {
			Helpers.startTimer();
			kdtree.windowQuery(false,1);
			time1 = Helpers.endTimer(false);
			Helpers.startTimer();
			kdtree.windowQuery(false,1);
			time2 = Helpers.endTimer(false);
		} while(!Helpers.withinThreePercent(time1,time2));
	}
}
