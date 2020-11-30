package internship.demux.project.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import internship.demux.project.R
import internship.demux.project.model.Question
import internship.demux.project.ui.adapter.QuestionsListAdapter

class QuestionsListFragment : Fragment(), QuestionsListAdapter.OnItemClickListener {

    private val viewModel: HomeActivityViewModel by activityViewModels()
    private lateinit var mContext: Activity
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: QuestionsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ques_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = requireActivity()
        recyclerView = view.findViewById(R.id.rv_ques_list)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        view.findViewById<FloatingActionButton>(R.id.bt_filter).apply {
            setOnClickListener {
                viewModel.openFilters(true)
            }
        }
        addQuesListObserver()
        addFilteredQuesListObserver()
        viewModel.fetchQues()
    }

    private fun addFilteredQuesListObserver() {
        viewModel.filteredQues.observe(viewLifecycleOwner, { response ->
            if (response.isNullOrEmpty()) {
                Toast.makeText(context, getString(R.string.question_empty), Toast.LENGTH_LONG)
                    .show()
            }
            listAdapter = QuestionsListAdapter(mContext, response, this)
            recyclerView.adapter = listAdapter
        })
    }

    private fun addQuesListObserver() {
        viewModel.originalQuesList.observe(viewLifecycleOwner, { response ->
            if (response.isNotEmpty()) {
                listAdapter = QuestionsListAdapter(mContext, response, this)
                recyclerView.adapter = listAdapter
            } else {
                Toast.makeText(context, getString(R.string.question_empty), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    companion object {
        fun instance() = QuestionsListFragment()
    }

    override fun onItemClick(question: Question) {
        viewModel.openQuesDesc(true)
        viewModel.openQues(question)
    }
}