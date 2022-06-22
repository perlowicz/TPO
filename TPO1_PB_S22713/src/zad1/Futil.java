package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.*;

public class Futil {

    static void processDir(String dirName, String resultFileName){
        Path startPath = Paths.get(System.getProperty("user.home")+"/TPO1dir");
        Path resPath = Paths.get("TPO1res.txt");
        Charset inputCoding = Charset.forName("Cp1250");
        Charset outputCoding = StandardCharsets.UTF_8;
        List<Path> pList = new ArrayList<>();

        /*Files.walk() for going through directory*/
        try (Stream<Path> paths = Files.walk(startPath)){
            pList = paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*FileChannel for reading and writing files*/
        try(FileChannel fcOut = FileChannel.open(resPath, CREATE, TRUNCATE_EXISTING, WRITE)){
            for (Path path : pList) {
                try (FileChannel fcIn = FileChannel.open(path)){
                    ByteBuffer bb = ByteBuffer.allocateDirect((int)fcIn.size());
                    fcIn.read(bb);
                    bb.flip();
                    CharBuffer cb = inputCoding.decode(bb);
                    fcOut.write(outputCoding.encode(cb));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
