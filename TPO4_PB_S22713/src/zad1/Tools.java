/**
 *
 *  @author Perliński Bartłomiej S22713
 *
 */

package zad1;


import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tools {

    public static Options createOptionsFromYaml(String fileName){
        Yaml yaml = new Yaml();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> tempMap = yaml.load(br);

        String host = (String) tempMap.get("host");
        int port = (int) tempMap.get("port");
        boolean concurMode = (boolean) tempMap.get("concurMode");
        boolean showSendRes = (boolean) tempMap.get("showSendRes");
        Map<String, List<String>> clientsMap = (LinkedHashMap<String, List<String>>) tempMap.get("clientsMap");

        return new Options(host,port,concurMode,showSendRes,clientsMap);
    }
}
