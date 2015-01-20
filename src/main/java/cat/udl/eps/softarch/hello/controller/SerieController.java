package cat.udl.eps.softarch.hello.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import cat.udl.eps.softarch.hello.model.Serie;
import cat.udl.eps.softarch.hello.repository.SerieRepository;
import cat.udl.eps.softarch.hello.service.UserGreetingsService;
import cat.udl.eps.softarch.hello.service.UserSeriesService;

import com.google.common.base.Preconditions;

@Controller
@RequestMapping(value = "/series")
public class SerieController {
    final Logger logger = LoggerFactory.getLogger(SerieController.class);

    @Autowired SerieRepository   serieRepository;
    @Autowired UserGreetingsService userGreetingsService;
    @Autowired UserSeriesService userSeriesService;
    
 // INITILIZE REPOSITORY
    @RequestMapping(value= "/initialize",method = RequestMethod.GET)
    @ResponseBody
    public void initialize(){
    	logger.info("Initializing repository...");
        userSeriesService.initializeRepository();
    }
    @RequestMapping(value= "/initialize",method = RequestMethod.GET, produces = "text/html")
    public ModelAndView listHTML() {
    	userSeriesService.initializeRepository();
        return new ModelAndView("series", "series", list(0, 50));
    }
    
 // LIST
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Serie> list(@RequestParam(required=false, defaultValue="0") int page,
                                   @RequestParam(required=false, defaultValue="10") int size) {
        PageRequest request = new PageRequest(page, size);
        return serieRepository.findAll(request).getContent();
    }
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public ModelAndView listHTML(@RequestParam(required=false, defaultValue="0") int page,
                                 @RequestParam(required=false, defaultValue="10") int size) {
        return new ModelAndView("series", "series", list(page, size));
    }
    
 // RETRIEVE
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Serie retrieve(@PathVariable("id") Long id) {
        logger.info("Retrieving serie number {}", id);
        Preconditions.checkNotNull(serieRepository.findOne(id), "Serie with id %s not found", id);
        return serieRepository.findOne(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView retrieveHTML(@PathVariable( "id" ) Long id) {
        return new ModelAndView("serie", "serie", retrieve(id));
    }

// CREATE
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Serie create(@Valid @RequestBody Serie serie, HttpServletResponse response) {
        logger.info("Creating serie with title'{}'", serie.getTitle());
        Serie newSerie = userSeriesService.addSerie(serie);
        response.setHeader("Location", "/series/" + newSerie.getId());
        return newSerie;
    }
    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces="text/html")
    public String createHTML(@Valid @ModelAttribute("serie") Serie serie, BindingResult binding, HttpServletResponse response) {
        if (binding.hasErrors()) {
            logger.info("Validation error: {}", binding);
            return "serieForm";
        }
        return "redirect:/series/"+create(serie, response).getId();
    }
    // Create form
    @RequestMapping(value = "/serieForm", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView createForm() {
        logger.info("Generating form for serie creation");
        Serie emptySerie = new Serie();
        //emptySerie.setDate(new Date());
        return new ModelAndView("serieForm", "serie", emptySerie);
    }

// UPDATE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Serie update(@PathVariable("id") Long id, @Valid @RequestBody Serie serie) {
        logger.info("Updating serie {}, new title is '{}'", id, serie.getTitle());
        Preconditions.checkNotNull(serieRepository.findOne(id), "Serie with id %s not found", id);
        return userSeriesService.updateSerie(serie, id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/x-www-form-urlencoded")
    @ResponseStatus(HttpStatus.OK)
    public String updateHTML(@PathVariable("id") Long id, @Valid @ModelAttribute("serie") Serie serie,
                         BindingResult binding) {
        if (binding.hasErrors()) {
            logger.info("Validation error: {}", binding);
            return "serieForm";
        }
        return "redirect:/series/"+update(id, serie).getId();
    }
    // Update form
    @RequestMapping(value = "/{id}/serieForm", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView updateForm(@PathVariable("id") Long id) {
        logger.info("Generating form for updating serie number {}", id);
        Preconditions.checkNotNull(serieRepository.findOne(id), "Serie with id %s not found", id);
        return new ModelAndView("serieForm", "serie", serieRepository.findOne(id));
    }


// DELETE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        logger.info("Deleting serie number {}", id);																																	
        Preconditions.checkNotNull(serieRepository.findOne(id), "Serie with id %s not found", id);
        userSeriesService.removeSerie(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    public String deleteHTML(@PathVariable("id") Long id) {
        delete(id);
        return "redirect:/series";
    }

}
