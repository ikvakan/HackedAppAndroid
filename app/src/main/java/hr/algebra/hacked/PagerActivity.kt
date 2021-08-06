package hr.algebra.hacked

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import hr.algebra.hacked.framework.fetchItems
import hr.algebra.hacked.framework.fetchSavedItems
import hr.algebra.hacked.model.ItemModel
import kotlinx.android.synthetic.main.activity_pager.*

const val ITEM_POSITION="hr.algebra.hacked.item_position"
const val SAVED_ITEMS="hr.algebra.hacked.saved_items"

class PagerActivity() : AppCompatActivity() {

    private lateinit var items:MutableList<ItemModel>
    private var itemPosition=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        inti()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    private fun inti() {

        var saved=intent.getBooleanExtra(SAVED_ITEMS,false)

        if (saved){
           items=fetchSavedItems()
        }else{
            items=fetchItems()

        }


        itemPosition=intent.getIntExtra(ITEM_POSITION,0)
        viewPager.adapter=PagerAdapter(items,this)
        viewPager.currentItem=itemPosition

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}