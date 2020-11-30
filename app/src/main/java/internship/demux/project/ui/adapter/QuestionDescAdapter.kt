package internship.demux.project.ui.adapter

import android.app.Activity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import internship.demux.project.R
import internship.demux.project.util.GlobalUtils
import java.util.*

class QuestionDescAdapter(
    private val context: Activity,
    private val quesSubPartsList: List<Pair<String, String>>
) :
    RecyclerView.Adapter<QuestionDescAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_ques_sp, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(quesSubPartsList[position], position)
    }

    override fun getItemCount() = quesSubPartsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var spView: ViewGroup = itemView.findViewById(R.id.ques_sp)
        private var spTitle: TextView = itemView.findViewById(R.id.title)
        private var spValue: TextView = itemView.findViewById(R.id.value)
        private var arrow: AppCompatImageButton = itemView.findViewById(R.id.bt_arrow)

        fun bindView(pair: Pair<String, String>, position: Int) {
            spTitle.text = GlobalUtils.format(pair.first).capitalize(Locale.ROOT)
            if (position == 2) {
                spValue.isClickable = true
                spValue.movementMethod = LinkMovementMethod.getInstance()
                spValue.text = Html.fromHtml(
                    "<a href='${pair.second}'>${pair.second}</a>",
                    Html.FROM_HTML_MODE_COMPACT
                )
            } else {
                spValue.text = pair.second
            }
            spValue.visibility = View.GONE
            arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
            arrow.setOnClickListener {
                if (spValue.visibility == View.VISIBLE) {
                    spValue.visibility = View.GONE
                    arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
                } else {
                    TransitionManager.beginDelayedTransition(spView, AutoTransition())
                    spValue.visibility = View.VISIBLE
                    arrow.setImageResource(R.drawable.ic_baseline_expand_less_24)
                }
            }
        }
    }


}