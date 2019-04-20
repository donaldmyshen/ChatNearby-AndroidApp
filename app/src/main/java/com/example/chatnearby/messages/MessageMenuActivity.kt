package com.example.chatnearby.messages


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.chatnearby.models.ChatMessage
import com.example.chatnearby.models.User
import com.example.chatnearby.R
import com.example.chatnearby.account.RegisterActivity
import com.example.chatnearby.nearby.GetLocationActivity
import com.example.chatnearby.views.LatestMessageRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.fragment_latest_messages.*


class MessageMenuActivity : AppCompatActivity() {


    //private val adapter = GroupAdapter<ViewHolder>()

    // private val latestMessagesMap = HashMap<String, ChatMessage>()

    companion object {
        var currentUser: User? = null
        //val TAG = MessageMenuActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_latest_messages)

        // verifyUserIsLoggedIn()
        fetchCurrentUser()


        val pageAdapter = PageAdapter(this, supportFragmentManager)
        viewpager.adapter = pageAdapter
        tabs.setupWithViewPager(viewpager)
    }

    // New page add here
    class PageAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {
        override fun getItem(p0: Int): Fragment {
            return if (p0 == 0) {
                MessageListFragment()
            } else {
                ContactsFragment()
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                context.getString(R.string.message)
            } else {
                context.getString(R.string.contacts)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // buttons on title bar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                overridePendingTransition(R.anim.enter, R.anim.exit)
            }

            R.id.menu_get_loc -> {
                val intent = Intent(this, GetLocationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid ?: return
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentUser = dataSnapshot.getValue(User::class.java)
            }

        })
    }
}
