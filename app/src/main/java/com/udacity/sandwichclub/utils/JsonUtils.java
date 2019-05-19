package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {

            JSONObject sandwichJSon = new JSONObject(json);

            sandwich.setMainName(sandwichJSon.getJSONObject("name").getString("mainName"));

            JSONArray alternativeNamesJsonArray = sandwichJSon.getJSONObject("name").getJSONArray("alsoKnownAs");

            List<String> listAlternativeNames = new ArrayList<>();
            for (int i = 0; i < alternativeNamesJsonArray.length(); i++) {
                listAlternativeNames.add(alternativeNamesJsonArray.getString(i));
            }
            sandwich.setAlsoKnownAs(listAlternativeNames);

            sandwich.setPlaceOfOrigin(sandwichJSon.getString("placeOfOrigin"));

            sandwich.setDescription(sandwichJSon.getString("description"));

            sandwich.setImage(sandwichJSon.getString("image"));

            JSONArray ingredientsJsonArray = sandwichJSon.getJSONArray("ingredients");
            List<String> listIngredients = new ArrayList<>();
            for (int j = 0; j < ingredientsJsonArray.length(); j++) {
                listIngredients.add(ingredientsJsonArray.getString(j));
            }
            sandwich.setIngredients(listIngredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return sandwich;
    }
}
