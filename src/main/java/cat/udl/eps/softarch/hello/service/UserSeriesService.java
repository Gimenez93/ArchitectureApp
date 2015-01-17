package cat.udl.eps.softarch.hello.service;

import cat.udl.eps.softarch.hello.model.Serie;
import cat.udl.eps.softarch.hello.utils.XQueryHelper.ShowDTO;

public interface UserSeriesService {
	Serie addSerie(Serie serie);

    Serie updateSerie(Serie updateSerie, Long serieId);

	void removeSerie(Long id);

	void initializeRepository();

	void addSerie(ShowDTO show);
}
