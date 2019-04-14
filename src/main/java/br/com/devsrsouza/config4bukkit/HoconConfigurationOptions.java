package br.com.devsrsouza.config4bukkit;

import com.typesafe.config.ConfigSyntax;
import org.bukkit.configuration.file.FileConfigurationOptions;

public class HoconConfigurationOptions extends FileConfigurationOptions {

    private ConfigSyntax syntax = ConfigSyntax.CONF;
    private boolean resolve = true;
    private boolean allowMissing = true;

    protected HoconConfigurationOptions(HoconConfiguration configuration) {
        super(configuration);
    }

    @Override
    public HoconConfiguration configuration() {
        return (HoconConfiguration) super.configuration();
    }

    @Override
    public HoconConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }

    @Override
    public HoconConfigurationOptions pathSeparator(char value) {
        super.pathSeparator(value);
        return this;
    }

    @Override
    public HoconConfigurationOptions header(String value) {
        super.header(value);
        return this;
    }

    @Override
    public HoconConfigurationOptions copyHeader(boolean value) {
        super.copyHeader(value);
        return this;
    }

    public ConfigSyntax syntax() {
        return syntax;
    }

    public void syntax(ConfigSyntax syntax) {
        this.syntax = syntax;
    }

    public boolean resolve() {
        return resolve;
    }

    public void resolve(boolean resolve) {
        this.resolve = resolve;
    }

    public boolean allowMissing() {
        return allowMissing;
    }

    public void allowMissing(boolean allowMissing) {
        this.allowMissing = allowMissing;
    }
}
