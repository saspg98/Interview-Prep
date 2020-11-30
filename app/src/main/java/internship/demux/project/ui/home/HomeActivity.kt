package internship.demux.project.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import internship.demux.project.R
import internship.demux.project.util.GlobalUtils

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar))

        addOpenFiltersFragRequestObserver()
        addOpenQuesDescFragRequestObserver()

        GlobalUtils.openFragment(R.id.fragment_container, QuestionsListFragment.instance(), supportFragmentManager)
    }

    private fun addOpenQuesDescFragRequestObserver() {
        viewModel.openQuesDescFrag.observe(this, {
            GlobalUtils.openFragmentWithStack(R.id.fragment_container, QuestionDescFragment.instance(), supportFragmentManager)
        })
    }

    private fun addOpenFiltersFragRequestObserver() {
        viewModel.openFiltersFrag.observe(this, {
            GlobalUtils.openFragmentWithStack(R.id.fragment_container, FilterListFragment.instance(), supportFragmentManager)
        })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }
}