public class A {

    private int x,y,z;

    private A a1;
    public A a2;

    // OPAL-based assignable: a.*
    // -> Needs proof when intersect!
    //_____________________________
    /*@ normal_behavior
      @ assignable this.a1;
      @*/
    public void externalPure() {
        if (a1 != null) {
            this.a1 = new A();
        }
    }

    // OPAL-based assignable: a.*
    // -> No Proof when intersect!
    //_____________________________
    /*@ normal_behavior
      @ ensures true;
      @*/
    public void externalPure2() {
        if (a1 != null) {
            this.a1 = new A();
        }
    }

    // OPAL-based assignable: this.*, a.*
    // -> Needs proof when intersect!
    //_____________________________
    /*@ normal_behavior
      @ assignable a.x, this.y;
      @*/
    public void contextuallyPure(A a) {
        if (a != null) {
            a.x = 42;
        }
        this.y = 73;
    }

    // OPAL-based assignable: this.*, a.*
    // -> No Proof when intersect.
    //_____________________________
    // assignable \everything;

    /*@ normal_behavior
      @ ensures true;
      @*/
    public void contextuallyPure2(A a) {
        if (a != null) {
            a.x = 42;
        }
        this.y = 73;
    }

    // OPAL-based assignable: \nothing;
    // -> No Proof when intersect.
    //_____________________________

    /*@ normal_behavior
      @ assignable \nothing;
      @*/
    public int sideeffectFree() {
        return this.x;
    }

    // OPAL Clause: \everything;
    // -> Needs proof when intersect!
    //_____________________________

    /*@ normal_behavior
      @ assignable this.a2;
      @*/
    public void impure() {
        this.a2.x = 42;
    }
}