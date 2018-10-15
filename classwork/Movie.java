public class Movie {
    private String name, description;
    private MovieType type;

    public Movie(String name, String description, MovieType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public MovieType getType() {
        return type;
    }
}