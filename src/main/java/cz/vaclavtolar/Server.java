package cz.vaclavtolar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vasek on 18. 6. 2016.
 */
@WebServlet("/hello")
public class Server extends HttpServlet {
    private static final long serialVersionUID = 1L;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //test();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            return;
        }
        String userAgent = req.getHeader("User-Agent");
        resp.getWriter().write("Hello, " + userAgent);
    }



}
