// MODULE: m1
// FILE: AnotherEnum.kt

enum class AnotherEnum {
    SIMPLE,
    COMPLEX {
        override fun baz() {}
    };

    open fun baz() {}
}

// MODULE: m2(m1)
// FILE: JavaEnum.java

public enum JavaEnum {
    FIRST, SECOND;

    public void bar() {}
}

// FILE: test.kt

enum class SomeEnum {
    SIMPLE,
    COMPLEX {
        override fun foo() {}
    };

    open fun foo() {}
}

fun test() {
    SomeEnum.SIMPLE.foo()
    SomeEnum.COMPLEX.foo()

    JavaEnum.FIRST.bar()
    JavaEnum.SECOND.bar()

    AnotherEnum.SIMPLE.baz()
    AnotherEnum.COMPLEX.baz()
}