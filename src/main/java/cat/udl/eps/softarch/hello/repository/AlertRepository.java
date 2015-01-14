package cat.udl.eps.softarch.hello.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cat.udl.eps.softarch.hello.model.Alert;
import cat.udl.eps.softarch.hello.model.Serie;

public interface AlertRepository extends PagingAndSortingRepository<Alert, Long> {
	
	List<Alert> findByTitleContaining(@Param("title") String title);
	List<Alert> findAlertByEmail(@Param("email") String email);

}
