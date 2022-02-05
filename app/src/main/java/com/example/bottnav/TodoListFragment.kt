package com.example.bottnav
//**********삭제 필요**********
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment

class TodoListFragment : Fragment(){

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_challenge_todo, container, false)

        val dbManager = DBManager(view.context)
        val challenge_todo_list = view.findViewById<ListView>(R.id.list_todo_textView)
        challenge_todo_list.adapter = TodoAdapter(view.context)

        return view
    }

    private class TodoAdapter(context: Context) : BaseAdapter(){
        val myContext : Context = context
        val dbManager =  DBManager(context)

        val list = dbManager.getChallenges("all")

        override fun getCount(): Int {
            if(list != null)
                return list.size

            return 0
        }

        override fun getItem(position: Int): String {
            val selected = list!!.get(position)
            return selected
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, view: View?, viewGroup : ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(myContext)
            val layout = layoutInflater.inflate(R.layout.challenge_todo_list, viewGroup, false )

            var list_todo_textView = layout.findViewById<TextView>(R.id.list_todo_textView)
            //list_todo_textView = list!!.get(position)

            return layout
        }
    }
    */
}