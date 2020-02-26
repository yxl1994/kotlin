/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.types

import org.jetbrains.kotlin.types.typeUtil.contains

fun substituteAlternativesInPublicType(type: KotlinType): UnwrappedType {
    if (!type.contains { it is TypeWithAlternative })
        return type.unwrap()

    return doReplace(type.unwrap())
}

private fun doReplace(type: UnwrappedType): UnwrappedType {
    if (type is ErrorType) return type

    val alternative = type.getAlternative()
    return if (alternative != null) {
        doReplace(alternative.unwrap())
    } else {
        when (val unwrappedType = type.unwrap()) {
            is SimpleType -> unwrappedType.updateArguments()
            is FlexibleType -> KotlinTypeFactory.flexibleType(
                unwrappedType.lowerBound.updateArguments(),
                unwrappedType.upperBound.updateArguments(),
            )
        }
    }
}

private fun SimpleType.updateArguments(): SimpleType {
    return replace(arguments.map { replaceProjection(it) })
}

private fun replaceProjection(projection: TypeProjection): TypeProjection {
    if (projection.isStarProjection) return projection

    return TypeProjectionImpl(projection.projectionKind, doReplace(projection.type.unwrap()))
}
