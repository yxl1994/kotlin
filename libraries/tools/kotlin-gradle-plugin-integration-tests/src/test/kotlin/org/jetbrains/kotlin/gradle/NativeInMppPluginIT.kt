/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle

import org.junit.Test

class NativeInMppPluginIT: BaseGradleIT() {

    @Test
    fun testProjectsWithTheSameName() = with(transformProjectWithPluginsDsl("new-mpp-klibs-with-same-name")) {
        // KT-36721.
        build("assemble") {
            assertSuccessful()
            assertTasksExecuted(
                ":foo:foo:compileKotlinJs",
                ":foo:foo:compileKotlinLinux",
                ":foo:compileKotlinJs",
                ":foo:compileKotlinLinux",
                ":compileKotlinJs",
                ":compileKotlinLinux"
            )
        }
    }

    // TODO: Move native specific tests from NewMultiplatformIT here.
}
