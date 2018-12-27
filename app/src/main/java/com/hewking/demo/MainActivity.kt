package com.hewking.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.hewking.androidview.cardview.CardViewFragment
import com.hewking.androidview.constrainlayout.ConstrainDemoFragment
import com.hewking.androidview.dialog.DialogProgressFragment
import com.hewking.androidview.flexboxlayout.FlexBoxLayoutFragment
import com.hewking.base.DemoActivity
import com.hewking.base.L
import com.hewking.custom.R
import com.hewking.custom.dispatch.DispatchFragment
import com.hewking.custom.gesture.GestureDetectorDemoFragment
import com.hewking.custom.stickytop.LagouTopFragment
import com.hewking.custom.stickytop.StickTopFragment
import com.hewking.language.LangageSwitchFragment
import com.hewking.language.LanguageActivity
import com.hewking.third.ImageExFragment


class MainActivity : LanguageActivity() {

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
        val refreshLayout = v<androidx.swiperefreshlayout.widget.SwipeRefreshLayout>(R.id.refreshlayout)
        val recyclerView = v<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerview)

        refreshLayout.setOnRefreshListener {

        }

        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.addItemDecoration(RBaseItemDecoration())
        recyclerView.adapter = mAdapter
    }

    fun createItems() : MutableList<Item>{
        val list = mutableListOf<Item>()
        list.add(Item(0,getString(R.string.text_home),DemoListFragment::class.java))
        list.add(Item(1,getString(R.string.text_htextview),HTextViewFragment::class.java))
        list.add(Item(2,getString(R.string.text_stickytop),StickTopFragment::class.java))
        list.add(Item(3,getString(R.string.text_language_switch),LangageSwitchFragment::class.java))
        list.add(Item(4,"拉钩主页", LagouTopFragment::class.java))
        list.add(Item(5,"自定义view Demo", CustomViewFragment::class.java))
        list.add(Item(6,"EditText Demo",EditTextTestFragment::class.java))
        list.add(Item(7,"CardView shadow Demo",CardViewFragment::class.java))
        list.add(Item(8,"ProgressDialog Demo",DialogProgressFragment::class.java))
        list.add(Item(9,"GestureDetector Demo",GestureDetectorDemoFragment::class.java))
        list.add(Item(10,"ImageExDemo Demo",ImageExFragment::class.java))
        list.add(Item(11,"DemoActivity Demo",DemoActivity::class.java,2))
        list.add(Item(12,"DispatchFragment Demo", DispatchFragment::class.java))
        list.add(Item(13,"RecyclerTestFragent Demo",LoadRecyclerFragment::class.java))
        list.add(Item(13,"WebView Demo",WebViewFragment::class.java))
        list.add(Item(14,"ViewDragHelper Demo",ViewDragFragment::class.java))
        list.add(Item(15,"TinderStacklayout Demo",TinderStackLayoutFragment::class.java))
        list.add(Item(16,"TanTanPaneView Demo",TanTanPanelFragment::class.java))
        list.add(Item(17,"TideRappleView Demo",TideRappleFragment::class.java))
        list.add(Item(18,"FlexBoxLayout Demo",FlexBoxLayoutFragment::class.java))
        list.add(Item(19, "ImageView ScaleType Demo", ImageScaleTypeFragment::class.java))
        list.add(Item(20, "XfermodeSampleView Demo", XfermodeFragment::class.java))
        list.add(Item(21, "ConstrainLayout Demo", ConstrainDemoFragment::class.java))
        list.add(Item(22, "CustomDrawable Demo", CustomDrawableFragment::class.java))
        return list
    }

    val mAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> by lazy {
        object : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>(){

            val datas = createItems()

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
                val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_text,parent,false)
                val vh = object : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
                }
                return vh
            }

            override fun getItemCount(): Int {
                return datas.size
            }

            override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
                val itemView = holder?.itemView
                itemView?.v<TextView>(R.id.tv_text)?.text = datas[position].info
                itemView?.setOnClickListener {
                    if (datas[position].type == 1) {
                        val intent = Intent(this@MainActivity, DemoFragmentActivity::class.java)
                        intent.putExtra(DemoFragmentActivity.FRAGMENT, datas[position].clazz.name)
                        this@MainActivity.startActivity(intent)
                    } else {
                        val intent = Intent(this@MainActivity,datas[position].clazz)
                        intent.resolveActivity(this@MainActivity.packageManager)
                        if (intent == null) {
                            T("${datas[position].clazz.simpleName } 不存在")
                            return@setOnClickListener
                        }
                        this@MainActivity.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        L.d("onTouchEvent","Dispatch MainActivity")
        return super.onTouchEvent(event)
    }

    data class Item(val id : Int,val info : String ,val clazz: Class<*>,val type : Int = 1)

    companion object {
        val NESTED_SCROLL = 0x0001
    }

    fun Activity.T(msg : String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }


}
