package hr.algebra.hacked

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import hr.algebra.hacked.model.ItemModel
import kotlinx.android.synthetic.main.item_pager.view.*



class PagerAdapter(private val items: MutableList<ItemModel>, private val context: Context) :
    RecyclerView.Adapter<PagerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvCompany: TextView = itemView.findViewById(R.id.tvCompanyPager)
        private val tvBreachDate: TextView = itemView.findViewById(R.id.tvBreachDatePager)
        private val tvDomain: TextView = itemView.findViewById(R.id.tvDomainPager)
        private val tvBreaches: TextView = itemView.findViewById(R.id.tvBreachesPager)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescirptionPager)


        private val ivVerified: ImageView = itemView.findViewById(R.id.ivVerified)
        private val ivSensitive: ImageView = itemView.findViewById(R.id.ivSensitive)
        private val ivRetired: ImageView = itemView.findViewById(R.id.ivRetired)
        private val ivSpam: ImageView = itemView.findViewById(R.id.ivSpam)

        fun bind(item: ItemModel) {
            tvCompany.text = item.title
            tvBreachDate.text = item.breachDate
            tvDomain.text = item.domain
            tvBreaches.text = item.pwnCount.toString()
            tvDescription.text = item.description
            ivVerified.setImageResource(if (item.isVerified) R.drawable.accept else R.drawable.cancel_icon)
            ivSensitive.setImageResource(if (item.isSensitive) R.drawable.accept else R.drawable.cancel_icon)
            ivRetired.setImageResource(if (item.isRetired) R.drawable.accept else R.drawable.cancel_icon)
            ivSpam.setImageResource(if (item.isSpamList) R.drawable.accept else R.drawable.cancel_icon)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]

        setupVerifiedListener(item, holder, position)
        setupSensitiveListener(item,holder,position)
        setupSpamListener(item,holder,position)
        setupRetiredListener(item,holder,position)




        holder.bind(items[position])
    }

    private fun setupRetiredListener(item: ItemModel, holder: ViewHolder, position: Int) {
        holder.itemView.ivRetired.setOnClickListener() {
            item.isRetired = !item.isRetired
            context.contentResolver.update(
                ContentUris.withAppendedId(HACKED_PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(ItemModel::isRetired.name, item.isRetired)
                },
                null,
                null
            )
            notifyItemChanged(position)

        }

    }

    private fun setupSpamListener(item: ItemModel, holder: PagerAdapter.ViewHolder, position: Int) {
        holder.itemView.ivSpam.setOnClickListener() {
            item.isSpamList = !item.isSpamList
            context.contentResolver.update(
                ContentUris.withAppendedId(HACKED_PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(ItemModel::isSpamList.name, item.isSpamList)
                },
                null,
                null
            )
            notifyItemChanged(position)
        }

    }


    private fun setupSensitiveListener(
        item: ItemModel,
        holder: ViewHolder,
        position: Int
    ) {
        holder.itemView.ivSensitive.setOnClickListener() {
            item.isSensitive = !item.isSensitive
            context.contentResolver.update(
                ContentUris.withAppendedId(HACKED_PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(ItemModel::isSensitive.name, item.isSensitive)
                },
                null,
                null
            )
            notifyItemChanged(position)
        }

    }


    private fun setupVerifiedListener(item: ItemModel, holder: ViewHolder, position: Int) {

        holder.itemView.ivVerified.setOnClickListener() {
            item.isVerified = !item.isVerified
            context.contentResolver.update(
                ContentUris.withAppendedId(HACKED_PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(ItemModel::isVerified.name, item.isVerified)
                },
                null,
                null
            )
            notifyItemChanged(position)
        }


    }



    override fun getItemCount(): Int = items.size


}