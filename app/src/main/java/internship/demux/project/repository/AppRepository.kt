package internship.demux.project.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import internship.demux.project.model.Question
import internship.demux.project.util.GlobalUtils

class AppRepository {
    fun getQuestions(): List<Question>? {
        val jsonFileString = GlobalUtils.getJsonDataFromAsset("leetcode_data.json")
        val listQuestionType = object : TypeToken<List<Question>>() {}.type
        return Gson().fromJson(jsonFileString, listQuestionType)
    }
}