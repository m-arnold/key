public class Calculations {
    //@ invariant  x > 1 && y > 1;
    //@ invariant add(x,y) == add(y,x);
    //@ invariant mul(x,y) == mul(y,x);
    //@ invariant mul(x,y) > div(x,y);
    //@ invariant add(x,y) > sub(x,y);
    int x,y,z;

    int add(int a, int b) { return a+b; }
    int sub(int a, int b) { return a-b; }
    int div(int a, int b) { return a/b; }
    int mul(int a, int b) { return a*b; }

    //@ ensures true;
    void peformHeapMods() { z++; }
}