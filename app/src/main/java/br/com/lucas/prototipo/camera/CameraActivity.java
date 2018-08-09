package br.com.lucas.prototipo.camera;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.lucas.prototipo.R;
import br.com.lucas.prototipo.notificacao.NotificacaoActivity;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int INTENT_RESULT_CAMERA = 10;

    private ImageView foto;
    private Button btnCamera;
    private Button btnArquivo;
    String imageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        this.btnCamera = (Button) findViewById(R.id.btn_camera);
        this.btnArquivo = (Button) findViewById(R.id.btn_arquivo);
        this.foto = (ImageView) findViewById(R.id.img_foto);
        this.btnCamera.setOnClickListener(this);
        this.btnArquivo.setOnClickListener(this);
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /*   prefix     */
                ".jpg",  /*   suffix     */
                storageDir     /*   directory  */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.btnCamera.getId()) {
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(pictureIntent.resolveActivity(getPackageManager()) != null){
                //Create a file to store the image
                File photoFile = null;

                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,"br.com.lucas.prototipo.provider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                    startActivityForResult(pictureIntent,
                            INTENT_RESULT_CAMERA);
                }
            }
        }

        if(view.getId() == btnArquivo.getId()) {

            criarNotificacao();

            System.out.println("TYPED -------------------------");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INTENT_RESULT_CAMERA) {

            if(resultCode == Activity.RESULT_OK) {
                //don't compare the data to null, it will always come as  null because we are providing a file URI, so load with the imageFilePath we obtained before opening the cameraIntent
                //adicionar dependencia para o Glide
                Glide.with(this).load(imageFilePath).into(foto);
                // If you are using Glide.
                Toast.makeText(this, "Imagem salva com sucesso!", Toast.LENGTH_LONG).show();
            }else{
                //resultCode == Activity.RESULT_CANCELED
                Toast.makeText(this, "Foto não realizada!", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void criarNotificacao() {

        Intent intent = new Intent(this, NotificacaoActivity.class);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(R.drawable.ic_camera)
                .setContentTitle("Notificação gerada!")
                .setContentText("Notificação gerada com sucesso!")

                //linhas a mais
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setPriority(Notification.PRIORITY_MAX);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

}
