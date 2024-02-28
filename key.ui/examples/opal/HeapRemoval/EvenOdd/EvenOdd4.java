class EvenOdd4 {
    //@ invariant isEven(arr[0]);
    //@ invariant isOdd(arr[1]);
    //@ invariant isEven(arr[2]);
    //@ invariant isOdd(arr[3]);
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