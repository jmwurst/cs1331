public abstract class MovieType {
    private String type, typeDescription;

    public MovieType(String type, String typeDescription) {
        this.type = type;
        this.typeDescription = typeDescription;
    }

    public abstract double getPrice();

    public String getType() {
        return type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }
}