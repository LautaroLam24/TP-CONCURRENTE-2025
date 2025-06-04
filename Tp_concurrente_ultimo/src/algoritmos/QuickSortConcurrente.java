package algoritmos;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSortConcurrente {
    private static final ForkJoinPool pool = new ForkJoinPool();

    public static void sort(int[] arr) {
        pool.invoke(new QuickSortTask(arr, 0, arr.length - 1));
    }

    @SuppressWarnings("serial")
    private static class QuickSortTask extends RecursiveAction {
        private final int[] arr;
        private final int low;
        private final int high;
        private static final Random rand = new Random();

        QuickSortTask(int[] arr, int low, int high) {
            this.arr = arr;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (low < high) {
                int pivotIndex = low + rand.nextInt(high - low + 1);
                swap(arr, pivotIndex, high);

                int p = partition(arr, low, high);

                QuickSortTask leftTask = new QuickSortTask(arr, low, p - 1);
                QuickSortTask rightTask = new QuickSortTask(arr, p + 1, high);
                invokeAll(leftTask, rightTask);
            }
        }

        private static int partition(int[] arr, int low, int high) {
            int pivot = arr[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
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
    }
}
