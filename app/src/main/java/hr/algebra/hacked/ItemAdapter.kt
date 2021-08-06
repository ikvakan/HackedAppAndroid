package hr.algebra.hacked


import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.hacked.framework.startActivity
import hr.algebra.hacked.model.ItemModel
import kotlinx.android.synthetic.main.item.view.*


class ItemAdapter(
    private var items: MutableList<ItemModel>,
    private val context: Context,
    private val savedItems: Boolean
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>(), Filterable {


    private var itemsCopy: MutableList<ItemModel> = items.toMutableList()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        private val tvCompany: TextView = itemView.findViewById(R.id.tvCompany)
        private val tvBreachDate: TextView = itemView.findViewById(R.id.tvBreachDate)
        private val tvNumOfBreaches: TextView = itemView.findViewById(R.id.tvNumOfBreaches)
        //private val btnSource: Button = itemView.findViewById(R.id.btnSource)
        private val ivSavedBreach: ImageView = itemView.findViewById(R.id.ivSavedBreach)


        fun bind(item: ItemModel) {

            ivItem.setImageResource(R.drawable.hacked_icon)
            tvCompany.text = item.title
            tvBreachDate.text = item.breachDate
            tvNumOfBreaches.text = item.pwnCount.toString()
            ivSavedBreach.setImageResource(if (item.isSaved) R.drawable.ic_baseline_star_24_black else R.drawable.ic_baseline_star_border_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.btnSource.setOnClickListener {
            context.startActivity<WebViewActivity>(ITEM_URL, items[position].domain)
        }



        if (!savedItems) {
            holder.itemView.setOnClickListener {

                Intent(context,PagerActivity::class.java).also {
                    it.putExtra(SAVED_ITEMS,false)
                    it.putExtra(ITEM_POSITION,position)
                    context.startActivity(it)
                }

            }



            holder.itemView.ivSavedBreach.setOnClickListener {
                val item = items[position]
                item.isSaved = !item.isSaved
                context.contentResolver.update(
                    ContentUris.withAppendedId(HACKED_PROVIDER_CONTENT_URI, item._id!!),
                    ContentValues().apply {
                        put(ItemModel::isSaved.name, item.isSaved)
                    }, null, null
                )


                if (item.isSaved) {
                    Toast.makeText(holder.itemView.context, "Breach saved.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(holder.itemView.context, "Breach removed.", Toast.LENGTH_SHORT)
                        .show()

                }

                notifyItemChanged(position)
            }
        }
        else  {

            holder.itemView.setOnClickListener {

                Intent(context,PagerActivity::class.java).also {
                    it.putExtra(SAVED_ITEMS,true)
                    it.putExtra(ITEM_POSITION,position)
                    context.startActivity(it)
                }

            }


            holder.itemView.ivSavedBreach.setOnClickListener {
                val item = items[position]
                item.isSaved = !item.isSaved
                context.contentResolver.update(
                    ContentUris.withAppendedId(HACKED_PROVIDER_CONTENT_URI, item._id!!),
                    ContentValues().apply {
                        put(ItemModel::isSaved.name, item.isSaved)
                    }, null, null
                )


                if (item.isSaved) {
                    Toast.makeText(holder.itemView.context, "Breach saved.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(holder.itemView.context, "Breach removed.", Toast.LENGTH_SHORT)
                        .show()
                    items.removeAt(position)
                    notifyDataSetChanged()

                }

                notifyItemChanged(position)
            }
        }



        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Delete")
                setMessage("Delete item ?")
                setIcon(R.drawable.ic_baseline_delete_24)
                setNegativeButton("Cancel", null)
                setPositiveButton("OK") { _, _ -> deleteItem(position) }
                show()
            }
            true
        }

        holder.bind(items[position])

    }

    private fun deleteItem(position: Int) {
        var item = items[position]
        context.contentResolver.delete(
            ContentUris.withAppendedId(
                HACKED_PROVIDER_CONTENT_URI,
                item._id!!
            ), null, null
        )
        items.removeAt(position)
        notifyItemChanged(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun getFilter(): Filter {


        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList = mutableListOf<ItemModel>()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(itemsCopy)
                } else {
                    var filterPattern = constraint.toString().toLowerCase().trim()

                    for (item in itemsCopy) {
                        if (item.title.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }

                var results = FilterResults()
                results.values = filteredList


                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                items.clear()

                items.addAll(results.values as MutableList<ItemModel>)
                notifyDataSetChanged()

            }

        }
    }


}

