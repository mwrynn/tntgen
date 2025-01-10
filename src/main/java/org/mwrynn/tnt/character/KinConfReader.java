package org.mwrynn.tnt.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class KinConfReader {
    private static final String KIN_CONF_RESOURCE_DEFAULT = "/kin_conf.yaml";
    private KinConf kinConf;

    public KinConfReader() throws IOException {
        this(KIN_CONF_RESOURCE_DEFAULT, false);
    }

    public KinConfReader(String path, boolean isFile) throws IOException {
        if (isFile) {
            // Load from external file path
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            kinConf = mapper.readValue(new File(path), KinConf.class);
        } else {
            // Load from classpath
            try (InputStream inputStream = KinConfReader.class.getResourceAsStream(path)) {
                if (inputStream == null) {
                    throw new IllegalArgumentException("Resource not found: " + path);
                }
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                kinConf = mapper.readValue(inputStream, KinConf.class);
            }
        }
    }

    public KinConf getKinConf() {
        return kinConf;
    }
}
