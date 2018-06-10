public class MapperFactory{

    public static Mapper getJacksonMapper(){
//        final ObjectMapper MAPPER = new ObjectMapper();
//        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        return MAPPER;
        return new JacksonMapper();
    }

}
