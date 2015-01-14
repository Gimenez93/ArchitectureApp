package cat.udl.eps.softarch.hello.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.udl.eps.softarch.hello.model.Serie;
import cat.udl.eps.softarch.hello.model.User;
import cat.udl.eps.softarch.hello.repository.SerieRepository;
import cat.udl.eps.softarch.hello.repository.UserRepository;

@Service
public class UserSeriesServiceImpl implements UserSeriesService{
	final Logger logger = LoggerFactory.getLogger(UserSeriesServiceImpl.class);

    @Autowired
    SerieRepository    serieRepository;
    @Autowired
    UserRepository     userRepository;

    
    @Transactional
	@Override
	public Serie addSerie(Serie serie) {
		serieRepository.save(serie);
		return serie;
	}

    @Transactional
	@Override
	public Serie updateSerie(Serie updateSerie, Long serieId) {
		Serie oldSerie = serieRepository.findOne(serieId);
		if (!updateSerie.getTitle().equals(oldSerie.getTitle())) {
            throw new SerieTitleUpdateException("Serie title cannot be updated");
        }
        return serieRepository.save(oldSerie);
	}

    @Transactional
	@Override
	public void removeSerie(Long id) {
		serieRepository.delete(id);
	}
}
