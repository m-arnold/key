class EvenOdd8 {
    //@ invariant isEven(arr[0]);
    //@ invariant isOdd(arr[1]);
    //@ invariant isEven(arr[2]);
    //@ invariant isOdd(arr[3]);
    //@ invariant isEven(arr[4]);
    //@ invariant isOdd(arr[5]);
    //@ invariant isEven(arr[6]);
    //@ invariant isOdd(arr[7]);
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