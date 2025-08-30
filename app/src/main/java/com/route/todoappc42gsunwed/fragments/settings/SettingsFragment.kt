package com.route.todoappc42gsunwed.fragments.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.route.todoappc42gsunwed.R
import com.route.todoappc42gsunwed.databinding.FragmentSettingsBinding
import java.util.Locale
import androidx.core.content.edit

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)


        val languages= arrayOf("English","Arabic")
        val modes= arrayOf("Light","Dark")
        val pref= requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val savedLanguage= pref.getString("lang","en")
        val savedMode= pref.getString("mode","Light")

        val languageAdapter = ArrayAdapter(requireContext(), R.layout.todo_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinnerView.adapter = languageAdapter
        binding.languageSpinnerView.setSelection(if (savedLanguage == "en") 0 else 1)

        binding.languageSpinnerView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val lang =
                if (position == 0)
                    "en"
                else
                    "ar"
                if (lang==Locale.getDefault().language) return

                pref.edit { putString("lang", lang) }

                val locale= Locale(lang)
                Locale.setDefault(locale)
                val resources = requireContext().resources
                val config = resources.configuration
                config.setLocale(locale)

                requireActivity().apply {
                    baseContext.resources.updateConfiguration(config, resources.displayMetrics)
                    recreate()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        var isModeSpinnerValid= false
        val modeAdapter = ArrayAdapter(requireContext(), R.layout.todo_spinner_item, modes)
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.modeSpinnerView.adapter=modeAdapter
        binding.modeSpinnerView.setSelection(if (savedMode=="Light") 0 else 1)

        binding.modeSpinnerView.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!isModeSpinnerValid) {
                    isModeSpinnerValid= true
                    return
                }

                when(position) {
                    0->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        pref.edit { putString("mode","Light") }
                    }
                    1->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        pref.edit { putString("mode","Dark") }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}