/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.types

interface TypeWithJavaEnhancement : TypeWithEnhancement {
    override fun wrapEnhancementIn(type: UnwrappedType, enhancement: KotlinType?): UnwrappedType {
        return type.wrapEnhancement(enhancement)
    }
}

class SimpleTypeWithJavaEnhancement(
    delegate: SimpleType,
    enhancement: KotlinType
) : SimpleTypeWithEnhancement(delegate, enhancement), TypeWithJavaEnhancement {
    override fun createSimpleTypeWithEnhancement(delegate: SimpleType, enhancement: KotlinType): SimpleTypeWithJavaEnhancement =
        SimpleTypeWithJavaEnhancement(delegate, enhancement)
}

class FlexibleTypeWithJavaEnhancement(
    origin: FlexibleType,
    enhancement: KotlinType
) : FlexibleTypeWithEnhancement(origin, enhancement), TypeWithJavaEnhancement {
    override fun createFlexibleTypeWithEnhancement(origin: FlexibleType, enhancement: KotlinType): FlexibleTypeWithJavaEnhancement =
        FlexibleTypeWithJavaEnhancement(origin, enhancement)
}

fun UnwrappedType.wrapEnhancement(enhancement: KotlinType?): UnwrappedType {
    if (enhancement == null) {
        return this
    }

    return when (this) {
        is SimpleType -> SimpleTypeWithJavaEnhancement(this, enhancement)
        is FlexibleType -> FlexibleTypeWithJavaEnhancement(this, enhancement)
    }
}

fun KotlinType.getEnhancement(): KotlinType? = when (this) {
    is TypeWithJavaEnhancement -> enhancement
    else -> null
}

fun KotlinType.unwrapEnhancement(): KotlinType = getEnhancement() ?: this
