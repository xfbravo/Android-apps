package com.example.mycalculator
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var result: TextView
    private lateinit var num1: Button
    private lateinit var num2: Button
    private lateinit var num3: Button
    private lateinit var num4: Button
    private lateinit var num5: Button
    private lateinit var num6: Button
    private lateinit var num7: Button
    private lateinit var num8: Button
    private lateinit var num9: Button
    private lateinit var num0: Button
    private lateinit var clear: Button
    private lateinit var Lbrack: Button
    private lateinit var Rbrack: Button
    private lateinit var divide: Button
    private lateinit var multiply: Button
    private lateinit var plus: Button
    private lateinit var minus: Button
    private lateinit var equil: Button
    private lateinit var dot: Button
    private lateinit var delete: Button

    private var expression: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 根据当前屏幕方向设置布局
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape)
        } else {
            setContentView(R.layout.activity_main)
        }

        // 绑定视图
        bindViews()

        // 设置点击事件
        setClickListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Calculator)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 恢复保存的状态
        if (savedInstanceState != null) {
            expression = savedInstanceState.getString("expression", "")
            result.text = expression
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("expression", expression)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main)
        }
        // 重新绑定视图
        bindViews()
        // 重新设置点击事件
        setClickListeners()
        result.text = expression
    }

    private fun bindViews() {
        result = findViewById(R.id.result)
        num1 = findViewById(R.id.number1)
        num2 = findViewById(R.id.number2)
        num3 = findViewById(R.id.number3)
        num4 = findViewById(R.id.number4)
        num5 = findViewById(R.id.number5)
        num6 = findViewById(R.id.number6)
        num7 = findViewById(R.id.number7)
        num8 = findViewById(R.id.number8)
        num9 = findViewById(R.id.number9)
        num0 = findViewById(R.id.number0)
        clear = findViewById(R.id.clear)
        Lbrack = findViewById(R.id.Lbracket)
        Rbrack = findViewById(R.id.Rbracket)
        divide = findViewById(R.id.divide)
        multiply = findViewById(R.id.multiply)
        plus = findViewById(R.id.plus)
        minus = findViewById(R.id.minus)
        equil = findViewById(R.id.equil)
        dot = findViewById(R.id.dot)
        delete = findViewById(R.id.delete)
    }

    private fun setClickListeners() {
        num1.setOnClickListener { appendToExpression("1") }
        num2.setOnClickListener { appendToExpression("2") }
        num3.setOnClickListener { appendToExpression("3") }
        num4.setOnClickListener { appendToExpression("4") }
        num5.setOnClickListener { appendToExpression("5") }
        num6.setOnClickListener { appendToExpression("6") }
        num7.setOnClickListener { appendToExpression("7") }
        num8.setOnClickListener { appendToExpression("8") }
        num9.setOnClickListener { appendToExpression("9") }
        num0.setOnClickListener { appendToExpression("0") }
        Lbrack.setOnClickListener { appendToExpression("(") }
        Rbrack.setOnClickListener { appendToExpression(")") }
        divide.setOnClickListener { appendToExpression("÷") }
        multiply.setOnClickListener { appendToExpression("×") }
        plus.setOnClickListener { appendToExpression("+") }
        minus.setOnClickListener { appendToExpression("-") }
        dot.setOnClickListener { appendToExpression(".") }
        delete.setOnClickListener { deleteLastCharacter() }
        clear.setOnClickListener { clearExpression() }
        equil.setOnClickListener { calculateResult() }
    }

    private fun appendToExpression(value: String) {
        expression += value
        result.text = expression
    }

    private fun deleteLastCharacter() {
        if (expression.isNotEmpty()) {
            expression = expression.dropLast(1)
            result.text = expression
        }
    }

    private fun clearExpression() {
        expression = ""
        result.text = "0"
    }

    private fun calculateResult() {
        try {
            val finalResult = calculateExpression(expression).toString()
            expression = finalResult
            result.text = finalResult
        } catch (e: Exception) {
            result.text = "Error"
        }
    }

    // 以下是你原有的计算逻辑
    fun calculateExpression(expression: String): Double {
        val expr = expression.replace("\\s".toRegex(), "")
        val postfix = infixToPostfix(expr)
        return evaluatePostfix(postfix)
    }

    private fun infixToPostfix(expr: String): List<String> {
        val output = mutableListOf<String>()
        val operators = ArrayDeque<Char>()
        var i = 0
        while (i < expr.length) {
            val ch = expr[i]
            when {
                ch.isDigit() || ch == '.' -> {
                    val num = StringBuilder()
                    while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                        num.append(expr[i])
                        i++
                    }
                    output.add(num.toString())
                }
                ch == '(' -> {
                    operators.push(ch)
                    i++
                }
                ch == ')' -> {
                    while (operators.isNotEmpty() && operators.peek() != '(') {
                        output.add(operators.pop().toString())
                    }
                    operators.pop()
                    i++
                }
                else -> {
                    while (operators.isNotEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                        output.add(operators.pop().toString())
                    }
                    operators.push(ch)
                    i++
                }
            }
        }
        while (operators.isNotEmpty()) {
            output.add(operators.pop().toString())
        }
        return output
    }

    private fun evaluatePostfix(postfix: List<String>): Double {
        val stack = ArrayDeque<Double>()
        for (token in postfix) {
            when {
                token.matches("\\d+(\\.\\d+)?".toRegex()) -> stack.push(token.toDouble())
                else -> {
                    val num2 = stack.pop()
                    val num1 = stack.pop()
                    val result = when (token) {
                        "+" -> num1 + num2
                        "-" -> num1 - num2
                        "×" -> num1 * num2
                        "÷" -> num1 / num2
                        else -> throw IllegalArgumentException("Invalid operator: $token")
                    }
                    stack.push(result)
                }
            }
        }
        return stack.pop()
    }
    fun <T> ArrayDeque<T>.push(element: T) {
        this.addLast(element)
    }

    // 模拟栈的 pop 操作
    fun <T> ArrayDeque<T>.pop(): T {
        if (this.isEmpty()) {
            throw NoSuchElementException("Stack is empty")
        }
        return this.removeLast()
    }

    // 模拟栈的 peek 操作（查看栈顶元素但不弹出）
    fun <T> ArrayDeque<T>.peek(): T {
        if (this.isEmpty()) {
            throw NoSuchElementException("Stack is empty")
        }
        return this.last()
    }
    private fun precedence(op: Char): Int {
        return when (op) {
            '+', '-' -> 1
            '×', '÷' -> 2
            else -> 0
        }
    }
}