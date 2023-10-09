package com.example.task3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.task3.Friend
import com.example.task3.R

class AddFriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val birthDateEditText: EditText = findViewById(R.id.birthDateEditText)
        val saveButton: Button = findViewById(R.id.saveButton)

        // Check if this is an edit action
        val isEditing = intent.hasExtra("friend")
        val friendToEdit: Friend? = intent.getSerializableExtra("friend") as Friend?
        val position = intent.getIntExtra("position", -1)

        if (isEditing && friendToEdit != null) {
            nameEditText.setText(friendToEdit.name)
            birthDateEditText.setText(friendToEdit.birthDate)
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val birthDate = birthDateEditText.text.toString()
            val newFriend = Friend(name, birthDate)

            val returnIntent = Intent()
            returnIntent.putExtra("newFriend", newFriend)

            if (isEditing) {
                returnIntent.putExtra("position", position)
            }

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

}
