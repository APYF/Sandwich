package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ingredientsIv = findViewById(R.id.image_iv);

        TextView alsoKnownHeaderTv = findViewById(R.id.also_known_header_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);

        TextView ingredientsHeaderTv = findViewById(R.id.ingredients_header_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);

        TextView originHeaderTv = findViewById(R.id.origin_header_tv);
        TextView originTv = findViewById(R.id.origin_tv);

        TextView descriptionHeaderTv = findViewById(R.id.description_header_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    // Don't show the ImageView if can't load the image
                    public void onError() {
                        ingredientsIv.setVisibility(View.GONE);
                    }
                });

        setTitle(sandwich.getMainName());

        // remove the [ and ] from the string from toString of the List
        String alsoKnownString = sandwich.getAlsoKnownAs().toString().replace("[", "").replace("]", "");
        // Hide if it's empty
        if(alsoKnownString.isEmpty()) {
            alsoKnownHeaderTv.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
        } else {
            alsoKnownTv.setText(alsoKnownString);
        }

        // remove the [ and ] from the string from toString of the List
        String ingredientsString = sandwich.getIngredients().toString().replace("[", "").replace("]", "");
        // Hide if it's empty
        if (ingredientsString.isEmpty()) {
            ingredientsHeaderTv.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        } else {
            ingredientsTv.setText(ingredientsString);
        }

        String originString = sandwich.getPlaceOfOrigin();
        // Hide if it's empty
        if (originString.isEmpty()) {
            originHeaderTv.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        } else {
            originTv.setText(originString);
        }

        String descriptionString = sandwich.getDescription();
        // Hide if it's empty
        if (descriptionString.isEmpty()) {
            descriptionHeaderTv.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        } else {

        descriptionTv.setText(descriptionString);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
