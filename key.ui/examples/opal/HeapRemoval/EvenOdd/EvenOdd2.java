class EvenOdd2 {
    //@ invariant isEven(arr[0]);
    //@ invariant isOdd(arr[1]);
    int[] arr; int x;

    boolean isEven(int i) {
        return (i % 2) == 0;
    }
    boolean isOdd(int i) {
        return (i % 2) != 0;
    }

    //@ ensures true;
    void performHeapMods() {x++;}
}