package de.uka.ilkd.key.staticanalysis.runner;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import de.uka.ilkd.key.staticanalysis.StaticAnalysisSettings;
import org.opalj.ai.domain.l2.DefaultPerformInvocationsDomainWithCFGAndDefUse;
import org.opalj.ai.fpcf.properties.AIDomainFactoryKey$;
import org.opalj.br.analyses.Project;
import org.opalj.br.analyses.cg.AllEntryPointsFinder$;
import org.opalj.br.analyses.cg.InitialEntryPointsKey$;
import org.opalj.br.fpcf.FPCFAnalysesManagerKey$;
import org.opalj.br.fpcf.FPCFAnalysis;
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
        if (StaticAnalysisSettings.getINST().useCloseWorldAssumption()) {
            config = config.withValue("org.opalj.br.analyses.cg.ClassExtensibilityKey.analysis",
                    ConfigValueFactory.fromAnyRef("org.opalj.br.analyses.cg.ClassHierarchyIsNotExtensible"));
        }
        p = Project.apply(new File(pathToJar), GlobalLogContext$.MODULE$, config);
    }

    public final void run() {
        startAnalysis();
        evaluateResult();
    }

    abstract void evaluateResult();

    void setDomain() {
//        Function1<Option<Set<Class<?>>>, Set<Class<?>>> infoFunction = new AbstractFunction1() {
//            @Override
//            public Object apply(Object v1) {
//                return CollectionConverters.asScala(Arrays.asList(DefaultPerformInvocationsDomainWithCFGAndDefUse.class)).toSet();
//            }
//        };
//        p.updateProjectInformationKeyInitializationData(AIDomainFactoryKey$.MODULE$, infoFunction);
    }

    private void startAnalysis() {
        setDomain();
        p.get(RTACallGraphKey$.MODULE$);
        store = p.get(FPCFAnalysesManagerKey$.MODULE$).runAll(
                determineAnalyses()
        )._1;
        store.waitOnPhaseCompletion();
    };

    abstract Seq<ComputationSpecification<FPCFAnalysis>> determineAnalyses();
}


