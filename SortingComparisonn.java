package sortingcomparisonn;

import java.util.Arrays;
import java.util.Random;

public class SortingComparisonn {

    // تحديد حجم المصفوفة
    private static final int SIZE = 100_000;

    // إنشاء مولّد للأعداد العشوائية
    private static final Random RANDOM = new Random();

    // خوارزمية الترتيب بالاختيار
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;

            // البحث عن أصغر عنصر في الجزء غير المرتب
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // تبديل العنصر الحالي بأصغر عنصر
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // خوارزمية الترتيب بالإدراج
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            // تحريك العناصر الأكبر من العنصر الحالي إلى اليمين
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            // .وضع العنصر في موقعه الصحيح
            arr[j + 1] = key;
        }
    }

    // خوارزمية الترتيب بالفقاعات
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;

            //. مقارنة العناصر المتجاورة وتبديلها عند الحاجة
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // التوقف إذا كانت المصفوفة مرتبة بالكامل
            if (!swapped) {
                break;
            }
        }
    }

    // خوارزمية الترتيب بالدمج
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        // إنشاء مصفوفة مؤقتة لعملية الدمج
        int[] temp = new int[arr.length];

        // بدء عملية تقسيم المصفوفة وترتيبها
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    // تقسيم المصفوفة إلى أجزاء أصغر حتى يسهل ترتيبها
    private static void mergeSort(
            int[] arr, int[] temp, int left, int right) {

        if (left >= right) {
            return;
        }

        // حساب موقع المنتصف
        int mid = left + (right - left) / 2;

        // ترتيب النصف الأيسر
        mergeSort(arr, temp, left, mid);

        // ترتيب النصف الأيمن
        mergeSort(arr, temp, mid + 1, right);

        // دمج النصفين بعد ترتيبهما
        merge(arr, temp, left, mid, right);
    }

    // دمج جزأين مرتبين في جزء واحد مرتب
    private static void merge(
            int[] arr, int[] temp,
            int left, int mid, int right) {

        int i = left;
        int j = mid + 1;
        int k = left;

        // مقارنة عناصر النصفين ونقل الأصغر إلى المصفوفة المؤقتة
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // نقل العناصر المتبقية من النصف الأيسر
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // نقل العناصر المتبقية من النصف الأيمن
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // إعادة العناصر المرتبة إلى المصفوفة الأصلية
        for (int p = left; p <= right; p++) {
            arr[p] = temp[p];
        }
    }

    // خوارزمية الترتيب السريع
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    // ترتيب المصفوفة باستخدام عنصر المحور
    private static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(arr, low, high);

            // ترتيب الجزء الأصغر أولاً لتقليل استهلاك الذاكرة
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(arr, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(arr, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    // تقسيم المصفوفة اعتماداً على عنصر المحور
    private static int partition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        // اختيار المحور باستخدام وسيط ثلاثة عناصر
        if (arr[low] > arr[mid]) {
            swap(arr, low, mid);
        }

        if (arr[low] > arr[high]) {
            swap(arr, low, high);
        }

        if (arr[mid] > arr[high]) {
            swap(arr, mid, high);
        }

        // تحديد قيمة المحور ونقله إلى نهاية الجزء الحالي
        int pivot = arr[mid];
        swap(arr, mid, high);

        int i = low - 1;

        // نقل العناصر الأصغر من المحور إلى الجهة اليسرى
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // وضع المحور في موقعه الصحيح
        swap(arr, i + 1, high);

        return i + 1;
    }

    // تبديل موقع عنصرين داخل المصفوفة
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // إنشاء مصفوفة تحتوي على أعداد عشوائية
    private static int[] createRandomArray(int size) {
        int[] arr = new int[size];

        // تعبئة المصفوفة بأعداد عشوائية
        for (int i = 0; i < size; i++) {
            arr[i] = RANDOM.nextInt(1_000_000);
        }

        return arr;
    }

    // إنشاء حالات الاختبار الأربع
    private static int[][] createTestCases(int[] original) {
        int n = original.length;

        // الحالة الأولى: مصفوفة عشوائية
        int[] random = Arrays.copyOf(original, n);

        // الحالة الثانية: مصفوفة مرتبة عكسياً
        int[] reversed = Arrays.copyOf(original, n);
        Arrays.sort(reversed);
        reverse(reversed);

        // الحالة الثالثة: مصفوفة مرتبة جزئياً بنسبة 80% تقريباً
        int[] partiallySorted = Arrays.copyOf(original, n);
        Arrays.sort(partiallySorted);

        int sortedPrefixLength = (int) (n * 0.80);

        // خلط الجزء المتبقي من المصفوفة
        for (int i = n - 1; i > sortedPrefixLength; i--) {
            int j = sortedPrefixLength
                    + RANDOM.nextInt(i - sortedPrefixLength + 1);

            swap(partiallySorted, i, j);
        }

        // الحالة الرابعة: مصفوفة مرتبة بالكامل
        int[] fullySorted = Arrays.copyOf(original, n);
        Arrays.sort(fullySorted);

        return new int[][] {
            random,
            reversed,
            partiallySorted,
            fullySorted
        };
    }

    // عكس ترتيب عناصر المصفوفة
    private static void reverse(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
        }
    }

    // قياس زمن تنفيذ خوارزمية الترتيب
    private static long measure(String algorithm, int[] source) {

        // إنشاء نسخة مستقلة قبل بدء حساب الزمن
        int[] testArray = Arrays.copyOf(source, source.length);

        // تسجيل وقت البداية بالنانوثانية
        long startTime = System.nanoTime();

        // تنفيذ خوارزمية الترتيب المطلوبة
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
                throw new IllegalArgumentException(
                        "Unknown algorithm: " + algorithm);
        }

        // تسجيل وقت النهاية بالنانوثانية
        long endTime = System.nanoTime();

        // التأكد من أن المصفوفة أصبحت مرتبة بشكل صحيح
        if (!isSorted(testArray)) {
            throw new IllegalStateException(
                    algorithm + " did not sort the array correctly.");
        }

        // حساب الزمن المستغرق وتحويله إلى ميكروثانية
        return (endTime - startTime) / 1000;
    }

    // التحقق من أن عناصر المصفوفة مرتبة تصاعدياً
    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }

        return true;
    }

    // الدالة الرئيسية التي يبدأ منها تنفيذ البرنامج
    public static void main(String[] args) {

        // أسماء خوارزميات الترتيب
        String[] algorithms = {
            "Selection Sort",
            "Insertion Sort",
            "Bubble Sort",
            "Merge Sort",
            "Quick Sort"
        };

        // أسماء حالات الاختبار
        String[] caseNames = {
            "Random",
            "Reversely Sorted",
            "Partially Sorted (80%)",
            "Fully Sorted"
        };

        // إنشاء المصفوفة الأصلية
        int[] original = createRandomArray(SIZE);

        // إنشاء المصفوفات الخاصة بحالات الاختبار
        int[][] testCases = createTestCases(original);

        // طباعة بيانات المشروع والطالب
        System.out.println("SORTING ALGORITHMS BENCHMARK");
        
        System.out.println("Array size: " + SIZE);
        System.out.println("Time unit: microseconds (us)");
        System.out.println();

        // طباعة عناوين جدول النتائج
        System.out.printf(
                "%-22s | %-16s | %-20s | %-25s | %-16s%n",
                "Algorithm",
                caseNames[0],
                caseNames[1],
                caseNames[2],
                caseNames[3]
        );

        System.out.println(
                "------------------------------------------------------------------------------------------------------"
        );

        // اختبار كل خوارزمية على حالات البيانات الأربع
        for (String algorithm : algorithms) {
            System.out.printf("%-22s", algorithm);

            for (int caseIndex = 0;
                    caseIndex < testCases.length;
                    caseIndex++) {

                // قياس الزمن وتسجيل النتيجة
                long time = measure(
                        algorithm,
                        testCases[caseIndex]
                );

                // طباعة الزمن المستغرق بالميكروثانية
                System.out.printf(" | %,16d", time);
            }

            System.out.println();
        }

        // توضيح وحدة الزمن المستخدمة في النتائج
        System.out.println();
        System.out.println("All results are in microseconds.");

        // تأكيد التحقق من صحة ترتيب المصفوفات
        System.out.println(
                "Each result is checked to ensure the array is sorted."
        );
    }
}
