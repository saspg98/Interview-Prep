package internship.demux.project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import internship.demux.project.ui.home.HomeActivity
import internship.demux.project.util.GlobalUtils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            GlobalUtils.startActivityAsNewStack(Intent(this, HomeActivity::class.java), this)
            finish()
        }, 1000)
    }
}