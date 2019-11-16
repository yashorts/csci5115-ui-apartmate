package com.csci5115.group8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.csci5115.group8.data.DataManager;
import com.csci5115.group8.data.user.User;

import com.csci5115.group8.ui.usersearch.UserSearchFragment;
import com.csci5115.group8.ui.usersearch.UserSearchState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;

public class UserSearchActivity extends AppCompatActivity {

    EditText searchText;
    TextView numMatches;
    CheckBox gender_male;
    CheckBox gender_female;
    CheckBox gender_other;
    EditText age;
    EditText age2;
    EditText maxBudget;
    EditText maxBudget2;
    TriStateToggleButton doesSmoke;
    TriStateToggleButton drugsOkay;
    TriStateToggleButton hasPets;
    TriStateToggleButton partiesOkay;
    TriStateToggleButton canCook;
    TriStateToggleButton needsPrivateBedroom;
    EditText nativeLanguage;
    TriStateToggleButton hasCar;

    final TriStateToggleButton.OnToggleChanged updateNumberOfMatchesCB1 = new TriStateToggleButton.OnToggleChanged() {
        @Override
        public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean b, int i) {
            numMatches.setText(getNumSearchResults() + " Matches");
        }
    };

    final TextWatcher updateNumberOfMatchesCB2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            numMatches.setText(getNumSearchResults() + " Matches");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        final ConstraintLayout layout = findViewById(R.id.activity_user_search);
        searchText = layout.findViewById(R.id.searchText2);
        numMatches = layout.findViewById(R.id.numMatches2);
        gender_male= layout.findViewById(R.id.male);
        gender_female = layout.findViewById(R.id.female);
        gender_other = layout.findViewById(R.id.otherSex);
        age = layout.findViewById(R.id.age);
        age2 = layout.findViewById(R.id.age2);
        maxBudget = layout.findViewById(R.id.maxBudget);
        maxBudget2 = layout.findViewById(R.id.maxBudget2);
        doesSmoke = layout.findViewById(R.id.doesSmoke);
        doesSmoke.setOnToggleChanged(updateNumberOfMatchesCB1);
        drugsOkay = layout.findViewById(R.id.drugsOkay);
        drugsOkay.setOnToggleChanged(updateNumberOfMatchesCB1);
        hasPets = layout.findViewById(R.id.hasPets);
        hasPets.setOnToggleChanged(updateNumberOfMatchesCB1);
        partiesOkay = layout.findViewById(R.id.partiesOkay);
        partiesOkay.setOnToggleChanged(updateNumberOfMatchesCB1);
        canCook = layout.findViewById(R.id.canCook);
        canCook.setOnToggleChanged(updateNumberOfMatchesCB1);
        needsPrivateBedroom = layout.findViewById(R.id.needsPrivateBedroom);
        needsPrivateBedroom.setOnToggleChanged(updateNumberOfMatchesCB1);
        nativeLanguage = layout.findViewById(R.id.nativeLanguage);
        hasCar = layout.findViewById(R.id.hasCar);
        hasCar.setOnToggleChanged(updateNumberOfMatchesCB1);
        searchText.addTextChangedListener(updateNumberOfMatchesCB2);
        gender_other.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {numMatches.setText(getNumSearchResults() + " Matches");}});
        gender_male.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {numMatches.setText(getNumSearchResults() + " Matches");}});
        gender_female.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {numMatches.setText(getNumSearchResults() + " Matches");}});
        age.addTextChangedListener(updateNumberOfMatchesCB2);
        age2.addTextChangedListener(updateNumberOfMatchesCB2);
        maxBudget.addTextChangedListener(updateNumberOfMatchesCB2);
        maxBudget2.addTextChangedListener(updateNumberOfMatchesCB2);
        nativeLanguage.addTextChangedListener(updateNumberOfMatchesCB2);

        FloatingActionButton submit = layout.findViewById(R.id.submit_user_search);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect data, serialize and put in intent
                Intent returnIntent = new Intent();
                returnIntent.putExtra("searchText", searchText.getText().toString());
                setResult(AppCompatActivity.RESULT_OK, returnIntent);


                returnIntent.putExtra("gender_male",gender_male.isChecked()?"1":"0" );
                returnIntent.putExtra("gender_female",gender_male.isChecked()?"1":"0"  );
                returnIntent.putExtra("gender_other",gender_male.isChecked()?"1":"0"  );
                returnIntent.putExtra("age", age.getText().toString());
                returnIntent.putExtra("maxBudget", maxBudget.getText().toString());
                returnIntent.putExtra("age2", age2.getText().toString());
                returnIntent.putExtra("maxBudget2", maxBudget2.getText().toString());
                returnIntent.putExtra("nativeLanguage", nativeLanguage.getText().toString());
                returnIntent.putExtra("doesSmoke", toggleStatusToInt(doesSmoke.getToggleStatus()));
                returnIntent.putExtra("drugsOkay", toggleStatusToInt(drugsOkay.getToggleStatus()));
                returnIntent.putExtra("hasPets", toggleStatusToInt(hasPets.getToggleStatus()));
                returnIntent.putExtra("partiesOkay", toggleStatusToInt(partiesOkay.getToggleStatus()));
                returnIntent.putExtra("canCook", toggleStatusToInt(canCook.getToggleStatus()));
                returnIntent.putExtra("needsPrivateBedroom", toggleStatusToInt(needsPrivateBedroom.getToggleStatus()));
                returnIntent.putExtra("hasCar", toggleStatusToInt(hasCar.getToggleStatus()));

                finish();
            }
        });
        numMatches.setText(DataManager.users.size() + " Matches");
    }

    private int toggleStatusToInt(TriStateToggleButton.ToggleStatus toggleStatus) {
        switch (toggleStatus) {
            case on:
                return 2;
            case mid:
                return 1;
            case off:
                return 0;
        }
        return -1;
    }

    private int getNumSearchResults() {
        String searchString = searchText.getText().toString();

        String ages = age.getText().toString();
        int agei = ages.length() < 1 ? -1 : Integer.parseInt(ages);

        String maxBudgets = maxBudget.getText().toString();
        int maxBudgeti = maxBudgets.length() < 1 ? -1 : Integer.parseInt(maxBudgets);

        String ages2 = age2.getText().toString();
        int agei2 = ages2.length() < 1 ? -1 : Integer.parseInt(ages2);

        String maxBudgets2 = maxBudget2.getText().toString();
        int maxBudgeti2 = maxBudgets2.length() < 1 ? -1 : Integer.parseInt(maxBudgets2);

        List<User> searchResults = DataManager.searchUsers(
                new UserSearchState(searchString,
                        gender_male.isChecked()?1:0,
                        gender_female.isChecked()?1:0,
                        gender_other.isChecked()?1:0,
                        agei,
                        agei2,
                        maxBudgeti,
                        maxBudgeti2,
                        toggleStatusToInt(doesSmoke.getToggleStatus()),
                        toggleStatusToInt(drugsOkay.getToggleStatus()),
                        toggleStatusToInt(hasPets.getToggleStatus()),
                        toggleStatusToInt(partiesOkay.getToggleStatus()),
                        toggleStatusToInt(canCook.getToggleStatus()),
                        toggleStatusToInt(needsPrivateBedroom.getToggleStatus()),
                        toggleStatusToInt(hasCar.getToggleStatus()),
                        nativeLanguage.getText().toString()
                )
        );
        return searchResults.size();
    }


}
