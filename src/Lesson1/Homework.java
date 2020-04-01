package Lesson1;

import java.util.Arrays;
import java.util.Objects;

public class Homework<T> {

    // 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);

    public void arraySwitch (T[] array, T a, T b){
        System.out.println(Arrays.toString(array));
        int aI = 0;
        int bI = 0;
        for (int i = 0; i < array.length; i++){
             if (Objects.equals(a, array[i])){
                aI = i;
             }
             if (Objects.equals(b, array[i])){
                bI = i;
             }
        }
        array[aI] = b;
        array[bI] = a;
        System.out.println(Arrays.toString(array));
    }

    //2. Написать метод, который преобразует массив в ArrayList;

    public void toArrayList (T[] array){
        String arrayList = Arrays.toString(Objects.requireNonNull(array));
        System.out.println(arrayList);
    }



    public static void main(String[] args) {
        Homework<String> myArray = new Homework<>();
        myArray.arraySwitch(new String[]{"a", "c", "f","d","q","b","w"}, "b", "c");
        myArray.toArrayList(new String[]{"a", "c", "f","d","q","b","w"});


    }




}
