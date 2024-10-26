package dev.pandemic.service.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UseJavaFXDialog {
    String value() default "This method is not recommended. Use JavaFX dialogs instead.";
}
