package com.akamyshev.expandablerecyclerview

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnStartDragListener {
    private var mItemTouchHelper: ItemTouchHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        val data: MutableList<String> = mutableListOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")

        //Drag & Expand adapter
        val adapter = MyDraggableAndExpandableADapter(this, data, this, R.layout.item)
        recycler.adapter = adapter

        val callback = SimpleItemTouchHelperCallback(adapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper?.attachToRecyclerView(recycler)


    }

    fun initRecyclerView() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        mItemTouchHelper?.startDrag(viewHolder)
    }



}

class MyDraggableAndExpandableADapter(context: Context, data: MutableList<String>, mDragStartListener: OnStartDragListener, childLayoutId: Int) :
        DraggableAndExpandableRecyclerViewAdapter<String, MyDragExpVH<String>>(context, data, mDragStartListener, childLayoutId) {
    override fun createViewHolder(view: View): MyDragExpVH<String> = MyDragExpVH(view)
}

class MyDragExpVH<T>(itemView: View) : DraggableAndExpandableViewHolder<T>(itemView) {
    override fun bindView(data: T) {
        if(data is String) {
            text?.text = data
        }
    }

    override fun getChild(): View = details

    val text = itemView.findViewById<TextView>(R.id.text)
    val details = itemView.findViewById<CardView>(R.id.details)
    val mainCard = itemView.findViewById<CardView>(R.id.main_card)
}



