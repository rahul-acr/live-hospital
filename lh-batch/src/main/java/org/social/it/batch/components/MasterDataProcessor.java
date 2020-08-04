package org.social.it.batch.components;

import java.io.File;
import java.io.IOException;

public interface MasterDataProcessor {

    void readData(File file) throws IOException;

}
