package mx.tecm.tepic.ladm_u2_p2_juegodemoscas

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import java.util.*

class Lienzo(p:MainActivity) :View(p) {

    // Variables de control
    var juego = true
    var timerIniciado = false
    var banderaFinal = true
    var numMoscas = (80..100).random().toInt()
    var tiempo = 60
    var contMoscas = 0 // Contador de moscas
    var random = Random()
    var moscas = random.nextInt(1000)
    var aplastada = false

    var total = numMoscas
    val fondo = Imagen(this,0f,0f,R.drawable.fondo)

    //Timer del juego
    val timer = object : CountDownTimer(60000,1000){

        // Decremento del tiempo
        override fun onTick(millisUntilFinished: Long) {
            tiempo --
            repintar()
            invalidate()
        }

        //Fin del juego
        override fun onFinish() {
            juego = false
            banderaFinal = true

            if(contMoscas == total){
                AlertDialog.Builder(p)
                    .setTitle("Has GANADO")
                    .setMessage("Puntos: ${contMoscas}")
                    .setPositiveButton("Terminar"){d,i->
                        d.dismiss()
                    }
                    .show()
            }else{
                AlertDialog.Builder(p)
                    .setTitle("Lo siento has Perdido :(!!")
                    .setMessage("Puntos: ${contMoscas}")
                    .setPositiveButton("Terminar"){d,i->
                        d.dismiss()
                    }
                    .show()
            }
        }
    }

    //Mancha
    val listMosca = ArrayList<Imagen>()
    var mancha = Imagen(this, -1000f, -1000f, R.drawable.mancha)

    //pintar en el lienzo
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val p = Paint()

        (1..numMoscas).forEach {
            val mosca = Imagen(this, -1000f, -1000f, R.drawable.mosca)
            listMosca.add(mosca)
        }

        //pintar fondo
        c.drawColor(Color.WHITE)
        fondo.pintar(c, p)

        if (juego){
            //Estadisticas del juego
            p.textSize=80f
            p.setColor(Color.BLACK)
            c.drawText("Tiempo restante: ${tiempo} seg",100f,1550f,p)
            c.drawText("Moscas aplastadas: ${contMoscas}",100f,1650f,p)
            c.drawText("Total de moscas: ${total}",100f,1750f,p)

            // Pintar moscas, en total son 80 menos 1 contando que el arreglo inicia en 0
            (0..(numMoscas-1)).forEach {
                listMosca[it].pintar(c,p)
            }

            // Pintar mancha
            if(aplastada){
                mancha.pintar(c, p)
            }
        }
    }

    // Evento: Aplastar moscas
    override fun onTouchEvent(event: MotionEvent): Boolean {

        aplastada = false

        if (juego){
            if(event.action == MotionEvent.ACTION_DOWN){
                if (timerIniciado == false){
                    timer.start()
                    timerIniciado = true
                }

                if(!listMosca.isEmpty()) { // Validamos si no esta vacia
                    (0..(numMoscas-1)).forEach {
                        if (listMosca[it].enPantalla((event.x), event.y)) {
                            contMoscas = contMoscas + 1
                            moscas--
                            aplastada = true
                            mancha.manchar(Canvas(), Paint(), (event.x), event.y)
                            listMosca.removeAt(it)
                            numMoscas--
                            invalidate()
                        }
                    } // fin del bucle
                }
                if (numMoscas == 0) {
                    timer.cancel()
                    timer.onFinish()
                }
            }
        }
        return true
    }

    // Re-pintar moscas
    fun repintar(){
        (0..(numMoscas-1)).forEach {
            listMosca[it].coordRandom()
        }
    }
}