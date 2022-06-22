package zad1;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class Service {

    private static final String API_KEY = "5e9a4161ee36492f03e33809b99daf75";
    private String kraj;
    private Currency currency;
    private String miasto;

    public Service(String kraj){
        this.kraj = kraj;
        this.currency = findCurrency(kraj);
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getMiasto() {
        return miasto;
    }

    public String getKraj() {
        return kraj;
    }

    public String getWeather(String miasto){
        this.miasto = miasto;
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + miasto + "," + kraj + "&appid=" + API_KEY + "&units=metric";
        String result = "";
        URL url = null;

        try {
            url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null){
                result += line;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Double getRateFor(String kod_waluty){

        URL url = null;

        String info = "";

        try {
            url = new URL("https://api.exchangerate.host/convert?from=" + this.currency.getCurrencyCode() + "&to=" + kod_waluty);
//            url = new URL("https://api.exchangerate.host/symbols");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObject = root.getAsJsonObject();


            info = jsonObject.get("result").getAsString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Double.parseDouble(info);
    }

    public Double getNBPRate(){
        if (findInURL("http://www.nbp.pl/kursy/kursya.html", this.currency) == 0.0)
            return findInURL("http://www.nbp.pl/kursy/kursyb.html", this.currency);
        else return findInURL("http://www.nbp.pl/kursy/kursya.html", this.currency);
    }

    private static Currency findCurrency(String country){
        Locale temp = new Locale(country);

        Map<String, Locale> collect = Arrays.stream(Locale.getAvailableLocales())
                .collect(
                        Collectors.toMap(
                                c -> c.getDisplayCountry(temp),
                                c -> c,
                                (x, y) -> x
                        )
                );

        Locale firstLocale = collect.getOrDefault(country, null);
        return Currency.getInstance(firstLocale);
    }

    private static Double findInURL(String url, Currency source){
        Scanner scanner = null;
        StringBuilder sb = null;
        Map<Currency, Double> myPage = new LinkedHashMap<>();
        try {
            scanner = new Scanner(new URL(url).openStream());
            sb = new StringBuilder();
            while (scanner.hasNext()){
                sb.append(scanner.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = sb.toString();
        String[] split = s.split("<tbody>")[1].split("</tbody>")[0].split("</tr>");
        for (String s1 : split) {
            s1 = s1.replaceAll("<tr><tdclass=\"left\">","");
            s1 = s1.replaceAll("</td><tdclass=\"right\">"," ");
            s1 = s1.replaceAll("</td>","");
            String[] s2 = s1.split(" ");
            String[] split1 = s2[1].split("[0-9]+");
            myPage.put(Currency.getInstance(split1[1]),Double.parseDouble(s2[2].replaceAll(",",".")));
        }
        if (source.getCurrencyCode().equals("PLN"))
            return 1.0;
        else if (myPage.get(source) == null)
            return 0.0;
        else return (1.0/myPage.get(source));
    }
}  
