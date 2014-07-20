import com.tsykul.joggingtracker.test.util.Integration

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
runner {
    if (!System.properties.containsKey('test.include.integration')) {
        exclude Integration
    }
}