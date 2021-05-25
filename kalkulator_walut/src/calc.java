import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class Main {
    public static void main(String[] args) {
        ArrayList<String> currency = new ArrayList<>();
        ArrayList<String> rate = new ArrayList<>();
        Reader reader = new Reader();
        reader.getxml(currency, rate);
        input(currency, rate);

    }
    public static void input(ArrayList<String> currency, ArrayList<String> rate) {
        Scanner in = new Scanner(System.in);
        System.out.println("Podaj kwote w EUR do przekonwertowania:");
        String givenValueString = in.nextLine();
        Float givenValue = null;
        try {
            givenValue = Float.parseFloat(givenValueString);
        } catch (Exception e) {
            System.out.println("Nieprawidlowy input");
            input(currency, rate);
        }
        System.out.println("Podaj docelowa walute:");
        String givenCurrency = in.nextLine();

        String upperGivenCurrency = givenCurrency.toUpperCase();
        if(!currency.contains(upperGivenCurrency)){
            System.out.println("Podana waluta nie istnieje w zbiorze");
            input(currency, rate);
        }
        Integer index = currency.indexOf(upperGivenCurrency);
        Float outputValue = givenValue * Float.parseFloat(rate.get(index));

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.println("Obliczona wartosc: " + df.format(outputValue) + " " + upperGivenCurrency);
    }
}

class Reader {
    public void getxml(ArrayList<String> currency, ArrayList<String> rate) {
        try {
            File file = new File("eurofxref-daily.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Cube");

            for (int s = 0; s < nodeList.getLength(); s++) {
                Node node = nodeList.item(s);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    if (element.hasAttribute("currency")) {
                        currency.add(element.getAttributes().getNamedItem("currency").getTextContent());
                        rate.add(element.getAttributes().getNamedItem("rate").getTextContent());
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



