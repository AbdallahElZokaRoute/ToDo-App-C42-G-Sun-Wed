package adaptar
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.route.todoappc42gsunwed.R
import model.CalderModel
import model.TaskModel
import kotlin.math.log

class CalnderAdapter(private val cander: List<CalderModel>,  private val listener: OnItemClickListener) :
    RecyclerView.Adapter<CalnderAdapter.CalnderVH>() {
    private var selectedPosition  =0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalnderVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calnder, parent, false)
        return CalnderVH(view);
    }

    override fun onBindViewHolder(holder: CalnderVH, position: Int) {
        val item = cander[position]
        holder.dayText.text = item.day
        holder.monthText.text = item.dayOfWeek
        holder.bind(item,position);
    }

    override fun getItemCount(): Int {
        return cander.size;
    }

    inner class CalnderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SuspiciousIndentation")
        fun bind(item: CalderModel, position: Int) {

            val selectedColor = ContextCompat.getColor(itemView.context, R.color.dark_primary)
            val unselectedColor = ContextCompat.getColor(itemView.context, R.color.text_calnder_item)
                if (position == selectedPosition) {
                    dayText.setTextColor(selectedColor)
                    monthText.setTextColor(selectedColor) // اللون عند الضغط
                } else {
                    dayText.setTextColor(unselectedColor)
                    monthText.setTextColor(unselectedColor) // اللون الافتراضي
                }
                val position = adapterPosition

            itemView.setOnClickListener {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                    listener.onItemClick(item)

            }
        }

        val monthText: TextView = itemView.findViewById(R.id.mounth)
        val dayText: TextView = itemView.findViewById(R.id.day)
    }
    interface OnItemClickListener {
        fun onItemClick(item: CalderModel){

        }


        fun OnItemHoldClickListener(task: TaskModel)
    }

}

