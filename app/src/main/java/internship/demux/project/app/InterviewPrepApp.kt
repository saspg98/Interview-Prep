package internship.demux.project.app

import android.app.Application
import android.content.res.AssetManager

class InterviewPrepApp : Application() {

    companion object {
        var assetManager: AssetManager? = null
    }

    override fun onCreate() {
        super.onCreate()

        assetManager = applicationContext.assets
    }
}