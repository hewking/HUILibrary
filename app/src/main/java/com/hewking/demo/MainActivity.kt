package com.hewking.demo

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.hewking.customviewtest.R


class MainActivity : AppCompatActivity() {

    internal var expand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = NESTED_SCROLL
        //        if (type == NESTED_SCROLL) {
        //            setContentView(R.layout.view_nested_view_parent);
        //        } else {
//        setContentView(R.layout.activity_main)
        //        }

        //        DialogFragment dialogFragment = new ImageDialogFragment();
        //        dialogFragment.show(getSupportFragmentManager(),"mainactivity");
        //        findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                StickyTopLayout topLayout = (StickyTopLayout) findViewById(R.id.sticktop);
        //                expand = !expand;
        //                topLayout.top(expand);
        //            }
        //        });

        setContentView(R.layout.activity_navi)

        initView()

    }

    private fun initView() {
        val refreshLayout = v<SwipeRefreshLayout>(R.id.refreshlayout)
        val recyclerView = v<RecyclerView>(R.id.recyclerview)

        refreshLayout.setOnRefreshListener {

        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(RBaseItemDecoration())
        recyclerView.adapter = mAdapter
    }

    fun createItems() : MutableList<Item>{
        val list = mutableListOf<Item>()
        list.add(Item(0,"首页测试",DemoListFragment::class.java))
        return list
    }

    val mAdapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        val datas = createItems()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_text,parent,false)
            val vh = object : RecyclerView.ViewHolder(itemView){
            }
            return vh
        }

        override fun getItemCount(): Int {
            return datas.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            val itemView = holder?.itemView
            itemView?.v<TextView>(R.id.tv_text)?.text = datas[position].info
            itemView?.setOnClickListener {
                val intent = Intent(this@MainActivity,DemoFragmentActivity::class.java)
                intent.putExtra(DemoFragmentActivity.FRAGMENT,datas[position].clazz.name)
                this@MainActivity.startActivity(intent)
            }
        }

    }



    data class Item(val id : Int,val info : String ,val clazz: Class<*>)

    companion object {
        val NESTED_SCROLL = 0x0001
    }

}
