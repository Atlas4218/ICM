package com.example.icm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;

    private final String texteInit = getString(R.string.resultat_init);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        envoyer = findViewById(R.id.calcul);
        reset = findViewById(R.id.reset);
        taille = findViewById(R.id.taille);
        poids = findViewById(R.id.poids);
        commentaire = findViewById(R.id.commentaire);
        group = findViewById(R.id.group);
        result = findViewById(R.id.result);

        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);


    }

    private View.OnClickListener envoyerListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //  on récupère la taille
            String t = taille.getText().toString();
            // On récupère le poids
            String p = poids.getText().toString();
            float tValue = Float.valueOf(t);

            // Puis on vérifie que la taille est cohérente
            if(tValue <= 0)
                Toast.makeText(MainActivity.this, getString(R.string.toast_taille), Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if(pValue <= 0)
                    Toast.makeText(MainActivity.this, getString(R.string.toast_poids), Toast.LENGTH_SHORT).show();
                else {
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) tValue = tValue / 100;

                    float imc = pValue / (tValue * tValue);
                    String resultat = getString(R.string.resultat) + imc+": ";

                    if(commentaire.isChecked()) resultat += interpreteIMC(imc);


                    result.setText(resultat);
                }
            }
        }
    };

    private String interpreteIMC(float imc) {
        String commentaire;
        if (imc<16.5) commentaire = getString(R.string.comment1);
        else
            if (imc<18.5) commentaire = getString(R.string.comment2);
            else
                if(imc<25) commentaire = getString(R.string.comment3);
                 else
                     if (imc<30) commentaire = getString(R.string.comment4);
                     else
                         if (imc<35) commentaire = getString(R.string.comment5);
                         else
                             if (imc<40) commentaire = getString(R.string.comment6);
                             else commentaire = getString(R.string.comment7);

        return commentaire;
    }

    private View.OnClickListener resetListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(texteInit);
        }
    };

    private View.OnClickListener checkedListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                result.setText(texteInit);
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(texteInit);

            if (taille.getText().toString().contains(".")) group.check(R.id.radio_metre);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };


}