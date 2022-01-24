package HomeWork2_5;

public class TwoMethods {

    static final int SIZE = 1000000;
    static final int HALF = SIZE / 2;

    static float[] array = new float[SIZE];

    static float[] arrayFirstHalf = new float[HALF];
    static float[] arraySecondHalf = new float[HALF];


    public static void main(String[] args) {

        firstMethodSuccessive();
        secondMethodWithThreads();

    }

    private static void firstMethodSuccessive() {
        array = FillArray();
        long a = System.currentTimeMillis();

        calculateArrayByFormula(array);
        System.out.println(System.currentTimeMillis() - a);
    }

    private static void secondMethodWithThreads() {
        array = FillArray();
        long a = System.currentTimeMillis();

        System.arraycopy(array,0,arrayFirstHalf, 0, HALF);
        System.arraycopy(array, HALF, arraySecondHalf, 0, HALF);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                calculateArrayByFormula(arrayFirstHalf);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                calculateArrayByFormula(arraySecondHalf);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arrayFirstHalf,0,array, 0, HALF);
        System.arraycopy(arraySecondHalf, 0, array, HALF, HALF);

        System.out.println(System.currentTimeMillis() - a);
    }

    private static float[] FillArray() {
        for (float i : array) i = 1;
        return array;
    }

    private static void calculateArrayByFormula(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
