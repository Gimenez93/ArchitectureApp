package cat.udl.eps.softarch.hello.service;

import cat.udl.eps.softarch.hello.model.Serie;

public interface UserSeriesService {
	Serie addSerie(Serie serie);

    Serie updateSerie(Serie updateSerie, Long serieId);

	void removeSerie(Long id);
}
