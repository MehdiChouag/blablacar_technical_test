package com.blablacar.technicaltest.common.inject.scope

import javax.inject.Scope

/**
 * Use to tie dependencies on Fragment lifetime.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope