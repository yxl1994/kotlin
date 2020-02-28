abstract class ConstantValue<out T>(open val value: T)

data class ClassLiteralValue(val classId: ClassId, val arrayNestedness: Int)

class ClassId
class KotlinType

class KClassValue(value: Value) : ConstantValue<KClassValue.Value>(value) {
    sealed class Value {
        data class NormalClass(val value: ClassLiteralValue) : Value() {
            val classId: ClassId get() = value.classId
            val arrayDimensions: Int get() = value.arrayNestedness
        }

        data class LocalClass(val type: KotlinType) : Value()
    }

    constructor(value: ClassLiteralValue) : this(Value.NormalClass(value))

    constructor(classId: ClassId, arrayDimensions: Int) : this(ClassLiteralValue(classId, arrayDimensions))

    fun getArgumentType(): KotlinType {
        when (value) {
            is Value.LocalClass -> return value.<!UNRESOLVED_REFERENCE!>type<!>
            is Value.NormalClass -> {
                val (<!UNRESOLVED_REFERENCE!>classId<!>, <!UNRESOLVED_REFERENCE!>arrayDimensions<!>) = value.<!UNRESOLVED_REFERENCE!>value<!>
            }
        }
    }
}
