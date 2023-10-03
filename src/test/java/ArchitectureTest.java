import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ArchitectureTest {
    private final String PACKAGE_NAME = "com.example.spring2023";
    private final JavaClasses classes = new ClassFileImporter().importPackages("com.example.spring2023");

    @Test
    @DisplayName("Соблюдены требования слоеной архитектуры")
    void testLayeredArchitecture() {
        Architectures.layeredArchitecture().consideringAllDependencies()
                .layer("domain").definedBy(getPackageName("domain"))
                .layer("app").definedBy(getPackageName("app"))
                .layer("extern").definedBy(getPackageName("extern"))
                .whereLayer("app").mayOnlyBeAccessedByLayers("app", "extern")
                .whereLayer("extern").mayOnlyBeAccessedByLayers("extern")
                .check(classes);
    }

    public String getPackageName(String packageName) {
        return PACKAGE_NAME + "." + packageName + "..";
    }
}
