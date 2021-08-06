package hr.algebra.hacked.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.hacked.HACKED_PROVIDER_CONTENT_URI
import hr.algebra.hacked.HackedAppReceiver
import hr.algebra.hacked.downloadImageAndStore
import hr.algebra.hacked.framework.sendBroadcast
import hr.algebra.hacked.model.ItemModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val HTTPS_PREFIX = "https://www."

class HackedFetcher(private val context: Context) {

    private var hackedApi: HackedApi


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        hackedApi = retrofit.create(HackedApi::class.java)
    }

    fun fetchItems() {

        val request = hackedApi.fetchItems()
        request.enqueue(object : Callback<List<HackedItem>> {
            override fun onResponse(
                call: Call<List<HackedItem>>,
                response: Response<List<HackedItem>>
            ) {
                if (response.body() != null) {
                    populateItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<HackedItem>>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }
        })


    }

    private fun populateItems(hackedItems: List<HackedItem>) {

        GlobalScope.launch {

            hackedItems.forEach {

                val values= ContentValues().apply {
                    put(ItemModel::title.name,it.title)
                    put(ItemModel::domain.name,if(it.domain.isBlank()) it.domain else HTTPS_PREFIX+it.domain)
                    put(ItemModel::pwnCount.name,it.pwnCount)
                    put(ItemModel::breachDate.name,it.breachDate)
                    put(ItemModel::description.name,Jsoup.parse(it.description).text())
                    put(ItemModel::isVerified.name,it.isVerified)
                    put(ItemModel::isSensitive.name,it.isSensitive)
                    put(ItemModel::isRetired.name,it.isRetired)
                    put(ItemModel::isSpamList.name,it.isSpamList)
                    put(ItemModel::isSaved.name,false)


                }
                context.contentResolver.insert(HACKED_PROVIDER_CONTENT_URI,values)
            }
            context.sendBroadcast<HackedAppReceiver>()
        }

    }


}