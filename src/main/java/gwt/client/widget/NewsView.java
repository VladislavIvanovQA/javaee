package gwt.client.widget;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
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
                "/news");
        try {
            builder.sendRequest("", new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    toList(response.getText());
                    for (int i = 0; i < newsList.size(); i++) {
                        Item news = new Item();
                        news.setTitle(newsList.get(i).getHtml(), newsList.get(i).getHref());
                        news.setDescription(newsList.get(i).getModif_date());
                        content.add(news);
                    }
                }

                @Override
                public void onError(Request request, Throwable throwable) {
                    for (int i = 0; i < 5; i++) {
                        Item item = new Item();
                        item.setTitle("none" + i, "http://google.ru");
                        item.setDescription("none" + i);
                        content.add(item);
                    }
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
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

        JSONArray productsArray = productsObj.get("items").isArray();

        if (productsArray != null) {
            for (int i = 1; i < productsArray.size(); i++) {
                JSONObject jsonValue = productsArray.get(i).isObject();
                String id = jsonValue.get("id").isString().stringValue();
                String html = jsonValue.get("html").isString().stringValue();
                String modif_date = jsonValue.get("modif_date").isString().stringValue();
                News news = new News(id, searchString(html), searchHref(html), modif_date.substring(0, modif_date.length() - 6));
                newsList.add(news);
            }
        }
    }

    private String searchString(String str){
        int lenght = str.indexOf("<span class=\"main__feed__title\">");
        int endLenght = str.indexOf("</span>");
        return str.substring(lenght + 52, endLenght - 17);
    }

    private String searchHref(String str){
        int lenght = str.indexOf("href");
        int endLenght = str.indexOf("main__feed__link");
        return str.substring(lenght + 6, endLenght - 9);
    }

}