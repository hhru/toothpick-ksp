/*
 * Copyright 2022 Baptiste Candellier
 * Copyright 2019 Stephane Nicolas
 * Copyright 2019 Daniel Molinero Reguera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package toothpick.compiler.factory.targets

import com.google.devtools.ksp.symbol.KSClassDeclaration
import toothpick.compiler.common.generators.targets.ParamInjectionTarget

/** Basically all information to create an object / call a constructor of a class.  */
data class ConstructorInjectionTarget(
    val builtClass: KSClassDeclaration,
    val scopeName: String?,
    val hasSingletonAnnotation: Boolean,
    val hasReleasableAnnotation: Boolean,
    val hasProvidesSingletonAnnotation: Boolean,
    val hasProvidesReleasableAnnotation: Boolean,
    /** true if the class has @Injected members  */
    val superClassThatNeedsMemberInjection: KSClassDeclaration?,
    val parameters: List<ParamInjectionTarget> = emptyList()
)
