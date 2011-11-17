import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/******************************************************************************
 * Jacob S. Russell
 * CSCE350 Algorithm Project
 * Date Created: Thursday, October 27, 2011
 * Date Modified: Tuesday, November 1, 2011
 * /home/jake/workspace/CSCE350Project
 */
public class QuickSort extends Thread{
	
	int iterations = 0;
	
	public void run(int quickArray[]) throws IOException
	{
		iterations = 0;
		//dialog
		System.out.println("");
		System.out.println("**************************************************"+
				"********************************************");
		System.out.println("Executing Quick Sort on Array:");
		System.out.println(Arrays.toString(quickArray));
		System.out.println("");
		
		sort(quickArray);
		
		System.out.println("Your quick sorted array is:");
		System.out.println(Arrays.toString(quickArray));
		System.out.println("It ran in "+iterations+" iterations.");
		System.out.println("");
		
		
		PrintWriter out = new PrintWriter(new FileWriter("mergeOutFile.txt")); 
		out.print(Arrays.toString(quickArray));
		System.out.println("Your quicksorted file is saved " +
				"in mergeOutFile.txt");
		System.out.println("**************************************************"+
				"********************************************");
		System.out.println("");
		out.close();
		
	}//end run
	
/******************************************************************************
* ROLL THAT BEAUTIFUL SORT FOOTAGE
*/
	//base case
	public void sort(int array[]) 
	{
		sort(array, 0, array.length - 1);// quicksort all the elements in the array
		iterations++;
	}//end sort
	
	//non-base case	
	public void sort(int array[], int start, int end)
	{
	        int i = start;                          
	        int k = end;                            

	        if (end - start >= 1)                   
	        {
	                int pivot = array[start];       

	                while (k > i)                   // while the scan indices from left and right have not met,
	                {
	                        while (array[i] <= pivot && i <= end && k > i)  // from the left, look for the first
	                                i++;                                    // element greater than the pivot
	                        while (array[k] > pivot && k >= start && k >= i) // from the right, look for the first
	                            k--;                                        // element not greater than the pivot
	                        if (k > i)                                       // if the left seekindex is still smaller than
	                                swap(array, i, k);                      // the right index, swap the corresponding elements
	                }//end while
	                swap(array, start, k);          // after the indices have crossed, swap the last element in
	                                                // the left partition with the pivot 
	                sort(array, start, k - 1);
	                sort(array, k + 1, end);
	        }//end if
	        else
	        {
	                return;
	        }//end else
	        iterations++;
	}//end sort

	public void swap(int array[], int index1, int index2) 
	{
		int temp = array[index1];           // store the first value in a temp
		array[index1] = array[index2];      // copy the value of the second into the first
		array[index2] = temp;               // copy the value of the temp into the second
	}

/******************************************************************************
* This method lets us access our iterations counter from outside the method
* for comparison. 
*/
	public int printIterations()
	{
		System.out.println("Iterations for QuickSort: "+iterations);
		return iterations;
	}//end printIterations
		

}
