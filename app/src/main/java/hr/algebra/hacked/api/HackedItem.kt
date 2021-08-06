package hr.algebra.hacked.api

import com.google.gson.annotations.SerializedName

class HackedItem (

    @SerializedName("Name") val name : String,
    @SerializedName("Title") val title : String,
    @SerializedName("Domain") val domain : String,
    @SerializedName("BreachDate") val breachDate : String,
    @SerializedName("PwnCount") val pwnCount : Int,
    @SerializedName("Description") val description : String,
    @SerializedName("LogoPath") val logoPath : String,
    @SerializedName("IsVerified") val isVerified : Boolean,
    @SerializedName("IsSensitive") val isSensitive : Boolean,
    @SerializedName("IsRetired") val isRetired : Boolean,
    @SerializedName("IsSpamList") val isSpamList : Boolean
)