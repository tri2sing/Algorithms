import java.util.*;

public class ArrayPuzzles {

	/*
	 * Prints out the elements of an array that have the specified difference.
	 * 
	 * @param arr an array containing integers to check for the difference
	 * @param diff the value of the difference to check for among array elements
	 * 
	 */
	public static void findDiffExists (int [] arr, int diff) {
	
		if (arr == null) throw new IllegalArgumentException ();
		
		System.out.println ("Diff = " + diff);
		
		int len = arr.length;
		HashSet<Integer> hs = new HashSet<Integer> (); 
		
		for (int i = 0; i < len; i ++) {
			hs.add(arr[i]);
		}
		
		for (int i = 0; i < len; i++) {
			if (hs.contains(arr[i] - diff)) {
				System.out.println (arr[i] + ", " + (arr[i] - diff));
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] arr = {10, 20, 30, 40, 15};
		ArrayPuzzles.findDiffExists(arr, 10);
		ArrayPuzzles.findDiffExists(arr, -10);
		ArrayPuzzles.findDiffExists(arr, 0);
		ArrayPuzzles.findDiffExists(arr, -1);

	}

}
