package algorithm;

public class InsertionSort {
    //插入排序
    public static void main(String[] args) {
        int[] arr = {8, 32, 12, 45, 231, 343, 4, 2};

        int current;
        for (int i = 0; i < arr.length - 1; i++) {

            current = arr[i + 1];
            int preIndex = i;

            while (preIndex >= 0 && current < arr[preIndex]) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;

            }
            arr[preIndex + 1] = current;
        }


        for (int a : arr) {
            System.out.println(a);
        }


    }
}