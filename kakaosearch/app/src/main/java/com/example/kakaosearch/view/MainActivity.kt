package com.example.kakaosearch.view

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kakaosearch.R
import com.example.kakaosearch.adapter.MainSearchRecyclerViewAdapter
import com.example.kakaosearch.base.BaseActivity
import com.example.kakaosearch.databinding.ActivityMainBinding
import com.example.kakaosearch.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    private val mainSearchRecyclerViewAdapter: MainSearchRecyclerViewAdapter by inject()

    override fun initStartView() {
        main_activity_search_rv.run {
            adapter = mainSearchRecyclerViewAdapter
            layoutManager = StaggeredGridLayoutManager(3,1).apply{
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                orientation = StaggeredGridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initDataBinding() {
        viewModel.imageSearchResponseLiveData.observe(this, Observer {
            it.documents.forEach {document ->
                mainSearchRecyclerViewAdapter.addImageItem(document.image_url, document.doc_url)
            }
            mainSearchRecyclerViewAdapter.notifyDataSetChanged()
        })
    }

    override fun initAfterBinding() {
        main_activity_search_btn.setOnClickListener {
            viewModel.getImageSearch(main_activity_search_et.text.toString(), 1, 80)
        }
    }
}