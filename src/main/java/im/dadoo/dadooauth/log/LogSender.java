package im.dadoo.dadooauth.log;

import java.io.IOException;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogSender {

	@Autowired
	private ObjectMapper mapper;
	
	private CloseableHttpClient hc;
	
	private String baseUrl;
	
	
	public LogSender(String baseUrl) {
		this.baseUrl = baseUrl;
		this.hc = HttpClients.createDefault();
	}
	
	public void send(AuthLog log) {
		CloseableHttpResponse res = null;
		try {
			HttpPost hp = new HttpPost(baseUrl);
			StringEntity se = new StringEntity(this.mapper.writeValueAsString(log));
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			
			System.out.println(se.toString());
			hp.setEntity(se);
			res = this.hc.execute(hp);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (res != null)
				try {
					res.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
