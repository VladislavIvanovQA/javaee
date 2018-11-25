package gwt.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import gwt.client.service.ApplicationServiceAsync;

import javax.inject.Inject;

import static gwt.client.gin.ApplicationInjector.INSTANCE;

public class CreateUsersView extends Composite {
    @UiTemplate("CreateUsersPart.ui.xml")
    public interface CreateUsersViewUiBinder extends UiBinder<HTMLPanel, CreateUsersView> {
    }

    /**
     * TODO: Не могу все понять как правильно сделать html форму, которая будет принимать файл и отправлять multipart..
     */

//    @UiField
//    FileUpload file;


//    @UiHandler("submit")
//    void clickHandler(ClickEvent click){
//        String boundary = "WebKitFormBoundaryNjTZ1FnPRGk4yyCA";
//
//        RequestBuilder builder = new RequestBuilder( RequestBuilder.POST, "/upload" );
//        builder.setHeader( "Content-Type", "multipart/form-data; charset=utf-8; boundary=" + boundary );
//
//        String CRLF = "\r\n";
//        String data = "--" + boundary + CRLF;
////        data += "--" + boundary + CRLF;
//
//        data += "Content-Disposition: form-data; ";
//        data += "name=\"file\"";
////        data += "filename=\"" + file.getFilename() + "\"" + CRLF + CRLF;
////        data += file + CRLF;
////        data += "--" + boundary + "--" + CRLF;
//        try {
//            builder.sendRequest(data, new RequestCallback() {
//                @Override
//                public void onResponseReceived(Request request, Response response) {
//                    Window.alert(response.getText());
//                }
//
//                @Override
//                public void onError(Request request, Throwable throwable) {
//
//                }
//            });
//        } catch (RequestException e) {
//            e.printStackTrace();
//        }
//    }

    private static CreateUsersViewUiBinder ourUiBinder = INSTANCE.getUsers();

    private ApplicationServiceAsync service;

    @Inject
    public CreateUsersView(ApplicationServiceAsync service) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
    }
}