package mchou.apps.chat.ui.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mchou.apps.chat.db.Dao
import mchou.apps.chat.db.User

class PeopleViewModel : ViewModel() {
    private val TAG = "tests"
    var users = Dao().getItems("users")


/*    private var _users = MutableLiveData<List<User>?>()
    *//*private val _text = MutableLiveData<String>().apply {
        value = "People Fragment"
    }
    val text: LiveData<String> = _text*//*
    var users : LiveData<List<User>?> = _users
    init {
        populateList()
    }
    fun populateList() {
        *//*var list = ArrayList<User>()
        val user = User("Mehdi Chou. ${System.currentTimeMillis()}","mchou120@gmail.com")
        list.add(user)
        list.add(user)
        list.add(user)
        list.add(user)
        list.add(user)
        list.add(user)*//*

        var dao = Dao()
        _users = dao.getArticles()

        //_users.apply { value =  dao.getArticles() }
    }*/
}