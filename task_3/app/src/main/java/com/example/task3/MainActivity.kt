package com.example.task3

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var friendsList: ArrayList<Friend>
    private lateinit var arrayAdapter: ArrayAdapter<*>
    private val RequestCodeAdd = 1
    private val RequestCodeEdit = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize list and adapter
        friendsList = arrayListOf(Friend("John", "01/01/2000"), Friend("Jane", "02/02/2000"))

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, friendsList)

        // Set the adapter to ListView
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = arrayAdapter


        val fab: FloatingActionButton = findViewById(R.id.addFriendButton)
        fab.setOnClickListener {
            val intent = Intent(this, AddFriendActivity::class.java)
            startActivityForResult(intent, RequestCodeAdd)

        }
        // Add an item click listener
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, AddFriendActivity::class.java)
            intent.putExtra("friend", friendsList[position])
            intent.putExtra("position", position)
            startActivityForResult(intent, RequestCodeEdit)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestCodeAdd && resultCode == Activity.RESULT_OK) {
            // Get the new friend's data from the returned Intent's extras
            val newFriend = data?.getSerializableExtra("newFriend")
            // Check if the newFriend string isn't null
            if (newFriend != null) {
                // Add the new friend to the list
                friendsList.add(newFriend as Friend)

                // Notify the adapter that the data set has changed, so the UI updates
                arrayAdapter.notifyDataSetChanged()
            }
            }else if(requestCode == RequestCodeEdit && resultCode == Activity.RESULT_OK){
                val friend = data?.getSerializableExtra("newFriend")
                val position = data?.getIntExtra("position", -1)
                if (friend != null && position != null) {
                    friendsList[position] = friend as Friend
                    arrayAdapter.notifyDataSetChanged()
                }
        }
    }

   /* private fun showEditDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        input.setText(friendsList[position])
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setTitle("Edit Friend")
            .setMessage("Update name and birthday:")
            .setPositiveButton("Update") { _, _ ->
                val friendInfo = input.text.toString()
                friendsList[position] = friendInfo
                arrayAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        builder.show()
    }*/
}
