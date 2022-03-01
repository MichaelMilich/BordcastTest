package millich.michael.bordcasttest.home

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.calculateAngle
import kotlin.math.cos
import kotlin.math.sin

class CustomLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): RecyclerView.LayoutParams {
        val recyclerViewLayoutParams = super.generateLayoutParams(lp)
        return RecyclerView.LayoutParams(recyclerViewLayoutParams)
    }

    override fun generateLayoutParams(c: Context, attrs: AttributeSet): RecyclerView.LayoutParams {
        val recyclerViewLayoutParams = super.generateLayoutParams(c, attrs)
        return RecyclerView.LayoutParams(recyclerViewLayoutParams)
    }
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        if (state.itemCount <= 0) return
        fillLayout(recycler, state.itemCount)
    }
    private fun fillLayout(recycler: RecyclerView.Recycler, adapterItemCount: Int){
       /* for( i in 0..adapterItemCount){

            val view = recycler.getViewForPosition(i)
            val angle1 = calculateAngle(event.eventTime)
            val angle =
                ((90 - angle1) * 0.017453).toFloat() // 0.017453 = 1 degree to radians
            val imageParameters: RelativeLayout.LayoutParams =
                RelativeLayout.LayoutParams(40, 40)
            imageParameters.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
            view.layoutParams = imageParameters
            view.translationX = r * cos(angle)
            view.translationY = -r * sin(angle)
            view.rotation = angle1
        }*/
    }
}