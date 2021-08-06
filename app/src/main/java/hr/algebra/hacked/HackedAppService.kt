package hr.algebra.hacked

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.hacked.api.HackedFetcher
import hr.algebra.hacked.framework.sendBroadcast

private const val JOB_ID = 1

class HackedAppService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
       HackedFetcher(this).fetchItems()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, HackedAppService::class.java, JOB_ID, intent)
        }
    }

}