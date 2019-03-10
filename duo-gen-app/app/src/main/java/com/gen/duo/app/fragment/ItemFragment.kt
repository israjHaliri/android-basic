package com.gen.duo.app.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ProgressBar
import com.gen.duo.app.adapter.ItemAdapter
import com.gen.duo.app.R
import com.gen.duo.app.activity.LoginActivity
import com.gen.duo.app.interactor.ItemInteractor
import com.gen.duo.app.model.Item
import com.gen.duo.app.presenter.ItemPresenter
import com.gen.duo.app.util.Common
import com.gen.duo.app.view.ItemView
import java.math.BigDecimal
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ItemFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFragment : Fragment(), ItemView {

    lateinit var intercator: ItemInteractor
    lateinit var presenter: ItemPresenter

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var layoutManager: GridLayoutManager

    lateinit var itemAdapter: ItemAdapter
    lateinit var recyclerViewItem: RecyclerView
    lateinit var progressBarItem: ProgressBar

    var offset: Int = 0
    var loading = false

    var listItemTemporary: MutableList<Item> = mutableListOf<Item>()
    var statuslistItemTemporary = false

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1)
            mParam2 = getArguments().getString(ARG_PARAM2)
        }

        intercator = ItemInteractor()
        intercator.init(getActivity())

        presenter = ItemPresenter()
        presenter.init(this, intercator)

        listItemTemporary.clear()
        itemAdapter = ItemAdapter(listItemTemporary)
    }

    public override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                     savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_item, container, false)

        presenter.getList(mParam1!!, mParam2!!, 10, offset)

        progressBarItem = view.findViewById<ProgressBar>(R.id.progress_bar_item)
        progressBarItem.setVisibility(View.GONE)

        recyclerViewItem = view.findViewById<RecyclerView>(R.id.recycler_view_item)
        recyclerViewItem.adapter = itemAdapter

        linearLayoutManager = LinearLayoutManager(getActivity())
        layoutManager = GridLayoutManager(getActivity(), 2)

        recyclerViewItem.layoutManager = linearLayoutManager
        recyclerViewItem.layoutManager = layoutManager

        var pastVisibleItem: Int
        var countVisibleItem: Int
        var countTotalItem: Int

        recyclerViewItem.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {

                    countVisibleItem = layoutManager.childCount
                    countTotalItem = layoutManager.itemCount
                    pastVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (loading == false && countVisibleItem + pastVisibleItem == countTotalItem) {
                        loading = true
                        recyclerViewItem.setVisibility(View.GONE)
                        progressBarItem.setVisibility(View.VISIBLE);
                        statuslistItemTemporary = true

                        Common.getLogger(this).info(offset.toString())

                        presenter.getList("", mParam2!!, 10, offset)
                    }
                }
            }
        })

        // Inflate the layout for this fragment
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    public override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            Common.getLogger(this).info("Item Fragment OnFragmentInteractionListener Not Implemented")
        }
    }

    public override fun onDetach() {
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
         * @return A new instance of fragment ItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ItemFragment {
            val fragment = ItemFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun getList(listItem: List<Item>) {
        recyclerViewItem.setVisibility(View.VISIBLE);
        for (value in listItem) {
            listItemTemporary.add(value)
        }

        if (statuslistItemTemporary == true) {
            progressBarItem.setVisibility(View.GONE);
            itemAdapter.notifyDataSetChanged()
            offset = offset + 10
        } else {
            itemAdapter = ItemAdapter(listItemTemporary)
            recyclerViewItem.adapter = itemAdapter
            offset = offset + 10
        }

        loading = false
    }

    override fun onUnAuthorized() {
        startActivity(Intent(getActivity(), LoginActivity::class.java))
        getActivity().finish()
    }

    override fun saveItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}// Required empty public constructor
