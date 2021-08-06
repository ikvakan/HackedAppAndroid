package hr.algebra.hacked.model



  class ItemModel(
    var _id: Long? ,
    var title: String,
    var domain : String,
    var pwnCount: Int,
    var breachDate :String,
    var description :String,
    var isVerified :Boolean,
    var isSensitive :Boolean,
    var isRetired :Boolean,
    var isSpamList :Boolean,
    var isSaved : Boolean

)

