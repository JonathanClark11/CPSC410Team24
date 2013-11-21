package ga.util;

public class ArrayUtil {
	public static double getSum(double arr[]) {
		double sum=0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}
	
	public static int getMaxIndex(double arr[]) {
		int maxIndex = 0; 
		double max=0;
		for (int i = 0; i < arr.length; i++) {
		    if (arr[i] > max) {
		        max = arr[i];
		        maxIndex = i;
		    }
		}
		return maxIndex;
	}
}
