package org.mwrynn.tnt.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class KinConfReader {
    private static final String KIN_CONF_PATH_DEFAULT = "src/main/resources/kin_conf.yaml";
    private String kinConfPath;
    private KinConf kinConf;

    public KinConfReader() throws IOException {
        this(KIN_CONF_PATH_DEFAULT);
    }

    public KinConfReader(String kinConfPath) throws IOException {
        this.kinConfPath = kinConfPath;

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        kinConf = mapper.readValue(new File(kinConfPath), KinConf.class);
    }

    public KinConf getKinConf() {
        return kinConf;
    }
}
