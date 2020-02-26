// !LANGUAGE: +NewInference
// FILE: A.java

import javax.annotation.*;

public class A {
    @Nonnull
    public static <S extends Bound1> S intersect(S first, S second) {
        return first;
    }
}

// FILE: main.kt

interface Bound1
interface Bound2

object First : Bound1, Bound2
object Second : Bound1, Bound2

fun foo() = A.intersect(First, Second)
val Any.nonNullProp: String get() = ""

fun main() {
    <!DEBUG_INFO_EXPRESSION_TYPE("Bound1")!>foo()<!>.nonNullProp
}
