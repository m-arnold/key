public class MyList {

    public final int maxSize;
    public int size;

    public Node head;
    public Node tail;


    //@ ensures \result.size <= \result.maxSize;
    public MyList doListModifications() {
        MyList l = new MyList(10);
        l.append(new Node(1));
        l.append(new Node(2));
        l.append(new Node(3));
        l.append(new Node(4));
        // l.append(new Node(5));
        // l.append(new Node(6));
        // l.append(new Node(7));
        // l.append(new Node(8));
        // l.append(new Node(9));
        // l.append(new Node(10));
        return l;
    }


    public MyList(int maxSize) {
        this.maxSize = maxSize;
        size = 0;
    }

    public final void append(Node n) {
        if (size == maxSize) {
            return;
        }
        if (head == null) {
            tail = head = n;
        } else {
            tail.succ = n;
        }
        size++;
    }

}


final class Node {

    public int data;
    public Node succ;

    public Node(int data) {
        this.data = data;
    }
}