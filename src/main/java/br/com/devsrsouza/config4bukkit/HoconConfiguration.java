package br.com.devsrsouza.config4bukkit;

import com.typesafe.config.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class HoconConfiguration extends FileConfiguration {
    protected static final String COMMENT_PREFIX = "// ";

    @Override
    public String saveToString() {
        String render = ConfigFactory.parseMap(getValues(false)).root().render();

        return buildHeader() + render;
    }

    @Override
    public void loadFromString(String contents) {
        Validate.notNull(contents, "Contents cannot be null");

        Config config = ConfigFactory.parseString(contents, configParseOptions());

        if (options().resolve()) config = config.resolve(ConfigResolveOptions.noSystem());

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }

        convertMapsToSections(map, this);
    }

    @Override
    protected String buildHeader() {
        String header = options().header();
        if(header != null) {
            String[] lines = options().header().split("\n");

            return Arrays.stream(lines)
                    .map(string -> COMMENT_PREFIX + string)
                    .collect(Collectors.joining("\n"));
        } else {
            return "";
        }
    }

    @Override
    public HoconConfigurationOptions options() {
        if (options == null) {
            options = new HoconConfigurationOptions(this);
        }

        return (HoconConfigurationOptions) options;
    }

    private ConfigParseOptions configParseOptions() {
        HoconConfigurationOptions op = options();

        return ConfigParseOptions.defaults()
                .setSyntax(op.syntax())
                .setAllowMissing(op.allowMissing());
    }

    private void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    public static HoconConfiguration loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");

        HoconConfiguration config = new HoconConfiguration();

        try {
            config.load(file);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
        }

        return config;
    }
}
