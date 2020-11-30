package internship.demux.project.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import internship.demux.project.R
import internship.demux.project.ui.adapter.FilterListAdapter
import internship.demux.project.util.GlobalUtils

class FilterListFragment : Fragment(), FilterListAdapter.OnCheckBoxClickListener {

    private lateinit var filtersList: List<Pair<String, List<String>>>
    private val viewModel: HomeActivityViewModel by activityViewModels()
    private lateinit var filtersSelected: HashMap<String, HashSet<String>>
    private lateinit var mContext: Activity
    private lateinit var recyclerView: RecyclerView
    private lateinit var filterListAdapter: FilterListAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = requireActivity()
        recyclerView = view.findViewById(R.id.rv_filter_options)
        recyclerView.layoutManager = LinearLayoutManager(mContext)

        addFiltersSelectedRequestObserver()
        viewModel.fetchFiltersSelected()

        view.findViewById<MaterialButton>(R.id.bt_apply_filter).apply {
            setOnClickListener {
                viewModel.addFilters(filtersSelected)
                fragmentManager?.popBackStackImmediate()
            }
        }

        view.findViewById<MaterialButton>(R.id.bt_close_filter).apply {
            setOnClickListener {
                fragmentManager?.popBackStackImmediate()
            }
        }
    }

    private fun addFiltersSelectedRequestObserver() {
        viewModel.filtersSelected.observe(viewLifecycleOwner, { response ->
            filtersSelected = response
            addFilterOptions()
        })
    }

    private fun addFilterOptions() {
        filtersList = GlobalUtils.prepFilters()
        filterListAdapter = FilterListAdapter(mContext, filtersList, filtersSelected, this)
        recyclerView.adapter = filterListAdapter
    }

    companion object {
        fun instance() = FilterListFragment()
    }

    override fun onCheckClick(key: String, value: String) {
        if (filtersSelected.containsKey(key)) {
            filtersSelected[key]?.add(value)
        } else {
            filtersSelected[key] = HashSet()
            filtersSelected[key]?.add(value)
        }
    }

    override fun onUncheckClick(key: String, value: String) {
        filtersSelected[key]?.remove(value)
        if (filtersSelected[key]?.size == 0) {
            filtersSelected.remove(key)
        }
    }
}