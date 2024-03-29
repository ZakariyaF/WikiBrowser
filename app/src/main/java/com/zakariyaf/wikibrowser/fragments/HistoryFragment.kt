package com.zakariyaf.wikibrowser.fragments


import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.WikiApplication
import com.zakariyaf.wikibrowser.adapters.ArticleListItemRecyclerAdapter
import com.zakariyaf.wikibrowser.managers.WikiManager
import com.zakariyaf.wikibrowser.models.WikiPage
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : Fragment() {


    private var wikiManager: WikiManager? = null
    var historyRecycler: RecyclerView? = null
    private val adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()
    var noHistory_textview: TextView? = null

    init {
        setHasOptionsMenu(true)
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        noHistory_textview = view.findViewById(R.id.noHistory_textview)
        historyRecycler = view.findViewById(R.id.history_article_recycler)
        historyRecycler!!.layoutManager = LinearLayoutManager(context)
        historyRecycler!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val history = wikiManager!!.getHistory()
            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
            if (history!!.isEmpty()) {
                noHistory_textview?.visibility = View.VISIBLE
                historyRecycler?.visibility = View.INVISIBLE
            } else {
                adapter.currentResults.clear()
                adapter.currentResults.addAll(history as ArrayList<WikiPage>)
                activity?.runOnUiThread { adapter.notifyDataSetChanged() }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_clear_history) {
            activity?.alert("Are you sure you want to clear history?", "Confirm") {
                yesButton {
                    adapter.currentResults.clear()
                    doAsync {
                        wikiManager?.clearHistory()

                    }
                    activity?.runOnUiThread { adapter.notifyDataSetChanged() }
                    noHistory_textview?.visibility = View.VISIBLE
                    historyRecycler?.visibility = View.INVISIBLE
                }
                noButton {

                }
            }?.show()
        }
        return true
    }

}
