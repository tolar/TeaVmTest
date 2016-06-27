package cz.vaclavtolar;

import com.itextpdf.text.DocumentException;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLButtonElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Client {

    private static HTMLDocument document = Window.current().getDocument();
    private static HTMLButtonElement helloButton = document.getElementById("hello-button").cast();
    private static HTMLElement responsePanel = document.getElementById("response-panel");
    private static HTMLElement thinkingPanel = document.getElementById("thinking-panel");

    private Client() {
    }

    public static void main(String[] args) {
        helloButton.listenClick(new EventListener<MouseEvent>() {
            @Override
            public void handleEvent(MouseEvent evt) {
                sayHello();
                try {
                    Util.test();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void sayHello() {


        helloButton.setDisabled(true);
        thinkingPanel.getStyle().setProperty("display", "");
        final XMLHttpRequest xhr = XMLHttpRequest.create();
        xhr.onComplete(new Runnable() {
            @Override
            public void run() {
                responsePanel.appendChild(document.createElement("div").withText(xhr.getResponseText()));
                helloButton.setDisabled(false);
                thinkingPanel.getStyle().setProperty("display", "none");
            }
        });
        xhr.open("GET", "hello");
        xhr.send();
    }




}

