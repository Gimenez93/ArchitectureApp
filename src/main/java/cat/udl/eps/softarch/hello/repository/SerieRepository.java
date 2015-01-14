package cat.udl.eps.softarch.hello.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cat.udl.eps.softarch.hello.model.Serie;

public interface SerieRepository extends PagingAndSortingRepository<Serie, Long> {
	
	List<Serie> findByTitleContaining(@Param("title") String title);

}
