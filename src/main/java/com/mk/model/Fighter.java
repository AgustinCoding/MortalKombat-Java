package com.mk.model;

public class Fighter {
    // Nombre del luchador
    private String name;
    // Ruta o identificador de la imagen asociada
    private String image;
    // Texto descriptivo del luchador
    private String description;
    // Género del luchador
    private String gender;
    // Identificador único
    private int id;
    // Fuerza base que aporta al cálculo del daño en combate
    private int baseStrength;

    /**
     * Constructor: inicializa todos los atributos del luchador.
     * @param name nombre del luchador
     * @param baseStrength fuerza base
     * @param gender género del luchador
     * @param image ruta/archivo de la imagen
     * @param description texto descriptivo
     * @param id identificador único
     */
    public Fighter(String name, int baseStrength, String gender, String image, String description, int id){
        this.name = name;
        this.image = image;
        this.description = description;
        this.gender = gender;
        this.id = id;
        this.baseStrength = baseStrength;
    }

    // Getters y setters para todos los atributos
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBaseStrength() { return baseStrength; }
    public void setBaseStrength(int baseStrength) { this.baseStrength = baseStrength; }
}
