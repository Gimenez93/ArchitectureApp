package cat.udl.eps.softarch.hello.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cat.udl.eps.softarch.hello.service.UserSeriesService;
import cat.udl.eps.softarch.hello.utils.XQueryHelper;
import cat.udl.eps.softarch.hello.utils.XQueryHelper.Show;
import cat.udl.eps.softarch.hello.utils.XQueryHelper.ShowDTO;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
	final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired UserSeriesService userSeriesService;
	
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
	
	@RequestMapping(value = "/result", method = RequestMethod.GET)
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
		model.addAttribute("json", jObj);
		return "result";
	}
	
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String getDetailedShow(HttpServletRequest request, Model model){
     	JSONObject total = new JSONObject();
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
			total.append("show", jObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("json",total);
		return "result";
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.POST)
	public String addDetailedShow(HttpServletRequest request, Model model){
		logger.error("YEY MUTHAFACKAA!!!ª!!");
     	JSONObject total = new JSONObject();
		ShowDTO show = (ShowDTO)XQueryHelper.getEspecificShow(request.getParameter("title"), ShowDTO.class);
		userSeriesService.addSerie(show);
		return "redirect:/series";
	}
	
	
	
	
	
}
