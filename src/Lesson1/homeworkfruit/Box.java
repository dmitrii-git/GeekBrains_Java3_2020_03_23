package Lesson1.homeworkfruit;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Box<T extends Fruit> {

    private ArrayList<T> fruits;
    private float weight;
    private T inst;

    public Box() {
        fruits = new ArrayList<>();
        weight = 0;
    }

    public boolean compareTo(Box<? extends Fruit> other) {
        return getWeight() > other.getWeight();
    }

    public void addFruit(T fruit) {
        inst = fruit;
        weight += fruit.getWeight();
        fruits.add(fruit);
    }

    public void dropFruits(@NotNull Box<? extends Fruit> otherBox) {
        if (inst.getClass() != otherBox.inst.getClass()) {
            throw new DropException();
        }
        for (Fruit fruit : otherBox.fruits) {
            addFruit((T) fruit);
        }
        otherBox.fruits.clear();
        otherBox.weight = 0;
    }

    public ArrayList<T> getFruits() {
        return fruits;
    }

    public double getWeight() {
        return weight;
    }

}
