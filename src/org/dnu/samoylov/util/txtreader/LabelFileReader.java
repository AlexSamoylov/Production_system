package org.dnu.samoylov.util.txtreader;

import org.dnu.samoylov.model.PsEnumLabel;
import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.storage.input.PsEnumStorage;
import org.dnu.samoylov.storage.input.PsLabelStorage;

import java.util.MissingFormatArgumentException;

public class LabelFileReader extends TxtReader {
    private static final String LABEL = "label";
    private static final String ENUM = "enum";


    @Override
    protected void handleLine(String line) {
        if (line.startsWith(LABEL)) {
            readLabel(line.substring(LABEL.length()+1));
        } else {
            if (line.startsWith(ENUM)) {
                readEnum(line.substring(ENUM.length()+1));
            } else {
                throw new MissingFormatArgumentException("other line must be start with " + LABEL + "or" + ENUM + "!!");
            }
        }
    }

    private void readEnum(String enumString) {
        final String[] prefixAndNames = enumString.split(":");
        if (prefixAndNames.length!=2) {
            throw new MissingFormatArgumentException("bad enum format in string:"+enumString);
        }
        String prefix = prefixAndNames[0];
        final String[] names = prefixAndNames[1].split(", ");

        final PsEnumLabel newEnum = PsEnumLabel.create(prefix, names);
        PsEnumStorage.getInstance().add(newEnum);
    }

    private void readLabel(String name) {
        PsLabelStorage.getInstance().add(PsLabel.create(name));
    }
}
