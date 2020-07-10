package mchou.apps.chat.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Dao {
    private val packageName: String = "mchou.apps.chat.db"
    private val tag = "tests"
    private val database : DatabaseReference = Firebase.database.reference

    init {
        Log.i(tag, "database : $database")

        /**
         * Tests
         */
        /*val  userID  = addUser("mc69","mchou120@gmail.com")
        Log.i(TAG, "user ID: $userID")
        val roomID = addRoom("Room#1", userID)
        Log.i(TAG, "room ID: $roomID")
        val msgID1 = addMsg("Hello Firebase!", userID, roomID)
        val msgID2 = addMsg("..I'm chatting!!", userID, roomID)*/
    }

    fun addUser(name: String, email : String) : String{
        val key = database.child("users").push().key ?: return ""
        val user = User( name, email )
        addItem(user, "users", key)
        return key
    }
    fun addMsg(body: String, author : String, room:  String?) : String{
       val key = database.child("msgs").push().key ?: return ""
       val msg = Msg("" , body, author, room, System.currentTimeMillis())
       addItem(msg, "msgs", key)
       return key
    }
    fun addRoom(name: String, author : String): String{
        val key = database.child("rooms").push().key ?: return ""
        val room = Room("", name, author, System.currentTimeMillis())
        addItem(room, "rooms", key)
        return key
    }

    private fun addItem(item: Any, child_name: String, key : String) {
        database.child(child_name).child(key).setValue(item)
            .addOnSuccessListener {
                logit("database ('$child_name'): item created successfully!")
            }
            .addOnFailureListener {
               logit("database ('$child_name'): item create failed!")
            }
    }

    fun read(child_name: String) {
        //var items : MutableList<Any?> = mutableListOf()

        val itemsQuery = database.child(child_name).limitToFirst(10).orderByValue()

        /*itemsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (itemSnapshot in dataSnapshot.children) {
                    var item = itemSnapshot.getValue<Any>()
                    items.add(item)
                }
                _items.apply { value = items }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("tests", "loadPost:onCancelled", databaseError.toException())
            }
        })*/
    }

    fun getItems(path_name: String) : MutableLiveData<List<Any>?> {

        var items: MutableLiveData<List<Any>?> = MutableLiveData()
        var list : MutableList<Any>

        database.child(path_name).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
               logit("error : ${error.message}")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    list = mutableListOf()
                    for (itemSnapshot in snapshot.children) {

                        var item = parseToItem(itemSnapshot, path_name)
                        logit(item.toString())
                        list.add(item!!)
                    }

                    items.postValue(list.toList())
                }
            }


        })

        logit("list items : $items")
        return items
    }
    private fun parseToItem(itemSnapshot: DataSnapshot, path_name : String): Any? {
        var entityName = formattedName(path_name)
        var entityClass : Class<FbEntity> = Class.forName("$packageName.$entityName") as Class<FbEntity>

        logit("classe : ${entityClass.canonicalName}")

        var item = itemSnapshot.getValue(entityClass)
        itemSnapshot.key?.let { item!!.setID(it) }
        return item
    }

    private fun formattedName(path_name: String): String {
        return path_name.substring(0,1).toUpperCase()+path_name.substring(1,path_name.length-1)
    }

    private fun logit(s: String) {
        Log.i(tag, s)
    }

    fun getUser(userID : String): MutableLiveData<User> {
        var user : MutableLiveData<User> = MutableLiveData()

        val userReference =  database.child("users").child(userID)
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user.postValue(dataSnapshot.getValue<User>())
            }
        })
        return user
    }
}