package hr.algebra.hacked

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import hr.algebra.hacked.framework.applyAnimation
import hr.algebra.hacked.framework.getBooleanPreference
import hr.algebra.hacked.framework.isOnline
import hr.algebra.hacked.framework.startActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

private const val DELAY :Long = 3000
public const val DATA_IMPORTED :String = "hr.algebra.hacked_data_imported"

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startAnimations()
        redirect()
    }

    private fun redirect() {

        if (getBooleanPreference(DATA_IMPORTED)){
            Handler(Looper.getMainLooper()).postDelayed({startActivity<HostActivity>()},DELAY)
        }
        else{
            if (isOnline()){
                Intent(this,HackedAppService::class.java).apply {
                    HackedAppService.enqueueWork(this@SplashScreenActivity,this)
                }

            }else{
                Toast.makeText(this,getString(R.string.internet_conn_required),Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    private fun startAnimations() {
        ivSplash.applyAnimation(R.anim.top_animation)
        tvSplash.applyAnimation(R.anim.bottom_animation)
        lottieProgressBar.applyAnimation(R.anim.delay)
    }


}