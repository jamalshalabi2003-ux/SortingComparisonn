package sortingcomparisonn;

import java.util.Arrays;
import java.util.Random;

public class SortingComparisonn {

    // The assignment requires N = 100,000.
    // For quick testing, temporarily change this to 10_000.
    private static final int SIZE = 100_000;
    private static final Random RANDOM = new Random();

    // 1. Selection Sort: O(N^2) in all cases.
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // 2. Insertion Sort: O(N) when sorted, O(N^2) average/worst case.
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    // 3. Bubble Sort with early exit: O(N) when sorted, O(N^2) average/worst case.
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    // 4. Merge Sort: O(N log N) in all cases.
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(arr, temp, left, mid);
        mergeSort(arr, temp, mid + 1, right);
        merge(arr, temp, left, mid, right);
    }

    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int p = left; p <= right; p++) {
            arr[p] = temp[p];
        }
    }

    // 5. Quick Sort using median-of-three pivot selection.
    // This reduces the chance of the O(N^2) behavior on already sorted/reversed data.
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(arr, low, high);

            // Recurse into the smaller side first to limit recursion depth.
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(arr, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(arr, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        // Arrange low, mid, high so arr[mid] is the median of the three.
        if (arr[low] > arr[mid]) swap(arr, low, mid);
        if (arr[low] > arr[high]) swap(arr, low, high);
        if (arr[mid] > arr[high]) swap(arr, mid, high);

        int pivot = arr[mid];
        swap(arr, mid, high);

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Create N random integers.
    private static int[] createRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = RANDOM.nextInt(1_000_000);
        }
        return arr;
    }

    // Create data cases from the same random source data.
    private static int[][] createTestCases(int[] randomArray) {
        int n = randomArray.length;

        int[] random = Arrays.copyOf(randomArray, n);

        int[] reversed = Arrays.copyOf(randomArray, n);
        Arrays.sort(reversed);
        reverse(reversed);

        int[] fullySorted = Arrays.copyOf(randomArray, n);
        Arrays.sort(fullySorted);

        int[] partiallySorted = Arrays.copyOf(fullySorted, n);
        int sortedPrefixLength = (int) (n * 0.80);

        // Keep about 80% sorted, and shuffle the remaining 20%.
        for (int i = n - 1; i > sortedPrefixLength; i--) {
            int j = sortedPrefixLength + RANDOM.nextInt(i - sortedPrefixLength + 1);
            swap(partiallySorted, i, j);
        }

        return new int[][] { random, reversed, partiallySorted, fullySorted };
    }

    private static void reverse(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            swap(arr, left++, right--);
        }
    }

    // Returns elapsed time in microseconds. Copying happens before the timer starts.
    private static long measure(String algorithm, int[] source) {
        int[] testArray = Arrays.copyOf(source, source.length);

        long startTime = System.nanoTime();

        switch (algorithm) {
            case "Selection Sort":
                selectionSort(testArray);
                break;
            case "Insertion Sort":
                insertionSort(testArray);
                break;
            case "Bubble Sort":
                bubbleSort(testArray);
                break;
            case "Merge Sort":
                mergeSort(testArray);
                break;
            case "Quick Sort":
                quickSort(testArray);
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }

        long endTime = System.nanoTime();

        if (!isSorted(testArray)) {
            throw new IllegalStateException(algorithm + " did not sort the array correctly.");
        }

        return (endTime - startTime) / 1_000;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] algorithms = {
            "Selection Sort",
            "Insertion Sort",
            "Bubble Sort",
            "Merge Sort",
            "Quick Sort"
        };

        String[] caseNames = {
            "Random",
            "Reversely Sorted",
            "Partially Sorted (80%)",
            "Fully Sorted"
        };

        int[] original = createRandomArray(SIZE);
        int[][] testCases = createTestCases(original);

        System.out.println("SORTING ALGORITHMS BENCHMARK");
        System.out.println("Array size: " + SIZE);
        System.out.println("Time unit: microseconds (us)");
        System.out.println();

        System.out.printf("%-22s | %-18s | %-18s | %-25s | %-18s%n",
                "Algorithm", "Random", "Reversely Sorted",
                "Partially Sorted (80%)", "Fully Sorted");
        System.out.println("------------------------------------------------------------------------------------------------");

        for (String algorithm : algorithms) {
            System.out.printf("%-22s", algorithm);

            for (int caseIndex = 0; caseIndex < testCases.length; caseIndex++) {
                long time = measure(algorithm, testCases[caseIndex]);
                System.out.printf(" | %,16d", time);
            }

            System.out.println();
        }

        System.out.println();
        System.out.println("All results are in microseconds. Each run checks that the result is sorted.");
        System.out.println("Note: O(N^2) algorithms at N=100,000 can take a very long time.");
    }
}
