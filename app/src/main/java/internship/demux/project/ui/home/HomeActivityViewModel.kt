package internship.demux.project.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import internship.demux.project.model.Question
import internship.demux.project.repository.AppRepository
import internship.demux.project.util.GlobalUtils
import java.util.*
import kotlin.collections.HashMap

class HomeActivityViewModel : ViewModel() {

    private val repository = AppRepository()
    private var lFiltersSelected: HashMap<String, HashSet<String>>? = null

    private var _originalQuesList = MutableLiveData<List<Question>>()
    val originalQuesList: LiveData<List<Question>> get() = _originalQuesList
    fun fetchQues() {
        _originalQuesList.value = repository.getQuestions()
    }

    private val _openQues = MutableLiveData<List<Any>>()
    val openQues: LiveData<List<Any>> get() = _openQues
    fun openQues(question: Question) {
        val parsedQues = ArrayList<Pair<String, String>>().apply {
            add(Pair("related topics", question.topics.joinToString(separator = ",") { " ${it.capitalize(Locale.ROOT)}" }))
            add(Pair("companies", question.companies.joinToString(separator = ",") { " ${it.capitalize(Locale.ROOT)}" }))
            add(Pair("link", question.link))
        }
        _openQues.value = listOf(
                GlobalUtils.format(question.title).capitalize(Locale.ROOT),
                GlobalUtils.format(question.difficulty).capitalize(Locale.ROOT),
                question.trending,
                parsedQues
        )
    }

    private val _openFiltersFrag = MutableLiveData<Boolean>()
    val openFiltersFrag: LiveData<Boolean> get() = _openFiltersFrag
    fun openFilters(bool: Boolean) {
        _openFiltersFrag.value = bool
    }

    private val _openQuesDescFrag = MutableLiveData<Boolean>()
    val openQuesDescFrag: LiveData<Boolean> get() = _openQuesDescFrag
    fun openQuesDesc(bool: Boolean) {
        _openQuesDescFrag.value = bool
    }

    private val _filtersSelected = MutableLiveData<HashMap<String, HashSet<String>>>()
    val filtersSelected: LiveData<HashMap<String, HashSet<String>>> get() = _filtersSelected
    private val _filteredQues = MutableLiveData<List<Question>>()
    val filteredQues: LiveData<List<Question>> get() = _filteredQues
    fun addFilters(filters: HashMap<String, HashSet<String>>?) {
        val allQues = repository.getQuestions()
        if (!filters.isNullOrEmpty()) {
            lFiltersSelected = filters
            _filteredQues.value = allQues?.filter {
                it.topics.any { topic -> filters["topics"]?.contains(GlobalUtils.format(topic)) != false } &&
                        it.companies.any { company -> filters["companies"]?.contains(GlobalUtils.format(company)) != false } &&
                        filters["difficulty"]?.contains(GlobalUtils.format(it.difficulty)) != false &&
                        if (filters["other tags"]?.contains("trending") == true) {
                            it.trending
                        } else {
                            true
                        }
            }
        } else {
            lFiltersSelected = null
            _filteredQues.value = allQues
        }
    }

    fun fetchFiltersSelected() {
        if (lFiltersSelected.isNullOrEmpty()) {
            _filtersSelected.value = HashMap<String, HashSet<String>>()
        } else {
            _filtersSelected.value = lFiltersSelected
        }
    }
}