package internship.demux.project.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import internship.demux.project.R
import internship.demux.project.ui.adapter.QuestionDescAdapter

class QuestionDescFragment : Fragment() {

    private val viewModel: HomeActivityViewModel by activityViewModels()
    private lateinit var mContext: Activity
    private lateinit var recyclerView: RecyclerView
    private lateinit var quesDescAdapter: QuestionDescAdapter
    private lateinit var title: TextView
    private lateinit var difficulty: TextView
    private lateinit var trending: ImageView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ques_desc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = requireActivity()
        title = view.findViewById(R.id.ques_heading)
        difficulty = view.findViewById(R.id.ques_diff)
        trending = view.findViewById(R.id.trending)
        recyclerView = view.findViewById(R.id.rv_ques_desc)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        addOpenQuesRequestObserver()
    }

    private fun addOpenQuesRequestObserver() {
        viewModel.openQues.observe(viewLifecycleOwner, { response ->

            title.text = response[0] as String
            difficulty.text = response[1] as String
            if (response[2] as Boolean) {
                trending.visibility = View.VISIBLE
            } else {
                trending.visibility = View.GONE
            }
            quesDescAdapter = QuestionDescAdapter(mContext, response[3] as List<Pair<String, String>>)
            recyclerView.adapter = quesDescAdapter
        })
    }

    companion object {
        fun instance() = QuestionDescFragment()
    }
}