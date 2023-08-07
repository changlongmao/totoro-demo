package org.totoro.demo;

import com.github.javaparser.utils.CodeGenerationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.totoro.generator.processor.MavenPathProcessor;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 分裂
 *
 * @author Deolin 2020-11-27
 */
@Slf4j
public class Division {

    public static void main(String[] args) {
        // 项目全名
        final String projectName = "totoro-example";

        // 项目中文名
        final String cnModuleName = "totoro范例项目";

        // 基础包名
        final String packageName = "org.totoro.example";

        final File stemDirectory = MavenPathProcessor.findMavenProject(Division.class).toFile();

        String destPath = stemDirectory.getPath().replace("totoro-demo", "" + projectName);
        if (new File(destPath).exists()) {
            log.error("project [{}] is already exist. [{}]", projectName, destPath);
            return;
        }

        FileUtils.iterateFiles(stemDirectory, null, true).forEachRemaining(file -> {
            String fileName = file.getName();
            String fileExtension = FilenameUtils.getExtension(fileName);
            if ("Division.java".equals(fileName)) {
                log.info("ignore [" + file.getPath() + "]");
                return;
            }
            if (".DS_Store".equals(fileName) || "class".equals(fileExtension) || "iml".equals(fileExtension)) {
                log.info("ignore [" + file.getPath() + "]");
                return;
            }
            if (file.getPath().contains(File.separator + ".idea" + File.separator) || file.getPath()
                    .contains(File.separator + "target" + File.separator) || file.getPath()
                    .contains(File.separator + ".git" + File.separator) || file.getPath()
                    .contains(File.separator + "logs" + File.separator)) {
                log.info("ignore [" + file.getPath() + "]");
                return;
            }

            try {
                // replace content
                String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                content = content.replace("totoro-demo", projectName);
                content = content.replace("org.totoro.demo", packageName);
                if ("README.md".equals(fileName)) {
                    content = String.format("# *%s*\n%s", projectName, cnModuleName);
                }

                // replace path
                String path = file.getPath();
                path = path.replace("totoro-demo", projectName);
                path = path.replace(CodeGenerationUtils.packageToPath("org.totoro.demo"),
                        CodeGenerationUtils.packageToPath(packageName));

                FileUtils.writeStringToFile(new File(path), content, StandardCharsets.UTF_8);
                log.info("create file [{}]", file);
            } catch (Exception e) {
                log.error("fail to create file", e);
            }
        });

        log.info("Division fully completed. [{}]", destPath);
    }


}