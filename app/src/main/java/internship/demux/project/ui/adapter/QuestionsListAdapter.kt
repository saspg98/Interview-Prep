package internship.demux.project.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import internship.demux.project.R
import internship.demux.project.model.Question
import internship.demux.project.util.GlobalUtils
import java.util.*

class QuestionsListAdapter(
    private val context: Activity,
    private val quesList: List<Question>,
    private val mListener: OnItemClickListener
) :
    RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_ques, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition;
        holder.bindView(quesList[pos])
        holder.itemView.setOnClickListener { mListener.onItemClick(quesList[pos]) }
    }

    override fun getItemCount() = quesList.size

    class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var title: TextView = itemView.findViewById(R.id.ques_heading)
        private var trending: ImageView = itemView.findViewById(R.id.trending)
        private var difficulty: ImageView = itemView.findViewById(R.id.ques_diff)

        fun bindView(ques: Question) {
            ques.let {
                title.text = GlobalUtils.format(it.title).capitalize(Locale.ROOT)
                if (it.trending) {
                    trending.visibility = View.VISIBLE
                } else {
                    trending.visibility = View.GONE
                }
                getDrawable(it.difficulty)?.let { x ->
                    difficulty.setImageResource(x)
                }
            }
        }

        private fun getDrawable(quesDiff: String): Int? =
            when (GlobalUtils.format(quesDiff)) {
                "easy" -> R.drawable.tag_easy
                "medium" -> R.drawable.tag_medium
                "hard" -> R.drawable.tag_difficult
                else -> null
            }
    }

    interface OnItemClickListener {
        fun onItemClick(question: Question)
    }
}