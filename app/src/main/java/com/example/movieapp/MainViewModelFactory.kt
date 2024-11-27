import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.repository.SeriesRepository
import com.example.movieapp.view_model.MainViewModel

class MainViewModelFactory(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(movieRepository, seriesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
