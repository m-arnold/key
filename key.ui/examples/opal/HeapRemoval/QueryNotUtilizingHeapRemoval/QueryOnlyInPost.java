public class QueryOnlyInPost {

    int x;

    boolean incrementedByOne(int oldVal, int newVal) {
        return (newVal - oldVal) == 1;
    }

    //@ ensures incrementedByOne(\old(x), x);
    void m1() {
        x++;
    }
}