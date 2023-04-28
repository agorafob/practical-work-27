package work1;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Runner {
    public void run() {
        int[] testArr = newArr();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int sum = forkJoinPool.invoke(new RecursiveCalc(testArr, 0, testArr.length));
        System.out.println("sum is " + sum);

    }

    public int[] newArr() {
        int[] arr = new int[1_000_000];
        Random number = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = number.nextInt(100);
        }
        return arr;
    }

    private static class RecursiveCalc extends RecursiveTask<Integer> {

        private final int[] array;
        private final int start;
        private final int end;

        public RecursiveCalc(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        protected Integer compute() {
            int mid = start + (end - start) / 2;

            if (end - start <= 20) {
                return calculate(array, start, end);
            } else {
                RecursiveCalc left = new RecursiveCalc(array, start, mid);
                RecursiveCalc right = new RecursiveCalc(array, mid, end);
                invokeAll(left, right);
                return left.join() + right.join();
            }
        }


        private int calculate(int[] array, int start, int end) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
    }

}
