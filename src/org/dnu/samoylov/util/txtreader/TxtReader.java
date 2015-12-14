package org.dnu.samoylov.util.txtreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class TxtReader {

    public void readFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();
            while (line != null) {
                handleLine(line);
                line = br.readLine();
            }

        }
    }


    protected abstract void handleLine(String line);
}
