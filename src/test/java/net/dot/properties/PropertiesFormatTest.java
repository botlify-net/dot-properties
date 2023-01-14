package net.dot.properties;

import net.dot.properties.exceptions.PropertiesAreMissingException;
import net.dot.properties.exceptions.NoJavaEnvFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.regex.Pattern;

import static net.dot.properties.DotProperties.logger;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropertiesFormatTest {

    enum TestEnum {
        FOO,
        BAR
    }

    @Test
    public void testFromEnum() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException, IllegalAccessException {
        PropertiesFormat propertiesFormat = new PropertiesFormat("propertyEnum", TestEnum.class);
        DotProperties dotProperties = new DotProperties.Builder()
                .requires(propertiesFormat)
                .setPath(".properties.test")
                .build();

    }

    // Test Default Format

    @Test
    public void testMongoDBFormat() {
        final String localMongoUrl = "mongodb://localhost:27017";
        final String localMongoUrlWithDatabase = "mongodb://localhost:27017/test";
        final String remoteMongoUrl = "mongodb://user:password@localhost:27017";
        final String remoteMongoUrlWithDatabase = "mongodb://user:password@localhost:27017/test";
        final String remoteMongoUrlWithDatabaseAndOptions = "mongodb://user:password@localhost:27017/test?authSource=admin&authMechanism=SCRAM-SHA-1";
        final String remoteMongoUrlWithDatabaseAndOptionsAndReplicaSet = "mongodb://user:password@localhost:27017/test?authSource=admin&authMechanism=SCRAM-SHA-1&replicaSet=rs0";

        final String[] mongoUrls = {
            localMongoUrl,
            localMongoUrlWithDatabase,
            remoteMongoUrl,
            remoteMongoUrlWithDatabase,
            remoteMongoUrlWithDatabaseAndOptions,
            remoteMongoUrlWithDatabaseAndOptionsAndReplicaSet
        };

        Pattern pattern = Pattern.compile(PropertiesFormat.Default.MONGO_URL.getRegex());
        for (String str : mongoUrls) {
            logger.info("Testing MongoDB URL: {}", str);
            assertTrue(pattern.matcher(str).matches());
        }
    }

}