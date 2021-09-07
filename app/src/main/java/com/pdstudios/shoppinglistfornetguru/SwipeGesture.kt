package com.pdstudios.shoppinglistfornetguru

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView


abstract class SwipeGesture(
    private val swipeLeftIcon: Drawable?,
    private val swipeRightIcon: Drawable?,
    swipeDirection: Int
): ItemTouchHelper.SimpleCallback(0, swipeDirection) {

    private val background = ColorDrawable()
    private val backgroundColorRed = Color.parseColor("#f44336")
    private val backgroundColorYellow = Color.parseColor("#F1C220")
    private val intrinsicHeight = swipeLeftIcon!!.intrinsicHeight

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val swipeLeft =  (dX < 0)
        val swipeRight = (dX > 0)
        when {
            swipeLeft -> {
                val intrinsicWidth = swipeLeftIcon!!.intrinsicWidth
                background.color = backgroundColorRed
                background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                background.draw(c)

                // Calculate position of delete icon
                val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val iconMargin = (itemHeight - intrinsicHeight) / 2
                val iconLeft = itemView.right - iconMargin - intrinsicWidth
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + intrinsicHeight

                // Draw the delete icon
                swipeLeftIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                swipeLeftIcon.draw(c)
            }
            swipeRight -> {
                val intrinsicWidth = swipeRightIcon!!.intrinsicWidth
                background.color = backgroundColorYellow
                background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
                background.draw(c)

                //Draw the swipe left icon
                val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val iconMargin = (itemHeight - intrinsicHeight) / 2
                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + intrinsicWidth
                val iconBottom = iconTop + intrinsicHeight

                //Draw the swipe right icon
                swipeRightIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                swipeRightIcon.draw(c)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}