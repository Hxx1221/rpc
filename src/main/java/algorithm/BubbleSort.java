package algorithm;

public class BubbleSort {
    public static void main(String[] args) {
        //冒泡排序  O(n^2)
        int[] arr = {2, 8, -1, 7, 23, 78, 45, 32};

        System.out.println("排序前数组为：");


        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j]>arr[j+1]){
                    int tmp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=tmp;

                }


            }
        }


        for (int a : arr) {
            System.out.println(a);
        }
    }
}