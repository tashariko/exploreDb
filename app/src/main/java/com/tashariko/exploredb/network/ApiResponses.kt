package com.tashariko.exploredb.network

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import com.tashariko.exploredb.database.entity.User


data class DealQuotationResponse(
        @SerializedName("success")
        var success: Boolean,

        @SerializedName("updatedStatus")
        var updatedStatus: String

)