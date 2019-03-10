package com.gen.duo.app.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gen.duo.app.R
import com.gen.duo.app.util.Common
import android.app.Activity.RESULT_OK
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.provider.MediaStore
import android.content.Intent
import android.util.Log
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Base64
import android.widget.*
import com.gen.duo.app.activity.LoginActivity
import com.gen.duo.app.activity.MainActivity
import com.gen.duo.app.interactor.ItemInteractor
import com.gen.duo.app.model.Item
import com.gen.duo.app.presenter.ItemPresenter
import com.gen.duo.app.view.ItemView
import java.io.ByteArrayOutputStream
import java.math.BigDecimal


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SketchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SketchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SketchFragment : Fragment(), ItemView {

    lateinit var intercator: ItemInteractor
    lateinit var presenter: ItemPresenter

    lateinit var imageViewItem: ImageView
    var imageData: String = ""

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }

        intercator = ItemInteractor()
        intercator.init(getActivity())

        presenter = ItemPresenter()
        presenter.init(this, intercator)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sketch, container, false)

        var category = arrayOf("ransel")

        val dataAdapter = ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, category);

        val spinner: Spinner = view.findViewById(R.id.spinner_category)
        spinner.adapter = dataAdapter

        val buttonTakePhoto = view.findViewById<Button>(R.id.button_take_photo)
        buttonTakePhoto.setOnClickListener(View.OnClickListener {
            takePicture(view)
        })

        imageViewItem = view.findViewById<ImageView>(R.id.image_view_item)

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            buttonTakePhoto.setEnabled(false)
            ActivityCompat.requestPermissions(getActivity(), arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }

        val buttonSubmit = view.findViewById<Button>(R.id.button_submit)
        buttonSubmit.setOnClickListener(View.OnClickListener {
            val title = view.findViewById<EditText>(R.id.edit_text_title)
            val description = view.findViewById<EditText>(R.id.edit_text_description)
            val price = view.findViewById<EditText>(R.id.edit_text_price)
            val category = view.findViewById<Spinner>(R.id.spinner_category)
            var item = Item(null, title.text.toString(), description.text.toString(), BigDecimal(price.text.toString()), null, imageData, 0, 0, category.getSelectedItem().toString())

            presenter.saveItem(item)
        })

        return view
    }

    fun takePicture(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                var extras: Bundle = data!!.getExtras();
                var imageBitmap: Bitmap = extras.get("data") as Bitmap;

                val imageItemDecoded = Base64.decode(encodeImage(imageBitmap), Base64.DEFAULT)
                val imageItemByte = BitmapFactory.decodeByteArray(imageItemDecoded, 0, imageItemDecoded.size)

                imageViewItem.setImageBitmap(imageItemByte)
                imageData = encodeImage(imageBitmap)
            }
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()

        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            Common.getLogger(this).info("Sketch Fragment OnFragmentInteractionListener Not Implemented")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SketchFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): SketchFragment {
            val fragment = SketchFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getList(listItem: List<Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveItem() {
        Common.showMessage(getActivity(), "Success")
        startActivity(Intent(getActivity(), MainActivity::class.java))
        getActivity().finish()
    }

    override fun onUnAuthorized() {
        startActivity(Intent(getActivity(), LoginActivity::class.java))
        getActivity().finish()
    }
}// Required empty public constructor
