package de.uka.ilkd.key.opal.runner;

import de.uka.ilkd.key.opal.OpalResultProvider;
import de.uka.ilkd.key.opal.result.ThrownExceptionsResult;
import de.uka.ilkd.key.opal.result.ThrownExceptionsResult.RuntimeException;
import de.uka.ilkd.key.util.Pair;
import org.opalj.ai.AIResult;
import org.opalj.ai.Domain;
import org.opalj.ai.InterruptableAI;
import org.opalj.ai.domain.l1.DefaultDomainWithRecordAllThrownExceptions;
import org.opalj.br.Method;
import org.opalj.br.analyses.Project;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.jdk.javaapi.CollectionConverters;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

/**
 * !! Absolute PROTOTYPING !!
 *
 * In contrast to the Field Immutability Analysis and Method Purity Analysis, this is solely based on abstract interpretation.
 *
 * Should this analysis be included within my thesis, I might want to reconsider the class hierarchy, since this is NOT a child
 * of AbstractAnalysisRunner
 *
 */
public class ExceptionUsageAnalysisRunner {

    protected Project<URL> p;

    HashMap<Pair<String, String>, java.util.Set<RuntimeException>> thrownExceptions;

    public ExceptionUsageAnalysisRunner(String pathToJar) {
        // TODO: Find out why jdk is a problem?!
        p = Project.apply(new File(pathToJar));
        thrownExceptions = new HashMap<>();
        //        if (StaticAnalysisSettings.analyzeJDKFiles) {
//            // With JDK
//            File[] classfiles = new File[] {new File(pathToJar)};
//            File[] libFiles = new File[] {getJREFolder()};
//            p = Project.apply(classfiles, libFiles);
//        } else {
//            // Without JDK
//            p = Project.apply(new File(pathToJar));
//        }
    }

    public final void run() {
        startAnalysis();
    }

    private void startAnalysis() {
        InterruptableAI ai = new InterruptableAI<Domain>();

        for (Method m : CollectionConverters.asJava(p.allMethods())) {
            AIResult result = ai.apply(m, new DefaultDomainWithRecordAllThrownExceptions<>(p, m));
            evaluateResult(m, result);
        }
        OpalResultProvider.getINST().setThrownExceptionsResult(new ThrownExceptionsResult(thrownExceptions));
    }

    private void evaluateResult(Method m, AIResult result) {
        Iterator<Tuple2> iter = ((DefaultDomainWithRecordAllThrownExceptions)(result.domain())).allThrownExceptions().iterator();
        java.util.Set<RuntimeException> runtimeExceptionsForM = new HashSet<>();
        while(iter.hasNext()) {
            // Unwrap the set!
            Iterator iter2 = ((Set)iter.next()._2).iterator();
            while (iter2.hasNext()) {
                String s = iter2.next().toString();
                try {
                    // Opal results look like: java.lang.NullPointerException[↦-100003;refId=104]
                    // Split it such that we only get the exception name, e.g. NullPointerException
                    RuntimeException ex = RuntimeException.valueOf((s.split("java.lang.")[1]).split("\\[")[0]);
                    runtimeExceptionsForM.add(ex);
                } catch (IllegalArgumentException e) {
                    // For RuntimeExceptions, the above code should not throw an IllegalArgumentException. For the moment
                    // we are only interested in RuntimeExeptions, other Exceptions are not added to the result set.
                }
            }
        }
        if (!runtimeExceptionsForM.isEmpty()) {
            String classname = m.declaringClassFile().thisType().fqn().split("\\$")[0];
            thrownExceptions.put(new Pair<>(classname, m.name()), runtimeExceptionsForM);
        }
    }
}
