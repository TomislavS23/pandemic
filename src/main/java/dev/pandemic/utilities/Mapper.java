package dev.pandemic.utilities;


import org.modelmapper.ModelMapper;

public class Mapper {

    private static final ModelMapper mapper = new ModelMapper();

    private Mapper() {
    }

    public static ModelMapper getInstance() {
        return mapper;
    }
}
