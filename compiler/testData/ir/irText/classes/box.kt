open class Box<T>(t: T) {
    var value = t
}

class DerivedBox : Box<Int>(1)

fun box(): String {
    val box: Box<Int> = Box<Int>(1)
    return if (box.value == 1) "OK" else "fail"
}

fun derivedBox(): String {
    val box: DerivedBox = DerivedBox()
    return if (box.value == 1) "OK" else "fail"
}
