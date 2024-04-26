import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    static List<Country> countries = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void RunMenu(){
        System.out.println("What do you wish to do?");
        System.out.println("1- Sort countries by name");
        System.out.println("2- Sort countries by population");
        System.out.println("3- Sort countries by area");
        String task = input.next();
        switch(task)
        {
            case "1":
            {
                for(Country country : sortByName())
                    country.toString();
                break;
            }
            case "2":
            {
                for(Country country : sortByPopulation())
                    country.toString();
                break;
            }
            case "3":
            {
                for(Country country : sortByArea())
                    country.toString();
                break;
            }
        }

    }

    public static List<Country> sortByName(){
        List<Country> sortedByName = new ArrayList<>(countries);
        sortedByName.sort(Comparator.comparing(Country::getName));
        return sortedByName;
    }

    public static List<Country> sortByPopulation(){
        List<Country> sortedByPopulation = new ArrayList<>(countries);

        for (int i = 0; i < sortedByPopulation.size() - 1; i++)
            for (int j = 0; j < sortedByPopulation.size() - i - 1; j++)
                if (sortedByPopulation.get(j).getPopulation() < sortedByPopulation.get(j + 1).getPopulation())
                {
                    Country temp = sortedByPopulation.get(j);
                    sortedByPopulation.set(j, sortedByPopulation.get(j + 1));
                    sortedByPopulation.set(j + 1, temp);
                }
        return sortedByPopulation;
    }

    public static List<Country> sortByArea(){
        List<Country> sortedByArea = new ArrayList<>(countries);
        for (int i = 0; i < sortedByArea.size() - 1; i++)
            for (int j = 0; j < sortedByArea.size() - i - 1; j++)
                if (sortedByArea.get(j).getArea() < sortedByArea.get(j + 1).getArea())
                {
                    Country temp = sortedByArea.get(j);
                    sortedByArea.set(j, sortedByArea.get(j + 1));
                    sortedByArea.set(j + 1, temp);
                }
        return sortedByArea;
    }

    public static void setUp() throws IOException {
        File countryList = new File("src/Resources/country-list.html");
        Document doc = Jsoup.parse(countryList, "UTF-8");

        for(Element country : doc.getElementsByClass("country")) {
            String name = country.getElementsByClass("country-name").text();
            Element info = country.getElementsByClass("country-info").first();
            String cap = info.getElementsByClass("country-capital").text();
            String p = info.getElementsByClass("country-population").text();
            String area = info.getElementsByClass("country-area").text();
            Country c = new Country(name, cap, Integer.parseInt(p), Double.parseDouble(area));
            countries.add(c);
        }

    }

    public static void main(String[] args) throws IOException {
        setUp();
        RunMenu();
    }
}
