package cn.techlog.task_engine.task_resolver;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpTaskResolver implements TaskResolver {
    @Override
    public JSONObject resolve(JSONObject task, Map<String, Object> params) {
        String url = task.getString("url");

        Map<String, Object> parameters = TaskUtils.getParameters(task.getJSONObject("inputs"), params);
        HttpClient httpClient = HttpClients.createDefault();

        if (task.getString("method").equals("get")) {
            List<String> paramPairs = new ArrayList<>();
            for (Map.Entry<String, Object> paramEntry: parameters.entrySet()) {
                paramPairs.add(paramEntry.getKey() + "=" + paramEntry.getValue());
            }
            if (!paramPairs.isEmpty()) {
                url += "?" + String.join("&", paramPairs);
            }
            HttpGet httpGet = new HttpGet(url);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            List<NameValuePair> urlParameters = new ArrayList<>();
            for (Map.Entry<String, Object> paramEntry: parameters.entrySet()) {
                urlParameters.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue().toString()));
            }
            HttpPost httpPost = new HttpPost(url);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
                HttpResponse response = httpClient.execute(httpPost);
                return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
