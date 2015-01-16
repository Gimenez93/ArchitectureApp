package cat.udl.eps.softarch.hello.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cat.udl.eps.softarch.hello.model.Serie;
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

	@Override
	public void initializeRepository() {
//		RestTemplate restTemplate = new RestTemplate();
//		Map<String,String> urlVariables = new HashMap();
//		urlVariables.put("code", "fff2c912374f99a84990aceedaabd492a34a6d5f2a0d93d37ba5b45429774cb4");
//		urlVariables.put("client_id", "744bdacfc4b5c2146d335571bb3dc8c050e65a92023bd092e852dc3f39052ecb");
//		urlVariables.put("client_secret", "23f865944376942cd880c1909cb124f3a29dd6febdccf267a4f163d9b3155866");
//		urlVariables.put("redirect_uri", "urn:ietf:wg:oauth:2.0:oob");
//		urlVariables.put("grant_type", "authorization_code");
//		URI url = null;
//		try {
//			url = new URI("https://api.trakt.tv/oauth/token");
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ResponseEntity<String> response = restTemplate.postForEntity(url, urlVariables,String.class);
//		JSONObject jObject = null;
//		String access_token = null;
//		logger.error("sweet flow");
//		try {
//			jObject = new JSONObject(response.getBody().toString());
//			access_token = jObject.get("access_token").toString();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		logger.error(access_token);
//		
//		Client client = ClientBuilder.newClient();
//		Response response = client.target("https://api.trakt.tv")
//		  .path("/oauth/authorize?response_type=code&client_id=744bdacfc4b5c2146d335571bb3dc8c050e65a92023bd092e852dc3f39052ecb&redirect_uri=urn:ietf:wg:oauth:2.0:oob")
//		  .request(MediaType.TEXT_PLAIN_TYPE)
//		  .get();
//
//		logger.error("status: " + response.getStatus());
//		logger.error("headers: " + response.getHeaders());
//		logger.error("body:" + response.readEntity(String.class));
//		
//		
//		client = ClientBuilder.newClient();
//		Entity payload = Entity.json("{  'code': 'fd0847dbb559752d932dd3c1ac34ff98d27b11fe2fea5a864f44740cd7919ad0',  'client_id': '9b36d8c0db59eff5038aea7a417d73e69aea75b41aac771816d2ef1b3109cc2f',  'client_secret': 'd6ea27703957b69939b8104ed4524595e210cd2e79af587744a7eb6e58f5b3d2',  'redirect_uri': '://savewalterwhite.com/auth/callback',  'grant_type': 'authorization_code'}");
//		response = client.target("https://api.trakt.tv")
//		  .path("/oauth/token")
//		  .request(MediaType.APPLICATION_JSON_TYPE)
//		  .post(payload);
//
//		System.out.println("status: " + response.getStatus());
//		System.out.println("headers: " + response.getHeaders());
//		System.out.println("body:" + response.readEntity(String.class));
		
		RestTemplate restTemplate = new RestTemplate();
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://services.tvrage.com/feeds/show_list.php")
		  .request(MediaType.APPLICATION_ATOM_XML_TYPE)
		  .get();
		logger.error(response.readEntity(String.class));
	}
}
