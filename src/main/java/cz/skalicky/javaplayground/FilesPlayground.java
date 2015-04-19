package cz.skalicky.javaplayground;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesPlayground {

    public static void main(String[] args) {

        final String BASE_PATH = "/home/tom/Documents/Development/Java/SourceFiles/java8/consumer/fooDir";
        Path sPath = Paths.get(BASE_PATH + File.separator + "sourceDir" + File.separator + "testDir.txt");
        Path dPath = Paths.get(BASE_PATH + File.separator + "targetDir" + File.separator + "barDir");
        try {
            Files.move(sPath, dPath, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error Happened!");
        }

    }

}
