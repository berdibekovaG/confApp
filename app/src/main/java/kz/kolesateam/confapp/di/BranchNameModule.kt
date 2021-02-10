package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.allevents.data.BranchIdDataSource
import kz.kolesateam.confapp.allevents.data.BranchIdMemoryDataSource
import org.koin.dsl.module

val branchNameModule: org.koin.core.module.Module = module {
    single {
        BranchIdMemoryDataSource() as BranchIdDataSource
    }
}