package dto;

public class OrderRequest {
    private String[] ingredients;

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

public OrderRequest(String[] ingredients) {
        this.ingredients = ingredients;
}

  //  ingredients = {"61c0c5a71d1f82001bdaaa6d",
  //          "61c0c5a71d1f82001bdaaa6f",
  //          "61c0c5a71d1f82001bdaaa70",
  //          "61c0c5a71d1f82001bdaaa71",
  //  "61c0c5a71d1f82001bdaaa72",
  //  "61c0c5a71d1f82001bdaaa6e",
  //  "61c0c5a71d1f82001bdaaa73",
  //  "61c0c5a71d1f82001bdaaa74",
  //  "61c0c5a71d1f82001bdaaa6c",
  //  "61c0c5a71d1f82001bdaaa75",
  //      "61c0c5a71d1f82001bdaaa76",
  //      "61c0c5a71d1f82001bdaaa77",
  //      "61c0c5a71d1f82001bdaaa78",
  //      "61c0c5a71d1f82001bdaaa79",
  //      "61c0c5a71d1f82001bdaaa7a"};
}
