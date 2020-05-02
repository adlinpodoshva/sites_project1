//package com.misha.sitesproject;
//
//import java.util.ArrayList;
//
//interface MakesSound {
//    void makeSound();
//}
//
//class Train implements MakesSound {
//    public void makeSound() {
//        System.out.println("chuu");
//    }
//}
//
//
//class Animal implements MakesSound {
//    protected String color;
//    protected int numLegs;
//
//    public Animal(String color, int numLegs) {
//        this.color = color;
//        this.numLegs = numLegs;
//    }
//
//    public void makeSound() {
//        System.out.println("");
//    }
//}
//
//class Dog extends Animal {
//    protected int tailLength;
//
//    public Dog(String color, int numLegs, int tailLength) {
//        super(color, numLegs);
//        this.tailLength = tailLength;
//    }
//
//    @Override
//    public void makeSound() {
//        System.out.println("hhau");
//    }
//
//}
//
//class Rotweiler extends Dog {
//    public Rotweiler(String color, int numLegs) {
//        super(color, numLegs, 7);
//    }
//}
//
//class Cat extends Animal {
//    private String name;
//
//    public Cat(String color, int numLegs, String name) {
//        super(color, numLegs);
//        this.name = name;
//    }
//
//    @Override
//    public void makeSound() {
//        System.out.println("miau");
//    }
//}
//
//
//
//class Other {
//
//    public static void manageSound(MakesSound makesSound) {
//        makesSound.makeSound();
//    }
//    public static void main(String[] args) {
//        Animal animal = new Animal("black", 7);
//        manageSound(animal);
//        Train train = new Train();
//        manageSound(train);
//
//        Chair chair = new Chair(3);
//        System.out.println(chair.getColor()); // brown
//        chair.setColor("black");
//        System.out.println(chair.getColor()); // black
//        chair.foo();
//
//        Dog dog = new Dog("black", 4, 3);
//        dog.makeSound(); // haau
//        Cat cat = new Cat("gray", 3, "ccat");
//        cat.makeSound(); // miauu
//
//        Animal animal;
//
//        if( 1 > 0) {
//            animal = new Dog("black", 3, 10);
//        } else {
//            animal = new Cat("brown", 7, "whis");
//        }
//
//        animal.makeSound(); // hau
//    }
//}
//
//
//class Chair {
//    static int counter = 0;
//
//    private int numOfLegs;
//    private String color;
//
//    Chair(int numLegs, String clr) {
//        numOfLegs = numLegs;
//        color = clr;
//    }
//
//    Chair(int numLegs) {
//        numOfLegs = numLegs;
//        color = "brown";
//    }
//
//    public String getColor() {
//        return color;
//
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    private void foo() {
//
//    }
//
//    static void printCounter() {
//        System.out.println(counter);
//    }
//
//    void sit() {
//        System.out.println("sit on " + color);
//    }
//
//    void sit(int numOFtimes) {
//        for(int i = 0; i < numOFtimes; i++) {
//            System.out.println("sit on " + color);
//        }
//    }
//
//
//    public static void main(String[] args) {
//        Chair ch = new Chair(3);
//        Chair ch2 = new Chair(5, "black");
//
//        System.out.println(ch.numOfLegs); // 3
//        System.out.println(ch.color); // brown
//        System.out.println(ch2.numOfLegs); // 5
//        System.out.println(ch2.color); // black
//
//        ch.sit(); // sit on brown
//        ch2.sit(2); // sit on black\n sit on black
//
//       // ch.counter++;
//        Chair.counter++;
//        //System.out.println(ch.counter); // 1
//        //System.out.println(ch2.counter); // 1
//        System.out.println(Chair.counter); // 1
//        Chair.printCounter(); // 1
//
//        int[] arr = new int[3]; // 0 0 0
//        arr[1] = 7; // 0 7 0
//
//        boolean[] boolArr = new boolean[2]; // false false
//        double[] doubleArr = new double[2];
//
//        ArrayList<Integer> arrayList = new ArrayList<>(); // empty list
//        arrayList.add(3); // 3
//        arrayList.add(3); // 3 3
//        arrayList.remove(1);
//    }
//}
//
//
