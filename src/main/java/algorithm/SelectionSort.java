package algorithm;

public class SelectionSort {
    //选择排序
    public static void main(String[] args) {
        int[] arr = {8, 32, 12, 45, 231, 343, 4, 2};
        for (int i = 0; i < arr.length; i++) {

            int minIndex = i;

            for (int j = i; j < arr.length; j++) {

                if (arr[j] < arr[minIndex]) {//如果小于 那么就替换

                    minIndex = j;

                }
            }
            int tmp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = tmp;
        }
        for (int a : arr) {
            System.out.println(a);
        }
    }
}