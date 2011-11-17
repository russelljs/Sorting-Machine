/**
 * Jacob S. Russell
 * CSCE350 Algorithm Project
 * Date Created: Tuesday, September 13, 2011
 * Date Modified: Tuesday, November 1, 2011
 * /home/jake/workspace/CSCE350Project
 * 
 * SUMMARY: The GUI class is where the majority of the work takes place. This
 * class is by far the largest and most difficult to read. It calls upon the
 * MergeSort.java and Quicksort.java classes to do their thing, but GUI does 
 * most of the work, such as preparing the arrays to be sorted and parsing the
 * infile. 
 */

//import things
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

//this class creates the GUI and its basic functions
public class gui {
	//create our main frame
	private JFrame mainFrame = new JFrame("Magnificent Sorting Machine");
	private MergeSort merge = new MergeSort();
	private QuickSort quick = new QuickSort();
		
	//create our buttons
	private JButton openFile = new JButton("Open File");
	private JButton exitButton = new JButton("Quit");
	private JButton sortStart = new JButton("Sort!");
	
	//text areas
	private JTextArea outputWindow = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(outputWindow);
	
	//menu stuff
	private JMenuBar mb = new JMenuBar();// Menu bar
	private JMenu mnuFile = new JMenu("File"); // File Entry on Menu bar
	private JMenuItem mnuItemQuit = new JMenuItem("Quit"); // Quit sub item
	private JMenu mnuHelp = new JMenu("Help"); // Help Menu entry
	private JMenuItem mnuItemAbout = new JMenuItem("About"); // About Entry
	
	//variables
	private File inFile=null;
	private int mergeArray[]=null;
	private int quickArray[]=null;
	
/******************************************************************************
 * This is the method in which the GUI is built using the above variables
 * 
 */
	public gui(){
		//set the menu bar
		mainFrame.setJMenuBar(mb);
		
		//Build Menus
		mnuFile.add(mnuItemQuit);  // Create Quit line
		mnuHelp.add(mnuItemAbout); // Create About line
		mb.add(mnuFile);        // Add Menu items to form
		mb.add(mnuHelp);
		
		//configure main frame
		//no "layout". We're doing it by pixels, ladies.
		mainFrame.getContentPane().setLayout(null);
		
		//redirect the output to the textArea
		redirectSystemStreams();
		
		//set up button functionality
		//This one is for the sort button. It's the nastiest part of the code.
		sortStart.addActionListener(new ActionListener()  
		{  
		   public void actionPerformed(ActionEvent e)
		   {  
			   //This executes if and only if your infile has been defined.
			   if(inFile!=null)
			   {
				   
				  System.out.println("Start Sort...");
				  
				  //call the parser from gui.java class
				  try {
					System.out.println("Parsing...");
					parser();
				  } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				  }//end trycatch
				
				  //call the merge sort from MergeSort.java
				  try {
					merge.run(mergeArray);
				  } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				  }
				  
				  //call the quick sort from QuickSort.java
				  try {
					quick.run(quickArray);
				  } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				  }//end trycatch
				  
				  System.out.println("Sort Complete!");	
				  
				  //compare the stuff
				  int mergeIter = merge.printIterations();
				  int quickIter = quick.printIterations();
				  
				  if(mergeIter > quickIter)
				  {
					  System.out.println("For this file, Quicksort was the "+
							  "faster method.");
				  }//end if
				  
				  else if(mergeIter < quickIter)
				  {
					  System.out.println("For this file, Mergesort was the "+
							  "faster method.");
				  }//end if
				  
				  else
				  {
					  System.out.println("For this file, Mergesort and "+
							  "Quicksort were evenly matched.");
				  }//end else					  	
				  
				  System.out.println("");
				  
			  }//end if "inFile!=null;
			  
			  else{
				  System.out.println("Please define your input file.");
			  }//end else
		   }//end action Performed
		});//end messy sort button trycatch clusterfuck
		
