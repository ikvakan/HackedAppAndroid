package hr.algebra.hacked

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.hacked.framework.fetchItems
import hr.algebra.hacked.model.ItemModel
import kotlinx.android.synthetic.main.fragment_items.*


class ItemsFragment : Fragment() {

    private lateinit var items: MutableList<ItemModel>
    private lateinit var itemsAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        items=requireContext().fetchItems()
        return inflater.inflate(R.layout.fragment_items, container, false)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsAdapter=ItemAdapter(items,requireContext(),false)
        rvItems.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=itemsAdapter

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.host_menu,menu)
        val searchItem= menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return  false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                itemsAdapter.filter.filter(newText)
                return false
            }
        })
    }



}