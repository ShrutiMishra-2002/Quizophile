package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_question.*


class QuestionActivity : AppCompatActivity() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var Name: String? = null
    private var score: Int = 0

    private var currentPosition: Int = 1
    private var questionList: ArrayList<QuestionData>? = null
    private var selecedOption: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        setUpNavDrawer()

        Name = intent.getStringExtra(QuestionDummyData.name)

        questionList = QuestionDummyData.getQuestion()

        setQuestion()

        opt_1.setOnClickListener {

            selectedOptionStyle(opt_1, 1)
        }
        opt_2.setOnClickListener {

            selectedOptionStyle(opt_2, 2)
        }
        opt_3.setOnClickListener {

            selectedOptionStyle(opt_3, 3)
        }
        opt_4.setOnClickListener {

            selectedOptionStyle(opt_4, 4)
        }

        submit.setOnClickListener {
            if (selecedOption != 0) {
                val question = questionList!![currentPosition - 1]
                if (selecedOption != question.correct_ans) {
                    setColor(selecedOption, R.drawable.wrong_question_option)
                } else {
                    score++;
                }
                setColor(question.correct_ans, R.drawable.correct_question_option)
                if (currentPosition == questionList!!.size)
                    submit.text = "FINISH"
                else
                    submit.text = "Go to Next"
            } else {
                currentPosition++
                when {
                    currentPosition <= questionList!!.size -> {
                        setQuestion()
                    }
                    else -> {
                        var intent = Intent(this, Result::class.java)
                        intent.putExtra(QuestionDummyData.name, Name.toString())
                        intent.putExtra(QuestionDummyData.score, score.toString())
                        intent.putExtra("total size", questionList!!.size.toString())

                        startActivity(intent)
                        finish()
                    }
                }
            }
            selecedOption = 0
        }
    }

    private fun setUpNavDrawer() {
        setSupportActionBar(topAppBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        // Maintain the State of your Click Events on HamBurger Icon
        actionBarDrawerToggle.syncState()

        // This Handles the Navigation menu click Events
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.btnGithub -> {
                    var aboutAppIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/ShrutiMishra-2002")
                    )
                    startActivity(aboutAppIntent)
                }

                R.id.btnLinkedIn -> {
                    var aboutAppIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.linkedin.com/in/shruti-mishra-b270a7203")
                    )
                    startActivity(aboutAppIntent)
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setColor(opt: Int, color: Int) {
        when (opt) {
            1 -> {
                opt_1.background = ContextCompat.getDrawable(this, color)
            }
            2 -> {
                opt_2.background = ContextCompat.getDrawable(this, color)
            }
            3 -> {
                opt_3.background = ContextCompat.getDrawable(this, color)
            }
            4 -> {
                opt_4.background = ContextCompat.getDrawable(this, color)
            }
        }
    }


    fun setQuestion() {

        val question = questionList!![currentPosition - 1]
        setOptionStyle()


        progress_bar.progress = currentPosition
        progress_bar.max = questionList!!.size
        progress_text.text = "${currentPosition}" + "/" + "${questionList!!.size}"
        question_text.text = question.question
        opt_1.text = question.option_one
        opt_2.text = question.option_tw0
        opt_3.text = question.option_three
        opt_4.text = question.option_four

    }

    fun setOptionStyle() {
        var optionList: ArrayList<TextView> = arrayListOf()
        optionList.add(0, opt_1)
        optionList.add(1, opt_2)
        optionList.add(2, opt_3)
        optionList.add(3, opt_4)

        for (op in optionList) {
            op.setTextColor(Color.parseColor("#555151"))
            op.background = ContextCompat.getDrawable(this, R.drawable.question_option)
            op.typeface = Typeface.DEFAULT
        }
    }

    fun selectedOptionStyle(view: TextView, opt: Int) {

        setOptionStyle()
        selecedOption = opt
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_question_option)
        view.typeface = Typeface.DEFAULT_BOLD
        view.setTextColor(Color.parseColor("#000000"))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}