/**
 * Jacob S Russell
 * CSCE350 Algorithm Project
 * Date Created: Tuesday, Sept 13, 2011
 * Date Modified: Tuesday, Sept 13, 2011
 * /home/jake/workspace/CSCE350Project
 * 
 * 
 * GOAL: This sorting program compares two kinds of sorts. Merge Sort and Quick
 * Sort will be compared side-by-side for a standard-input inFile. Make note, 
 * if the infile has empty lines or lines with characters, it will choke. Once
 * the file has been sent to the sorts, they split into separate threads and
 * "race". The total number of iterations required to perform each sort will be
 * rendered in the window. 
 * 
 * SUMMARY: This program presents the user with a GUI. The GUI has options
 * for opening an infile, sorting said infile, and exiting the program. 
 * It displays console output in a window on the GUI. The program outputs the
 * sorted files to a pair of outfiles, one for each sort, so you can compare
 * and make sure they're identical.
 */

//import stuff
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageFilter;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.SwingUtilities;


public class main{
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gui newGui = new gui();
		newGui.launchFrame();
		
	}//end public static void main
}//end class main