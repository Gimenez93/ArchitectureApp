package cat.udl.eps.softarch.hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Preconditions;

import cat.udl.eps.softarch.hello.model.User;
import cat.udl.eps.softarch.hello.repository.UserRepository;
import cat.udl.eps.softarch.hello.service.UserGreetingsService;

/**
 * Created by http://rhizomik.net/~roberto/
 */

@Controller
@RequestMapping(value = "/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired UserRepository       userRepository;
    @Autowired UserGreetingsService userGreetingsService;

    // LIST
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> list(@RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        PageRequest request = new PageRequest(page, size);
        return userRepository.findAll(request).getContent();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public ModelAndView listHTML(@RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return new ModelAndView("users", "users", list(page, size));
    }

    // RETRIEVE
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User retrieve(@PathVariable("id") Long id) {
        logger.info("Retrieving user number {}", id);
        Preconditions.checkNotNull(userRepository.findOne(id), "User with id %s not found", id);
        return userGreetingsService.getUserAndGreetings(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView retrieveHTML(@PathVariable( "id" ) Long id) {
        return new ModelAndView("user", "user", retrieve(id));
    }
    
    // DELETE
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        logger.info("Deleting user number {}", id);
        Preconditions.checkNotNull(userRepository.findOne(id), "User with id %s not found", id);
        userGreetingsService.removeUser(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    public String deleteHTML(@PathVariable("id") Long id) {
        delete(id);
        return "redirect:/users";
    }
    
    
    
}
