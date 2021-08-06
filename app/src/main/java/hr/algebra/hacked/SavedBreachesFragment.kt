package hr.algebra.hacked

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.hacked.framework.fetchItems
import hr.algebra.hacked.framework.fetchSavedItems
import hr.algebra.hacked.model.ItemModel
import kotlinx.android.synthetic.main.fragment_items.*


class SavedBreachesFragment : Fragment() {

    private lateinit var itemsAdapter: ItemAdapter
    private lateinit var savedItems : MutableList<ItemModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        savedItems=requireContext().fetchSavedItems()
        return inflater.inflate(R.layout.fragment_saved_breaches, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsAdapter= ItemAdapter(savedItems,requireContext(),true)
        rvItems.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=itemsAdapter
        }
    }

}