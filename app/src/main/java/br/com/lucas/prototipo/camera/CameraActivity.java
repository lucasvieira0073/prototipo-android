package br.com.lucas.prototipo.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import br.com.lucas.prototipo.R;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int INTENT_RESULT_CAMERA = 10;

    private ImageView foto;
    private Button btnCamera;
    private File arquivoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        this.btnCamera = (Button) findViewById(R.id.btn_camera);
        this.foto = (ImageView) findViewById(R.id.img_foto);
        this.btnCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.btnCamera.getId()) {
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File imagePath = new File(getFilesDir(), "external_files");
            imagePath.mkdir();
            arquivoFoto = new File(imagePath.getPath(),  System.currentTimeMillis() + ".jpg");
            Uri uri = FileProvider.getUriForFile(this, "br.com.lucas.prototipo.fileprovider", arquivoFoto);

            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intentCamera, INTENT_RESULT_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INTENT_RESULT_CAMERA) {
//            Bitmap bitmap = BitmapFactory.decodeFile(FileProvider.getUriForFile(getApplicationContext(),
//                    "br.com.lucas.prototipo.fileprovider", arquivoFoto).toString());
//            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
//            foto.setImageBitmap(bitmapReduzido);
        }
    }
}
