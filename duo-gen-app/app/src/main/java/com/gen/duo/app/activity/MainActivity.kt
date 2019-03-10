package com.gen.duo.app.activity

import android.app.ActionBar
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.gen.duo.app.util.Common
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.widget.LinearLayout
import android.view.LayoutInflater
import com.gen.duo.app.*
import com.gen.duo.app.fragment.*
import com.gen.duo.app.interactor.MainInteractor
import com.gen.duo.app.model.Item
import com.gen.duo.app.presenter.MainPresenter
import com.gen.duo.app.util.Preferences
import com.gen.duo.app.view.MainView
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    lateinit var intercator: MainInteractor
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navigation_view.setNavigationItemSelectedListener(this)

        intercator = MainInteractor()
        intercator.init(this@MainActivity)

        presenter = MainPresenter()
        presenter.init(this, intercator)

        presenter.getHotList()
        presenter.getRecomendList()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val settings = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)

        val searchViewMain = menu.findItem(R.id.search_view_main)?.actionView as SearchView
        searchViewMain.setIconifiedByDefault(false)
        searchViewMain.layoutParams = settings

        val searchViewMainManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchViewMain.setSearchableInfo(searchViewMainManager.getSearchableInfo(componentName))

        searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        Common.showMessage(this@MainActivity, "Ops sorry this feature under development")
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_cart -> {
                Common.showMessage(this@MainActivity, "Ops sorry this feature under development")
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item);
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_category -> {
                val fragment = CategoryFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            }
            R.id.navigation_orders -> {
                val fragment = OrdersFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            }
            R.id.navigation_message -> {
                val fragment = MessageFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            }
            R.id.navigation_sketch -> {
                val fragment = SketchFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            }
            R.id.navigation_profile -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_content_main, fragment)
                        .addToBackStack(null)
                        .commit()
            }
            R.id.navigation_logout -> {
                val preferences = Preferences(this@MainActivity)
                preferences.setToken("")

                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)

        return false
    }

    override fun getHotList(listItem: List<Item>) {
        val layout = findViewById<LinearLayout>(R.id.layout_hot_item)

        printData(layout, listItem)
    }

    override fun getRecomendList(listItem: List<Item>) {
        val layout = findViewById<LinearLayout>(R.id.layout_recomend_item)

        printData(layout, listItem)
    }

    override fun onUnAuthorized() {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }

    private fun printData(layout: LinearLayout,listItem: List<Item>){
        val inflater = LayoutInflater.from(getApplicationContext())


        for (item in listItem) {
            val contentLayoutItem = inflater.inflate(R.layout.activity_main_item, null, false)

            val cardViewItem = contentLayoutItem.findViewById<CardView>(R.id.card_view_item)
            val imageItem = contentLayoutItem.findViewById<ImageView>(R.id.image_item)
            val textViewTitle = contentLayoutItem.findViewById<TextView>(R.id.text_view_title)
            val textViewDescription = contentLayoutItem.findViewById<TextView>(R.id.text_view_description)
            val ratingBarRating = contentLayoutItem.findViewById<RatingBar>(R.id.rating_bar_rating)
            val textViewPrice = contentLayoutItem.findViewById<TextView>(R.id.text_view_price)

            val imageItemDecoded = Base64.decode(item.image, Base64.DEFAULT)
            val imageItemByte = BitmapFactory.decodeByteArray(imageItemDecoded, 0, imageItemDecoded.size)

            cardViewItem.setBackgroundColor(Color.WHITE)
            imageItem.setImageBitmap(imageItemByte)
            imageItem.getLayoutParams().height = 250;
            imageItem.getLayoutParams().width = 250;
            textViewTitle.setText(item.title)
            textViewDescription.setText(item.description)
            if (item.viewed!! > 5){
                ratingBarRating.setRating(5F);
            }else{
                ratingBarRating.setRating(3F);
            }

            textViewPrice.setText("Rp." + item.price)

            layout.addView(contentLayoutItem)

            cardViewItem.setOnClickListener(View.OnClickListener {
                Common.showMessage(this@MainActivity, "Ops sorry this feature under development")
            })
        }
    }

    private fun hideNavigation() {
        val navigationView: NavigationView

        navigationView = findViewById<View>(R.id.navigation_view) as NavigationView
        val nav_Menu = navigationView.getMenu()
        nav_Menu?.findItem(R.id.navigation_message)?.isVisible = false
    }
}
