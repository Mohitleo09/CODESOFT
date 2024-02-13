package com.example.calculator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

private var Nothing?.text: String
    get() {
        TODO("Not yet implemented")
    }
    set() {}
private val Any.children: Any
    get() {
        TODO("Not yet implemented")
    }

abstract class MainActivity : AppCompatActivity()
{
    private abstract val tvResult: Any

    //binding
    private lateinit var binding : ActivityMainBinding

    //other
    private var firstnumber = ""
    private var currentNumber = ""
    private var currentOperator = ""
    private var result = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NoLimitScreen
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        //initViews
        binding.apply {
            // get all buttons
            binding.layoutMain.children.filterIsInstance<Button>().forEach { button ->
                //buttons click listener
                button.setOnClickListener {
                    //get clicked button text
                    val buttonText = button.text.toString()
                    when{
                        buttonText.matches(Regex("[0-9]"))->{
                            if(currentOperator.isEmpty())
                            {
                                firstnumber+=buttonText
                                tvResult.text = firstnumber
                            }else
                            {
                                currentNumber+=buttonText
                                tvResult.text = currentNumber
                            }
                        }
                        buttonText.matches(Regex("[+\\-*/]"))->{
                            currentNumber = ""
                            if (tvResult.text.toString().isNotEmpty())
                            {
                                currentOperator = buttonText
                                tvResult.text = "0"
                            }
                        }
                        buttonText == "="->{
                            if (currentNumber.isNotEmpty()&& currentOperator.isNotEmpty())
                            {
                                val tvFormula =null
                                tvFormula.text = "$firstnumber$currentOperator$currentNumber"
                                result = evaluateExpression(firstnumber,currentNumber,currentOperator)
                                firstnumber = result
                                val tvResult=null
                                tvResult.text = result
                            }
                        }
                        buttonText == "."->{
                            if(currentOperator.isEmpty())
                            {
                                if (! firstnumber.contains("."))
                                {
                                    if(firstnumber.isEmpty())firstnumber+="0$buttonText"
                                    else firstnumber +=buttonText
                                    val tvResult =null
                                    tvResult.text = firstnumber
                                }
                            }else
                            {
                                if (! currentNumber.contains("."))
                                {
                                    if(currentNumber.isEmpty()) currentNumber+="0$buttonText"
                                    else currentNumber +=buttonText
                                    val tvResult= null
                                    tvResult.text = currentNumber
                                }
                            }
                        }
                        buttonText == "C"->{
                            currentNumber = ""
                            firstnumber = ""
                            currentOperator = ""
                            val tvResult=null
                            tvResult.text = "0"
                            val tvFormula= null
                            tvFormula.text = ""
                        }
                    }
                }
            }


        }
    }

    //functions
    private fun evaluateExpression(firstNumber:String,secondNumber:String,operator:String):String
    {
        val num1  = firstNumber.toDouble()
        val num2  = secondNumber.toDouble()
        return when(operator)
        {
            "+"-> (num1+num2).toString()
            "-"-> (num1-num2).toString()
            "*"-> (num1*num2).toString()
            "/"-> (num1/num2).toString()
            else ->""
        }
    }
}

private fun Any.forEach(any: Any) {
    TODO("Not yet implemented")
}

private fun <T> Any.filterIsInstance(): Any {
    TODO("Not yet implemented")
}

class ActivityMainBinding {
    val layoutMain: Any=TODO()
    val root: Any
        get() {
            TODO()
        }

    companion object {
        fun inflate(layoutInflater: LayoutInflater): ActivityMainBinding {
            TODO("Not yet implemented")
        }
    }

}
