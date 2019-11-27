package com.success.wiki.activities


import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.success.wiki.R
import com.success.wiki.fragments.ExploreFragment
import com.success.wiki.fragments.FavoritesFragment
import com.success.wiki.fragments.HistoryFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val exploreFragment : ExploreFragment
    private val favoriteFragment : FavoritesFragment
    private val historyFragment : HistoryFragment

    init {
        exploreFragment = ExploreFragment()
        favoriteFragment = FavoritesFragment()
        historyFragment = HistoryFragment()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        when (item.itemId){
            R.id.navigation_explore -> transaction.replace(R.id.fragment_container, exploreFragment)
            R.id.navigation_favorites -> transaction.replace(R.id.fragment_container, favoriteFragment)
            R.id.navigation_history -> transaction.replace(R.id.fragment_container, historyFragment)
        }
        transaction.commit()

        true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)



        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, exploreFragment)
        transaction.commit()



        //val navView: BottomNavigationView = findViewById(R.id.nav_view)

        /*val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
    }
}
