package gwt.client.widget.image;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface ApplicationImages extends ClientBundle {
    @Source("styles.css")
    Style style();

    @Source("exclamation.gif")
    ImageResource field_invalid();

    public interface Style extends CssResource{
        String dockLayoutPanel();
        String rightFlowPanel();
        String footerPanel();
        String menuPanel();
        String search_form();
        String search_btn();
        String search_box();
        //        String leftMenuPanel();
        String logoImg();
        String logoPanel();
        String currencies();
        String news();
        String loginpanel();
        String loginform();
        String logininput();
        String loginsubmitcontainer();
        String loginsubmit();
        String adminpanel();
        String admingrid();
        String panelmargins();
        String mainDeckPanel();
    }
}
