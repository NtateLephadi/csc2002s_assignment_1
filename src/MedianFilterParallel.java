import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
public class MedianFilterParallel extends RecursiveAction {
    private static final int THRESHOLD = 1000;
    private final int[] input;
    private final int[] output;
    private final int filterSize;
    private final int start;
    private final int end;

    public MedianFilterParallel(int[] input, int[] output, int filterSize, int start, int end) {
        this.input = input;
        this.output = output;
        this.filterSize = filterSize;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                output[i] = calculateMedian(i);
            }
        } else {
            int mid = (start + end) / 2;
            invokeAll(
                    new MedianFilterParallel(input, output, filterSize, start, mid),
                    new MedianFilterParallel(input, output, filterSize, mid, end)
            );
        }
    }

    private int calculateMedian(int index) {
        int[] window = new int[filterSize];
        int halfSize = filterSize / 2;
        int startWindow = Math.max(index - halfSize, 0);
        int endWindow = Math.min(index + halfSize + 1, input.length);
        int windowSize = endWindow - startWindow;

        System.arraycopy(input, startWindow, window, halfSize - (index - startWindow), windowSize);
        Arrays.sort(window);

        return window[filterSize / 2];
    }
    public static void main(String[] args) {
        int[] input = {2, 80, 6, 3, 1};
        int[] output = new int[input.length];
        int filterSize = 3;

        ForkJoinPool pool = ForkJoinPool.commonPool();
        MedianFilterParallel filter = new MedianFilterParallel(input, output, filterSize, 0, input.length);
        pool.invoke(filter);

        System.out.println(Arrays.toString(output));
    }
}
