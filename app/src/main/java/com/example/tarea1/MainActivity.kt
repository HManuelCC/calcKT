package com.example.tarea1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tarea1.ui.theme.Tarea1Theme
import java.util.Arrays

class MainActivity : ComponentActivity() {

    var operador:String =""
    var numero1: Double = 0.0
    var numero2: Double = 0.0
    var resultado: Double = 0.0
    lateinit var etProceso: EditText
    var onDisplayChangue:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.constrain_prueba)

        val btnSuma: Button = findViewById(R.id.buttonSuma)
        val btnResta: Button = findViewById(R.id.buttonResta)
        val btnDiv: Button = findViewById(R.id.buttonDiv)
        val btnPor: Button = findViewById(R.id.buttonMult)
        val btnDot: Button = findViewById(R.id.buttonPunto)
        val btnpercent: Button = findViewById(R.id.buttonPercent)
        val btnAC: Button = findViewById(R.id.buttonAC)
        val btnMM: Button = findViewById(R.id.buttonSigno)
        val btnIgual: Button = findViewById(R.id.buttonIgual)
        val btnMod: Button = findViewById(R.id.buttonMod)

        this.etProceso=findViewById(R.id.textViewDisplay)

        var etConcatenar:EditText

        var listButtons:List<Button> = Arrays.asList<Button>(
            findViewById(R.id.button_0),
            findViewById(R.id.button_1),
            findViewById(R.id.button_2),
            findViewById(R.id.button_3),
            findViewById(R.id.button_4),
            findViewById(R.id.button_5),
            findViewById(R.id.button_6),
            findViewById(R.id.button_7),
            findViewById(R.id.button_8),
            findViewById(R.id.button_9)
        )



        btnAC.setOnClickListener{
            etProceso.setText("0")
            this.numero1=0.0
            this.numero2=0.0
            this.operador=""
            this.onDisplayChangue=true
        }

        onClicOperator("+",btnSuma)
        onClicOperator("-",btnResta)
        onClicOperator("/",btnDiv)
        onClicOperator("*",btnPor)
        onClicOperator("+/-",btnMM)
        onClicOperator("mod",btnMod)
        onClicOperator("%",btnpercent)


        listButtons.forEach{button->
            button.setOnClickListener{
                var buttonId = resources.getResourceName(button.id)
                var num = buttonId.split('_')[1]
                if(this.onDisplayChangue){
                    etProceso.setText(num)
                    this.onDisplayChangue=false

                }else{
                    etProceso.setText(etProceso.text.toString()+num)
                }

            }
        }

        btnDot.setOnClickListener{
            var displayText = this.etProceso.text.toString()
            if(!displayText.contains('.')){
                this.etProceso.setText(this.etProceso.text.toString()+".");
            }
        }

        btnIgual.setOnClickListener{
            mostrarresultado()
        }

    }

    fun onClicOperator(operator:String,btn:Button){
        btn.setOnClickListener {
            println(operator)
            if(this.operador!="" && !this.onDisplayChangue){
                this.numero2=this.etProceso.text.toString().toDouble()
                this.numero1=calcular(operator)
                if(operator!="%" && operator!="mod"){
                    this.operador=operator
                }

            }else{
                if(operator!="%" && operator!="mod"){
                    this.operador=operator
                }
                this.numero1 = this.etProceso.text.toString().toDouble();
                if(operator == "+/-"){
                    this.operador=""
                    this.numero1=calcular(operator)
                }

            }
            this.etProceso.setText(this.numero1.toString())
            println(this.operador)
            this.onDisplayChangue=true

        }
    }

    fun calcular(operator: String):Double{
        println(operator)
        return when(operator){
            "+" -> this.numero1 + this.numero2
            "-" -> this.numero1 - this.numero2
            "*" -> this.numero1 * this.numero2
            "/" -> {
                if (this.numero2 != 0.0) {
                    this.numero1 / this.numero2
                } else {
                    return 0.0
                }
            }
            "+/-" -> {
                if (this.numero1 != 0.0){
                    this.numero1 * (-1)
                }else{
                    this.numero1
                }
            }
            "mod"-> {
                if (this.numero2 != 0.0) {
                    this.numero2 = this.numero1%this.numero2
                    this.numero1 = calcular(this.operador)
                    return this.numero1
                } else {
                    return this.numero1
                }
            }
            "%"->{
                if(this.numero2 != 0.0){
                    this.numero2= (this.numero2*this.numero1)/100.0
                    this.numero1=calcular(this.operador)
                    return this.numero1
                }else{
                    return this.numero1
                }
            }
            else -> throw IllegalArgumentException("Operador no soportado")
        }
    }

    fun mostrarresultado(){
        if(this.operador!=""){
            this.numero2=this.etProceso.text.toString().toDouble()
            this.numero1=calcular(this.operador)
        }
        this.etProceso.setText(this.numero1.toString())
        this.onDisplayChangue=true
    }

}

