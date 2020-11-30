package internship.demux.project.ui.adapter

import android.app.Activity
import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import internship.demux.project.R
import java.util.*

class FilterListAdapter(
        private val context: Activity,
        private val filterList: List<Pair<String, List<String>>>,
        private val filtersSelected: HashMap<String, HashSet<String>>,
        private val mListener: OnCheckBoxClickListener
) : RecyclerView.Adapter<FilterListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_filter_category, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition;
        holder.bindView(context, filterList[pos], filtersSelected)
        for (i in filterList[pos].second.indices) {
            val view = (holder.filterOptionsGrp.getChildAt(i) as AppCompatCheckBox)
            view.setOnClickListener {
                if (view.isChecked) {
                    mListener.onCheckClick(filterList[pos].first, filterList[pos].second[i])
                } else {
                    mListener.onUncheckClick(filterList[pos].first, filterList[pos].second[i])
                }
            }
        }
    }

    override fun getItemCount() = filterList.size

    class ViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        private var filterCategory: ViewGroup = itemView.findViewById(R.id.filter_category)
        private var filterHeading: TextView = itemView.findViewById(R.id.filter_heading)
        var filterOptionsGrp: LinearLayout = itemView.findViewById(R.id.filter_options_grp)
        private var arrow: AppCompatImageButton = itemView.findViewById(R.id.bt_arrow)

        fun bindView(context: Context, filter: Pair<String, List<String>>, filtersSelected: HashMap<String, HashSet<String>>) {
            addFilterOptions(context, filter, filtersSelected)
            arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
            arrow.setOnClickListener {
                if (filterOptionsGrp.visibility == View.VISIBLE) {
                    filterOptionsGrp.visibility = View.GONE
                    arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
                } else {
                    TransitionManager.beginDelayedTransition(filterCategory, AutoTransition())
                    filterOptionsGrp.visibility = View.VISIBLE
                    arrow.setImageResource(R.drawable.ic_baseline_expand_less_24)
                }
            }
        }

        private fun addFilterOptions(context: Context, filter: Pair<String, List<String>>, filtersSelected: HashMap<String, HashSet<String>>) {
            filterHeading.text = filter.first.capitalize(Locale.ROOT)
            for (option in filter.second) {
                filterOptionsGrp.addView(AppCompatCheckBox(context).apply {
                    text = option.capitalize(Locale.ROOT)
                    isChecked = filtersSelected[filter.first]?.contains(option) ?: false
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                })
            }
        }
    }

    interface OnCheckBoxClickListener {
        fun onCheckClick(key: String, value: String)
        fun onUncheckClick(key: String, value: String)
    }
}