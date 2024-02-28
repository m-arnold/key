class EvenOdd {
    //@ invariant isEven(arr[0]);
    int[] arr; int x;

    boolean isEven(int i) {
        return (i % 2) == 0;
    }
    boolean isOdd(int i) {
        return (i % 2) != 0;
    }

    //@ ensures true;
    void performHeapMods() {x++;}

    //@ ensures true;
    void modifyArr() {arr[0] = 42;}
}