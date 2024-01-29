public class A {

    public B b;
    public C c;


    /*@ public normal_behavior
      @ ensures a.b.c.x == a.b.x;
      @
    */
    public void foo(A a){
        a.c = new C();
        a.b = new B(a.c);
    }
}

class B {

    public final int x;
    public final int y;
    public final int z;
    public C c;

    public B(C c) {
        x = 1;
        y = 2;
        z = 3;
        this.c = c;
    }
}

class C {

    public final int x;
    public final int y;
    public final int z;
    public C() {
        x = 1;
        y = 2;
        z = 3;
    }
}