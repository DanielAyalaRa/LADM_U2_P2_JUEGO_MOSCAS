package mx.tecm.tepic.ladm_u2_p2_juegodemoscas

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import java.util.*

class Imagen (punteroLienzo: Lienzo, posX:Float, posY:Float, nombreImagen:Int) {

    var puntero = punteroLienzo
    var x = posX
    var y = posY
    var imagen = BitmapFactory.decodeResource(punteroLienzo.resources, nombreImagen)

    fun pintar(c: Canvas, p: Paint){
        c.drawBitmap(imagen, x, y, p)
    }// Pintar


    fun coordRandom(){
        var pantalla = Random()

        //Dimenciones de aparicion en pantalla
        this.x = pantalla.nextInt(900-20).toFloat()
        this.y = pantalla.nextInt(1200-20).toFloat()
    }// coordenadas Random de aparicion


    fun enPantalla(toqueX:Float, toqueY:Float) : Boolean{
        var x2 = x+imagen.width
        var y2 = y+imagen.height

        if (toqueX >= x && toqueX <= x2){
            if (toqueY >= y && toqueY <= y2){
                return true
            }// if-y
        }// if-x
        return false
    }// Esta en el area


    fun manchar(c: Canvas, p: Paint, x:Float, y:Float){
        imagen = BitmapFactory.decodeResource(puntero.resources, R.drawable.mancha)
        this.x = x
        this.y = y
        c.drawBitmap(imagen, x, y,p)
    }// Marca de mosta aplastada


}//class