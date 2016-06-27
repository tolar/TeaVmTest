package cz.vaclavtolar;

import com.itextpdf.text.DocumentException;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static junit.framework.TestCase.fail;


/**
 * Created by vasek on 24. 6. 2016.
 */
public class ServerTest {

    @Test
    public void testPdfSign() {
        try {
            Util.test();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
            e.printStackTrace();
        } catch (DocumentException e) {
            fail(e.getMessage());
            e.printStackTrace();
        }
    }
}
