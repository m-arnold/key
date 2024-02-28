public class HeapAnonymize {

    private int x,y;

    //@ assignable
    abstract void m1();

    //@ ensures \result == 73;
    private void m2() {
        x = 73;
        m1();
        return x;
    }

}