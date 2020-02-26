/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.types

interface TypeWithAlternative : TypeWithEnhancement {
    override fun wrapEnhancementIn(type: UnwrappedType, enhancement: KotlinType?): UnwrappedType {
        if (enhancement == null) return type
        return type.addAlternative(enhancement)
    }
}

class SimpleTypeWithAlternative(
    mainType: SimpleType,
    alternative: KotlinType
) : SimpleTypeWithEnhancement(mainType, alternative), TypeWithAlternative {
    override fun createSimpleTypeWithEnhancement(delegate: SimpleType, enhancement: KotlinType): SimpleTypeWithAlternative =
        SimpleTypeWithAlternative(delegate, enhancement)
}

class FlexibleTypeWithAlternative(
    mainType: FlexibleType,
    alternative: KotlinType
) : FlexibleTypeWithEnhancement(mainType, alternative), TypeWithAlternative {
    override val lowerBound: SimpleTypeWithAlternative
        get() = wrapEnhancementIn(super.lowerBound, enhancement.lowerIfFlexible()) as SimpleTypeWithAlternative
    override val upperBound: SimpleTypeWithAlternative
        get() = wrapEnhancementIn(super.upperBound, enhancement.upperIfFlexible()) as SimpleTypeWithAlternative

    override fun createFlexibleTypeWithEnhancement(origin: FlexibleType, enhancement: KotlinType): FlexibleTypeWithAlternative =
        FlexibleTypeWithAlternative(origin, enhancement)
}

fun UnwrappedType.addAlternative(alternativeType: KotlinType): UnwrappedType {
    return when (this) {
        is SimpleType -> SimpleTypeWithAlternative(this, alternativeType)
        is FlexibleType -> FlexibleTypeWithAlternative(this, alternativeType)
    }
}

fun KotlinType.getAlternative(): KotlinType? {
    return when (this) {
        is TypeWithAlternative -> this.enhancement
        else -> null
    }
}
