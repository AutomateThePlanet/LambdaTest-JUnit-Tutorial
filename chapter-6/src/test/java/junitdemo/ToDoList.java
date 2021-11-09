package junitdemo;

import java.util.List;

public class ToDoList {
    public ToDoList(String technology, List<String> itemsToAdd, List<String> itemsToCheck, int expectedItemsLeft) {
        this.technology = technology;
        this.itemsToAdd = itemsToAdd;
        this.itemsToCheck = itemsToCheck;
        this.expectedItemsLeft = expectedItemsLeft;
    }

    private String technology;
    private List<String> itemsToAdd;
    private List<String> itemsToCheck;
    private int expectedItemsLeft;

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public List<String> getItemsToAdd() {
        return itemsToAdd;
    }

    public void setItemsToAdd(List<String> itemsToAdd) {
        this.itemsToAdd = itemsToAdd;
    }

    public List<String> getItemsToCheck() {
        return itemsToCheck;
    }

    public void setItemsToCheck(List<String> itemsToCheck) {
        this.itemsToCheck = itemsToCheck;
    }

    public int getExpectedItemsLeft() {
        return expectedItemsLeft;
    }

    public void setExpectedItemsLeft(int expectedItemsLeft) {
        this.expectedItemsLeft = expectedItemsLeft;
    }

    public ToDoList(String technology) {
        this.technology = technology;
    }
}
