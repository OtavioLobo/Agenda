package com.example.agenda;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agenda.controller.ContatoController;
import com.example.agenda.model.Contato;

public class AdicionarContatoActivity extends AppCompatActivity {
    private EditText editNome, editEmail, editTelefone;
    private Button btnSalvar;
    private ImageView imageViewFoto;
    private Contato contatoEdicao;

    ContatoController contatoController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_contato);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contatoController = new ContatoController(this);
        inicializarViews();
        verificaEdicao();
    }

    public void inicializarViews(){
        editNome = findViewById(R.id.editTextNome);
        editEmail = findViewById(R.id.editTextEmail);
        editTelefone = findViewById(R.id.editTextTelefone);
        btnSalvar = findViewById(R.id.btnSalvar);
        imageViewFoto = findViewById(R.id.imageView2);

        btnSalvar.setOnClickListener(v -> salvarContato());
    }

    public void salvarContato(){
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String telefone = editTelefone.getText().toString().trim();

        if (nome.isEmpty() || telefone.isEmpty()){
            Toast.makeText(this, "Nome e telefone são OBRIGATÓRIOS!", Toast.LENGTH_SHORT).show();
        }

        if (contatoEdicao != null) {
            contatoController.atualizarContato(contatoEdicao.getId(), contatoEdicao.getNome(), contatoEdicao.getEmail(),contatoEdicao.getTelefone(), contatoEdicao.getFoto());
            Toast.makeText(this, "Contato atualizado com SUCESSO", Toast.LENGTH_LONG).show();
        } else {
            contatoController.adicionarContato(nome, email, telefone, "");
            Toast.makeText(this, "Contato adicionado com SUCESSO!", Toast.LENGTH_SHORT).show();
        }


        finish();
    }

    private void verificaEdicao() {
        if(getIntent().hasExtra("contato")) {
            contatoEdicao = (Contato) getIntent().getSerializableExtra("contato");

            editNome.setText(contatoEdicao.getNome());
            editEmail.setText(contatoEdicao.getEmail());
            editTelefone.setText(contatoEdicao.getTelefone());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contatoController.close();
    }
}