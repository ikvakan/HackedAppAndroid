package hr.algebra.hacked.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.animation.AnimationUtils
import androidx.preference.PreferenceManager
import com.airbnb.lottie.parser.IntegerParser
import hr.algebra.hacked.HACKED_PROVIDER_CONTENT_URI
import hr.algebra.hacked.model.ItemModel

fun View.applyAnimation(resourceId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, resourceId))

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)  }) // ?????????

inline fun <reified T : Activity> Context.startActivity(key:String,value:Int) =
    startActivity(Intent(this, T::class.java).apply { putExtra(key,value) })

inline fun <reified T : Activity> Context.startActivity(key:String,value:String) =
    startActivity(Intent(this, T::class.java).apply { putExtra(key,value)})

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)

fun Context.isOnline(): Boolean {

    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork

    if (network != null) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities != null) {
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }

    return false
}

fun Context.fetchItems(): MutableList<ItemModel> {
    val items = mutableListOf<ItemModel>()
    val cursor = contentResolver?.query(HACKED_PROVIDER_CONTENT_URI, null, null, null, null)

    if (cursor != null) {

        while (cursor.moveToNext()) {


            items.add(
                ItemModel(
                    cursor.getLong(cursor.getColumnIndex(ItemModel::_id.name)),
                    cursor.getString(cursor.getColumnIndex(ItemModel::title.name)),
                    cursor.getString(cursor.getColumnIndex(ItemModel::domain.name)),
                    cursor.getInt(cursor.getColumnIndex(ItemModel::pwnCount.name)),
                    cursor.getString(cursor.getColumnIndex(ItemModel::breachDate.name)),
                    cursor.getString(cursor.getColumnIndex(ItemModel::description.name)),
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isVerified.name)) == 1,
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isSensitive.name)) == 1,
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isRetired.name)) == 1,
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isSpamList.name)) == 1,
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isSaved.name)) == 1

                )
            )
        }
    }


    return items
}
fun Context.fetchSavedItems(): MutableList<ItemModel> {
    val items = mutableListOf<ItemModel>()

    val cursor = contentResolver?.query(HACKED_PROVIDER_CONTENT_URI, null, "${ItemModel::isSaved.name} = ?", arrayOf("1"), null)

    if (cursor != null) {

        while (cursor.moveToNext()) {


            items.add(
                ItemModel(
                    cursor.getLong(cursor.getColumnIndex(ItemModel::_id.name)),
                    cursor.getString(cursor.getColumnIndex(ItemModel::title.name)),
                    cursor.getString(cursor.getColumnIndex(ItemModel::domain.name)),
                    cursor.getInt(cursor.getColumnIndex(ItemModel::pwnCount.name)),
                    cursor.getString(cursor.getColumnIndex(ItemModel::breachDate.name)),
                    cursor.getString(cursor.getColumnIndex(ItemModel::description.name)),
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isVerified.name)) == 1,
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isSensitive.name)) == 1,
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isRetired.name)) == 1,
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isSpamList.name)) == 1,
                    cursor.getInt(cursor.getColumnIndex(ItemModel::isSaved.name)) == 1

                )
            )
        }
    }


    return items
}