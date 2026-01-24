import androidx.room.*
import model.TaskModel

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskModel)

    @Update
    suspend fun updateTask(task: TaskModel)

    @Delete
    suspend fun deleteTask(task: TaskModel)

    @Query("SELECT * FROM tasks where :selectTime = date")
    suspend fun getTasksByDate(selectTime: String): List<TaskModel>


@Query("SELECT * FROM tasks")
suspend fun getTasks(): List<TaskModel>
}

