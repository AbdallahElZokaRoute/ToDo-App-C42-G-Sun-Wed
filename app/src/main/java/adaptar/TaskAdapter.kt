package adaptar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.todoappc42gsunwed.R
import model.CalderModel
import model.TaskModel

class TaskAdapter(private val tasks: List<TaskModel>,  private val onItemLongClick: (TaskModel,Int)-> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.description.text = task.description
        holder.time.text = task.date
        holder.bind(task,position)

    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.task_title)
        val description: TextView = itemView.findViewById(R.id.task_description)
        val time: TextView = itemView.findViewById(R.id.task_time)

        fun bind(task: TaskModel,position: Int){
            itemView.setOnLongClickListener {
                onItemLongClick(task,position)
                true

            }
        }
    //    val status: TextView = itemView.findViewById(R.id.task_status)
    }

}


