package algorithm;

import java.util.HashMap;

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

        int tmp;
        for (int i = 0; i < arr.length - 1; i++) {
            tmp = arr[i + 1];
            int preIndex = i;
//如果当前下标大于等于0并且 当前循环的下标的下一个下标中的数据大于小于当前下标中的数据
            while (preIndex>=0&&tmp<arr[preIndex]){

                arr[preIndex+i]=arr[preIndex];
                preIndex--;


            }



        }
        /*1从第一个元素开始，该元素可以认为已经被排序；
        2取出下一个元素，在已经排序的元素序列中从后向前扫描；
        3如果该元素（已排序）大于新元素，将该元素移到下一位置；
        重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
        将新元素插入到该位置后；*/





        for (int a : arr) {
            System.out.println(a);
        }
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("","");

float a=4>>>2;
        System.out.println(a);
    }
}