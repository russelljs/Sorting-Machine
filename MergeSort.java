import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/******************************************************************************
 * Jacob S. Russell
 * CSCE350 Algorithm Project
 * Date Created: Thursday, October 27, 2011
 * Date Modified: Thursday, November 10, 2011
 * Date Finalized: Thursday, November 10, 2011
 * /home/jake/workspace/CSCE350Project
 */
public class MergeSort extends Thread{

	int iterations;
	public void run(int mergeArray[]) throws IOException {

		iterations = 0;//gotta reset the iteration counter
		
		//dialog
		System.out.println("");
		System.out.println("**************************************************"+
				"********************************************");
		System.out.println("Executing Merge Sort on Array:");
		System.out.println(Arrays.toString(mergeArray));
		System.out.println("");
		
		//HERE WE GO, AWWW YEAH
		sort(mergeArray, 0, mergeArray.length-1);
		
		//print output
		System.out.println("Your merge sorted array is:");
		System.out.println(Arrays.toString(mergeArray));
		System.out.println("It ran in "+iterations+" iterations.");
		System.out.println("");
		
		
		PrintWriter out = new PrintWriter(new FileWriter("mergeOutFile.txt")); 
		out.print(Arrays.toString(mergeArray));
		System.out.println("Your mergesorted file is saved " +
				"in mergeOutFile.txt");
		System.out.println("**************************************************"+
				"********************************************");
		System.out.println("");
		out.close();
	}//end run
/******************************************************************************
* ROLL THAT BEAUTIFUL SORT FOOTAGE
*/
	private void sort(int array[], int lo, int n)
	{
		int low = lo;
		int high = n;
		iterations++;
		
		//This guy controls the duration of the sort
		if (low >= high)
		{
			return;
		}//end if

		int middle = (low + high) / 2;
		sort(array, low, middle);
		sort(array, middle + 1, high);
		int end_low = middle;
		int start_high = middle + 1;
		while ((lo <= end_low) && (start_high <= high)) 
		{
				  
			if (array[low] < array[start_high]) 
			{
				low++;
			}//end if
			  
			else 
			{
				int Temp = array[start_high];
			
				for (int k = start_high- 1; k >= low; k--) 
				{
					array[k+1] = array[k];
				}//end for
			
				array[low] = Temp;
				low++;
				end_low++;
				start_high++;
			}//end else
		}//end while
	}//end sort

/******************************************************************************
* This method lets us access our iterations counter from outside the method
* for comparison. 
*/
	public int printIterations()
	{
		System.out.println("Iterations for MergeSort: "+iterations);
		return iterations;
	}//end printIterations
	
}//end class*******************************************************************
