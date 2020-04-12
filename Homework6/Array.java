import java.util.Arrays;

public class Array {
    public int[] arrayEnter(int[] array) {

        int key = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                key = i;
                //System.out.println(key);
            }
        }
        if (key == -1) {
            throw new RuntimeException("number 4 is missing in the array");
        }
        int[] arrayNew = Arrays.copyOfRange(array, key + 1, array.length);

        return arrayNew;
    }
    public boolean arrayEnter14(int[] array){
        boolean answer = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1 || array[i] == 4){
                answer = true;
            }
        }
        return answer;
    }
}



