/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.types

import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.renderer.DescriptorRenderer
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
import org.jetbrains.kotlin.types.refinement.TypeRefinement

interface AlternativePublicType

class SimpleAlternativePublicType(
    override val delegate: SimpleType
) : DelegatingSimpleType(), AlternativePublicType {
    @TypeRefinement
    override fun replaceDelegate(delegate: SimpleType): SimpleAlternativePublicType = SimpleAlternativePublicType(delegate)

    override fun replaceAnnotations(newAnnotations: Annotations): SimpleAlternativePublicType =
        SimpleAlternativePublicType(delegate.replaceAnnotations(newAnnotations))

    override fun makeNullableAsSpecified(newNullability: Boolean): SimpleAlternativePublicType =
        SimpleAlternativePublicType(delegate.makeNullableAsSpecified(newNullability))
}

class FlexibleAlternativePublicType(
    val origin: FlexibleType
) : FlexibleType(origin.lowerBound, origin.upperBound), AlternativePublicType {
    override val delegate: SimpleType
        get() = origin.delegate

    override fun render(renderer: DescriptorRenderer, options: DescriptorRendererOptions): String {
        return origin.render(renderer, options)
    }

    @TypeRefinement
    override fun refine(kotlinTypeRefiner: KotlinTypeRefiner): FlexibleType {
        return FlexibleAlternativePublicType(origin.refine(kotlinTypeRefiner))
    }

    override fun replaceAnnotations(newAnnotations: Annotations): UnwrappedType =
        updateByOrigin { origin.replaceAnnotations(newAnnotations) }

    override fun makeNullableAsSpecified(newNullability: Boolean): UnwrappedType =
        updateByOrigin { origin.makeNullableAsSpecified(newNullability) }

    private fun updateByOrigin(updater: (UnwrappedType) -> UnwrappedType): UnwrappedType {
        return when (val updatedOrigin = updater(origin)) {
            is SimpleType -> SimpleAlternativePublicType(updatedOrigin)
            is FlexibleType -> FlexibleAlternativePublicType(updatedOrigin)
        }
    }
}

fun UnwrappedType.asAlternativeType(): UnwrappedType {
    return when (this) {
        is SimpleType -> SimpleAlternativePublicType(this)
        is FlexibleType -> FlexibleAlternativePublicType(this)
    }
}
