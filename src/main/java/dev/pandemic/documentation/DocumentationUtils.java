package dev.pandemic.documentation;

import dev.pandemic.enumerations.FilePath;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class DocumentationUtils {

    private DocumentationUtils() {
    }

    private static final String CLASS_FILE_NAME_EXTENSION = ".class";

    public static void generateHtmlDocumentationFile() throws IOException, ClassNotFoundException {

        Path start = Paths.get(FilePath.PATH_WITH_CLASSES.getPath());
        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            List<String> classList = stream
                    .filter(f -> f.getFileName().toString().endsWith(CLASS_FILE_NAME_EXTENSION)
                            && Character.isUpperCase(f.getFileName().toString().charAt(0)))
                    .map(String::valueOf)
                    .sorted()
                    .toList();

            String htmlString = generateHtmlDocumentationCode(classList);

            Files.writeString(Path.of(FilePath.DOCUMENTATION_PATH.getPath()), htmlString);
        }
    }

    private static String generateHtmlDocumentationCode(List<String> classList) throws ClassNotFoundException {

        StringBuilder htmlBuilder = new StringBuilder();

        prepareBaseHtml(htmlBuilder);

        for (String className : classList) {
            className = className
                    .substring(FilePath.PATH_WITH_CLASSES.getPath().length(), className.length() - CLASS_FILE_NAME_EXTENSION.length())
                    .replace("/", ".");

            Class<?> clazz = Class.forName(className);
            htmlBuilder.append("<h1>Class: ").append(clazz.getSimpleName()).append("</h1><br /><hr><hr>");

            extractClass(htmlBuilder, clazz);
            extractAnnotations(htmlBuilder, clazz);
            extractField(htmlBuilder, clazz);
            extractMethods(htmlBuilder, clazz);
        }

        String htmlEnd = """
                </body>
                </html>""";

        htmlBuilder.append(htmlEnd);

        return htmlBuilder.toString();
    }

    private static void prepareBaseHtml(StringBuilder htmlBuilder) {
        String htmlStart = """
                <!DOCTYPE html>
                <html>
                <head>
                <title>Java documentation</title>
                </head>
                <body>
                
                <h1>List of classes:</h1>""";

        htmlBuilder.append(htmlStart);
    }

    private static void extractMethods(StringBuilder htmlBuilder, Class<?> clazz) {
        htmlBuilder.append("<h2>Methods:</h2><br />");
        if (clazz.getMethods().length > 0) {
            for (Method method : clazz.getMethods()) {
                String modifier = Modifier.toString(method.getModifiers());
                htmlBuilder.append("<ul><li><b>")
                        .append(modifier.isEmpty() ? "" : modifier + " ")
                        .append("</b><i>")
                        .append(method.getReturnType().getSimpleName())
                        .append(" ")
                        .append(method.getName())
                        .append("</i>(");

                extractMethodParameters(htmlBuilder, method);

                htmlBuilder.append(")");

                extractMethodExceptions(htmlBuilder, method);

                htmlBuilder.append("</li></ul><br />");


            }
        } else {
            htmlBuilder.append("<p>None present.</p><br />");
        }
    }

    private static void extractMethodExceptions(StringBuilder htmlBuilder, Method method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        if (exceptionTypes.length > 0) {
            htmlBuilder.append(" <b>throws</b> ");
            for (int i = 0; i < exceptionTypes.length; i++) {
                htmlBuilder.append(exceptionTypes[i].getSimpleName());
                if (i < exceptionTypes.length - 1) {
                    htmlBuilder.append(", ");
                }
            }
        }
    }

    private static void extractMethodParameters(StringBuilder htmlBuilder, Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        for (int i = 0; i < paramTypes.length; i++) {
            htmlBuilder.append(paramTypes[i].getSimpleName());
            if (i < paramTypes.length - 1) {
                htmlBuilder.append(", ");
            }
        }
    }

    private static void extractField(StringBuilder htmlBuilder, Class<?> clazz) {
        htmlBuilder.append("<h2>Fields:</h2><br />");
        if (clazz.getDeclaredFields().length > 0) {
            for (Field field : clazz.getDeclaredFields()) {
                String modifier = Modifier.toString(field.getModifiers());
                htmlBuilder
                        .append("<ul><li><b>")
                        .append(modifier.isEmpty() ? "" : modifier + " ")
                        .append("</b><i>")
                        .append(field.getType().getSimpleName())
                        .append(" ")
                        .append(field.getName())
                        .append("</li></i>")
                        .append("</ul><br />");
            }
        } else {
            htmlBuilder.append("<p>None present.</p><br />");
        }
    }

    private static void extractAnnotations(StringBuilder htmlBuilder, Class<?> clazz) {
        htmlBuilder.append("<h2>Annotations:</h2><br />");
        if (clazz.getDeclaredAnnotations().length > 0) {
            for (Annotation annotation : clazz.getDeclaredAnnotations()) {
                htmlBuilder.append("<ul><li><i>").append(annotation).append("</i></li>").append("</ul><br />");
            }
        } else {
            htmlBuilder.append("<p>None present.</p><br />");
        }
    }

    private static void extractClass(StringBuilder htmlBuilder, Class<?> clazz) {
        htmlBuilder.append("<h2>Constructors:</h2><br />");
        if (clazz.getConstructors().length > 0) {
            for (Constructor<?> constructor : clazz.getConstructors()) {
                htmlBuilder.append("<ul><li>")
                        .append("<i>")
                        .append(constructor.getDeclaringClass().getSimpleName())
                        .append("</i>(");
                htmlBuilder.append(")</li></ul><br />");
            }
        } else {
            htmlBuilder.append("<p>None present.</p><br />");
        }
    }
}
