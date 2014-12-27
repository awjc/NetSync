package core;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Main {
	public static void main(String[] args){
		Robot r = RobotHandler.getRobot();
		r.setAutoDelay(20);
		r.mouseMove(600, 480);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		type(r, "hEllo");
	}

	private static void type(Robot robot, String str){
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(Character.isUpperCase(c)){
				robot.keyPress(KeyEvent.VK_SHIFT);
			}
			robot.keyPress(Character.toUpperCase(c));
			robot.keyRelease(Character.toUpperCase(c));

			if(Character.isUpperCase(c)){
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
		}
	}
}
