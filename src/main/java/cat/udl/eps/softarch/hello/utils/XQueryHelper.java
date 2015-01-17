package cat.udl.eps.softarch.hello.utils;
 
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQItem;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * Created by http://rhizomik.net/~roberto/
 */
public class XQueryHelper {
	final Logger logger = LoggerFactory.getLogger(XQueryHelper.class);
 
    private XQPreparedExpression expr;
    private XQConnection         conn;
 
    private JAXBContext          jaxbContext;
    private Unmarshaller         jaxbUnmarshaller;
 
    static final String apiURL1   = "http://services.tvrage.com/feeds/show_list.php";
    
    
 
    @XmlRootElement
    public static class Show {
        @XmlElement String title;
 
        @Override
        public String toString() {
            return "Title: "+title;
        }
        
        public String getTitle(){
            return title;
        }
    }
 
    @XmlRootElement
    public static class ShowDTO {
        @XmlElement String title;
        @XmlElement String seasons;
        @XmlElement String country;
        @XmlElement String status;
        @XmlElement String airday;
        @XmlElement String genre;
        @XmlElement String link;
 
        @Override
        public String toString() {
            return "Title: "+title+ "Status: "+status+"Seasons: " +seasons + "Country: " +country + "Airday: " +airday + "Genere:" + genre;
        }
        
        public String getTitle(){
            return title;
        }

		public String getSeasons() {
			return seasons;
		}

		public String getCountry() {
			return country;
		}

		public String getStatus() {
			return status;
		}

		public String getAirday() {
			return airday;
		}

		public String getGenre() {
			return genre;
		}

		public String getLink() {
			return link;
		}
        
        
    }   
    
    
    	
    
    
    public XQueryHelper(String xquery, URL url, Class c)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, XQException, IOException, JAXBException {
        URLConnection urlconn = url.openConnection();
        urlconn.setReadTimeout(50000);
 
        XQDataSource xqds = (XQDataSource) Class.forName("net.sf.saxon.xqj.SaxonXQDataSource").newInstance();
        this.conn = xqds.getConnection();
        this.expr = conn.prepareExpression(xquery);
        this.expr.bindDocument(new javax.xml.namespace.QName("doc"), urlconn.getInputStream(), null, null);
 
        this.jaxbContext = JAXBContext.newInstance(c);
        this.jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }
 
    ArrayList<Show> passShows() {
        ArrayList<Show> shows = new ArrayList<Show>();
        try {
            XQResultSequence rs = this.expr.executeQuery();
            while (rs.next()) {
                XQItem item = rs.getItem();
                Show show = (Show) jaxbUnmarshaller.unmarshal(item.getNode());
                shows.add(show);
            }

        }
        catch (Exception e) {
            //log.log(Level.SEVERE, e.getMessage());
        }
        finally 
        { 
        	close();
        }
        
        return shows;
    }
 
    
    
    
    
    private void close() {
        try {
            this.expr.close();
            this.conn.close();
        } catch (XQException e) {
            //log.log(Level.SEVERE, e.getMessage());
        }
    }
    
    public static ArrayList<Show> getShows(String title, Class c) {
        try {
            XQueryHelper xQueryHelper = new XQueryHelper(createQuery(title), new URL(apiURL1), c);
            return xQueryHelper.passShows();
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Show>();
        }
    }

	private static String createQuery(String title) {
		String query = " declare variable $doc external;\n"
	            + "for $x in $doc//shows/show[matches(name, '"
	    		+ title
	            + "', \"i\")]\n"
	    	    + "return\n"
	    	    + "<show>\n"
	            + "<title>{$x//name/text()}</title>\n"
	    	    +"</show>";
		return query;
	}
	
	public static ShowDTO getEspecificShow(String title, Class c){
		XQueryHelper xQueryHelper = null;
        try {
        	URI uri = new URI("http", "services.tvrage.com", "/feeds/full_search.php","show="+title,null);
        	URL url = uri.toURL();
		xQueryHelper = new XQueryHelper(createQuerySpecific(), url, c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xQueryHelper.createShow();
	}


	private static String createQuerySpecific() {
		String query = " declare variable $doc external;\n"
	            + "for $x in $doc//Results/show[1]\n"
	    	    + "return\n"
	    	    + "<showDTO>\n"
	            + "<title>{$x//name/text()}</title>\n"
	            + "<seasons>{$x//seasons/text()}</seasons>\n"
	            + "<country>{$x//country/text()}</country>\n"
	            + "<status>{$x//status/text()}</status>\n"
	            + "<airday>{$x//airday/text()}</airday>\n"
	            + "<genre>{$x//genres/genre[1]/text()}</genre>\n"
	            + "<link>{$x//link/text()}</link>\n"
	    	    +"</showDTO>";
		return query;
	}

	ShowDTO createShow() {
		ShowDTO show = null;
		try {
			XQResultSequence rs = this.expr.executeQuery();
            while (rs.next()) {
                XQItem item = rs.getItem();
                show = (ShowDTO) jaxbUnmarshaller.unmarshal(item.getNode());
            }
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{close();}
		return show;
	}
	
    
 
}