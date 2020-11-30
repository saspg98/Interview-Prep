package internship.demux.project.util

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import internship.demux.project.app.InterviewPrepApp.Companion.assetManager
import java.util.*

class GlobalUtils {

    companion object {

        /**
         * For starting desired activity as a new task,
         * intent_flags are added for clear back stack.
         * @param context: context of current fragment/activity
         * @param intent: activity class need to start
         *
         * Note: call finish() method after this function execution
         */
        fun startActivityAsNewStack(intent: Intent, context: Context) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }

        /**
         * For starting desired fragment in an activity. Previous fragment is added to back stack
         * @param containerViewId: Id of the Frame Layout in the activity xml
         * @param fragment: Instance of the fragment to be created.
         * @param manager: Fragment Manager to create the requested fragment.
         *
         */
        fun openFragmentWithStack(
            containerViewId: Int,
            fragment: Fragment,
            manager: FragmentManager
        ) {
            val transaction = manager.beginTransaction()
            transaction.replace(containerViewId, fragment, fragment.tag)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        fun openFragment(containerViewId: Int, fragment: Fragment, manager: FragmentManager) {
            val transaction = manager.beginTransaction()
            transaction.replace(containerViewId, fragment, fragment.tag)
            transaction.commit()
        }

        fun format(str: String): String {
            return str.toLowerCase(Locale.ROOT).trim().replace("\\s+".toRegex(), " ")
        }

        fun prepFilters(): List<Pair<String, List<String>>> {
            return listOf(
                Pair(
                    "companies",
                    listOf("amazon", "microsoft", "adobe", "google", "uber", "facebook", "oracle")
                ),
                Pair(
                    "topics",
                    listOf(
                        "binary search",
                        "dynamic programming",
                        "recursion",
                        "backtracking",
                        "depth-first search",
                        "breadth-first search",
                        "Union find",
                        "string",
                        "math",
                        "hash table",
                        "linked list",
                        "array",
                        "heap",
                        "divide and conquer"
                    )
                ),
                Pair("difficulty", listOf("easy", "medium", "hard")),
                Pair("other tags", listOf("trending"))
            )
        }

        fun getJsonDataFromAsset(fileName: String): String? {
            var jsonString: String? = null
            assetManager?.let { manager ->
                jsonString = manager.open(fileName).bufferedReader().use { it.readText() }
            }
            return jsonString
        }
    }
}