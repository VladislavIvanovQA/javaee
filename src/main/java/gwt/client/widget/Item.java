package gwt.client.widget;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class Item extends Composite {
    
    public static ItemUiBinder getOurUiBinder() {
        return ourUiBinder;
    }

    public static void setOurUiBinder(ItemUiBinder ourUiBinder) {
        Item.ourUiBinder = ourUiBinder;
    }

    public interface ItemUiBinder extends UiBinder<HTMLPanel, Item> {
    }

    private static ItemUiBinder ourUiBinder = GWT.create(ItemUiBinder.class);
    
    @UiField
    static Element title;
    @UiField
    static Element description;

    public Item() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public String getTitle() {
        return title.getInnerText();
    }

    public void setTitle(String s, String href) {
        title.setInnerText(s);
        title.setAttribute("href", href);
    }

    public String getDescription() {
        return description.getInnerText();
    }

    public void setDescription(String s) {
        description.setInnerText(s);
    }
}
