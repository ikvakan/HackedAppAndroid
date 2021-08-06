package hr.algebra.hacked

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.UriMatcher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.*
import hr.algebra.hacked.model.ItemModel


import kotlinx.android.synthetic.main.fragment_report.*

import java.util.*

class ReportFragment : Fragment() {

    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var itemModel: ItemModel

    private lateinit var listEditText :MutableList<EditText>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_report, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()
        setupDatePickerDialog()
        setupImageViewListeners()
        sendReport()


    }

    private fun init() {
        listEditText= mutableListOf(etCompany,etDate,etDomain,etNumOfBreaches,etDescription)
    }

    private fun sendReport() {

        btnSendReport.setOnClickListener {


            if (formValid()){

                itemModel = ItemModel(
                    null,
                    etCompany.text.toString(),
                    etDomain.text.toString(),
                    Integer.parseInt(etNumOfBreaches.text.toString()),
                    etDate.text.toString(),
                    etDescription.text.toString(),
                    tvVerified.tag as Boolean,
                    tvSensitive.tag as Boolean,
                    tvRetired.tag as Boolean,
                    tvSpam.tag as Boolean,
                   false

                )



                requireContext().contentResolver.insert(
                    HACKED_PROVIDER_CONTENT_URI,
                    ContentValues().apply {
                        put(ItemModel::title.name, itemModel.title)
                        put(ItemModel::domain.name, itemModel.domain)
                        put(ItemModel::pwnCount.name, itemModel.pwnCount)
                        put(ItemModel::breachDate.name, itemModel.breachDate)
                        put(ItemModel::description.name, itemModel.description)
                        put(ItemModel::isVerified.name, itemModel.isVerified)
                        put(ItemModel::isSensitive.name, itemModel.isSensitive)
                        put(ItemModel::isRetired.name, itemModel.isRetired)
                        put(ItemModel::isSpamList.name, itemModel.isSpamList)
                        put(ItemModel::isSaved.name, itemModel.isSaved)
                    })

                resetForm()

            }else{
                Toast.makeText(requireContext(),"Please fill all fields.",Toast.LENGTH_SHORT).show()
            }


        }


    }

    private fun formValid(): Boolean {

        var result = true

        listEditText.forEach{
            if (it.text.toString().trim().isNullOrBlank()){
//                Toast.makeText(requireContext(),"Please fill all fields.",Toast.LENGTH_SHORT).show()
                result=false

            }
        }


        return result
    }

    private fun resetForm() {

        resetText()
        resetImages()
        Toast.makeText(requireContext(), "Report added", Toast.LENGTH_LONG).show()
    }

    private fun resetText() {

        etCompany.text.clear()
        etDomain.text.clear()
        etNumOfBreaches.text.clear()
        etDate.text.clear()
        etDescription.text.clear()

        etCompany.requestFocus()
    }

    private fun resetImages() {
        ivVerified.setImageResource(R.drawable.accept)
        ivSensitive.setImageResource(R.drawable.accept)
        ivSpam.setImageResource(R.drawable.accept)
        ivRetired.setImageResource(R.drawable.accept)
    }

    private fun setupImageViewListeners() {

        setOnClickListenerVerified(ivVerified)
        setOnClickListenerSpam(ivSpam)
        setOnClickListenerRetired(ivRetired)
        setOnClickListenerSensitive(ivSensitive)

    }

    private fun setOnClickListenerSensitive(ivSensitive: ImageView) {
        setListener(ivSensitive, tvSensitive)
    }

    private fun setOnClickListenerRetired(ivRetired: ImageView) {
        setListener(ivRetired, tvRetired)
    }

    private fun setOnClickListenerSpam(ivSpam: ImageView) {
        setListener(ivSpam, tvSpam)
    }

    private fun setOnClickListenerVerified(ivVerified: ImageView) {
        setListener(ivVerified, tvVerified)
    }

    private fun setListener(imageView: ImageView, view: View) {
        var result = false
        view.tag = true

        imageView.setOnClickListener {
            if (result) {
                imageView.setImageResource(R.drawable.accept)
                view.tag = true

                result = false

            } else {
                imageView.setImageResource(R.drawable.cancel_icon)
                view.tag = false

                result = true
            }
        }


    }


    private fun setupDatePickerDialog() {

        val calendar: Calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        etDate.showSoftInputOnFocus = false
        etDate.setOnClickListener {


            val setListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
                    val month = month + 1
                    etDate.setText("$year-$month-$day", TextView.BufferType.EDITABLE)
                }
            }
            datePickerDialog = DatePickerDialog(requireContext(), setListener, year, month, day)
            datePickerDialog.show()
        }
    }


}