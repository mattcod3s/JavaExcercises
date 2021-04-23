package ie.tudublin;

import example.MyVisual;
import example.RotatingAudioBands;

public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new MyVisual());		
	}

	public void Audio1() {
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Audio1());	
	}

	public void Audio2() {
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Audio2());	
	}
	
	public void Test() {
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Test());	
	}


	public static void main(String[] args)
	{
		Main main = new Main();
		main.Test();			
	}
}