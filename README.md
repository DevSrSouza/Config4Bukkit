# Config4Bukkit
[Typesafe Config](https://github.com/lightbend/config) for Bukkit.

## Usage
```java
File file = File(plugin.getDataFolder(), "myconfig.conf")

FileConfiguration config = HoconConfiguration.loadConfiguration(file);

config.set("test", "my little test");

config.save(file);
```
