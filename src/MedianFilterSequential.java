import java.util.Arrays;

public class MedianFilterSequential {
    public static void main(String[] args) {
        int[] input = {2, 80, 6, 3, 1};
        int[] output = new int[input.length];
        int filterSize = 3;

        for (int i = 0; i < input.length; i++) {
            output[i] = calculateMedian(input, filterSize, i);
        }

        System.out.println(Arrays.toString(output));
    }

    private static int calculateMedian(int[] input, int filterSize, int index) {
        int[] window = new int[filterSize];
        int halfSize = filterSize / 2;
        int startWindow = Math.max(index - halfSize, 0);
        int endWindow = Math.min(index + halfSize + 1, input.length);
        int windowSize = endWindow - startWindow;

        System.arraycopy(input, startWindow, window, halfSize - (index - startWindow), windowSize);
        Arrays.sort(window);

        return window[filterSize / 2];
    }
}
