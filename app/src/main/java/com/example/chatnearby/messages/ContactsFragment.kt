package com.example.chatnearby.messages


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chatnearby.R
import com.example.chatnearby.models.User
import com.example.chatnearby.views.BigImageDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import java.util.HashSet

class ContactsFragment : Fragment() {

    companion object {
        const val USER_KEY = "USER_KEY"
        private val TAG = ContactsFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_new_message, container, false)
    }

    override fun onStart() {
        super.onStart()
        swiperefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))

        //supportActionBar?.title = "Select User"

        fetchUsers()
        swiperefresh.setOnRefreshListener {
            fetchUsers()
        }
    }

    private fun fetchUsers() {
        swiperefresh.isRefreshing = true
        var uid  = FirebaseAuth.getInstance().uid
        var friend = HashSet<String>()
        val ref2 = FirebaseDatabase.getInstance().getReference("/users/$uid/contacts")
        ref2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    var friId = it.getValue()
                    friend.add(friId.toString())
                    Log.d(TAG, friId.toString())
                    // print(it.toString())
                }
            }
        })
        //var test = friend
        val ref = FirebaseDatabase.getInstance().getReference("/users")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                dataSnapshot.children.forEach {
                    // Log.d(TAG, it.toString())
                    @Suppress("NestedLambdaShadowedImplicitParameter")
                    it.getValue(User::class.java)?.let {
                        // var id  = FirebaseAuth.getInstance().uid
                        // var curId = it.toString()
                        if (it.uid != uid && friend.contains(it.uid)) {
                            adapter.add(UserItem(it, requireContext()))
                        }
                    }
                }

                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)
                }

                recyclerview_newmessage.adapter = adapter
                swiperefresh.isRefreshing = false
            }

        })
    }
}


class UserItem(val user: User, val context: Context) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_textview_new_message.text = user.name

        if (!user.profileImageUrl!!.isEmpty()) {
            val requestOptions = RequestOptions().placeholder(R.drawable.no_image2)


            Glide.with(viewHolder.itemView.imageview_new_message.context)
                .load(user.profileImageUrl)
                .apply(requestOptions)
                .into(viewHolder.itemView.imageview_new_message)

            viewHolder.itemView.user_id.text = user.uid

            viewHolder.itemView.imageview_new_message.setOnClickListener {
                BigImageDialog.newInstance(user?.profileImageUrl!!).show((context as Activity).fragmentManager
                    , "")
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}
