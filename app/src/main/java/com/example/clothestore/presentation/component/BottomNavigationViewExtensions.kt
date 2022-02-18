package com.example.clothestore.presentation.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.clothestore.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Set up badge with the given number in a menu item.
 * Set up 0 to hide the badge
 * @param badgeValue : Int
 * @param tabResId : Int
 */
fun BottomNavigationView.setBadge(tabResId: Int, badgeValue: Int) {
    getOrCreateBadge(this, tabResId)?.let { badge ->
        badge.visibility = if (badgeValue > 0) {
            badge.text = "$badgeValue"
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

/**
 * Function to create badge
 * @param bottomBar : View
 * @param tabResId : Int
 */
private fun getOrCreateBadge(bottomBar: View, tabResId: Int): TextView? {
    val parentView = bottomBar.findViewById<ViewGroup>(tabResId)
    return parentView?.let {
        var badge = parentView.findViewById<TextView>(R.id.menuItemBadge)
        if (badge == null) {
            LayoutInflater.from(parentView.context)
                .inflate(R.layout.bottom_nav_badge, parentView, true)
            badge = parentView.findViewById(R.id.menuItemBadge)
        }
        badge
    }
}
