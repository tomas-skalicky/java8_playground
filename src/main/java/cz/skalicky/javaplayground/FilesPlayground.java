package cz.skalicky.javaplayground;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesPlayground {

    public static void main(String[] args) {

        final String BASE_PATH = "/home/tom/Documents/development/Java/SourceFiles/java8/java8_playground/fooDir";
        Path sPath = Paths.get(BASE_PATH + File.separator + "sourceDir" + File.separator + "testFile.txt");
        Path dPath = Paths.get(BASE_PATH + File.separator + "targetDir" + File.separator + "barDir");
        try {
            Files.copy(sPath, dPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error Happened!");
        }

    }

}
