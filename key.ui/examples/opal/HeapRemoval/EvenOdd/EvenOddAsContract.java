class EvenOddAsContract {
    int[] arr; int x;

    boolean isEven(int i) {
        return (i % 2) == 0;
    }
    boolean isOdd(int i) {
        return (i % 2) != 0;
    }

    //@ requires isEven(arr[0]);
    //@ ensures isEven(arr[0]);
    void performHeapMods() {x++;}
}