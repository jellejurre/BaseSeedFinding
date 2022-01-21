package SeedFinding.util;

public class Triple<A, B, C> {
    A a;
    B b;
    C c;

    public Triple(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A getFirst() {
        return a;
    }

    public void setFirst(A a) {
        this.a = a;
    }

    public B getSecond() {
        return b;
    }

    public void setSecond(B b) {
        this.b = b;
    }

    public C getThird() {
        return c;
    }

    public void setThird(C c) {
        this.c = c;
    }
}
