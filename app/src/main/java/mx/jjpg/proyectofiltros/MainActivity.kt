package mx.jjpg.proyectofiltros

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Spinner ---
        val adaptador1 = ArrayAdapter.createFromResource( this, R.array.basicos, android.R.layout.simple_spinner_item )
        val cmbOpciones1: Spinner = findViewById( R.id.lista1 )
        adaptador1.setDropDownViewResource( android.R.layout.simple_spinner_item )
        cmbOpciones1.adapter = adaptador1


        val adaptador2 = ArrayAdapter.createFromResource( this, R.array.convolucion, android.R.layout.simple_spinner_item )
        val cmbOpciones2: Spinner = findViewById( R.id.lista2 )
        adaptador2.setDropDownViewResource( android.R.layout.simple_spinner_item )
        cmbOpciones2.adapter = adaptador2


        // ------------------------- INICIO SPINNER -------------------------
        cmbOpciones1.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected( parent: AdapterView<*> ) {
                Toast.makeText(
                    applicationContext,
                    "Sin selección Efectos Basicos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override  fun onItemSelected(parent:AdapterView<*>, view: View?, position:Int, id:Long ){
                val pos=parent.getItemAtPosition( position )
                //Se convierte la imagen a Bitmap para poder trabajarla con los filtros
                var bmap: Bitmap = (imagenId.getDrawable() as BitmapDrawable).bitmap
                var cambio: Bitmap
                Toast.makeText(
                    applicationContext,
                    "Seleccionado: $pos",
                    Toast.LENGTH_SHORT
                ).show()
                //Seleccionar filtro
                if( position == 1 ) {
                    cambio = invert(bmap)
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 2 ) {
                    cambio = grayscale(bmap)
                    imagenId.setImageBitmap( cambio )
                }
                if( position == 3 ) {
                    cambio = applyBrightnessEffect(bmap, 25)
                    imagenId.setImageBitmap( cambio )
                }
                if( position == 4 ) {
                    cambio = contrast(bmap, 10.0)
                    imagenId.setImageBitmap( cambio )
                }
                if( position == 5 ) {
                    cambio = gamma(bmap, 15.0, 30.0, 45.0)
                    imagenId.setImageBitmap( cambio )
                }
                if( position == 6 ) { //Rojo
                    cambio = cfilter(bmap, 100.0, 0.0, 0.0)
                    imagenId.setImageBitmap( cambio )
                }
                if( position == 7 ) { //Verde
                    cambio = cfilter(bmap, 0.0, 100.0, 0.0)
                    imagenId.setImageBitmap( cambio )
                }
                if( position == 8 ) { //Azul
                    cambio = cfilter(bmap, 0.0, 0.0, 100.0)
                    imagenId.setImageBitmap( cambio )
                }
            }
        }

        cmbOpciones2.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected( parent: AdapterView<*> ) {
                Toast.makeText(
                    applicationContext,
                    "Sin selección Efectos Convolucion",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override  fun onItemSelected(parent:AdapterView<*>, view: View?, position:Int, id:Long ){
                val pos=parent.getItemAtPosition( position )
                //Se convierte la imagen a Bitmap para poder trabajarla con los filtros
                var bmap: Bitmap = (imagenId.getDrawable() as BitmapDrawable).bitmap
                var cambio: Bitmap
                Toast.makeText(
                    applicationContext,
                    "Seleccionado: $pos",
                    Toast.LENGTH_SHORT
                ).show()
                //Seleccionar filtro
                if( position == 1 ) {
                    cambio = smooth(bmap,150.0)
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 2 ) {
                    cambio = gaussian( bmap )
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 3 ) {
                    cambio = sharpen( bmap )
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 4 ) {
                    cambio = applyMeanRemoval( bmap )
                    imagenId.setImageBitmap(cambio)
                }
                if( position ==5 ) {
                    cambio = emboss( bmap )
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 6 ) {
                    //cambio = sketch( bmap)
                    //imagenId.setImageBitmap(cambio)
                }
                if( position == 7 ) {
                    cambio = noise( bmap )
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 8 ) {
                    cambio = saturation( bmap, 150 )
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 9 ) {
                    cambio = boost( bmap, 1,75.0f )
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 10 ) {
                    cambio = hue( bmap, 180.0f )
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 11 ) {
                    cambio = tint( bmap, 200 )
                    imagenId.setImageBitmap(cambio)
                }
                if( position == 12 ) {
                    cambio = sketch( bmap)
                    imagenId.setImageBitmap(cambio)
                }

            }
        }
        // --- Spinner ---

        // --- Imagen galeria ---
        btnCargarImg.setOnClickListener() {
            Toast.makeText(applicationContext, "Elige una imagen :)", Toast.LENGTH_SHORT).show()
            //check runtime permission
            if (VERSION.SDK_INT >= VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
        // --- Tomar foto ---
        dispatchTakePictureIntent()
        // --- Guardar foto ---
        /*
        btnGuardar.setOnClickListener() {
            Toast.makeText(applicationContext, "Guardando imagen...", Toast.LENGTH_SHORT).show()
            //check runtime permission

            //convertir imagen
            //imagenId.buildDrawingCache()
            //var bmap: Bitmap = imagenId.getDrawingCache()
            var bmap: Bitmap = (imagenId.getDrawable() as BitmapDrawable).bitmap

            //guardar imagen
            val savefile = Save()
            savefile.SaveImage(context, bmap)

        }
        
         */

    }

    // ------------------------- INICIO ELEGIR IMAGEN GALERIA -------------------------
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    // ------------------------- FIN ELEGIR IMAGEN GALERIA -------------------------
    // ------------------------- INICIO TOMAR FOTO -------------------------
    val REQUEST_IMAGE_CAPTURE = 1
    //Abre la aplicación de la camara y permite tomarnos una foto
    private fun dispatchTakePictureIntent() {
        btnTomarImg.setOnClickListener() {
            Toast.makeText(applicationContext, "Tomate una foto :)", Toast.LENGTH_SHORT).show()
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
    //Captura la foto tomada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // ------------------------- SIGUIENTE ACTIVIDAD -------------------------
        Toast.makeText(applicationContext, "Vamos a ello! :D", Toast.LENGTH_SHORT).show()

        super.onActivityResult(requestCode, resultCode, data)
        // --- Seleccionar imagen ---
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imagenId.setImageURI(data?.data)
        }

        // --- Tomar foto ---
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imagenId.setImageBitmap(imageBitmap)
        }


    }
    // ------------------------- FIN DE TOMAR FOTO -------------------------

    // ------------------------- INICIO FILTROS DE BASICOS ----------------------------
    // --- Inversión o Negativo ---
    open fun invert(src: Bitmap?): Bitmap {
        var src = src
        val output = Bitmap.createBitmap(src!!.width, src!!.height, src!!.config)
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixelColor: Int
        val height = src!!.height
        val width = src!!.width
        for (y in 0 until height) {
            for (x in 0 until width) {
                pixelColor = src!!.getPixel(x, y)
                A = Color.alpha(pixelColor)
                R = 255 - Color.red(pixelColor)
                G = 255 - Color.green(pixelColor)
                B = 255 - Color.blue(pixelColor)
                output.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        src!!.recycle()
        src = null
        return output
    }
    // --- Escala de Grises ---
    open fun grayscale(src: Bitmap?): Bitmap {
        var src = src
        //Array to generate Gray-Scale image
        var GrayArray= floatArrayOf(
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        )
        var colorMatrixGray: ColorMatrix =  ColorMatrix(GrayArray)

        var w: Int = src!!.getWidth()
        var h: Int = src!!.getHeight()

        val bitmapResult: Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        var canvasResult: Canvas = Canvas(bitmapResult)

        var paint: Paint = Paint()
        var filter: ColorMatrixColorFilter = ColorMatrixColorFilter(colorMatrixGray)
        paint.setColorFilter(filter)
        canvasResult.drawBitmap(src!!, 0.0f, 0.0f, paint)

        src!!.recycle()
        src = null
        return bitmapResult
    }
    // --- Brillo ---
    open fun applyBrightnessEffect(src: Bitmap, value: Int): Bitmap {
        // image size
        val width = src.width
        val height = src.height
        // create output bitmap
        val bmOut = Bitmap.createBitmap(width, height, src.config)
        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src.getPixel(x, y)
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)

                // increase/decrease each channel
                R += value
                if (R > 255) {
                    R = 255
                } else if (R < 0) {
                    R = 0
                }
                G += value
                if (G > 255) {
                    G = 255
                } else if (G < 0) {
                    G = 0
                }
                B += value
                if (B > 255) {
                    B = 255
                } else if (B < 0) {
                    B = 0
                }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }
    // --- Contraste ---
    // [-100, +100] -> Default = 0
    open fun contrast(src: Bitmap?, value: Double): Bitmap {
        // image size
        var src = src
        val width = src!!.width
        val height = src!!.height
        // create output bitmap

        // create a mutable empty bitmap
        val bmOut = Bitmap.createBitmap(width, height, src!!.config)

        // create a canvas so that we can draw the bmOut Bitmap from source bitmap
        val c = Canvas()
        c.setBitmap(bmOut)

        // draw bitmap to bmOut from src bitmap so we can modify it
        c.drawBitmap(src, 0.0f, 0.0f, Paint(Color.BLACK))


        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int
        // get contrast value
        val contrast = Math.pow((100 + value) / 100, 2.0)

        // scan through all pixels
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src!!.getPixel(x, y)
                A = Color.alpha(pixel)
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel)
                R = (((R / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (R < 0) {
                    R = 0
                } else if (R > 255) {
                    R = 255
                }
                G = Color.green(pixel)
                G = (((G / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (G < 0) {
                    G = 0
                } else if (G > 255) {
                    G = 255
                }
                B = Color.blue(pixel)
                B = (((B / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (B < 0) {
                    B = 0
                } else if (B > 255) {
                    B = 255
                }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        src!!.recycle()
        src = null
        return bmOut
    }
    // --- Gamma ---
    // red, green, blue [0, 48]
    open fun gamma(src: Bitmap?, red: Double, green: Double, blue: Double): Bitmap {
        var src = src
        var red = red
        var green = green
        var blue = blue
        red = (red + 2) / 10.0
        green = (green + 2) / 10.0
        blue = (blue + 2) / 10.0
        // create output image
        val bmOut = Bitmap.createBitmap(src!!.width, src!!.height, src!!.config)
        // get image size
        val width = src!!.width
        val height = src!!.height
        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int
        // constant value curve
        val MAX_SIZE = 256
        val MAX_VALUE_DBL = 255.0
        val MAX_VALUE_INT = 255
        val REVERSE = 1.0

        // gamma arrays
        val gammaR = IntArray(MAX_SIZE)
        val gammaG = IntArray(MAX_SIZE)
        val gammaB = IntArray(MAX_SIZE)

        // setting values for every gamma channels
        for (i in 0 until MAX_SIZE) {
            gammaR[i] = Math.min(
                MAX_VALUE_INT,
                (MAX_VALUE_DBL * Math.pow(
                    i / MAX_VALUE_DBL,
                    REVERSE / red
                ) + 0.5).toInt()
            )
            gammaG[i] = Math.min(
                MAX_VALUE_INT,
                (MAX_VALUE_DBL * Math.pow(
                    i / MAX_VALUE_DBL,
                    REVERSE / green
                ) + 0.5).toInt()
            )
            gammaB[i] = Math.min(
                MAX_VALUE_INT,
                (MAX_VALUE_DBL * Math.pow(
                    i / MAX_VALUE_DBL,
                    REVERSE / blue
                ) + 0.5).toInt()
            )
        }

        // apply gamma table
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src!!.getPixel(x, y)
                A = Color.alpha(pixel)
                // look up gamma
                R = gammaR[Color.red(pixel)]
                G = gammaG[Color.green(pixel)]
                B = gammaB[Color.blue(pixel)]
                // set new color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        src!!.recycle()
        src = null

        // return final image
        return bmOut
    }
    // --- Separación de Canales (Rojo, Verde y Azul) ---
    // [0, 150], default => 100
    open fun cfilter( src: Bitmap?, red: Double, green: Double, blue: Double): Bitmap {
        var src = src
        var red = red
        var green = green
        var blue = blue
        red = red / 100
        green = green / 100
        blue = blue / 100

        // image size
        val width = src!!.width
        val height = src!!.height
        // create output bitmap
        val bmOut = Bitmap.createBitmap(width, height, src!!.config)
        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // scan through all pixels
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src!!.getPixel(x, y)
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel)
                R = (Color.red(pixel) * red).toInt()
                G = (Color.green(pixel) * green).toInt()
                B = (Color.blue(pixel) * blue).toInt()
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        src!!.recycle()
        src = null

        // return final image
        return bmOut
    }
    // ------------------------- FIN FILTROS DE BASICOS ----------------------------

    // ------------------------- INICIO FILTROS DE CONVENCION ----------------------
    // --- Smoothing ---
    open fun smooth(src: Bitmap?, value: Double): Bitmap {
        var src = src
        var convMatrix: ConvolutionMatrix = ConvolutionMatrix(3)
        convMatrix.setAll(1.0)
        convMatrix.Matrix[1][1] = value
        convMatrix.Factor = value + 8
        convMatrix.Offset = 1.0
        return ConvolutionMatrix.computeConvolution3x3(src!!, convMatrix)
    }
    // --- Gaussian Blur ---
    open fun gaussian(src: Bitmap?): Bitmap {
        val GaussianBlurConfig = arrayOf(
            doubleArrayOf(1.0, 2.0, 1.0),
            doubleArrayOf(2.0, 4.0, 2.0),
            doubleArrayOf(1.0, 2.0, 1.0)
        )
        val convMatrix = ConvolutionMatrix(3)
        convMatrix.applyConfig(GaussianBlurConfig)
        convMatrix.Factor = 16.0
        convMatrix.Offset = 0.0
        return ConvolutionMatrix.computeConvolution3x3(src, convMatrix)
    }
    // --- Sharpen ---
    open fun sharpen(src: Bitmap?): Bitmap {
        val SharpConfig = arrayOf(
            doubleArrayOf(0.0, -2.0, 0.0),
            doubleArrayOf(-2.0, 11.0, -2.0),
            doubleArrayOf(0.0, -2.0, 0.0)
        )
        val convMatrix = ConvolutionMatrix(3)
        convMatrix.applyConfig(SharpConfig)
        convMatrix.Factor = 3.0
        return ConvolutionMatrix.computeConvolution3x3(src, convMatrix)
    }
    // --- Mean Removal ---
    open fun applyMeanRemoval(src: Bitmap?): Bitmap {
        val MeanRemovalConfig = arrayOf(
            doubleArrayOf(-1.0, -1.0, -1.0),
            doubleArrayOf(-1.0, 9.0, -1.0),
            doubleArrayOf(-1.0, -1.0, -1.0)
        )
        val convMatrix = ConvolutionMatrix(3)
        convMatrix.applyConfig(MeanRemovalConfig)
        convMatrix.Factor = 1.0
        convMatrix.Offset = 0.0
        return ConvolutionMatrix.computeConvolution3x3(src, convMatrix)
    }
    // --- Embossing ---
    open fun emboss(src: Bitmap?): Bitmap {
        val EmbossConfig = arrayOf(
            doubleArrayOf(-1.0, 0.0, -1.0),
            doubleArrayOf(0.0, 4.0, 0.0),
            doubleArrayOf(-1.0, 0.0, -1.0)
        )
        val convMatrix = ConvolutionMatrix(3)
        convMatrix.applyConfig(EmbossConfig)
        convMatrix.Factor = 1.0
        convMatrix.Offset = 127.0
        return ConvolutionMatrix.computeConvolution3x3(src, convMatrix)
    }
    // --- Edge Detection ---
    fun sketch(src: Bitmap?): Bitmap {
        var src = src
        val type = 6
        val threshold = 130
        val width = src!!.width
        val height = src!!.height
        val result = Bitmap.createBitmap(width, height, src!!.config)
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var sumR: Int
        var sumG: Int
        var sumB: Int
        val pixels = Array(3) { IntArray(3) }
        for (y in 0 until height - 2) {
            for (x in 0 until width - 2) {
                //      get pixel matrix
                for (i in 0..2) {
                    for (j in 0..2) {
                        pixels[i][j] = src!!.getPixel(x + i, y + j)
                    }
                }
                // get alpha of center pixel
                A = Color.alpha(pixels[1][1])
                // init color sum
                sumB = 0
                sumG = sumB
                sumR = sumG
                sumR = type * Color.red(pixels[1][1]) - Color.red(pixels[0][0]) - Color.red(pixels[0][2]) - Color.red(pixels[2][0]) - Color.red(pixels[2][2])
                sumG = type * Color.green(pixels[1][1]) - Color.green(pixels[0][0]) - Color.green(pixels[0][2]) - Color.green(pixels[2][0]) - Color.green(pixels[2][2])
                sumB = type * Color.blue(pixels[1][1]) - Color.blue(pixels[0][0]) - Color.blue(pixels[0][2]) - Color.blue(pixels[2][0]) - Color.blue(pixels[2][2])
                // get final Red
                R = (sumR + threshold)
                if (R < 0) {
                    R = 0
                } else if (R > 255) {
                    R = 255
                }
                // get final Green
                G = (sumG + threshold)
                if (G < 0) {
                    G = 0
                } else if (G > 255) {
                    G = 255
                }
                // get final Blue
                B = (sumB + threshold)
                if (B < 0) {
                    B = 0
                } else if (B > 255) {
                    B = 255
                }
                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B))
            }
        }
        src!!.recycle()
        src = null
        return result
    }

    // ------------------------- FIN FILTROS DE CONVENCION -------------------------

    // ------------------------- INICIO FILTROS EXTRA -------------------------
    // --- Noise ---
    open fun noise(source: Bitmap?): Bitmap {
        var source = source
        val COLOR_MAX = 0xFF

        // get image size
        val width = source!!.width
        val height = source!!.height
        val pixels = IntArray(width * height)
        // get pixel array from source
        source!!.getPixels(pixels, 0, width, 0, 0, width, height)
        // a random object
        val random = Random()
        var index = 0
        // iteration through pixels
        for (y in 0 until height) {
            for (x in 0 until width) {
                // get current index in 2D-matrix
                index = y * width + x
                // get random color
                val randColor: Int = Color.rgb(
                    random.nextInt(COLOR_MAX),
                    random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX)
                )
                // OR
                pixels[index] = pixels[index] or randColor
            }
        }
        // output bitmap
        val bmOut = Bitmap.createBitmap(width, height, source!!.config)
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height)
        source!!.recycle()
        source = null
        return bmOut
    }
    // --- Saturation ---
    // [0, 200] -> Default = 100
    open fun saturation(src: Bitmap?, value: Int): Bitmap {
        var src = src
        val f_value = (value / 100.0).toFloat()
        val w = src!!.width
        val h = src!!.height
        val bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvasResult = Canvas(bitmapResult)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(f_value)
        val filter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = filter
        canvasResult.drawBitmap(src, 0.0f, 0.0f, paint)
        src!!.recycle()
        src = null
        return bitmapResult
    }
    // --- Boost ---
    // percent = [0, 150], type = (1, 2, 3) => (R, G, B)
    open fun boost(src: Bitmap?, type: Int, percent: Float): Bitmap {
        var src = src
        var percent = percent
        percent = percent / 100
        val width = src!!.width
        val height = src!!.height
        val bmOut = Bitmap.createBitmap(width, height, src!!.config)
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int
        for (x in 0 until width) {
            for (y in 0 until height) {
                pixel = src!!.getPixel(x, y)
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)
                if (type == 1) {
                    R = (R * (1 + percent)).toInt()
                    if (R > 255) R = 255
                } else if (type == 2) {
                    G = (G * (1 + percent)).toInt()
                    if (G > 255) G = 255
                } else if (type == 3) {
                    B = (B * (1 + percent)).toInt()
                    if (B > 255) B = 255
                }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        src!!.recycle()
        src = null
        return bmOut
    }
    // --- Hue ---
    // hue = [0, 360] -> Default = 0
    open fun hue(bitmap: Bitmap?, hue: Float): Bitmap {
        var bitmap = bitmap
        val newBitmap = bitmap!!.copy(bitmap.config, true)
        val width = newBitmap.width
        val height = newBitmap.height
        val hsv = FloatArray(3)
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = newBitmap.getPixel(x, y)
                Color.colorToHSV(pixel, hsv)
                hsv[0] = hue
                newBitmap.setPixel(x, y, Color.HSVToColor(Color.alpha(pixel), hsv))
            }
        }
        bitmap.recycle()
        bitmap = null
        return newBitmap
    }
    // --- Tint ---
    fun tint(src: Bitmap?, color: Int): Bitmap {
        // image size
        var src = src
        val width = src!!.width
        val height = src.height
        // create output bitmap

        // create a mutable empty bitmap
        val bmOut = Bitmap.createBitmap(width, height, src.config)
        val p = Paint(Color.RED)
        val filter: ColorFilter = LightingColorFilter(color, 1)
        p.colorFilter = filter
        val c = Canvas()
        c.setBitmap(bmOut)
        c.drawBitmap(src, 0.0f, 0.0f, p)
        src.recycle()
        src = null
        return bmOut
    }
    // FIN FILTROS EXTRA


}

