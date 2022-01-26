package cn.techlog.task_engine.resolver;

import cn.techlog.task_engine.utils.TaskUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpTaskResolver implements TaskResolver {
    @Override
    public JSONObject resolve(JSONObject task, Map<String, Object> params) throws Exception {
        String url = task.getString("url");
        Map<String, Object> parameters = TaskUtils.getParameters(task.getJSONObject("inputs"), params);
        HttpClient httpClient = HttpClients.createDefault();

        if (task.getString("method").equals("get")) {
            return doGet(url, parameters, httpClient);
        } else {
            return doPost(url, parameters, httpClient);
        }
    }

    private JSONObject doPost(String url, Map<String, Object> parameters, HttpClient httpClient) throws IOException {
        List<NameValuePair> urlParameters = parameters.entrySet().stream()
                .map(paramEntry -> new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue().toString()))
                .collect(Collectors.toList());
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
            HttpResponse response = httpClient.execute(httpPost);
            return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private JSONObject doGet(String url, Map<String, Object> parameters, HttpClient httpClient) throws IOException {
        List<String> paramPairs = parameters.entrySet().stream()
                .map(paramEntry -> paramEntry.getKey() + "=" + paramEntry.getValue()).collect(Collectors.toList());
        url = paramPairs.isEmpty()? url: url + "?" + String.join("&", paramPairs);
        try {
            HttpResponse response = httpClient.execute(new HttpGet(url));
            return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
