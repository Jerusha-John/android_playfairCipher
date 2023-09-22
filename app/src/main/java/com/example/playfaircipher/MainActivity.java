package com.example.playfaircipher;
import com.example.playfaircipher.PlayfairCipher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText keyText;
    private EditText inputText;
    private TextView outputText;
    private Button encryptButton;
    private Button decryptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyText = findViewById(R.id.keyText);
        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        encryptButton = findViewById(R.id.encryptButton);
        decryptButton = findViewById(R.id.decryptButton);

        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = keyText.getText().toString();
                String plaintext = inputText.getText().toString();
                String encryptedText = encrypt(key, plaintext);
                outputText.setText(encryptedText);
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = keyText.getText().toString();
                String ciphertext = inputText.getText().toString();
                String decryptedText = decrypt(key, ciphertext);
                outputText.setText(decryptedText);
            }
        });
    }

    private String encrypt(String key, String plaintext) {
        PlayfairCipher cipher = new PlayfairCipher(key);
        return cipher.encrypt(plaintext);
    }

    private String decrypt(String key, String ciphertext) {
        PlayfairCipher cipher = new PlayfairCipher(key);
        return cipher.decrypt(ciphertext);
    }
}