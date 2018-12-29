package lichKing.client.utils;

/**
 *
 * @author catPan
 */
public class ReflectionField {

       public static void insertSort(int[] data) {
             //正排序，从小排到大
                     for (int i = 1; i < data.length; i++) {
                            //保证前i+1个数排好序
                            for (int j = 0; j < i; j++) {
                                   if (data[j] > data[i]) {
                                          //交换在位置j和i两个数
                                          swap(data, i, j);
                                   }
                            }
                     }
    }

       /**
        * 交换数组中指定的两元素的位置
        * @param data
        * @param x
        * @param y
        */
       private static void swap(int[] data, int x, int y) {
              int temp = data[x];
              data[x] = data[y];
              data[y] = temp;
       }
}
