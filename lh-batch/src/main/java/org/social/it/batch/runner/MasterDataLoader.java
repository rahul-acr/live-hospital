package org.social.it.batch.runner;

import org.social.it.batch.components.MasterDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;

@Lazy
@Component("MasterDataLoader")
public class MasterDataLoader implements Runner {

    @Autowired
    private MasterDataProcessor masterDataProcessor;

    @Override
    public void run(String[] args) throws Exception {
        masterDataProcessor.readData(new File(args[1]));
    }
}
