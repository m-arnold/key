package de.uka.ilkd.key.opal.runner;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;
import org.apache.commons.lang3.NotImplementedException;
import org.opalj.ai.domain.l2.DefaultPerformInvocationsDomainWithCFGAndDefUse;
import org.opalj.ai.fpcf.properties.AIDomainFactoryKey$;
import org.opalj.br.analyses.Project;
import org.opalj.br.analyses.cg.AllEntryPointsFinder$;
import org.opalj.br.analyses.cg.InitialEntryPointsKey$;
import org.opalj.br.fpcf.FPCFAnalysesManager;
import org.opalj.br.fpcf.FPCFAnalysesManagerKey$;
import org.opalj.br.fpcf.FPCFAnalysis;
import org.opalj.br.fpcf.PropertyStoreKey$;
import org.opalj.fpcf.ComputationSpecification;
import org.opalj.fpcf.PropertyStore;
import org.opalj.log.GlobalLogContext$;
import org.opalj.tac.cg.RTACallGraphKey$;
import scala.Function1;
import scala.Option;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.jdk.javaapi.CollectionConverters;
import scala.runtime.AbstractFunction1;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

public abstract class AbstractAnalysisRunner {

    protected Project<URL> p;
    protected PropertyStore store;

    public AbstractAnalysisRunner(String pathToJar) {
        Config config = ConfigFactory.load(this.getClass().getClassLoader())
                .withValue(InitialEntryPointsKey$.MODULE$.ConfigKey(),
                        ConfigValueFactory.fromAnyRef("org.opalj.br.analyses.cg.AllEntryPointsFinder"))
                .withValue(AllEntryPointsFinder$.MODULE$.ConfigKey(), ConfigValueFactory.fromAnyRef(true));
        if (StaticAnalysisSettings.useCloseWorldAssumption) {
            config = config.withValue("org.opalj.br.analyses.cg.ClassExtensibilityKey.analysis",
                    ConfigValueFactory.fromAnyRef("org.opalj.br.analyses.cg.ClassHierarchyIsNotExtensible"));
        }

        if (StaticAnalysisSettings.analyzeJDKFiles) {
            // With JDK
            File[] classfiles = new File[] {new File(pathToJar)};
            File[] libFiles = new File[] {getJREFolder()};
            p = Project.apply(classfiles, libFiles, GlobalLogContext$.MODULE$, config);
        } else {
            // Without JDK
            p = Project.apply(new File(pathToJar), GlobalLogContext$.MODULE$, config);
        }
    }

    public final void run() {
        startAnalysis();
        evaluateResult();
    }

    abstract void evaluateResult();

    void setDomain() {
        Function1<Option<Set<Class<?>>>, Set<Class<?>>> infoFunction = new AbstractFunction1() {
            @Override
            public Object apply(Object v1) {
                // When using local Opal Snapshot. (which contains Fix for the Domain)
                return CollectionConverters.asScala(Arrays.asList(DefaultPerformInvocationsDomainWithCFGAndDefUse.class)).toSet();
                // Otherwise:
                // return CollectionConverters.asScala(Arrays.asList(DefaultDomainWithCFGAndDefUse.class)).toSet();
            }
        };
        p.updateProjectInformationKeyInitializationData(AIDomainFactoryKey$.MODULE$, infoFunction);
    }

    private void startAnalysis() {
        setDomain();
        store = p.get(PropertyStoreKey$.MODULE$);
        FPCFAnalysesManager manager = p.get(FPCFAnalysesManagerKey$.MODULE$);
        p.get(RTACallGraphKey$.MODULE$);
        store = manager.runAll(
                determineAnalyses()
        )._1;
        store.waitOnPhaseCompletion();
    }

    abstract Seq<ComputationSpecification<FPCFAnalysis>> determineAnalyses();


    private File getJREFolder() {

        String javaVersion = System.getProperty("java.version");
        if (javaVersion.startsWith("1.")) {
            throw new NotImplementedException("TODO!");
        } else {
            String javaJMods = System.getProperty("java.home") + "/jmods";
            File directory = new File(javaJMods);
            if (!directory.exists()) {
                throw new RuntimeException("cannot locate the JRE libraries");
            }
            return directory;
        }
    }
}

