package kz.kolesateam.confapp.allevents.data

import kz.kolesateam.confapp.events.data.models.BranchApiData


class BranchIdMemoryDataSource: BranchIdDataSource {

    private lateinit var branchId: BranchApiData

    override fun getBranchId(): BranchApiData =branchId

    override fun setBranchId(branchId: BranchApiData) {
        this.branchId=branchId
    }
}