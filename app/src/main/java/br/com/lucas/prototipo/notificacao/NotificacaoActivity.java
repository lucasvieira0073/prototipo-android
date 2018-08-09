package br.com.lucas.prototipo.notificacao;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.lucas.prototipo.R;

public class NotificacaoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        this.botao = (Button) findViewById(R.id.notificacao);

        this.botao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == this.botao.getId()) {

            // adicionar permissão VIBRATE
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent resultIntent = new Intent(this, NotificacaoSucessoActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            stackBuilder.addParentStack(NotificacaoSucessoActivity.class);
            stackBuilder.addNextIntent(resultIntent);

            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Título");
            builder.setContentText("Descrição");
            //builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setSmallIcon(R.drawable.ic_camera);
            builder.setContentIntent(pendingIntent);

            //configurações a mais
            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
            builder.setPriority(Notification.PRIORITY_MAX);


            notificationManager.notify(1, builder.build());


        }
    }
}



    // adicionar permissão VIBRATE
//    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//    PendingIntent pendingIntent = PendingIntent
//            .getActivity(this, 0,
//                    new Intent(this, NotificacaoSucessoActivity.class), 0);
//
//    Notification.Builder builder = new Notification.Builder(this);
//            builder.setTicker("Texto rápido");
//                    builder.setContentTitle("Título");
//                    builder.setContentText("Descrição");
//                    builder.setSmallIcon(R.mipmap.ic_launcher);
//                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//
//                    Notification notification = builder.build();
//                    notification.vibrate = new long[]{150, 300, 150, 600};//150 vibrando, 300 parado, 150 vibrando, 600 parado
//                    notificationManager.notify(R.mipmap.ic_launcher, notification);// id da imagem usado como id ( boa prática )
//
//                    // tocando som da notificação
//                    try {
//                    Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    Ringtone toque = RingtoneManager.getRingtone(this, som);
//                    toque.play();
//                    }catch (Exception e) {
//                    e.printStackTrace();
//                    }
