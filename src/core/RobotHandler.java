package core;

import java.awt.AWTException;
import java.awt.Robot;

public class RobotHandler {
	private RobotHandler() {
		try{
			robot = new Robot();
		} catch(AWTException e){
			e.printStackTrace();
		}
		robot.setAutoDelay(0);
	}
	
	private static Robot robot;
	
	static {
		try {
			robot = new Robot();
			robot.setAutoDelay(0);
		} catch (AWTException e) {
			robot = null;
		}
	}
	
	public static Robot getRobot() {
		synchronized(robot) {
			return robot;
		}
	}
}
