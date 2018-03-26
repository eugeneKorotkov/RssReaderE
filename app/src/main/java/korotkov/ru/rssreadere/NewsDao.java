package korotkov.ru.rssreadere;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Eugene on 26.03.2018.
 */
@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    List<News> getAll();

    @Insert
    void insertAll(News... news);

}
