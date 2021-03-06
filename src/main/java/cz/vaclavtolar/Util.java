package cz.vaclavtolar;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

/**
 * Created by vasek on 24. 6. 2016.
 */
class Util {

    public static final String DIR = "C:/work/TeaVMTest/src/main/resources/";

    public static String KEYSTORE = DIR + "pkcs.p12";
    public static char[] PASSWORD = "Heslo123,".toCharArray();
    public static String SRC = DIR + "test.pdf";
    public static String DEST = DIR + "test_signed_%s.pdf";

    public static void sign(String src, String dest,
              java.security.cert.Certificate[] chain,
              PrivateKey pk, String digestAlgorithm, String provider,
              MakeSignature.CryptoStandard subfilter,
              String reason, String location) throws IOException, DocumentException, GeneralSecurityException, DocumentException {
        // Creating the reader and the stamper
        byte[] pdfBytes = Files.readAllBytes(Paths.get(SRC));
//        PdfReader reader = new PdfReader(new FileInputStream(src));
        PdfReader reader = new PdfReader(pdfBytes);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '0');
        // Creating the appearance
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setReason(reason);
        appearance.setLocation(location);
        appearance.setVisibleSignature(new Rectangle(36f, 748f, 144f, 780f), 1, "sig");
        // Creating the signature
        BouncyCastleDigest digest = new BouncyCastleDigest();
        PrivateKeySignature signature = new PrivateKeySignature(pk, digestAlgorithm, provider);
        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, subfilter);
    }

    public static void test() throws GeneralSecurityException, IOException, DocumentException {
        String KEYSTORE = DIR + "pkcs.p12";
        char[] PASSWORD = "Heslo123,".toCharArray();
        String SRC = DIR + "test.pdf";
        String DEST = DIR + "test_signed_%s.pdf";

        Provider provider = new BouncyCastleProvider();
        Security.addProvider(provider);

        KeyStore ks = KeyStore.getInstance("pkcs12");
        ks.load(new FileInputStream(KEYSTORE), PASSWORD);

        //        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        //        ks.load(new FileInputStream(KEYSTORE), PASSWORD);
        PrivateKey pk = (PrivateKey) ks.getKey(ks.aliases().nextElement(), PASSWORD);
        java.security.cert.Certificate[] chain = ks.getCertificateChain(ks.aliases().nextElement());

        Util app = new Util();
        app.sign(SRC, java.lang.String.format(DEST, 1), chain, pk, DigestAlgorithms.SHA256, provider.getName(), MakeSignature.CryptoStandard.CMS, "Test 1", "Ghent");
        app.sign(SRC, java.lang.String.format(DEST, 2), chain, pk, DigestAlgorithms.SHA512, provider.getName(), MakeSignature.CryptoStandard.CMS, "Test 2", "Ghent");
        app.sign(SRC, java.lang.String.format(DEST, 3), chain, pk, DigestAlgorithms.SHA256, provider.getName(), MakeSignature.CryptoStandard.CADES, "Test 3", "Ghent");
        app.sign(SRC, java.lang.String.format(DEST, 4), chain, pk, DigestAlgorithms.RIPEMD160, provider.getName(), MakeSignature.CryptoStandard.CADES, "Test 4", "Ghent");

    }


}
