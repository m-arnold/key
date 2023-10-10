package de.uka.ilkd.key.opal;

import javax.tools.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class JarProcessor {

    private String outputPath;
    private String jarPath;
    private String[] filesToCompile;
    private List<String> existingClassFiles;
    private boolean deleteJarAfterAnalysis = true;

    public JarProcessor(String[] filePaths) {
        outputPath = System.getProperty("user.home");
        jarPath = outputPath + "/forAnalysis.jar";
        this.filesToCompile = filePaths;
    }

    public String createForAnalysis() {
        try {
            retrieveExistingClassFiles();
            compileJavaFiles();
            return createJar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void retrieveExistingClassFiles() {
        existingClassFiles = new ArrayList<>();
        File file = new File(outputPath);
        for(File f : file.listFiles()) {
            if (f.getName().endsWith(".class")) {
                existingClassFiles.add(f.getName());
            }
        }
    }

    private void compileJavaFiles() throws IOException {
        System.out.println("RUN COMPILE");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(outputPath)));

        for (String filePath: filesToCompile) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(filePath);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            boolean success = task.call();
            System.out.println("Filename: " + filePath + "| Success: " + success);
            if (!success) {
                diagnostics.getDiagnostics().stream().forEach(error -> OpalResultProvider.getINST().addCompileError(error.toString()));
            }
        }
        System.out.println("COMPILE FINISHED");
    }

    private String createJar() {
        try {
            System.out.println("CREATE JAR");
            File file = new File(outputPath);
            File[] classFiles = file.listFiles();
            FileOutputStream fos = new FileOutputStream(jarPath);
            JarOutputStream jos = new JarOutputStream(fos);
            for (File classFile: classFiles) {
                if (classFile.getName().endsWith(".class") && !existingClassFiles.contains(classFile.getName())) {
                    addClassFileToJar(jos, classFile);
                    boolean b = classFile.delete();
                }
            }
            jos.close();
            System.out.println("JAR CREATED AT:"+ jarPath);
            return jarPath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addClassFileToJar(JarOutputStream jos, File classFile) throws IOException {
        JarEntry entry = new JarEntry(classFile.getName());
        jos.putNextEntry(entry);
        FileInputStream fis = new java.io.FileInputStream(classFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            jos.write(buffer, 0, bytesRead);
        }
        fis.close();
        jos.closeEntry();
    }

    public void deleteAnalysisJar() {
        if (deleteJarAfterAnalysis) {
            File file = new File(jarPath);
            file.delete();
        }
    }

}
