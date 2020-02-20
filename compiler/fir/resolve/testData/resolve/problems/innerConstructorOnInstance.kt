// FILE: BaseOuter.java

public class BaseOuter {
    public class BaseInner {}
}

// FILE: JavaOuter.java

public class JavaOuter extends BaseOuter {
    public class JavaInner {}
}

// FILE: test.kt

class Outer : JavaOuter() {
    inner class Inner
}

fun test(o: Outer, jo: JavaOuter) {
    val inner = o.Inner()
    val javaInner = jo.JavaInner()
    val javaViaKotlinInner = o.JavaInner()

    val otherInner = o.BaseInner()
    val anotherInner = jo.BaseInner()
}