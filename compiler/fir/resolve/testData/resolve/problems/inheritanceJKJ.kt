// FILE: ModuleBasedConfiguration.java

public class ModuleBasedConfiguration {
    public String getModule() {
        return "";
    }
}

// FILE: JetRunConfiguration.kt

class JetRunConfiguration : ModuleBasedConfiguration()

// FILE: KotlinRunConfiguration.java

public class KotlinRunConfiguration extends JetRunConfiguration {
}

// FILE: test.kt
fun test(configuration: KotlinRunConfiguration) {
    val module = configuration.module
}