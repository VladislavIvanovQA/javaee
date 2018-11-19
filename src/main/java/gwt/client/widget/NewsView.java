package gwt.client.widget;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import gwt.client.News;
import gwt.client.service.ApplicationServiceAsync;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class NewsView extends Composite {
    @UiTemplate("NewsViewPart.ui.xml")
    public interface NewsViewUiBinder extends UiBinder<HTMLPanel, NewsView> {
    }

    private List<News> newsList = new ArrayList<>();

    @UiField
    HTMLPanel content;

    protected void onLoad(){
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
                "https://www.rbc.ru/v10/ajax/main/region/world/publicher/main_main");
        try {
            builder.setHeader("Access-Control-Allow-Origin", "*");
//            builder.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
//            builder.setHeader("Access-Control-Allow-Headers", "Access-Control-Request-Headers");
            builder.setHeader("Allow", "GET");
            builder.setHeader("Content-Type", "application/json;charset=UTF-8");
//            builder.setHeader("Access-Control-Allow-Credentials", "true");
            builder.sendRequest("", new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {

                    toList(response.getText());
                }

                @Override
                public void onError(Request request, Throwable throwable) {

                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }

        if (newsList.size() != 0){
            for (int i = 0; i < newsList.size(); i++) {
                Item news = new Item();
                news.setTitle(newsList.get(i).getId());
                news.setDescription(newsList.get(i).getHtml());
                content.add(news);
            }
        }else {
            for (int i = 0; i < 5; i++) {
                Item item = new Item();
                item.setTitle("none" + i);
                item.setDescription("none" + i);
                content.add(item);
            }
        }



    }


    private static NewsViewUiBinder ourUiBinder = INSTANCE.getNews();

    private ApplicationServiceAsync service;

    @Inject
    public NewsView(ApplicationServiceAsync service) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
    }

    private void toList(String jsonStr) {

        JSONValue value = JSONParser.parseStrict(jsonStr);

        JSONObject productsObj = value.isObject();

        JSONArray productsArray = productsObj.get("users").isArray();

        if (productsArray != null) {
            for (int i = 0; i < productsArray.size(); i++) {
                JSONObject jsonValue = productsArray.get(i).isObject();
                String id = jsonValue.get("id").isString().stringValue();
                String html = jsonValue.get("html").isString().stringValue();
                String modif_date = jsonValue.get("modif_date").isString().stringValue();
                News news = new News(id, html, modif_date);
                newsList.add(news);
            }
        }
    }

}