package com.zakariyaf.wikibrowser.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zakariyaf.wikibrowser.R
import com.zakariyaf.wikibrowser.activities.SearchActivity
import kotlinx.android.synthetic.main.fragment_explore.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
        search_card_view.setOnClickListener {
            val searchIntent = Intent(context, SearchActivity::class.java)
            context!!.startActivity(searchIntent)
        }
    }


}
