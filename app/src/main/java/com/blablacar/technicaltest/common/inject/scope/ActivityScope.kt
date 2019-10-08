package com.blablacar.technicaltest.common.inject.scope

import javax.inject.Scope

/**
 * Use to tie dependencies on Activity lifetime.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope