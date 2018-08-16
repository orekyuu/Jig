package org.dddjava.jig.infrastructure.configuration;

import org.dddjava.jig.application.service.AngleService;
import org.dddjava.jig.application.service.DependencyService;
import org.dddjava.jig.application.service.GlossaryService;
import org.dddjava.jig.application.service.ImplementationService;
import org.dddjava.jig.domain.basic.ConfigurationContext;
import org.dddjava.jig.domain.model.characteristic.CharacterizedTypeFactory;
import org.dddjava.jig.domain.model.implementation.bytecode.ByteCodeFactory;
import org.dddjava.jig.domain.model.implementation.datasource.SqlReader;
import org.dddjava.jig.domain.model.implementation.sourcecode.JapaneseReader;
import org.dddjava.jig.domain.model.japanese.JapaneseNameRepository;
import org.dddjava.jig.infrastructure.Layout;
import org.dddjava.jig.infrastructure.LocalProject;
import org.dddjava.jig.infrastructure.PrefixRemoveIdentifierFormatter;
import org.dddjava.jig.infrastructure.PropertyCharacterizedTypeFactory;
import org.dddjava.jig.infrastructure.asm.AsmByteCodeFactory;
import org.dddjava.jig.infrastructure.javaparser.JavaparserJapaneseReader;
import org.dddjava.jig.infrastructure.mybatis.MyBatisSqlReader;
import org.dddjava.jig.infrastructure.onmemoryrepository.OnMemoryJapaneseNameRepository;
import org.dddjava.jig.presentation.controller.ClassListController;
import org.dddjava.jig.presentation.controller.EnumUsageController;
import org.dddjava.jig.presentation.controller.PackageDependencyController;
import org.dddjava.jig.presentation.controller.ServiceDiagramController;
import org.dddjava.jig.presentation.view.ViewResolver;
import org.dddjava.jig.presentation.view.graphvizj.DiagramFormat;
import org.dddjava.jig.presentation.view.graphvizj.MethodNodeLabelStyle;
import org.dddjava.jig.presentation.view.handler.JigDocumentHandlers;
import org.springframework.core.env.Environment;

import java.util.StringJoiner;

public class Configuration {

    final LocalProject localProject;
    final ImplementationService implementationService;
    final JigDocumentHandlers documentHandlers;
    final AngleService angleService;
    final DependencyService dependencyService;

    public Configuration(Layout layout, JigProperties properties) {
        this(layout, properties, new DependencyService(new ConfigurationContext() {
            @Override
            public String classFileDetectionWarningMessage() {
                return "";
            }

            @Override
            public String modelDetectionWarningMessage() {
                return "";
            }
        }));
    }

    public Configuration(Layout layout, JigProperties properties, ConfigurationContext configurationContext) {
        this(layout, properties, new DependencyService(configurationContext));
    }

    public Configuration(Layout layout, JigProperties properties, Environment environment) {
        this(layout, properties, new DependencyService(new ConfigurationContext() {
            @Override
            public String classFileDetectionWarningMessage() {
                String propertyValue = environment.getProperty("directory.classes");
                String variable = new StringJoiner(System.lineSeparator())
                        .add("以下の値を確認してください。この値はディレクトリの絞り込みに使用されます。")
                        .add("- directory.classes: " + propertyValue).toString();
                return variable;
            }

            @Override
            public String modelDetectionWarningMessage() {
                String propertyValue = environment.getProperty("jig.model.pattern");
                String variable = new StringJoiner(System.lineSeparator())
                        .add("以下の値を確認してください。")
                        .add("- jig.model.pattern: " + propertyValue).toString();
                return variable;
            }
        }));
    }

    public Configuration(Layout layout, JigProperties properties, DependencyService dependencyService) {
        this.dependencyService = dependencyService;
        JapaneseNameRepository japaneseNameRepository = new OnMemoryJapaneseNameRepository();
        CharacterizedTypeFactory characterizedTypeFactory = new PropertyCharacterizedTypeFactory(
                properties.getModelPattern(),
                properties.getRepositoryPattern()
        );
        JapaneseReader japaneseReader = new JavaparserJapaneseReader();
        GlossaryService glossaryService = new GlossaryService(
                japaneseReader,
                japaneseNameRepository
        );
        SqlReader sqlReader = new MyBatisSqlReader();
        ByteCodeFactory byteCodeFactory = new AsmByteCodeFactory();
        this.angleService = new AngleService();
        PrefixRemoveIdentifierFormatter typeIdentifierFormatter = new PrefixRemoveIdentifierFormatter(
                properties.getOutputOmitPrefix()
        );
        ViewResolver viewResolver = new ViewResolver(
                typeIdentifierFormatter, MethodNodeLabelStyle.SIMPLE.name(), DiagramFormat.SVG.name()
        );
        ClassListController classListController = new ClassListController(
                typeIdentifierFormatter,
                glossaryService,
                angleService
        );
        EnumUsageController enumUsageController = new EnumUsageController(
                angleService,
                glossaryService,
                viewResolver
        );
        PackageDependencyController packageDependencyController = new PackageDependencyController(
                dependencyService,
                glossaryService,
                viewResolver,
                properties.getDepth().value()
        );
        ServiceDiagramController serviceDiagramController = new ServiceDiagramController(
                angleService,
                glossaryService,
                viewResolver
        );
        this.localProject = new LocalProject(layout);
        this.implementationService = new ImplementationService(
                byteCodeFactory,
                glossaryService,
                sqlReader,
                characterizedTypeFactory
        );
        this.documentHandlers = new JigDocumentHandlers(
                serviceDiagramController,
                classListController,
                packageDependencyController,
                enumUsageController
        );
    }

    public AngleService angleService() {
        return angleService;
    }

    public LocalProject localProject() {
        return localProject;
    }

    public ImplementationService importService() {
        return implementationService;
    }

    public JigDocumentHandlers documentHandlers() {
        return documentHandlers;
    }

    public DependencyService dependencyService() {
        return dependencyService;
    }
}
