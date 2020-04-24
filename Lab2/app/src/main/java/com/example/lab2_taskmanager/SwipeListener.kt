package com.example.lab2_taskmanager

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class SwipeListener(
    ctx: Context?,
    private val adapter: Adapter,
    private val recycler: RecyclerView,
    private val context: Context,
    private val view: View,
    private val inflater: LayoutInflater
) : View.OnTouchListener
{
    private val gestureDetector: GestureDetector
    private val swipeThreshold = 1
    private val swipeVelThreshold = 4

    override fun onTouch(v: View?, event: MotionEvent?): Boolean
    {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener()
    {
        override fun onFling(motion1: MotionEvent, motion2: MotionEvent, velX: Float, velY: Float): Boolean
        {
            var result = false
            try {
                val diffY = motion2.y - motion1.y
                val diffX = motion2.x - motion1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > swipeThreshold && Math.abs(velX) > swipeVelThreshold) {
                        if (diffX > 0)
                        {
                            onSwipeRight(motion1)
                        }
                        else
                        {
                            onSwipeLeft(motion2)
                        }
                        result = true
                    }
                }
                else if (abs(diffY) > swipeThreshold && Math.abs(velY) > swipeVelThreshold)
                {
                    result = true
                }
            }
            catch (exception: Exception)
            {
                exception.printStackTrace()
            }
            return true
        }

        override fun onDown(e: MotionEvent): Boolean
        {
            return true
        }
    }

    fun getPosition(posX: Float, posY:Float): Int
    {
        val child = recycler.findChildViewUnder(posX, posY)
        return if(child !== null)
        {
            recycler.getChildAdapterPosition(child) - 1
        }
        else
        {
            adapter.vals.size - 1
        }
    }

    fun onSwipeLeft(motion: MotionEvent)
    {
        adapter.vals.get(getPosition(motion.rawX, motion.rawY)).status = "Done"
        //adapter.vals.get(getPosition(motion.rawX, motion.rawY)).status.setTextColor(Color.parseColor("#4444DD"))
        adapter.notifyDataSetChanged()
    }

    fun onSwipeRight(motion: MotionEvent)
    {
        adapter.vals.removeAt(getPosition(motion.rawX, motion.rawY))
        adapter.notifyDataSetChanged()
    }

    init
    {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }
}