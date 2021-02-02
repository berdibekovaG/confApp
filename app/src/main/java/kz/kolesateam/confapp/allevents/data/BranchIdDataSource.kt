package kz.kolesateam.confapp.allevents.data

import kz.kolesateam.confapp.events.data.models.BranchApiData

interface BranchIdDataSource {
    fun getBranchId(): BranchApiData

    fun setBranchId(
            branchId: BranchApiData
    )
}