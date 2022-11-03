package pgdp.arrays;

import java.util.Arrays;

public class ProbabilisticSearch extends MiniJava {
    /**
     * Binary search slightly modified from the lecture
     */
    public static int[] find(int[] a, int x) {

        return find0(a, x, 0, a.length - 1, 1);
    }

    public static int[] find0(int[] a, int x, int n1, int n2, int numberOfSteps) {
        int t = (n1 + n2) / 2;
        if (a[t] == x)
            return new int[]{t, numberOfSteps};
        else if (n1 >= n2)
            return new int[]{-1, numberOfSteps};
        else if (x > a[t])
            return find0(a, x, t + 1, n2, numberOfSteps + 1);
        else if (n1 < t)
            return find0(a, x, n1, t - 1, numberOfSteps + 1);
        else return new int[]{-1, numberOfSteps};
    }

    public static int[] probalisticSearch(int[] arr, int value) {
        int maxNumber = arr[arr.length - 1];
        int minNumber = arr[0];
        int arrLength = arr.length;
        int position = (int) Math.round((value - minNumber) / ((double) (maxNumber - minNumber) / (arrLength - 1)));
        ;
        if (position < 0) {
            position = 0;
        }
        if (position >= arrLength) {
            position = arrLength;
        }
        boolean leftOrRight = true;
        if (value < arr[position]) {
            leftOrRight = false;
        }
        if (value == arr[position])
            return new int[]{position, 1};

        return actions(arr, value, 1, position, -1, leftOrRight);

    }

    public static int[] actions(int[] arr, int input, int numOfCalls, int currPosition, int prevPosition, boolean leftOrRight) {
        prevPosition = currPosition;
        if (leftOrRight == false) {
            currPosition = (int) (currPosition - Math.pow(2d, numOfCalls - 1));
        } else currPosition = (int) (currPosition + Math.pow(2d, numOfCalls - 1));

        if (currPosition < 0) {
            currPosition = 0;
        }

        if (currPosition >= arr.length) {
            currPosition = arr.length - 1;
        }

        if (arr[currPosition] == input) {
            return new int[]{currPosition, numOfCalls + 1};
        }

        boolean between;
        if ((leftOrRight == false && arr[currPosition] < input) || (leftOrRight == true && arr[currPosition] > input)) {
            between = true;
        } else between = false;


        if (currPosition == prevPosition) {
            return new int[]{-1, numOfCalls};
        }
        if (leftOrRight == true && between == true) {
            return find0(arr, input, prevPosition + 1, currPosition - 1, numOfCalls + 1);
        } else if (leftOrRight == false && between == true) {
            return find0(arr, input, currPosition + 1, prevPosition - 1, numOfCalls + 1);
        }
        if (leftOrRight == true) {
            return actions(arr, input, numOfCalls + 1, currPosition, prevPosition, true);
        } else {
            return actions(arr, input, numOfCalls + 1, currPosition, prevPosition, false);
        }

    }

    public static int binCalls(int[] arr, int min, int max, int maxBinaryCalls) {
        for (int i = min; i <= max; i++) {
            int[] binaryResult = find(arr, i);
            if (binaryResult[1] > maxBinaryCalls) {
                maxBinaryCalls = binaryResult[1];
            }
        }
        return maxBinaryCalls;
    }


    public static int binValues(int[] arr, int min, int max, int maxBinaryCalls, int binaryValues) {
        for (int i = min; i <= max; i++) {
            int[] binaryResult = find(arr, i);
            if (binaryResult[1] > maxBinaryCalls) {
                maxBinaryCalls = binaryResult[1];
                binaryValues = i;
            }
        }
        return binaryValues;
    }

    public static int binaryCalls(int[] arr, int min, int max, int maxBinaryCalls, int binCalls) {
        for (int i = min; i <= max; i++) {
            int[] binaryResult = find(arr, i);
            if (binaryResult[1] > maxBinaryCalls) {
                maxBinaryCalls = binaryResult[1];
            }
            binCalls += binaryResult[1];
        }
        return binCalls;
    }


    public static int probCalls(int[] arr, int min, int max, int maxProbabilisticCalls) {
        for (int i = min; i <= max; i++) {
            int[] probabilisticResult = probalisticSearch(arr, i);
            if (probabilisticResult[1] > maxProbabilisticCalls) {
                maxProbabilisticCalls = probabilisticResult[1];
            }
        }
        return maxProbabilisticCalls;
    }


    public static int probabilisticValues(int[] arr, int min, int max, int maxProbabilisticCalls, int maxProbabilisticValues) {
        for (int i = min; i <= max; i++) {
            int[] probabilisticResult = probalisticSearch(arr, i);
            if (probabilisticResult[1] > maxProbabilisticCalls) {
                maxProbabilisticCalls = probabilisticResult[1];
                maxProbabilisticValues = i;
            }
        }
        return maxProbabilisticValues;
    }

    public static int probabilisticCalls(int[] arr, int min, int max, int maxProbabilisticCalls, int totalProbabilisticCalls) {
        for (int i = min; i <= max; i++) {
            int[] probabilisticResult = probalisticSearch(arr, i);
            if (probabilisticResult[1] > maxProbabilisticCalls) {
                maxProbabilisticCalls = probabilisticResult[1];
            }
            totalProbabilisticCalls += probabilisticResult[1];
        }
        return totalProbabilisticCalls;
    }


    public static void compareApproaches(int[] arr, int min, int max) {
        StringBuilder sb = new StringBuilder();
        sb.append("Binary Search:\n");
        sb.append("Maximum number of calls:\n");
        sb.append(binCalls(arr, min, max, 0));
        sb.append("\nValue at which the maximum number of calls occurs:\n");
        sb.append(binValues(arr, min, max, 0, Integer.MIN_VALUE));
        sb.append("\nNumber of total calls:\n");
        sb.append(binaryCalls(arr, min, max, 0, 0));
        sb.append("\nProbabilistic Search:\n");
        sb.append("Maximum number of calls\n");
        sb.append(probCalls(arr, min, max, 0));
        sb.append("\nValue at which the maximum number of calls occurs:\n");
        sb.append(probabilisticValues(arr, min, max, 0, Integer.MIN_VALUE));
        sb.append("\nNumber of total calls:\n");
        sb.append(probabilisticCalls(arr, min, max, 0, 0));

        System.out.println(sb);
    }

    public static void main(String[] args) {
        
    }
}
