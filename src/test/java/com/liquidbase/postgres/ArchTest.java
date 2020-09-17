package com.liquidbase.postgres;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.liquidbase.postgres");

        noClasses()
            .that()
                .resideInAnyPackage("com.liquidbase.postgres.service..")
            .or()
                .resideInAnyPackage("com.liquidbase.postgres.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.liquidbase.postgres.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
