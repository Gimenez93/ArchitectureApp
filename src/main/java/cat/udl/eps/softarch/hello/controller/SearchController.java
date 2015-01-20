package cat.udl.eps.softarch.hello.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Preconditions;

import cat.udl.eps.softarch.hello.model.User;
import cat.udl.eps.softarch.hello.repository.UserRepository;
import cat.udl.eps.softarch.hello.service.UserGreetingsService;
import cat.udl.eps.softarch.hello.service.UserSeriesService;
import cat.udl.eps.softarch.hello.utils.XQueryHelper;
import cat.udl.eps.softarch.hello.utils.XQueryHelper.Show;
import cat.udl.eps.softarch.hello.utils.XQueryHelper.ShowDTO;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
	final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired UserSeriesService userSeriesService;
	@Autowired UserRepository userRepository;
	@Autowired UserGreetingsService userGreetingsService;
	
	
//	@RequestMapping(method = RequestMethod.GET, produces = "text/html")
//    public ModelAndView search() {
//    	//userSeriesService.initializeRepository();
//        return new ModelAndView("search");
//    }
	@RequestMapping(method = RequestMethod.GET)
    public String controllerMethod(HttpServletRequest request){  
        // this way you get value of the input you want
        //String pathValue1 = request.getParameter("title");
        //logger.error(pathValue1);
        return "search";
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String controller2Method(HttpServletRequest request, Model model){  
        // this way you get value of the input you want
        String title = request.getParameter("title");
        logger.error(title);
        model.addAttribute("title",title );
        return "redirect:/search/result";
    }
	
	@RequestMapping(value = "/result", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getSearchResult(HttpServletRequest request, Model model){
    	JSONObject jObj = new JSONObject();
		for (Show s : XQueryHelper.getShows(request.getParameter("title"),Show.class)){
        	logger.error(s.getTitle());
        	try {
				jObj.append("title", s.getTitle());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
        }
		model.addAttribute("", jObj);
		try {
			return jObj.getJSONArray("title").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	@RequestMapping(value = "/result", method = RequestMethod.GET, produces = "text/html")
	public String getSearchResultHTML(HttpServletRequest request, Model model){
		JSONObject jObj = new JSONObject(); 
		 		for (Show s : XQueryHelper.getShows(request.getParameter("title"),Show.class)){ 
		         	logger.error(s.getTitle()); 
		         	try { 
		 				jObj.append("title", s.getTitle()); 
		 			} catch (JSONException e) { 
		 				// TODO Auto-generated catch block 
		 				e.printStackTrace(); 
		 			} 
		       
		         } 
		 		model.addAttribute("json", jObj); 
				return "result"; 

	}
	
	
	@RequestMapping(value = "/show", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getDetailedShow(HttpServletRequest request, Model model){
		ShowDTO show = (ShowDTO)XQueryHelper.getEspecificShow(request.getParameter("title"), ShowDTO.class);
        logger.error(show.toString());
        JSONObject jObj = new JSONObject();
		try {
			jObj.append("title", show.getTitle());
			jObj.append("seasons", show.getSeasons());
			jObj.append("country", show.getCountry());
			jObj.append("status", show.getStatus());
			jObj.append("link", show.getLink());
			jObj.append("genre", show.getGenre());
			jObj.append("airday", show.getAirday());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("json",jObj);
			return jObj.toString();
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.POST)
	public String addDetailedShow(HttpServletRequest request, Model model){
		logger.error("YEY MUTHAFACKAA!!!ª!!");
		User u = userGreetingsService.addUser(request.getParameter("username"));
		
     	JSONObject total = new JSONObject();
		ShowDTO show = (ShowDTO)XQueryHelper.getEspecificShow(request.getParameter("title"), ShowDTO.class);
		userSeriesService.addSerie(show);
		
		userGreetingsService.addSerieToUser(u,show);
		return "redirect:/series";
	}
	
    
    @RequestMapping(value = "/addShow", method = RequestMethod.GET, produces = "text/html")
    public String addShowMethod(HttpServletRequest request){  
        // this way you get value of the input you want
        //String pathValue1 = request.getParameter("title");
        //logger.error(pathValue1);
        return "addShow";
    }
	
	
	
	
	
}
