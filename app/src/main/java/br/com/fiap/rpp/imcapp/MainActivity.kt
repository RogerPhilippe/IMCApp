package br.com.fiap.rpp.imcapp

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.fiap.rpp.imcapp.extensions.format
import br.com.fiap.rpp.imcapp.extensions.value
import br.com.fiap.rpp.imcapp.watchers.DecimalTextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mFemale = false
    var mImc = 19.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initial process
        processImc(19.0)

        btnCalc.setOnClickListener { this.calculate() }

        editWeight.addTextChangedListener(DecimalTextWatcher(editWeight, 1))
        editHeight.addTextChangedListener(DecimalTextWatcher(editHeight))

        // Define gen
        swGen.setOnClickListener {
            it as Switch
            mFemale = it.isChecked
            if (it.isChecked) {
                genLabel.text = getString(R.string.male_label)
                processImc(mImc)
            }
            else {
                genLabel.text = getString(R.string.female_label)
                processImc(mImc)
            }
        }

    }

    private fun calculate() {

        val weight = editWeight.value()
        val height = editHeight.value()

        if (weight.isNotEmpty() || height.isNotEmpty()) {

            mImc = weight.toDouble() / (height.toDouble() * height.toDouble())
            processImc(mImc)
        }
    }

    private fun processImc(imc: Double) {

        when(imc) {
            in 0.0..18.5 -> configImc(imc, getDrawableID(0), R.string.abaixo_peso)
            in 18.6..24.9 -> configImc(imc, getDrawableID(1), R.string.peso_normal)
            in 25.0..29.9 -> configImc(imc, getDrawableID(2), R.string.sobrepeso)
            in 30.0..34.9 -> configImc(imc, getDrawableID(3), R.string.obesidade_grau_1)
            in 35.0..39.9 -> configImc(imc, getDrawableID(4), R.string.obesidade_grau_2)
            else -> configImc(imc, getDrawableID(5), R.string.obesidade_grau_3)
        }
    }

    private fun getDrawableID(index: Int): Int {

        return when(index) {
            0 -> if (!mFemale) { R.drawable.fem_abaixo } else { R.drawable.masc_abaixo }
            1 -> if (!mFemale) { R.drawable.fem_ideal } else { R.drawable.masc_ideal }
            2 -> if (!mFemale) { R.drawable.fem_sobre } else { R.drawable.masc_sobre }
            3 -> if (!mFemale) { R.drawable.fem_obeso } else { R.drawable.masc_obeso }
            else -> if (!mFemale) { R.drawable.fem_extremo_obeso } else { R.drawable.masc_extremo_obeso }
        }
    }

    private fun configImc(imc: Double, drawableId: Int, stringId: Int) {

        indexValue.text = getString(R.string.index_value, imc.format(2))
        indexImage.setImageDrawable(ContextCompat.getDrawable(this, drawableId))
        indexMsg.text = getString(stringId)
    }

}
