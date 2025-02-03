package dev.pandemic.utilities;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

public class Mapper {

    private static Mapper instance;

    @Getter
    @Setter
    private ModelMapper mapper;

    private Mapper() {
    }

    public static Mapper getInstance() {
        if (instance == null) {
            instance = new Mapper();
        }
        return instance;
    }
}
