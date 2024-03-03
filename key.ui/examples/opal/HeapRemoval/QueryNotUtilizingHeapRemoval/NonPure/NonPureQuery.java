public class NonPureQuery {
    //@ invariant nonPureQuery() > x;
    private int x, queryData;

    public int nonPureQuery() { return queryData; }

    //@ ensures true;
    public void m() {
        queryData = 73;
        x = 42;
    }
}