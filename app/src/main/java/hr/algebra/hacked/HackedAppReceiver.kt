package hr.algebra.hacked

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.hacked.framework.setBooleanPreference
import hr.algebra.hacked.framework.startActivity

class HackedAppReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED,true)

        context.startActivity<HostActivity>()
    }
}