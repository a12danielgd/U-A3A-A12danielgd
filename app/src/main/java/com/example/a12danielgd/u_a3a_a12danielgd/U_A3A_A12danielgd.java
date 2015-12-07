package com.example.a12danielgd.u_a3a_a12danielgd;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

public class U_A3A_A12danielgd extends AppCompatActivity {
    private Chronometer crono;
    public static boolean pararThread=false;
    private final int TEMPO_CRONO=20;
    private static int numRandom;
    private TextView random;
    private TextView resultado;
    // INICO DA CLASE HANDLER
    private static class Manexador extends Handler {

        private WeakReference<U_A3A_A12danielgd> mTarget = null;

        Manexador(U_A3A_A12danielgd target) {
            mTarget = new WeakReference<U_A3A_A12danielgd>(target);
        }

        @Override
        public void handleMessage(Message msg) {

            U_A3A_A12danielgd target = mTarget.get();
            TextView texto1 = (TextView) target
                    .findViewById(R.id.tv_resultado);

            if (msg.arg2==1){
                Toast.makeText(target.getApplicationContext(), "ACABOUSE O CRONO CON VALOR "+msg.arg1, Toast.LENGTH_LONG).show();
                pararThread=false;
                if(msg.arg1 == numRandom){
                    
                }
            }
            else {
                texto1.setText(String.valueOf(msg.arg1));
            }
        }
    }; // Fin do Handler

    private Manexador mane = new Manexador(this);


    private class MeuFio extends Thread{

        public void run(){
            int a;
            for (a=TEMPO_CRONO;a>=0;a--){
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.arg1=a;
                    mane.sendMessage(msg);
                    if(pararThread){
                        break;
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            Message msgFin = new Message();
            msgFin.arg1=a;
            msgFin.arg2=1;
            mane.sendMessage(msgFin);
        }       // FIN DO RUN
    };

    private Thread meuFio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u__a3_a__a12danielgd);
        Button btnEmpezarThread = (Button)findViewById(R.id.btn_empezarThread);
        Button btnPararThread = (Button) findViewById(R.id.btn_pararThread);
        random=(TextView) findViewById(R.id.tv_numAleatorio);
        resultado=(TextView) findViewById(R.id.tv_resultado);
        btnEmpezarThread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if ((meuFio == null) || (!meuFio.isAlive())) {
                    Random rnd=new Random();
                    numRandom=rnd.nextInt(21);
                    random.setText(""+numRandom);
                    Toast.makeText(getApplicationContext(), "INICIANDO FIO", Toast.LENGTH_LONG).show();
                    meuFio = new MeuFio();
                    meuFio.start();
                } else {
                    Toast.makeText(getApplicationContext(), "NON TE DEIXO INICIAR O FIO ATA QUE REMATE :)", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPararThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((meuFio != null) && (meuFio.isAlive())) {
                    pararThread = true;
                }

            }
        });
    }
}