//This is where the open file dialog is handled. Pretty basic.
		openFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				String filename = File.separator+"";
				JFileChooser fc = new JFileChooser(new File(filename));

				// Show open dialog; this method does not return 
				// until the dialog is closed
				fc.showOpenDialog(mainFrame);
				inFile = fc.getSelectedFile();
				System.out.println(inFile+" Selected!");
			}//end actionPerformed
		});//end openFile button stuff
		
		exitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//exit the program
				System.exit(0);
			}
		}
		);//end exit button stuff
		
		//add things to the form
		addElement(mainFrame, openFile, 10, 10, 130, 40);
		addElement(mainFrame, sortStart, 10, 55, 130, 40);
		addElement(mainFrame, exitButton, 10, 505, 130, 40);
		addElement(mainFrame, scrollPane, 150, 10, 635, 535);
		
		//allow closing
		mainFrame.addWindowListener(new ListenCloseWdw());
		mnuItemQuit.addActionListener(new ListenMenuQuit());
		
	}//end gui constructor*****************************************************
/******************************************************************************
* Here's our launchFrame method. This should really be closer to the top, but 
* I've grown accustomed to where it is. It handles the size and visibility of 
* the gui. 
*/
	public void launchFrame(){
		// Display Frame
		  mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  mainFrame.setSize(800, 600);
		  mainFrame.setVisible(true);
		  mainFrame.setResizable(false);
	}//end void launchframe
	
/******************************************************************************
* This is a pretty simple little guy that adds elements to the frame.  
*/
	private void addElement (Container c, Component w, int x, int y, int h, 
			int z) {
		w.setBounds(x, y, h, z);
		c.add(w);
	}//end addElement

/******************************************************************************
* The parser does a pretty important job. This is where the file is read and 
* placed into the arrays to go to the sorter classes. 
*/
	private void parser() throws IOException
	{
		//clear the arrays after each iteration of the parser
		mergeArray = null;
		quickArray = null;
		
		//This is so we can get a count of how many lines are in the infile.
		String fileStr = inFile.toString();
		int length = count(fileStr);
		
		//The primary array we'll be working with internally
		int tall[] = new int[length];
		
		Scanner myFile = new Scanner(new FileReader(inFile));
		
		//System.out.println("Initializations passed!");//debug	
		//System.out.println(length+" is the number of lines in "+inFile);
		
		//This populates the internal array from the inFile
		for(int i = 0; i<length; i++)
		{
			int holder = Integer.parseInt(myFile.nextLine());
			tall[i] = holder;
		}//end for loop
		
		//System.out.println("Parser loop passed!");//debug
		
		//close the file
		myFile.close();
		
		//here we prepare the arrays to send to the sorters
		mergeArray=tall.clone();
		quickArray=tall.clone();
		
		//dialog
		System.out.println("Parse Complete!");
		System.out.println("");
		System.out.println("Entering Sort Methods...");
		
	}//end parser
	
/******************************************************************************
*  This counting method is how we determine how long the inFile is in terms of 
*  lines. It's important so we can put an upper bound on the for loop in the
*  parser
*/
	private int count(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        while ((readChars = is.read(c)) != -1) {
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    ++count;
	            }
	        }
	        return count;
	    } finally {
	        is.close();
	    }
	}//end counter

/******************************************************************************
* This updates our text area with what would be the console output. 
*/
	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				outputWindow.append(text);
			}
		});
	}//end update Text Area
	
/******************************************************************************
* This method redirects the console output to the GUI by overriding some basic
* writing methods.
*/
	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() 
		{
			@Override
			public void write(int b) throws IOException 
			{
				updateTextArea(String.valueOf((char) b));
			}//end write
	
			@Override
			public void write(byte[] b, int off, int len) throws IOException 
			{
				updateTextArea(new String(b, off, len));
			}//end write
	
			@Override
			public void write(byte[] b) throws IOException 
			{
				write(b, 0, b.length);
			}//end write
		};//end outputStream override

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}//end redirect system streams
/******************************************************************************
	private static void copy( int[] aArray , int aNumIterations) {
	    for ( int idx = 0 ; idx < aNumIterations; ++idx ) {

	      int [] copy = new int[aArray.length];
	      System.arraycopy( aArray, 0, copy, 0, aArray.length );

	    }
	  }//end copy
******************************************************************************/
}//end class gui


			  

