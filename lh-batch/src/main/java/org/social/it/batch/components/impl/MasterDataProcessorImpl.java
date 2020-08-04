package org.social.it.batch.components.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.social.it.batch.bean.HospitalCsvBean;
import org.social.it.batch.components.MasterDataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.it.entity.ContactEntity;
import org.social.it.entity.HospitalEntity;
import org.social.it.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class MasterDataProcessorImpl implements MasterDataProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDataProcessorImpl.class);

    @Autowired
    private HospitalRepository hospitalRepository;

    public void readData(File file) throws IOException {
        hospitalRepository.deleteAll();
        LOG.info("Cleaned up master data from DB");

        CsvToBean<HospitalCsvBean> csvToBean;
        try (FileReader filereader = new FileReader(file)) {
            HeaderColumnNameMappingStrategy<HospitalCsvBean> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(HospitalCsvBean.class);
            csvToBean = new CsvToBeanBuilder<HospitalCsvBean>(filereader)
                    .withType(HospitalCsvBean.class)
                    .withMappingStrategy(mappingStrategy)
                    .build();

            csvToBean.iterator().forEachRemaining((e) -> {
                if (e.name.trim().length() > 0) {
                    ContactEntity contactEntity = new ContactEntity(e.address,
                            e.district,
                            "West Bengal",
                            "India",
                            -1, e.phone);
                    HospitalEntity hospitalEntity = new HospitalEntity(e.name, new String[]{e.alias1, e.alias2, e.alias3},
                            e.additionalInfo, e.facility, contactEntity, e.isPrivate.toUpperCase().equals("Y"));
                    hospitalRepository.save(hospitalEntity);
                    LOG.info("Loading data for {}", e.name);
                }
            });
        }
    }


}
