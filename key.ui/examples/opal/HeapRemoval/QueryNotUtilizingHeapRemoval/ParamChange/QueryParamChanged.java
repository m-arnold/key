public class QueryParamChanged {

    //@ invariant add(x,y) == add(y,x);
    int x,y;

    int add(int a, int b) {
        return a+b;
    }

    //@ ensures true;
    void perfomHeapMods() {
        x++;
    }
}